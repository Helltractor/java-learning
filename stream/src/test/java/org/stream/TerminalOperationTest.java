package org.stream;

import org.junit.jupiter.api.Test;
import org.stream.entity.Person;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: helltractor
 * @Date: 2024/5/12 下午3:14
 */

/**
 * Search and match: Short-circuiting operations
 * 短回路操作， 命中目标结束操作
 * allMatch, anyMatch, noneMatch
 * findFirst, findAny
 */
class SearchAndMatch {

    private final List<Person> people = List.of(
            new Person(3, "c", "China"),
            new Person(4, "d", "USA"),
            new Person(5, "e", "China"),
            new Person(4, "d", "USA"),
            new Person(5, "e", "China")
    );

    /**
     * Stream allMatch
     */
    @Test
    public void allMatch() {
        boolean result = people.stream()
                .allMatch(person -> person.getAge() > 3);
        System.out.println(result);
    }

    /**
     * Stream anyMatch
     */
    @Test
    public void anyMatch() {
        boolean result = people.stream()
                .anyMatch(person -> person.getAge() > 3);
        System.out.println(result);
    }

    /**
     * Stream noneMatch
     */
    @Test
    public void noneMatch() {
        boolean result = people.stream()
                .noneMatch(person -> person.getAge() > 3);
        System.out.println(result);
    }

    /**
     * Stream findFirst
     */
    @Test
    public void findFirst() {
        Optional<Person> optionalPerson = people.stream().findFirst();
        optionalPerson.ifPresent(System.out::println);
    }

    /**
     * Stream findAny
     */
    @Test
    public void findAny() {
        Optional<Person> optionalPerson = people.stream().findAny();
        optionalPerson.ifPresent(System.out::println);
    }
}

/**
 * Aggregation operations
 * 聚合操作，统计元素
 * count max min sum average
 */
class Aggregation {

    private final List<Person> people = List.of(
            new Person(3, "c", "China"),
            new Person(4, "d", "USA"),
            new Person(5, "e", "China"),
            new Person(4, "d", "USA"),
            new Person(5, "e", "China")
    );

    /**
     * Stream count
     */
    @Test
    public void count() {
        long count = people.stream().count();
        System.out.println(count);
    }

    /**
     * Stream max
     */
    @Test
    public void max() {
        Optional<Person> optionalPerson = people.stream().max((p1, p2) -> p1.getAge() - p2.getAge());
        optionalPerson.ifPresent(System.out::println);
    }

    /**
     * Stream min
     */
    @Test
    public void min() {
        Optional<Person> optionalPerson = people.stream().min((p1, p2) -> p1.getAge() - p2.getAge());
        optionalPerson.ifPresent(System.out::println);
    }

    /**
     * Stream sum
     */
    @Test
    public void sum() {
        int sum = people.stream().mapToInt(Person::getAge).sum();
        System.out.println(sum);
    }

    /**
     * Stream average
     */
    @Test
    public void average() {
        OptionalDouble average = people.stream().mapToInt(Person::getAge).average();
        average.ifPresent(System.out::println);
    }

    /**
     * Stream reduce
     */
    @Test
    public void reduce() {
        int sum = people.stream().map(Person::getAge).reduce(0, Integer::sum);
        System.out.println(sum);

        String s = people.stream().map(Person::getName).reduce("", String::concat);
        System.out.println(s);

        String s1 = people.stream().map(Person::getName).reduce("", (s2, s3) -> s2 + s3 + ',');
        System.out.println(s1);
    }
}

class Collect {

    private final List<Person> people = List.of(
            new Person(3, "c", "China"),
            new Person(4, "d", "USA"),
            new Person(5, "e", "China"),
            new Person(4, "d", "USA"),
            new Person(5, "e", "China")
    );

    /**
     * Stream toMap
     */
    @Test
    public void toMap() {
        Map<String, Integer> map = people.stream()
                .collect(Collectors.toMap(Person::getName, Person::getAge));
        System.out.println(map);
    }

    /**
     * Stream groupingBy
     */
    @Test
    public void groupingBy() {
        Map<Integer, List<Person>> groupByAge = people.stream().collect(Collectors.groupingBy(Person::getAge));
        System.out.println(groupByAge);
    }

    /**
     * Stream partitioningBy
     */
    @Test
    public void partitioningBy() {
        Map<Boolean, List<Person>> partitionByAge = people.stream().collect(Collectors.partitioningBy(person -> person.getAge() > 3));
        System.out.println(partitionByAge);
    }

    /**
     * Stream joining
     */
    @Test
    public void joining() {
        String joinedName = people.stream().map(Person::getName).collect(Collectors.joining(","));
        System.out.println(joinedName);
    }

    /**
     * Stream summarizingInt
     */
    @Test
    public void summarizingInt() {
        IntSummaryStatistics ageSummary = people.stream().collect(Collectors.summarizingInt(Person::getAge));
        System.out.println(ageSummary.getAverage());
        System.out.println(ageSummary.getMax());
    }

