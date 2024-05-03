package org.reflection.entity;

import org.reflection.annotation.Autowired;

/**
 * @Author: helltractor
 * @Date: 2024/5/3 17:54
 */

public class Order {
    private Customer customer;
    private Address address;
    
    public Order() {
    }

    @Autowired
    public Order(Customer customer, Address address) {
        this.customer = customer;
        this.address = address;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Address getAddress() {
        return address;
    }
}
