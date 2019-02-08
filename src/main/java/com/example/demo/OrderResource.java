package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

/*
    This class is created to ensure a level of indirection between data and presentation
    layer. Additionally, OrderResource variables are final but linked to Order variables.
    One to one relationship between OrderResource and Order.
 */

public class OrderResource extends ResourceSupport {
    private final long id;
    private final String description;
    private final long costInCents;
    private final boolean isComplete;
    public OrderResource(Order order) {
        id = order.getId();
        description = order.getDescription();
        costInCents = order.getCostInCents();
        isComplete = order.isComplete();
    }

    // Json Property is used to attach the key value "id" to result of getResourceId
    // getId can not be used as it is implemented in ResourceSupport class
    @JsonProperty("id")
    public Long getResourceId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public long getCostInCents() {
        return costInCents;
    }
    public boolean isComplete() {
        return isComplete;
    }
}
