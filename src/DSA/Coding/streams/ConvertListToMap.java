package DSA.Coding.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConvertListToMap {

    // Mock Person class setup referenced in the video [00:00:35]
    static class Person {
        private String name;
        private String city;
        private int age;

        public Person(String name, String city, int age) {
            this.name = name;
            this.city = city;
            this.age = age;
        }

        public String getName() { return name; }
        public String getCity() { return city; }
        public int getAge() { return age; }

        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + "}";
        }
    }

    public static void main(String[] args) {
        // Setup input mock list of Person objects from the video trace [00:01:19, 00:04:07]
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("Sumit", "Kolkata", 28));
        personList.add(new Person("Amit", "Pune", 24));
        personList.add(new Person("Jatin", "Pune", 26));
        personList.add(new Person("Alex", "Pune", 30));
        personList.add(new Person("Pile", "Bangalore", 25));

        // ====================================================================================
        // APPROACH 1: Using Collectors.groupingBy() (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Convert a list of Person objects into a Map where the key is the city string, 
        // and the value is a list of all Person objects residing in that city [00:00:08, 00:00:14].
        //
        // STRATEGY:
        // 1. Stream the person collection list [00:01:53].
        // 2. Pass a method reference classifier `Person::getCity` into `Collectors.groupingBy` [00:02:14, 00:02:33].
        // 3. This implicitly forms a `Map<String, List<Person>>` collection mapping layout [00:03:51].
        // ====================================================================================

        Map<String, List<Person>> approach1Result = personList.stream()
                .collect(Collectors.groupingBy(Person::getCity)); // Groups instances using their city value [00:02:33]

        System.out.println("Approach 1 (Video groupingBy City Logic):");
        // Print key-value pairs using BiConsumer internal map iteration [00:02:49]
        approach1Result.forEach((city, people) -> System.out.println(city + " -> " + people));
        /* Output:
           Kolkata -> [Person{name='Sumit', age=28}]
           Pune -> [Person{name='Amit', age=24}, Person{name='Jatin', age=26}, Person{name='Alex', age=30}]
           Bangalore -> [Person{name='Pile', age=25}] [00:04:07]
        */


        // ====================================================================================
        // APPROACH 2: Unique Key Mapping using Collectors.toMap() (Alternative Target Scenario)
        //
        // PROBLEM STATEMENT:
        // Convert the list into a unique associative Map layout (e.g., mapping Name to Age) where 
        // each unique key strictly corresponds to a single individual value instead of a List container.
        //
        // STRATEGY:
        // Utilize `Collectors.toMap(KeyMapper, ValueMapper)`. If overlapping keys exist, you must provide 
        // a merge function to declare which element overrides the other to prevent IllegalStateException.
        // ====================================================================================

        Map<String, Integer> approach2Result = personList.stream()
                .collect(Collectors.toMap(
                        Person::getName, // Map Key: Person Name
                        Person::getAge,  // Map Value: Person Age
                        (existing, replacement) -> existing // Merge Function fallback rule for duplicates
                ));

        System.out.println("\nApproach 2 (Alternative toMap Unique KV Projection):");
        approach2Result.forEach((name, age) -> System.out.println(name + " is " + age + " years old"));
    }
}