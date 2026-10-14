package DSA.JPMC.path.theory;

import java.util.*;

// Implementing Comparable defines the "Natural Order" (e.g., sorting by ID)
class Employee implements Comparable<Employee> {
    int id;
    String name;
    double salary;

    public Employee(int id, String name, double salary) {
        this.id = id; this.name = name; this.salary = salary;
    }

    // Overriding compareTo for Comparable
    @Override
    public int compareTo(Employee other) {
        // Natural order: Ascending by ID
        return Integer.compare(this.id, other.id);
    }
    
    @Override
    public String toString() { return name + "($" + salary + ")"; }
}

public class SortingDemo {
    public static void main(String[] args) {
        List<Employee> list = new ArrayList<>(Arrays.asList(
            new Employee(3, "Alice", 90000),
            new Employee(1, "Bob", 120000),
            new Employee(2, "Charlie", 75000)
        ));

        // 1. COMPARABLE: Sorts using the class's compareTo method (Sorts by ID)
        Collections.sort(list);
        System.out.println("Natural Order (by ID): " + list);
        // Output: [Bob($120000.0), Charlie($75000.0), Alice($90000.0)]

        // 2. COMPARATOR: Custom sorting logic without touching the Employee class
        // Using Java 8 Lambda syntax to sort by Salary descending
        Comparator<Employee> salaryComparator = (e1, e2) -> Double.compare(e2.salary, e1.salary);
        
        list.sort(salaryComparator);
        System.out.println("Custom Order (by Salary DESC): " + list);
        // Output: [Bob($120000.0), Alice($90000.0), Charlie($75000.0)]
    }
}