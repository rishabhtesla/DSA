package DSA.Coding.thread;

import java.util.concurrent.*;

public class RunnableCallableDemo {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // 1. RUNNABLE: Returns nothing, cannot throw checked exceptions without a try/catch inside
        Runnable logTask = () -> {
            System.out.println("Writing to audit log (fire and forget)...");
            // If we throw a checked exception here, it will not compile!
        };

        // 2. CALLABLE: Returns a String, CAN throw checked exceptions natively
        Callable<String> fetchTask = () -> {
            System.out.println("Fetching data from DB...");
            Thread.sleep(500); // Checked exception (InterruptedException) thrown safely
            return "DB_RESULT_SUCCESS";
        };

        try {
            // Submit Runnable
            executor.submit(logTask);
            
            // Submit Callable and capture the Future
            Future<String> futureResult = executor.submit(fetchTask);
            
            // .get() blocks until the Callable finishes and returns the result
            System.out.println("Callable returned: " + futureResult.get()); 
            
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Task failed: " + e.getMessage());
        } finally {
            executor.shutdown();
        }
    }
}