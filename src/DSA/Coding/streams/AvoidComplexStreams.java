package DSA.Coding.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AvoidComplexStreams {

    // Mock Person class setup referenced in the video [00:00:32]
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
        // Setup input mock database with duplicate names over age 30 [00:02:35]
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("Raj", 35));
        personList.add(new Person("Raj", 40)); // Duplicate name
        personList.add(new Person("Amit", 45));
        personList.add(new Person("Sam", 25)); // Under age 30

        // ====================================================================================
        // BAD PRACTICE: Cramming too many tasks in a single stream pipeline (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Given a list of Person objects, fetch unique names of people older than 30, sorted 
        // alphabetically [00:00:15].
        //
        // DRAWBACKS DISCUSSED:
        // 1. Poor Readability: The code becomes cramped and visually overwhelming [00:02:52].
        // 2. Debugging Nightmare: Hard to figure out which specific operation caused an exception [00:02:52, 00:05:48].
        // 3. Maintenance Cost: Unnecessarily difficult to scale or refactor down the road [00:03:01].
        // ====================================================================================

        List<String> badPracticeResult = personList.stream()
                .filter(p -> p.getAge() > 30) // Filtering [00:01:18]
                .map(Person::getName)         // Transforming [00:01:29]
                .distinct()                   // Deduplicating [00:01:40]
                .sorted()                     // Sorting alphabetically [00:01:58]
                .collect(Collectors.toList());

        System.out.println("Approach 1 (Bad Practice - Monolithic Crammed Stream Pipeline):");
        System.out.println(badPracticeResult); 
        // Output: [Amit, Raj] [00:02:42]


        // ====================================================================================
        // GOOD PRACTICE: Split the stream pipeline into logical steps (As shown in the video)
        //
        // STRATEGY:
        // Break the operation down logically into two focused stages [00:05:33]:
        // Stage 1: Filter elements by age metrics and project the targeted text properties [00:03:33].
        // Stage 2: Consume the text payload into a subsequent sub-pipeline to address formatting 
        //          (distinct & sorted constraints) cleanly [00:04:36].
        // ====================================================================================

        System.out.println("\nApproach 2 (Good Practice - Split Logical Pipelines for Debugging):");

        // Step A: Focus purely on retrieval and extraction [00:03:33]
        List<String> namesOverThirty = personList.stream()
                .filter(p -> p.getAge() > 30)
                .map(Person::getName)
                .collect(Collectors.toList());

        // Step B: Focus cleanly on deduplication and ordering modifications [00:04:36, 00:04:58]
        List<String> goodPracticeResult = namesOverThirty.stream()
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        System.out.println(goodPracticeResult);
        // Output: [Amit, Raj]
    }
}