package org.stream.entity;

import java.util.Objects;

/**
 * @Author: helltractor
 * @Date: 2024/5/12 下午3:23
 */

public class Person {
    int age;
    String name;
    String country;

    public Person(int age, String name, String country) {
        this.age = age;
        this.name = name;
        this.country = country;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(name, person.name) && Objects.equals(country, person.country);
    }

    @Override
    public int hashCode() {
        int result = age;
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(country);
        return result;
    }
}
