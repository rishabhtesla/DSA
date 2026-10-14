package DSA.JPMC.path.theory;

public class DatabaseConnectionManager {

    // 1. 'volatile' ensures that multiple threads handle the instance correctly
    // It prevents instruction reordering by the JVM and ensures visibility across threads.
    private static volatile DatabaseConnectionManager instance;

    // 2. Private constructor prevents other classes from using 'new DatabaseConnectionManager()'
    private DatabaseConnectionManager() {
        // Optional: Protection against Reflection API instantiation
        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to create");
        }
        System.out.println("Database Connection Initialized.");
    }

    // 3. Global access point
    public static DatabaseConnectionManager getInstance() {
        // Check 1: If instance is already created, skip the lock for performance
        if (instance == null) {
            
            // Lock the class block so only one thread can enter at a time
            synchronized (DatabaseConnectionManager.class) {
                
                // Check 2: Another thread might have created the instance while we were waiting for the lock
                if (instance == null) {
                    instance = new DatabaseConnectionManager();
                }
            }
        }
        return instance;
    }

    public void executeQuery(String query) {
        System.out.println("Executing: " + query);
    }
}