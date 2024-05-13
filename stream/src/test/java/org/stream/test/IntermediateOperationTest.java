package org.stream.test;

import org.junit.jupiter.api.Test;
import org.stream.entity.Person;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Author: helltractor
 * @Date: 2024/5/12 下午3:13
 */

public class IntermediateOperationTest {

    private final Person[] people = {
            new Person(3, "c", "China"),
            new Person(4, "d", "USA"),
            new Person(5, "e", "China"),
            new Person(4, "d", "USA"),
            new Person(5, "e", "China"),
    };

    private final List<List<Person>> peopleGroups  = List.of(
            List.of(new Person(3, "c", "China"), new Person(4, "d", "USA")),
            List.of(new Person(5, "e", "China"), new Person(4, "d", "USA")),
            List.of(new Person(5, "e", "China"), new Person(4, "d", "USA"))
    );

    /**
     * Stream filter
     */
    @Test
    public void streamFilter() {
        Arrays.stream(people)
                .filter(person -> person.getAge() > 3)
                .forEach(System.out::println);
    }

    /**
     * Stream limit
     */
    @Test
    public void streamLimit() {
        Arrays.stream(people)
                .limit(2)
                .forEach(System.out::println);
        Stream.of("a", "b", "c")
                .limit(6)
                .forEach(System.out::println);
    }

    /**
     * Stream skip
     */
    @Test
    public void streamSkip() {
        Arrays.stream(people)
                .skip(2)
                .forEach(System.out::println);
        Arrays.stream(people)
                .skip(6)
                .forEach(System.out::println);
    }

    /**
     * Stream distinct
     */
    @Test
    public void streamDistinct() {
        Arrays.stream(people)
                .distinct()
                .forEach(System.out::println);
    }

    /**
     * Stream map
     */
    @Test
    public void streamMap() {
        Arrays.stream(people)
                .map(Person::getName)
                .forEach(System.out::println);
    }

    /**
     * Stream mapToInt
     */
    @Test
    public void streamMapToInt() {
        IntStream intStream = Arrays.stream(people)
                .mapToInt(Person::getAge);
        intStream.forEach(System.out::println);

    }

    /**
     * Stream flatMap
     */
    @Test
    public void streamFlatMap() {
//        Arrays.stream(people)
//                .flatMap(person -> Stream.of(person.getName()))
//                .forEach(System.out::println);
        peopleGroups.stream()
                .flatMap(List::stream)
                .map(Person::getName)
                .forEach(System.out::println);
    }

    /**
     * Stream sorted
     */
    @Test
    public void streamSorted() {
        Arrays.stream(people)
                .sorted((p1, p2) -> p2.getAge() - p1.getAge())
                .forEach(System.out::println);
        Arrays.stream(people)
                .sorted(Comparator.comparingInt(Person::getAge).reversed())
                .forEach(System.out::println);
    }
}
