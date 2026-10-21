package DSA.JPMC.path;

/**
 * PROBLEM: Reverse-add Palindrome
 * Take a number, reverse its digits, and add it to the original. Repeat until the sum 
 * becomes a palindrome. Print the palindrome and the number of iterations.
 * 
 * EXAMPLE: 
 * Input: 195 
 * Output: 9339 (Steps: 195+591=786 -> 786+687=1473 -> 1473+3741=5214 -> 5214+4125=9339)
 * 
 * LOGIC:
 * Use a helper function to reverse a number (using StringBuilder for ease) and another 
 * to check if it is a palindrome. Use 'long' to prevent integer overflow during addition.
 * Note: Some numbers (Lychrel numbers like 196) may never form a palindrome, so cap iterations.
 * 
 * COMPLEXITY: Time: O(K * D) where K is iterations and D is number of digits. Space: O(D).
 */
/**
 * Learning supplement: each reverse/add step costs O(D) for D digits, so K
 * iterations cost O(KD) time and O(D) temporary space. Examples: 195 reaches
 * 9339 after four additions; an already palindromic 121 needs zero additions.
 * Invariant: current is the result after exactly iterations reverse-add
 * operations. The main run advances 195->786->1473->5214->9339. Ask why a
 * maximum iteration guard is needed for numbers with no observed palindrome.
 */
public class ReverseAddPalindrome {
    
    // Helper to reverse the number
    private static long reverseNumber(long num) {
        String reversedStr = new StringBuilder(String.valueOf(num)).reverse().toString();
        return Long.parseLong(reversedStr);
    }
    
    // Helper to check palindrome
    private static boolean isPalindrome(long num) {
        return num == reverseNumber(num);
    }

    public static void findPalindrome(long num) {
        long current = num;
        int iterations = 0;
        int MAX_ITERATIONS = 1000; // Guard against infinite loops (e.g., Lychrel numbers)
        
        while (!isPalindrome(current) && iterations < MAX_ITERATIONS) {
            long reversed = reverseNumber(current);
            current = current + reversed;
            iterations++;
        }
        
        if (iterations == MAX_ITERATIONS) {
            System.out.println("No palindrome found within limit for: " + num);
        } else {
            System.out.println("Original: " + num + " -> Palindrome: " + current + " in " + iterations + " steps.");
        }
    }

    public static void main(String[] args) {
        findPalindrome(195); // Output: Palindrome: 9339 in 4 steps
    }
}