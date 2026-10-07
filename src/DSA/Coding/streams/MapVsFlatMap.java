package DSA.Coding.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MapVsFlatMap {

    // Mock Person class setup referenced in the video [00:00:24]
    static class Person {
        private String name;
        private List<String> colors;

        public Person(String name, List<String> colors) {
            this.name = name;
            this.colors = colors;
        }

        public String getName() { return name; }
        public List<String> getColors() { return colors; }
    }

    public static void main(String[] args) {
        // Setup input mock list of Person objects, each containing a list of strings [00:00:33]
        List<Person> persons = Arrays.asList(
                new Person("Sam", Arrays.asList("Red", "Blue")),
                new Person("Adam", Arrays.asList("Green", "Yellow")),
                new Person("Peter", Arrays.asList("Blue", "Black"))
        );

        // ====================================================================================
        // APPROACH 1: Using Stream.map() -> Results in a List of Lists (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Demonstrate what happens when using `.map()` on a nested structure [00:00:06].
        //
        // STRATEGY:
        // `.map()` transforms each element of a stream into another single element (a 1-to-1 mapping) [00:00:15, 00:02:06].
        // Since `Person::getColors` returns a `List<String>`, calling `.map()` gives us a 
        // stream of lists, resulting in a nested `List<List<String>>` container structure [00:00:48, 00:01:09].
        // ====================================================================================

        List<List<String>> mapResult = persons.stream()
                .map(Person::getColors) // Transforms Person -> List<String> (1-to-1) [00:01:28]
                .collect(Collectors.toList());

        System.out.println("Using Stream.map() (Produces Nested List Structure):");
        System.out.println(mapResult); 
        // Output: [[Red, Blue], [Green, Yellow], [Blue, Black]] [00:01:58]


        // ====================================================================================
        // APPROACH 2: Using Stream.flatMap() -> Flattens into a Single List (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Demonstrate how to merge and flatten the nested collections into a unified list [00:02:21].
        //
        // STRATEGY:
        // `.flatMap()` transforms each element into a stream of values and then flattens all 
        // these individual sub-streams into a single combined top-level stream (a 1-to-many mapping) [00:02:21, 00:04:05].
        // For nested elements or collection of collections, it merges them seamlessly into a `List<String>` [00:02:30, 00:02:39].
        // ====================================================================================

        List<String> flatMapResult = persons.stream()
                .flatMap(p -> p.getColors().stream()) // Extracts and flattens inner lists into one stream [00:03:06, 00:03:23]
                .collect(Collectors.toList());

        System.out.println("\nUsing Stream.flatMap() (Produces Flattened Single List):");
        System.out.println(flatMapResult);
        // Output: [Red, Blue, Green, Yellow, Blue, Black] [00:03:50]


        // ====================================================================================
        // EXTRA OPTIMIZATION: Deduplicated Flattening (Using Set Alternative)
        //
        // PROBLEM STATEMENT:
        // Get all unique favorite colors chosen across all tracked individuals, removing color overlaps.
        //
        // STRATEGY:
        // Flatten the data using `.flatMap()` exactly like before, but collect the entries 
        // directly into a unique `Collectors.toSet()` container structure to automatically strip 
        // repeating colors like the duplicated "Blue".
        // ====================================================================================

        java.util.Set<String> uniqueColors = persons.stream()
                .flatMap(p -> p.getColors().stream())
                .collect(Collectors.toSet());

        System.out.println("\nAlternative: Using flatMap + toSet (Deduplicated Unique Items):");
        System.out.println(uniqueColors);
        // Output: [Red, Green, Blue, Yellow, Black]
    }
}