package org.stream.test;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @Author: helltractor
 * @Date: 2024/5/12 下午1:46
 */

public class StreamCreationTest {

    /**
     * Create stream from collection, list, set
     */
    @Test
    public void createByList() {
        List<String> list = List.of("a", "b", "c");
        Stream<String> stream = list.stream();
        stream.forEach(System.out::println);

        List<String> list1 = Arrays.asList("a", "b", "c");
        Stream<String> stream1 = list1.stream();
        stream1.forEach(System.out::println);
    }

    /**
     * Create stream from array
     */
    @Test
    public void createByArrays() {
        String[] array = new String[] {"a", "b", "c"};
        Stream<String> stream = Arrays.stream(array);
        stream.forEach(System.out::println);
    }

    /**
     * Create stream, use Stream.Builder
     */
    @Test
    public void createDynamic() {
        Stream.Builder<String> stringBuilder = Stream.builder();
        stringBuilder.add("a").add("b").add("c");
        Stream<String> stream = stringBuilder.build();
        stream.forEach(System.out::println);

        // IllegalStateException: stream has already been operated upon or closed
//        stringBuilder.add("d").add("e").add("f");
//        stream.forEach(System.out::println);
    }

    /**
     * Create stream from path
     * try with resources
     * the file must shut down after using
     */
    @Test
    public void createByPath() {
        Path path = Paths.get("stream/src/main/resources/.txt/path.txt");
        try (Stream<String> stream = Files.lines(path)) {
            stream.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create stream from IntStream
     */
    @Test
    public void createIntStream() {
        IntStream intStream = IntStream.of(1, 2, 3, 4, 5);
        intStream.forEach(System.out::println);

        // Exclude the end value, [1, 5)
        IntStream intStream1 = IntStream.range(1, 5);
        intStream1.forEach(System.out::println);

        // Include the end value, [1, 5]
        IntStream intStream2 = IntStream.rangeClosed(1, 5);
        intStream2.forEach(System.out::println);

        // Create random values
        new Random().ints(5).forEach(System.out::println);
    }

    /**
     * Create stream from LongStream
     */
    @Test
    public void createLongStream() {
        LongStream longStream = LongStream.of((long) Math.pow(10, 9), 2, 3, 4, 5);
        longStream.forEach(System.out::println);
    }

    /**
     * Create stream from DoubleStream
     */
    @Test
    public void createDoubleStream() {
        DoubleStream doubleStream = DoubleStream.of(1.0, 2.0, 3.0, 4.0, 5.0);
        doubleStream.forEach(System.out::println);
    }

    /**
     * Create infinite stream
     */
    @Test
    public void createInfiniteStream() {
//        Stream.iterate(0, i -> i < 10, i -> i + 1)
//                .forEach(System.out::println);
        Stream<Integer> iterateStream = Stream.iterate(0, i -> i + 1);
        iterateStream.limit(10).forEach(System.out::println);

        Stream<Character> generateStream = Stream.generate(() -> 'a');
        generateStream.limit(10).forEach(System.out::println);

//        Stream.generate(Math::random)
//             .limit(10)
//             .forEach(System.out::println);
        Stream<Character> generateRandomStream = Stream.generate(() -> 'a');
        generateRandomStream.limit(10).forEach(System.out::println);
    }

    /**
     * Create parallel stream
     */
    @Test
    public void createParallelStream() {
        List<String> list = List.of("a", "b", "c");
        list.parallelStream().forEach(System.out::println);
    }
}
