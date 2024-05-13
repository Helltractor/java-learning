# **How to use the stream module**

## **Three steps to use the stream**

### Step 1: Stream creation

- From a collection: Collection.stream(), Collection.parallelStream()
- From an array: Arrays.stream(T[] array), Stream.of(T[] array)
- From static factory methods: Stream.of(T t), IntStream.range(int startInclusive, int endExclusive), Files.lines(Path path), Pattern.splitAsStream(CharSequence input)
- From a file: Files.lines(Path path)
- From a stream builder: Stream.builder().add(T t).build()
- From a generator: Stream.generate(Supplier<T> s), IntStream.iterate(int seed, IntUnaryOperator f)
- From a custom supplier: Stream.generate(Supplier<T> s)
- From a custom iterator: Stream.iterate(T seed, UnaryOperator<T> f)

### Step 2: Intermediate operations

- Stateless operations: filter(), map(), mapToInt(), mapToLong(), mapToDouble(), flatMap(), flatMapToInt(), flatMapToLong(), flatMapToDouble(), distinct(), sorted(), peek()
- Stateful operations: sorted(), distinct()

> Mind: Intermediate operation won't do anything unless it's part of a stream pipeline that has a terminal operation.

### Step 3: Terminal operations

- Short-circuiting terminal operations (Search and Match operations): findFirst(), findAny(), anyMatch(), allMatch(), noneMatch(), limit(), skip()
- Aggregation: reduce(), collect(), min(), max(), count()
- Side-effect operations: forEach(), peek()

## **How to create stream's collector**

### The factor

- Supplier: It creates a new result container where the result will be collected.
- Accumulator: It incorporates an additional input element into a result.
- Combiner: It combines two result containers into one, which is used in parallel processing.
- Finisher: It performs the final transformation from the intermediate accumulation type to the final result type.
- Characteristics: It defines the behavior of the collector. For example, Collector.Characteristics.CONCURRENT indicates that the accumulator function can be called concurrently with the same result container from multiple threads.
    - UNORDERED: The collection operation does not commit to preserving the encounter order of input elements.
    - CONCURRENT: The collection operation supports concurrent execution. Such as CurrentHashMap (**Only the data source unordered, the Characteristics will be used**). It means just the collector only use one Supplier.
    - IDENTITY_FINISH: The finisher function is the identity function and can be elided. Just return the Accumulator.

> Mind: more detail in the [JavaDoc](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collector.html)

### Example

```java
Collector<String, StringBuilder, String> stringCollector = Collector.of(
    StringBuilder::new, // supplier
    StringBuilder::append, // accumulator
    (sb1, sb2) -> sb1.append(sb2.toString()), // combiner
    StringBuilder::toString, // finisher
    Collector.Characteristics.CONCURRENT // characteristics
);

List<String> strings = Arrays.asList("a", "b", "c", "d");
String result = strings.stream().collect(stringCollector);
System.out.println(result); // prints "abcd"
```

> Mind: more example in the [Collect](https://github.com/Helltractor/Java-Learning/blob/d84117e2fc57b8b2aa052a5806c4ada998dea98d/stream/src/test/java/org/stream/test/TerminalOperationTest.java)