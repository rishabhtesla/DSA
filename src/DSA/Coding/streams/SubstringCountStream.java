package DSA.Coding.streams;

import java.util.stream.IntStream;

public class SubstringCountStream {
    public static void main(String[] args) {
        // Setup initial input scenarios matching the video context [00:00:14, 00:01:48]
        String str1 = "bybirdiebyby";
        String target1 = "by"; // Video first checks "by" but later tests length-2/length-3 scenarios [00:00:07, 00:01:57]

        String str2 = "XOXObirdie";
        String target2 = "XO";

        // ====================================================================================
        // APPROACH 1: Using IntStream.range() and string.substring() windowing (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Given a main string, print the absolute count of occurrences of a particular substring 
        // using the Java 8 Stream API [00:00:07].
        //
        // STRATEGY:
        // 1. Calculate safe execution upper bounds using `str.length() - target.length() + 1`.
        //    *Note: The video calculates this dynamically as `len - (patternLength - 1)` to prevent 
        //    IndexOutOfBoundsExceptions while extracting characters downstream [00:00:41, 00:02:05].
        // 2. Generate starting index frames using `IntStream.range(0, dynamicEndBound)` [00:00:30].
        // 3. Extract the moving sliding window chunks using `str.substring(x, x + target.length())` [00:01:08, 00:01:21].
        // 4. Verify structural matching configurations via `.filter(sub -> sub.equals(target))` [00:01:21].
        // 5. Aggregate aggregate matches terminal counts via `.count()` [00:01:32].
        // ====================================================================================

        // Test Scenario A: "bybirdiebyby" searching for "by" -> Expects 3 [00:00:14]
        long count1 = IntStream.range(0, str1.length() - target1.length() + 1) // Safeguards index bounds [00:00:41]
                .filter(i -> str1.substring(i, i + target1.length()).equals(target1)) // Extracts & evaluates chunks [00:01:21]
                .count(); // Terminal reduction count [00:01:32]

        System.out.println("Approach 1 (Video IntStream Windowing Logic):");
        System.out.println("Count of '" + target1 + "' in '" + str1 + "' = " + count1); 


        // Test Scenario B: "XOXObirdie" searching for "XO" -> Expects 2 [00:01:48]
        long count2 = IntStream.range(0, str2.length() - target2.length() + 1)
                .filter(i -> str2.substring(i, i + target2.length()).equals(target2))
                .count();

        System.out.println("Count of '" + target2 + "' in '" + str2 + "' = " + count2);


        // ====================================================================================
        // APPROACH 2: Declarative Regular Expression Split (Alternative Best Practice)
        //
        // PROBLEM STATEMENT:
        // Same calculation metrics, but utilizing declarative string splits instead of tracking 
        // index indices calculations which can accidentally trigger off-by-one pointer errors.
        //
        // STRATEGY:
        // By appending a trailing placeholder symbol and splitting by the target token, the 
        // calculation matches `splitArray.length - 1`. This provides an elegant non-indexed 
        // shortcut strategy for distinct matching boundaries.
        // ====================================================================================

        System.out.println("\nApproach 2 (Alternative Declarative Regex Splitting):");
        System.out.println("Count of 'by': " + countSubstringRegex(str1, target1));
        System.out.println("Count of 'XO': " + countSubstringRegex(str2, target2));
    }

    private static int countSubstringRegex(String text, String target) {
        if (text == null || target == null || target.isEmpty()) return 0;
        // Append dummy character to ensure trailing targets aren't dropped by default split actions
        String wrapped = text + "_"; 
        return wrapped.split(target, -1).length - 1;
    }
}