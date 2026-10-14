package DSA.JPMC.path.theory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// Custom Checked Exception (Extends Exception)
class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) { super(message); }
}

// Custom Unchecked Exception (Extends RuntimeException)
class InvalidTransactionException extends RuntimeException {
    public InvalidTransactionException(String message) { super(message); }
}

public class ExceptionHierarchyDemo {
    
    // Method declares a checked exception, forcing the caller to handle it
    public static void withdraw(double balance, double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            // Unchecked: Caller isn't forced to catch this, it's a code logic error
            throw new InvalidTransactionException("Withdraw amount must be positive"); 
        }
        if (amount > balance) {
            // Checked: Expected business logic failure, must be handled
            throw new InsufficientFundsException("Cannot withdraw " + amount + " from balance " + balance);
        }
        System.out.println("Withdrawal successful.");
    }

    public static void main(String[] args) {
        try {
            withdraw(100.0, 500.0);
        } catch (InsufficientFundsException e) {
            System.err.println("Recoverable business error: " + e.getMessage());
        } catch (InvalidTransactionException e) {
            System.err.println("Programming error: " + e.getMessage());
        }
        
        // Example of a core Java Checked Exception (File I/O)
        try {
            Scanner scanner = new Scanner(new File("missing_config.txt"));
        } catch (FileNotFoundException e) {
            System.err.println("File missing, falling back to default config.");
        }
    }
}