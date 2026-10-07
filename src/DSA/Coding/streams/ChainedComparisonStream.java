package DSA.Coding.streams;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ChainedComparisonStream {

    // Mock Person class setup referenced in the video [00:00:13]
    static class Person {
        private String firstName;
        private String lastName;

        public Person(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }

        @Override
        public String toString() {
            return "Person{" + firstName + " " + lastName + "}";
        }
    }

    public static void main(String[] args) {
        // Setup mock input list with matching first names but different last names [00:00:47, 00:01:20]
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("John", "Johnson"));
        personList.add(new Person("Bobby", "Smith"));
        personList.add(new Person("Alice", "Smith"));
        personList.add(new Person("Bobby", "Adams"));

        // ====================================================================================
        // APPROACH 1: Using Comparator.comparing() and thenComparing() (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Given a list of Person objects, sort them alphabetically by their first name, and if 
        // the first names are identical, resolve the tie by sorting by their last name [00:00:07].
        //
        // STRATEGY:
        // 1. Stream the person records using `personList.stream()`.
        // 2. Pass a chained comparator pipeline into `.sorted()`:
        //    - First key level: `Comparator.comparing(Person::getFirstName)` [00:01:42].
        //    - Tiebreaker chain link: `.thenComparing(Person::getLastName)` [00:01:57].
        // 3. Collect into a final ordered list via `.collect(Collectors.toList())` [00:02:19].
        // ====================================================================================

        List<Person> approach1Result = personList.stream()
                .sorted(Comparator.comparing(Person::getFirstName)  // Primary sort by first name [00:01:42]
                        .thenComparing(Person::getLastName))        // Secondary tie-breaker by last name [00:01:57]
                .collect(Collectors.toList());

        System.out.println("Approach 1 (Primary: First Name -> Secondary: Last Name):");
        System.out.println(approach1Result);
        // Expected Output: [Person{Alice Smith}, Person{Bobby Adams}, Person{Bobby Smith}, Person{John Johnson}] [00:02:52]


        // ====================================================================================
        // APPROACH 2: Reversed Primary Sorting Hierarchy (Alternative Scenario from Video)
        //
        // PROBLEM STATEMENT:
        // Sort the records by last name first, then break ties using the first name [00:03:06].
        //
        // STRATEGY:
        // Swap the ordering functions inside the comparator chain so that `Person::getLastName` 
        // serves as the primary evaluator and `Person::getFirstName` acts as the tie-breaker [00:03:34].
        // ====================================================================================

        List<Person> approach2Result = personList.stream()
                .sorted(Comparator.comparing(Person::getLastName)   // Primary sort by last name [00:03:34]
                        .thenComparing(Person::getFirstName))       // Secondary tie-breaker by first name
                .collect(Collectors.toList());

        System.out.println("\nApproach 2 (Primary: Last Name -> Secondary: First Name):");
        System.out.println(approach2Result);
        // Expected Output: [Person{Bobby Adams}, Person{John Johnson}, Person{Alice Smith}, Person{Bobby Smith}] [00:03:15]
    }
}