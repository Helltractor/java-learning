package org.reflection.entity;

/**
 * @Author: helltractor
 * @Date: 2024/5/3 17:54
 */

public class Message {
    private String message;

    public Message() {
    }

    public Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void printMessage() {
        System.out.println("Message: " + message);
    }
}
