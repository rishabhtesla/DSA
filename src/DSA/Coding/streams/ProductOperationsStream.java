package DSA.Coding.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductOperationsStream {

    // Mock Product class setup referenced in the video [00:00:06]
    static class Product {
        private int id;
        private String name;
        private double price;
        private String category;

        public Product(int id, String name, double price, String category) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.category = category;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public double getPrice() { return price; }
        public String getCategory() { return category; }
        public void setPrice(double price) { this.price = price; } // [00:03:52]

        @Override
        public String toString() {
            return "Product{name='" + name + "', price=" + price + "}";
        }
    }

    public static void main(String[] args) {
        // Setup initial mock input data mentioned in the video examples [00:01:58, 00:04:45]
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1, "Lifebuoy", 20.0, "Hygiene"));
        productList.add(new Product(2, "Surf Excel", 50.0, "Laundry"));
        productList.add(new Product(3, "Petronics", 200.0, "Electronics"));

        // ====================================================================================
        // TASK 1: Filter names of products whose price < 100 (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Filter out and print only the names of products whose price falls below 100 [00:00:13, 00:00:44].
        // ====================================================================================
        
        List<String> filteredNames = productList.stream()
                .filter(p -> p.getPrice() < 100) // Filter criteria [00:00:54]
                .map(Product::getName)          // Extract names [00:01:30]
                .collect(Collectors.toList());

        System.out.println("Task 1 (Filtered Names (< 100)): " + filteredNames);
        // Output: [Lifebuoy, Surf Excel] [00:01:58]


        // ====================================================================================
        // TASK 2: Create a new list containing products with a reduced price of 20% (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Apply a 20% discount structure to all elements across the dataset [00:00:20, 00:02:05].
        //
        // CRITICAL NOTE FROM THE VIDEO:
        // Invoking setters inside a `.map()` block modifies the source elements directly [00:04:07]. 
        // Side-effects inside streams alter downstream metrics like averages unless instances are deep-copied [00:04:15, 00:06:25].
        // ====================================================================================

        List<Product> discountedProducts = productList.stream()
                .map(p -> {
                    double discountedPrice = p.getPrice() * 0.80; // Multiplies by 0.80 to cut 20% [00:03:23]
                    p.setPrice(discountedPrice);                 // MUTATES base object directly! [00:03:52]
                    return p;
                })
                .collect(Collectors.toList());

        System.out.println("Task 2 (20% Reduced Price List): " + discountedProducts);
        // Output logs modified prices: Lifebuoy=16.0, Surf Excel=40.0, Petronics=160.0 [00:04:45]


        // ====================================================================================
        // TASK 3: Calculate the average price of all products (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Compute the mathematical average price using primitive DoubleStream mappings [00:00:28, 00:05:01].
        // Note: Calculates based on the modified/discounted values from Task 2 [00:06:25].
        // ====================================================================================

        double averagePrice = productList.stream()
                .mapToDouble(Product::getPrice) // Converts object stream to primitive DoubleStream [00:05:28]
                .average()                     // Computes average returning OptionalDouble [00:05:35]
                .orElse(0.0);                  // Safe extraction strategy [00:06:00]

        System.out.println("Task 3 (Average Price): " + averagePrice);
        // Output: 72.0 (Average of 16, 40, and 160) [00:06:17]


        // ====================================================================================
        // TASK 4: Find the product with the lowest price (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Use a stream to identify and return the product object with the minimum price value [00:00:36, 00:06:40].
        // ====================================================================================

        Optional<Product> lowestPricedProduct = productList.stream()
                .min((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice())); // Comparator pairing [00:07:14, 00:07:39]

        System.out.print("Task 4 (Lowest Priced Product): ");
        if (lowestPricedProduct.isPresent()) {
            System.out.println(lowestPricedProduct.get()); // [00:08:23]
        } else {
            System.out.println("No product found"); // [00:08:32]
        }
        // Output: Product{name='Lifebuoy', price=16.0} [00:08:49]


        // ====================================================================================
        // ALTERNATIVE IMPROVEMENT: Non-Mutating Discounting Pattern (Safe Best Practice)
        //
        // STRATEGY:
        // To preserve original list pricing, create and map into entirely *new* Product instances 
        // inside the map pipeline rather than running mutator side-effects on shared references.
        // ====================================================================================

        List<Product> freshProductList = List.of(
                new Product(1, "Lifebuoy", 20.0, "Hygiene"),
                new Product(2, "Surf Excel", 50.0, "Laundry"),
                new Product(3, "Petronics", 200.0, "Electronics")
        );

        List<Product> safelyDiscounted = freshProductList.stream()
                .map(p -> new Product(p.getId(), p.getName(), p.getPrice() * 0.80, p.getCategory())) // Clones safely
                .collect(Collectors.toList());
        
        System.out.println("\nAlternative (Safe Non-Mutating Implementation):");
        System.out.println("Original 1st Item Price: " + freshProductList.get(0).getPrice()); // Remains 20.0
        System.out.println("Discounted 1st Item Price: " + safelyDiscounted.get(0).getPrice()); // Evaluates 16.0
    }
}