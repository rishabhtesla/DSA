package DSA.JPMC.path.theory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TryWithResourcesDemo {
    public static void main(String[] args) {
        
        // The resource (BufferedReader) is declared inside the parentheses of the try block.
        // Once the block finishes (normally or via exception), reader.close() is called automatically.
        try (BufferedReader reader = new BufferedReader(new FileReader("test.txt"))) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            
        } catch (IOException e) {
            // This catches both the exception from reading the file AND 
            // any exception thrown during the automatic closing of the resource.
            System.err.println("Error reading the file: " + e.getMessage());
        }
        
        // NO 'finally' block needed! 
    }
}