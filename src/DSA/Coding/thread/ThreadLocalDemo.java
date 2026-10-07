package DSA.Coding.thread;

public class ThreadLocalDemo {
    // Each thread gets its own private String value
    private static final ThreadLocal<String> userContext = new ThreadLocal<>();

    public static void main(String[] args) {
        Runnable task1 = () -> {
            userContext.set("User_Alice_Token");
            System.out.println(Thread.currentThread().getName() + " sees: " + userContext.get());
            // CRITICAL: Always clean up in thread pools!
            userContext.remove();
        };

        Runnable task2 = () -> {
            userContext.set("User_Bob_Token");
            System.out.println(Thread.currentThread().getName() + " sees: " + userContext.get());
            userContext.remove();
        };

        new Thread(task1, "Worker-1").start();
        new Thread(task2, "Worker-2").start();
    }
}