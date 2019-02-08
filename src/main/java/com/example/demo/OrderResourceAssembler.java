package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

@Component
public class OrderResourceAssembler extends ResourceAssembler<Order, OrderResource> {
    @Autowired
    protected EntityLinks entityLinks;
    private static final String UPDATE_REL = "update";
    private static final String DELETE_REL = "delete";

    @Override
    public OrderResource toResource(Order order) {
        OrderResource resource = new OrderResource(order);
        final Link selfLink = entityLinks.linkToSingleResource(order);
        resource.add(selfLink.withSelfRel());

        //Used when this resource should be updated
        resource.add(selfLink.withRel(UPDATE_REL));

        //Used when this resource should be deleted
        resource.add(selfLink.withRel(DELETE_REL));
        return resource;
    }
}