package com.frankmoley.business.order.domain;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

/**
 * @author Frank Moley
 */
public class OrderResource extends Resource<Order> {

    private static final String COLL_URL = "http://localhost:8083/orders";

    public OrderResource(){
        super(new Order());
    }

    public void handleId(){
        Link link = this.getLink("self");
        String href = link.getHref();
        this.getContent().setId(href.replace(COLL_URL,"").replace("/",""));

    }
}
