package DSA.Coding.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeDomainOccurrence {

    // Mock Employee class setup referenced in the video [00:01:40]
    static class Employee {
        private String name;
        private String email;

        public Employee(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public String getName() { return name; }
        public String getEmail() { return email; }
    }

    public static void main(String[] args) {
        // Setup base target mock database lists from the video example [00:02:25, 00:02:40]
        List<Employee> empList = new ArrayList<>();
        empList.add(new Employee("Sam", "sam@gmail.com"));
        empList.add(new Employee("Adam", "adam@gmail.com"));
        empList.add(new Employee("Peter", "peter@yahoo.com"));

        // ====================================================================================
        // APPROACH 1: Using Map/Substring and Grouping/Counting (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Given a list of employee objects containing an email string field, calculate the 
        // frequency occurrence of each distinct domain string (e.g., "@gmail.com", "@yahoo.com") [00:00:07].
        //
        // STRATEGY:
        // 1. Map over the employee stream and isolate the email field [00:04:13].
        // 2. Extract the suffix using `substring(email.indexOf("@"))` [00:04:26, 00:04:37].
        // 3. Apply `Collectors.groupingBy` with `Function.identity()` and `Collectors.counting()` [00:05:06, 00:05:24, 00:06:12].
        // ====================================================================================

        Map<String, Long> approach1Result = empList.stream()
                .map(emp -> {
                    String email = emp.getEmail();
                    // Grabs everything starting exactly from the index location of '@' [00:04:37]
                    return email.substring(email.indexOf("@")); 
                })
                .collect(Collectors.groupingBy(
                        java.util.function.Function.identity(), // Groups by the extracted domain string [00:05:24]
                        Collectors.counting()                  // Counts occurrences per domain bucket [00:06:12]
                ));

        System.out.println("Approach 1 (Video Substring + Grouping Logic):");
        System.out.println(approach1Result); 
        // Output: {@gmail.com=2, @yahoo.com=1} [00:05:58]


        // ====================================================================================
        // APPROACH 2: Clean Clean-Cut Domain Extraction (Without the '@' Symbol Variant)
        //
        // PROBLEM STATEMENT:
        // Same as above, but isolating strictly the clear host domain names (e.g., "gmail.com") 
        // without including the prefix delimiter character '@'.
        //
        // STRATEGY:
        // Instead of truncating straight at the index position of '@', use `email.indexOf("@") + 1`. 
        // This splits the domain correctly, providing a more standard text output for grouping analytics.
        // ====================================================================================

        Map<String, Long> approach2Result = empList.stream()
                .map(emp -> {
                    String email = emp.getEmail();
                    int atIndex = email.indexOf("@");
                    return atIndex != -1 ? email.substring(atIndex + 1) : "unknown"; // Skips over the '@' symbol safely
                })
                .collect(Collectors.groupingBy(
                        java.util.function.Function.identity(),
                        Collectors.counting()
                ));

        System.out.println("\nApproach 2 (Alternative Pure Domain Name Extraction):");
        System.out.println(approach2Result);
        // Output: {gmail.com=2, yahoo.com=1}
    }
}