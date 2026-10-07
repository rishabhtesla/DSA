package DSA.Coding;

public class EvenOddThreads {

    // The maximum number up to which we want to print
    private static final int MAX_NUMBER = 10;
    
    // Starting counter
    private static int counter = 1;
    
    // Shared lock object used for synchronization between the two threads
    private static final Object lock = new Object();

    public static void main(String[] args) {
        
        // Thread 1: Responsible for printing ODD numbers
        Thread oddThread = new Thread(() -> {
            while (counter <= MAX_NUMBER) {
                // Synchronize on the shared lock object to ensure exclusive access
                synchronized (lock) {
                    
                    // If the counter is currently EVEN, this thread must wait 
                    // because it is the turn of the even thread.
                    while (counter % 2 == 0) {
                        try {
                            // Releases the lock and suspends this thread until notified
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    
                    // Double-check condition to prevent printing past MAX_NUMBER 
                    // if the counter was incremented right before waking up
                    if (counter <= MAX_NUMBER) {
                        System.out.println(Thread.currentThread().getName() + ": " + counter);
                        counter++; // Move to the next number (which will be Even)
                    }
                    
                    // Notify the Even thread that it can now wake up and check the condition
                    lock.notify();
                }
            }
        }, "Odd-Thread");

        // Thread 2: Responsible for printing EVEN numbers
        Thread evenThread = new Thread(() -> {
            while (counter <= MAX_NUMBER) {
                // Synchronize on the same shared lock object
                synchronized (lock) {
                    
                    // If the counter is currently ODD, this thread must wait
                    // because it is the turn of the odd thread.
                    while (counter % 2 != 0) {
                        try {
                            // Releases the lock and suspends this thread until notified
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    
                    // Double-check condition before printing
                    if (counter <= MAX_NUMBER) {
                        System.out.println(Thread.currentThread().getName() + ": " + counter);
                        counter++; // Move to the next number (which will be Odd)
                    }
                    
                    // Notify the Odd thread that it can now wake up and check the condition
                    lock.notify();
                }
            }
        }, "Even-Thread");

        // Start both threads execution
        oddThread.start();
        evenThread.start();
    }
}