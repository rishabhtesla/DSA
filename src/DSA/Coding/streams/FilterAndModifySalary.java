package DSA.Coding.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilterAndModifySalary {

    // Mock Employee class setup referenced in the video [00:00:21]
    static class Empl {
        private String name;
        private double salary;

        public Empl(String name, double salary) {
            this.name = name;
            this.salary = salary;
        }

        public String getName() { return name; }
        public double getSalary() { return salary; }
        public void setSalary(double salary) { this.salary = salary; } // [00:01:48]

        @Override
        public String toString() {
            return "Employee{name='" + name + "', salary=" + salary + "}";
        }
    }

    public static void main(String[] args) {
        // Setup initial mock input data mentioned in the video examples [00:00:40]
        List<Empl> empList = new ArrayList<>();
        empList.add(new Empl("Anil", 45000.0));
        empList.add(new Empl("Sunil", 10000.0)); // Under 20,000 threshold
        empList.add(new Empl("Punit", 60000.0));

        // ====================================================================================
        // APPROACH 1: Using Stream.filter() and Stream.peek() (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Filter out employees whose salary is strictly greater than 20,000, increment their 
        // salary by 1,000, and print the resulting collection [00:00:07, 00:00:53].
        //
        // STRATEGY:
        // 1. Filter elements by salary using `.filter(e -> e.getSalary() > 20000)` [00:01:07].
        // 2. Perform mutating side-effects on the filtered object pipeline inside an intermediate 
        //    `.peek()` operation by calling `e.setSalary(...)` [00:01:37, 00:01:48].
        // 3. Collect the resulting mutated stream values using `.collect(Collectors.toList())` [00:02:23].
        //
        // CRITICAL WARNING:
        // As discussed in the video, `peek()` is an intermediate operation and is lazily evaluated [00:02:13]. 
        // It requires a terminal operation (like `collect`) to trigger execution [00:02:23]. Modifying 
        // state inside `peek()` directly mutates the source elements in the shared list reference!
        // ====================================================================================

        List<Empl> approach1Result = empList.stream()
                .filter(e -> e.getSalary() > 20000) // Filter threshold criteria [00:01:07]
                .peek(e -> e.setSalary(e.getSalary() + 1000)) // Mutates salary directly via Consumer [00:01:48]
                .collect(Collectors.toList()); // Terminal trigger execution [00:02:23]

        System.out.println("Approach 1 (Video filter() + peek() Mutating Logic):");
        System.out.println(approach1Result); 
        // Output: [Employee{name='Anil', salary=46000.0}, Employee{name='Punit', salary=61000.0}] [00:02:55]


        // ====================================================================================
        // APPROACH 2: Safe Non-Mutating Stream Transformation (Alternative Best Practice)
        //
        // PROBLEM STATEMENT:
        // Same business filtering and transformation logic, but following strict functional 
        // programming principles where the source database items remain entirely untouched.
        //
        // STRATEGY:
        // Instead of executing modifying mutations inside `peek()`, map filtered matching targets 
        // into completely new instantiated `Empl` object records. This shields the original collection 
        // records from unexpected reference alterations downstream.
        // ====================================================================================
        
        // Resetting original pristine list database references for isolated comparison
        List<Empl> freshEmpList = List.of(
                new Empl("Anil", 45000.0),
                new Empl("Sunil", 10000.0),
                new Empl("Punit", 60000.0)
        );

        List<Empl> approach2Result = freshEmpList.stream()
                .filter(e -> e.getSalary() > 20000)
                .map(e -> new Empl(e.getName(), e.getSalary() + 1000)) // Safe non-mutating deep clone
                .collect(Collectors.toList());

        System.out.println("\nApproach 2 (Alternative Safe Non-Mutating Mapped Implementation):");
        System.out.println("Transformed Result: " + approach2Result);
        System.out.println("Original Source Record 1 Salary Check: " + freshEmpList.get(0).getSalary()); // Stays 45000.0
    }
}