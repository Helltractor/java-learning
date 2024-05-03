package org.reflection.entity;

import org.reflection.annotation.Printable;

/**
 * @Author: helltractor
 * @Date: 2024/5/3 17:55
 */

public class Customer {
    private String name;
    private String email;

    public Customer() {
    }

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Printable
    public void printName() {
        System.out.println("Customer name: " + name);
    }

    @Printable
    public void printEmail() {
        System.out.println("Customer email: " + email);
    }
}
