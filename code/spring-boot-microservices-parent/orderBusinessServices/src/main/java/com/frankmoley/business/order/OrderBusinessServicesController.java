package com.frankmoley.business.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.frankmoley.business.order.domain.DetailedOrder;
import com.frankmoley.business.order.domain.DetailedOrderLine;
import com.frankmoley.business.order.domain.Order;
import com.frankmoley.business.order.domain.OrderLine;
import com.frankmoley.business.order.domain.OrderResource;
import com.frankmoley.business.order.domain.PersonResource;
import com.frankmoley.business.order.domain.Product;
import com.frankmoley.business.order.domain.ProductResource;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frankmoley.business.order.domain.Person;

/**
 * @author Frank Moley
 */
@RestController
@RequestMapping("/")
public class OrderBusinessServicesController {

    private static final String ORDER_URL = "http://localhost:8083/orders";
    private static final String PERSON_URL = "http://localhost:8081/persons";
    private static final String PRODUCT_URL = "http://localhost:8082/products";

    @RequestMapping(value="/orders/{id}", method=RequestMethod.GET)
    public DetailedOrder getDetailedOrder(@PathVariable("id") String id){
        DetailedOrder detailedOrder = new DetailedOrder();
        Order order = this.getOrder(id);
        if(null == order){
            return null;
        }
        Person person = this.getPerson(order.getCustomerId());
        List<DetailedOrderLine> orderLines = new ArrayList<>();
        for(OrderLine orderLine:order.getOrderLines()){
            Product product = this.getProduct(orderLine.getProductId());
            orderLines.add(new DetailedOrderLine(product, orderLine.getQuantity()));
        }
        return new DetailedOrder(order, person, orderLines);
    }

    private Person getPerson(String id){

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
        converter.setObjectMapper(mapper);

        String url = PERSON_URL+"/"+id;
        RestTemplate restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
        PersonResource resource = restTemplate.getForObject(url,PersonResource.class);
        resource.handleId();
        return resource.getContent();
    }

    private Order getOrder(String id){

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
        converter.setObjectMapper(mapper);

        String url = ORDER_URL+"/"+id;
        RestTemplate restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
        OrderResource resource = restTemplate.getForObject(url,OrderResource.class);
        resource.handleId();
        return resource.getContent();
    }

    private Product getProduct(String id){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
        converter.setObjectMapper(mapper);

        String url = PRODUCT_URL+"/"+id;
        RestTemplate restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
        ProductResource resource = restTemplate.getForObject(url,ProductResource.class);
        resource.handleId();
        return resource.getContent();
    }

}
