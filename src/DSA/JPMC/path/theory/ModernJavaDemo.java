package DSA.JPMC.path.theory;

// JAVA 17 FEATURE: Record (Immutable data carrier)
record Transaction(String id, double amount) {}

public class ModernJavaDemo {
    public static void main(String[] args) {
        
        // JAVA 11 FEATURE: Local-variable type inference (var)
        var transactions = java.util.List.of(
            new Transaction("T1", 100.50),
            new Transaction("T2", 500.00)
        );

        // JAVA 17 FEATURE: Text Blocks (Multi-line strings using """)
        String jsonPayload = """
            {
                "status": "success",
                "count": 2
            }
            """;
        System.out.println("Payload:\n" + jsonPayload);

        // JAVA 8 FEATURE: Streams & Lambdas
        double total = transactions.stream()
            .mapToDouble(Transaction::amount) // Method reference
            .sum();
            
        System.out.println("Total Amount: " + total);
    }
}