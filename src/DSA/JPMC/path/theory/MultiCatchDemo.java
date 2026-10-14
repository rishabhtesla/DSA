package DSA.JPMC.path.theory;

public class MultiCatchDemo {
    public static void main(String[] args) {
        try {
            int[] numbers = {1, 2, 3};
            // This will cause an ArrayIndexOutOfBoundsException
            System.out.println(numbers[5]); 
            
            // This would cause an ArithmeticException (if it reached here)
            int result = 10 / 0; 
            
        } 
        // Catching multiple unrelated exceptions in a single block using the '|' operator
        catch (ArrayIndexOutOfBoundsException | ArithmeticException e) {
            
            // The exception parameter 'e' is implicitly 'final' in a multi-catch block.
            // You cannot reassign it (e.g., e = new Exception() is illegal).
            System.err.println("A mathematical or array index error occurred: " + e.getMessage());
            
        } catch (Exception e) {
            // General fallback for anything else
            System.err.println("Some other error occurred");
        }
    }
}