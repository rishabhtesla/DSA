package DSA.Coding;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FirstRepeatedCharacter {

    public static void main(String[] args) {
        String input = "swiss";
        System.out.println("Input String: \"" + input + "\"\n");

        System.out.println("====== JAVA 8 APPROACHES ======");
        // Approach 1: Using IntStream and LinkedHashMap (Keeps entry order)
        Character j8Result = findFirstRepeatedJava8(input);
        System.out.println("1. Stream + Frequency Map: " + (j8Result != null ? "'" + j8Result + "'" : "None"));

        // Approach 2: Using Stream and a Set filter (More efficient Stream approach)
        Character j8SetResult = findFirstRepeatedJava8Set(input);
        System.out.println("2. Stream + HashSet Filter: " + (j8SetResult != null ? "'" + j8SetResult + "'" : "None"));


        System.out.println("\n====== PRE-JAVA 8 APPROACHES ======");
        // Approach 3: Classic HashSet (Fastest & most intuitive)
        Character classicResult = findFirstRepeatedClassic(input);
        System.out.println("3. Classic HashSet Lookup: " + (classicResult != null ? "'" + classicResult + "'" : "None"));

        // Approach 4: Nested Loops (No extra memory, but slower O(n²) time)
        Character loopResult = findFirstRepeatedLoops(input);
        System.out.println("4. Classic Nested Loops  : " + (loopResult != null ? "'" + loopResult + "'" : "None"));
    }

    // =========================================================================
    // SECTION 1: JAVA 8 APPROACHES
    // =========================================================================

    /**
     * Java 8 Approach 1: Stream + LinkedHashMap
     * Maps characters to their frequency count while preserving string order.
     */
    public static Character findFirstRepeatedJava8(String str) {
        if (str == null || str.isEmpty()) return null;

        return str.chars() // Returns an IntStream of character codes
                .mapToObj(c -> (char) c) // Converts int to Character object
                .collect(Collectors.groupingBy(
                        Function.identity(), 
                        LinkedHashMap::new, // Crucial: Keeps the characters in their original sequence
                        Collectors.counting()
                )) // Resulting Map: {s=3, w=1, i=1}
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 1) // Keeps only repeated items
                .map(Map.Entry::getKey)
                .findFirst() // Returns the very first one that matched the filter
                .orElse(null);
    }

    /**
     * Java 8 Approach 2: Stream + Filter via HashSet
     * Highly efficient Java 8 pipeline that exits early as soon as a duplicate is hit.
     */
    public static Character findFirstRepeatedJava8Set(String str) {
        if (str == null || str.isEmpty()) return null;

        Set<Character> seen = new HashSet<>();
        
        return str.chars()
                .mapToObj(c -> (char) c)
                .filter(c -> !seen.add(c)) // If seen.add returns false, ! makes it true (found duplicate)
                .findFirst() // Short-circuits immediately on the first match
                .orElse(null);
    }

    // =========================================================================
    // SECTION 2: PRE-JAVA 8 APPROACHES
    // =========================================================================

    /**
     * Pre-Java 8 Approach 3: Classic HashSet Lookup
     * Time Complexity: O(n) | Space Complexity: O(n)
     * Traverses the string linearly. If a character is already in the set, it's our first duplicate.
     */
    public static Character findFirstRepeatedClassic(String str) {
        if (str == null || str.isEmpty()) return null;

        Set<Character> seen = new HashSet<Character>();

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            // If the character cannot be added, it means it already exists in the set
            if (!seen.add(ch)) {
                return ch; // Found our first duplicate! Exit early.
            }
        }
        return null; // No repeated characters found
    }

    /**
     * Pre-Java 8 Approach 4: Nested Loops (Brute Force)
     * Time Complexity: O(n²) | Space Complexity: O(1)
     * Scans forward from every character to check if it appears again.
     */
    public static Character findFirstRepeatedLoops(String str) {
        if (str == null || str.isEmpty()) return null;

        // Outer loop tracks the candidate character
        for (int i = 0; i < str.length(); i++) {
            char current = str.charAt(i);
            
            // Inner loop scans ahead to see if 'current' appears again
            for (int j = i + 1; j < str.length(); j++) {
                if (current == str.charAt(j)) {
                    return current; // Found it
                }
            }
        }
        return null;
    }
}