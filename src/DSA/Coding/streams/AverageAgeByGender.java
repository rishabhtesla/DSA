package DSA.Coding.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AverageAgeByGender {

    // Mock Employee class setup referenced in the video [00:00:07, 00:01:25]
    static class Employee {
        private String name;
        private int age;
        private String gender;

        public Employee(String name, int age, String gender) {
            this.name = name;
            this.age = age;
            this.gender = gender;
        }

        public String getName() { return name; }
        public int getAge() { return age; }
        public String getGender() { return gender; }
    }

    public static void main(String[] args) {
        // Setup initial mock employee list dataset matching the video context [00:00:25]
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee("Sam", 30, "Male"));
        employeeList.add(new Employee("Adam", 40, "Male"));
        employeeList.add(new Employee("Sophia", 25, "Female"));
        employeeList.add(new Employee("Emma", 35, "Female"));

        // ====================================================================================
        // APPROACH 1: Using Collectors.groupingBy and averagingInt (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Given a list of Employee objects, calculate the mathematical average age of male and 
        // female employees separately using Java 8 Streams [00:00:00, 00:00:07].
        //
        // STRATEGY:
        // 1. Group the stream dataset by gender using `Collectors.groupingBy(Employee::getGender)` [00:00:33, 00:00:57].
        // 2. Feed the segregated groups into a downstream collector `Collectors.averagingInt(Employee::getAge)` 
        //    to calculate the mathematical average of their ages per gender partition [00:01:03, 00:01:14].
        // 3. This maps a clean key-value tracking layout outputting `Map<String, Double>` [00:01:48].
        // ====================================================================================

        Map<String, Double> approach1Result = employeeList.stream()
                .collect(Collectors.groupingBy(
                        Employee::getGender,                // Groups instances based on Gender string property [00:00:57]
                        Collectors.averagingInt(Employee::getAge) // Computes the average age per segregated bucket [00:01:14]
                ));

        System.out.println("Approach 1 (Video groupingBy + averagingInt Logic):");
        System.out.println(approach1Result); 
        // Output logs averages: {Female=30.0, Male=35.0} [00:01:41]


        // ====================================================================================
        // APPROACH 2: Optimized Binary Partitioning using Collectors.partitioningBy
        //
        // PROBLEM STATEMENT:
        // Same aggregation goal, but structurally optimized for true/false binary classification workloads. 
        //
        // STRATEGY:
        // Since Gender in this dataset acts as a direct boolean-like split (e.g., checking `isMale`), 
        // leverage `Collectors.partitioningBy(Predicate, Downstream)`. 
        // Partitioning splits arrays strictly into exactly two index paths (`true` vs `false`), matching 
        // faster lookup performance compared to generic hash-bucket groupings.
        // ====================================================================================

        Map<Boolean, Double> approach2Result = employeeList.stream()
                .collect(Collectors.partitioningBy(
                        emp -> "Male".equalsIgnoreCase(emp.getGender()), // Split criteria evaluation predicate
                        Collectors.averagingInt(Employee::getAge)         // Identical downstream calculation mapping
                ));

        System.out.println("\nApproach 2 (Alternative partitioningBy Performance Optimization):");
        System.out.println("Male Average Age (true): " + approach2Result.get(true));
        System.out.println("Female Average Age (false): " + approach2Result.get(false));
    }
}