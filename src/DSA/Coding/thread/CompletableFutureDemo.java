package DSA.Coding.thread;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureDemo {
    public static void main(String[] args) {
        System.out.println("Main thread starts shopping...");

        CompletableFuture<Void> pipeline = CompletableFuture
            // Step 1: Run background task that returns a value
            .supplyAsync(() -> {
                System.out.println("Fetching product price from database on: " 
                    + Thread.currentThread().getName());
                simulateDelay(1000); // Takes 1 second
                return 500; // Original price
            })
            // Step 2: Transform the value (apply discount)
            .thenApply(price -> {
                System.out.println("Applying 10% student discount...");
                return price * 0.90; // Returns 450.0
            })
            // Step 3: Consume the final result (print receipt)
            .thenAccept(finalPrice -> {
                System.out.println("Final amount to pay: Rs. " + finalPrice);
            })
            // Step 4: Handle any exceptions gracefully
            .exceptionally(ex -> {
                System.out.println("Something went wrong: " + ex.getMessage());
                return null;
            });

        System.out.println("Main thread is NOT blocked! Can continue other work.");
        
        // Wait for pipeline so program doesn't exit before background task finishes
        pipeline.join(); 
    }

    private static void simulateDelay(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) {}
    }
}