    /**
     * use Collect.of(Supplier, Accumulator, Combiner, Finisher) to implement Collectors.toList
     */
    public List<Person> collectPeople(Stream<Person> stream) {
        return stream.collect(Collector.of(
                // Supplier
                ArrayList::new,
                // Accumulator
                (list, person) -> {
                    System.out.println("Accumulator"
                            + System.lineSeparator() + "person: " + person
                            + System.lineSeparator() + "list: " + list);
                    list.add(person);
                },
                // Combiner
                (left, right) -> {
                    System.out.println("Combiner: "
                            + System.lineSeparator() + "left: " + left
                            + System.lineSeparator() + "right: " + right);
                    left.addAll(right);
                    return left;
                },
                // Finisher
                Collector.Characteristics.IDENTITY_FINISH));
    }

    @Test
    public void toListByCollector() {
        List<Person> collect = collectPeople(people.stream());
        collect.forEach(System.out::println);
        List<Person> parallelCollect = collectPeople(people.parallelStream());
        parallelCollect.forEach(System.out::println);
    }

    /**
     * use Collector.of(Supplier, Accumulator, Combiner, Finisher) to implement Collectors.toMap
     */
    @Test
    public void toMapByCollector(){
        HashMap<String, List<Person>> collect = people.stream().collect(Collector.of(
                // Supplier
                () -> {
                    System.out.println("Supplier : new HashMap " + "Thread: " + Thread.currentThread().getName());;
                    return new HashMap<>();
                },
                // Accumulator
                (map, person) -> {
                    System.out.println("Accumulator: " + person + " Thread: " + Thread.currentThread().getName());
                    map.computeIfAbsent(person.getCountry(), k -> new ArrayList<>()).add(person);
                },
                // Combiner
                (left, right) -> {
                    System.out.println("Combiner: "
                            + System.lineSeparator() + "left: " + left + " Thread: " + Thread.currentThread().getName()
                            + System.lineSeparator() + "right: " + right + " Thread: " + Thread.currentThread().getName());
                    right.forEach((key, value) -> left.merge(key, value, (list, newList) -> {
                        list.addAll(newList);
                        return list;
                    }));
                    return left;
                },
                // Finisher
                Collector.Characteristics.IDENTITY_FINISH
        ));

//        collect.forEach((key, value) -> System.out.println(key + " : " + value));
        for (Map.Entry<String, List<Person>> entry : collect.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        int size = people.parallelStream().collect(Collector.of(
                // Supplier
                HashMap<String, List<Person>>::new,
                // Accumulator
                (map, person) -> {
                    System.out.println("Accumulator: " + person + " Thread: " + Thread.currentThread().getName());
                    map.computeIfAbsent(person.getCountry(), k -> new ArrayList<>()).add(person);
                },
                // Combiner
                (left, right) -> {
                    System.out.println("Combiner: "
                            + System.lineSeparator() + "left: " + left + " Thread: " + Thread.currentThread().getName()
                            + System.lineSeparator() + "right: " + right + " Thread: " + Thread.currentThread().getName());
                    right.forEach((key, value) -> left.merge(key, value, (list, newList) -> {
                        list.addAll(newList);
                        return list;
                    }));
                    return left;
                },
                // Finisher
                map -> map.size()
        ));
        System.out.println(size);
    }

    /**
     * use Collector.of(Supplier, Accumulator, Combiner, Finisher) to implement Collectors.toConcurrentMap
     * 使用线程安全的数据容器： ConcurrentHashMap
     * List 数据源有序，策略会忽略CONCURRENT特性
     * Set 数据源无序，策略会忽略UNORDERED特性, 但是会保留CONCURRENT特性
     */
    @Test
    public void toConcurrentMapByCollector(){
        ConcurrentHashMap<String, String> collect = Set.of('L',  'I',  'Y', 'U', 'P').parallelStream().collect(Collector.of(
                // Supplier
                () -> {
                    System.out.println("Supplier : new ConcurrentHashMap " + "Thread: " + Thread.currentThread().getName());;
                    return new ConcurrentHashMap<>();
                },
                // Accumulator
                (map, item) -> {
                    System.out.println("Accumulator: " + item + " Thread: " + Thread.currentThread().getName());
                    map.put(item.toString().toLowerCase(), item.toString());
                },
                // Combiner
                (left, right) -> {
                    System.out.println("Combiner: "
                            + System.lineSeparator() + "left: " + left + " Thread: " + Thread.currentThread().getName()
                            + System.lineSeparator() + "right: " + right + " Thread: " + Thread.currentThread().getName());
                    left.putAll(right);
                    return left;
                },
                // Finisher
                Collector.Characteristics.IDENTITY_FINISH,
                Collector.Characteristics.UNORDERED,
                Collector.Characteristics.CONCURRENT
        ));

        System.out.println(collect);
    }
}
