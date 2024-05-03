package org.reflection.entity;

import org.reflection.annotation.Printable;

/**
 * @Author: helltractor
 * @Date: 2024/5/3 17:54
 */

public class Address {
    private String street;
    private String postCode;

    public Address() {
    }

    public Address(String street, String postCode) {
        this.street = street;
        this.postCode = postCode;
    }

    public String getStreet() {
        return street;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Printable
    public void printStreet() {
        System.out.println("Address street: " + street);
    }

    @Printable
    public void printPostCode() {
        System.out.println("Address postCode: " + postCode);
    }
}
