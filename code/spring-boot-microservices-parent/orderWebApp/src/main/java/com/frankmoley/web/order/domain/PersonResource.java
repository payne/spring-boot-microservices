package com.frankmoley.web.order.domain;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;

/**
 * @author Frank Moley
 */
public class PersonResource extends Resource<Person> {

    private static final String COLL_URL = "http://localhost:8081/persons";

    public PersonResource(){
        super(new Person());
    }

    public PersonResource(Person content, Link... links) {
        super(content, links);
    }

    public PersonResource(Person content, Iterable<Link> links) {
        super(content, links);
    }

    public void handleId(){
        Link link = this.getLink("self");
        String href = link.getHref();
        this.getContent().setId(href.replace(COLL_URL,"").replace("/",""));

    }
}
