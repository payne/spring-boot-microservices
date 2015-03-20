package com.frankmoley.web.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.frankmoley.web.order.domain.Order;
import com.frankmoley.web.order.domain.OrderLine;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frankmoley.web.order.domain.Person;
import com.frankmoley.web.order.domain.PersonResource;

/**
 * @author Frank Moley
 */
@Controller
public class WebAppController {

    private static final String PERSONS_URL = "http://localhost:8081/persons";
    private static final String ORDERS_URL = "http://localhost:8083/orders";

    @RequestMapping("/home")
    public String getHomePage(Model model){
        return "index";
    }

    @RequestMapping("/customers")
    public String getCustomersPage(Model model){

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
        converter.setObjectMapper(mapper);

        RestTemplate restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));

        PagedResources pagedResources = restTemplate.getForObject(PERSONS_URL, PagedResources.class);
        List<Person> persons = new ArrayList<>();
        Iterator<Map<String, Object>> iterator = pagedResources.getContent().iterator();
        while(iterator.hasNext()){
            Map<String, Object> personMap = iterator.next();
            Person person = new Person();
            person.setFirstName((String)personMap.get("firstName"));
            person.setLastName((String)personMap.get("lastName"));
            person.setEmailAddress((String)personMap.get("emailAddress"));
            person.setAddress((String)personMap.get("address"));
            person.setId(this.getIdFromSelfLink((Map<String, Map>)personMap.get("_links"), PERSONS_URL));

            persons.add(person);
        }


        model.addAttribute("persons", persons);

        return "customers";
    }

    @RequestMapping("/customers/{id}")
    public String getCustomerPage(@PathVariable("id")String id, Model model){

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
        converter.setObjectMapper(mapper);

        String url = PERSONS_URL+"/"+id;
        RestTemplate restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
        PersonResource resource = restTemplate.getForObject(url,PersonResource.class);
        resource.handleId();
        model.addAttribute("person", resource.getContent());


        return "customer";
    }

    @RequestMapping("/orders")
    public String getOrders(Model model){

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
        converter.setObjectMapper(mapper);

        RestTemplate restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));

        PagedResources pagedResources = restTemplate.getForObject(ORDERS_URL, PagedResources.class);
        List<Order> orders = new ArrayList<>();
        Iterator<Map<String, Object>> iterator = pagedResources.getContent().iterator();
        while(iterator.hasNext()){
            Map<String, Object> orderMap = iterator.next();
            Order order = new Order();
            order.setCustomerId((String) orderMap.get("customerId"));
            List<Map<String, Object>> orderLines = (List<Map<String, Object>>) orderMap.get("orderLines");
            List<OrderLine> finalOrderLines = new ArrayList<>();
            for(Map<String, Object> orderLineInnerMap:orderLines){
                OrderLine orderLine = new OrderLine();
                orderLine.setProductId((String)orderLineInnerMap.get("productId"));
                Integer quantityInteger = (Integer) orderLineInnerMap.get("quantity");
                orderLine.setQuantity(quantityInteger);
                finalOrderLines.add(orderLine);
            }
            order.setOrderLines(finalOrderLines);



            order.setId(this.getIdFromSelfLink((Map<String, Map>) orderMap.get("_links"), ORDERS_URL));

            orders.add(order);
        }


        model.addAttribute("orders", orders);

        return "orders";
    }


    private String getIdFromSelfLink(Map<String, Map> linksMap, String urlBase){
        Map<String, String> selfMap = linksMap.get("self");
        String link = selfMap.get("href");
        String id = link.replace(urlBase,"").replace("/","");
        return id;
    }
}
