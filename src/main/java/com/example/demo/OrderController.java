package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
// The following denotes this is a controller for REST endpoints
@RestController
// The following denotes that if a link is needed to an Order object this controller should be used
@ExposesResourceFor(Order.class)
// http://localhost:8080/order will reach this controller
@RequestMapping(value = "/order", produces = "application/json")
public class OrderController {
    @Autowired
    private OrderRepository repository;
    @Autowired
    private OrderResourceAssembler assembler;

    /*
        ResponseEntity class is converted to JSONString on web side and allows status codes
        to be passed along response object.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<OrderResource>> findAllOrders() {
        List<Order> orders = repository.findAll();
        return new ResponseEntity<>(assembler.toResourceCollection(orders), HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<OrderResource> createOrder(@RequestBody Order order) {
        Order createdOrder = repository.create(order);
        return new ResponseEntity<>(assembler.toResource(createdOrder), HttpStatus.CREATED);
    }

    // {id} denotes a variable passed through the link of name id
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<OrderResource> findOrderById(@PathVariable Long id) {
        Optional<Order> order = repository.findById(id);
        if (order.isPresent()) {
            return new ResponseEntity<>(assembler.toResource(order.get()), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        boolean wasDeleted = repository.delete(id);
        HttpStatus responseStatus = wasDeleted ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(responseStatus);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<OrderResource> updateOrder(@PathVariable Long id, @RequestBody Order updatedOrder) {
        boolean wasUpdated = repository.update(id, updatedOrder);
        if (wasUpdated) {
            return findOrderById(id);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}