package DSA.Coding.thread;

import java.util.concurrent.*;

public class ExecutorServiceDemo {
    public static void main(String[] args) {
        // Step 1: Create a fixed thread pool of 2 worker threads
        ExecutorService pool = Executors.newFixedThreadPool(2);

        // Step 2: Define a Callable task that computes something and returns a result
        Callable<String> task = () -> {
            System.out.println("Worker thread " + Thread.currentThread().getName() + " started calculation...");
            Thread.sleep(1500); // Simulate time-consuming work
            return "Calculation Finished Successfully!";
        };

        // Step 3: Submit task to pool (non-blocking, returns Future immediately)
        System.out.println("Submitting task to pool from main thread...");
        Future<String> futureResult = pool.submit(task);

        // Step 4: Main thread does independent work while worker runs
        System.out.println("Main thread is free to do other things...");

        try {
            // Step 5: Blocking wait for the result
            String output = futureResult.get(); 
            System.out.println("Received from worker: " + output);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            // Step 6: ALWAYS shut down the executor service!
            pool.shutdown();
            System.out.println("Pool shutdown initiated.");
        }
    }
}