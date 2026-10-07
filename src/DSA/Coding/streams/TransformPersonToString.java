package DSA.Coding.streams;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class TransformPersonToString {

    // Mock Person class setup referenced in the video [00:00:15]
    static class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() { return name; }
        public int getAge() { return age; }
    }

    public static void main(String[] args) {
        // Setup input mock list of Person objects [00:00:49]
        List<Person> persons = Arrays.asList(
                new Person("Sam", 20),
                new Person("Adam", 25),
                new Person("Peter", 30)
        );

        // ====================================================================================
        // APPROACH 1: Using a Custom Collector via Collector.of() (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Transform a stream of Person objects into a single combined string consisting of all 
        // names in uppercase, separated by a pipe character '|' [00:00:00].
        //
        // STRATEGY:
        // Create a custom collector using `Collector.of(...)` passing the 4 core lifecycle functions [00:00:57, 00:01:48]:
        // 1. Supplier: Creates a new internal accumulator state `() -> new StringJoiner("|")` [00:02:17].
        // 2. Accumulator: Processes each `Person`, mapping name to uppercase and adding it to the Joiner [00:02:53].
        // 3. Combiner: Merges two distinct StringJoiners together during parallel execution [00:03:50].
        // 4. Finisher: Maps the accumulated container to the final String result via `StringJoiner::toString` [00:04:04].
        // ====================================================================================

        Collector<Person, StringJoiner, String> personCollector = Collector.of(
                () -> new StringJoiner("|"),                                   // Supplier [00:02:17]
                (j, p) -> j.add(p.getName().toUpperCase()),                     // Accumulator [00:02:53]
                (j1, j2) -> j1.merge(j2),                                      // Combiner [00:03:50]
                StringJoiner::toString                                         // Finisher [00:04:04]
        );

        String approach1Result = persons.stream()
                .collect(personCollector); // Evaluates using our custom dynamic collector structure [00:05:08]

        System.out.println("Approach 1 (Video Custom Collector.of Logic):");
        System.out.println(approach1Result); 
        // Output: SAM|ADAM|PETER [00:00:24]


        // ====================================================================================
        // APPROACH 2: Idiomatic/Standard Pipeline Transformation (Optimal Alternative)
        //
        // PROBLEM STATEMENT:
        // Same as above, but written using standard pre-built Java 8 pipeline operations instead 
        // of writing custom structural collector components.
        //
        // STRATEGY:
        // 1. Extract the name string fields from the object structure via `.map(Person::getName)`.
        // 2. Transform strings into upper letters using `.map(String::toUpperCase)`.
        // 3. Combine them using the built-in `Collectors.joining("|")` string join mechanism.
        // ====================================================================================

        String approach2Result = persons.stream()
                .map(Person::getName)            // Transmute stream of objects into stream of name strings
                .map(String::toUpperCase)        // Modify text characters to uppercase
                .collect(Collectors.joining("|")); // Joins elements linearly using the pipe delimiter

        System.out.println("\nApproach 2 (Alternative Standard Map + Joining Pattern):");
        System.out.println(approach2Result);
        // Output: SAM|ADAM|PETER
    }
}