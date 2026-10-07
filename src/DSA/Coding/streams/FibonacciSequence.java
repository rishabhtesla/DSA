package DSA.Coding.streams;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FibonacciSequence {
    public static void main(String[] args) {
        
        // ====================================================================================
        // APPROACH 1: Using Stream.iterate() with a 2-element array state (As shown in the video)
        //
        // PROBLEM STATEMENT:
        // Generate the first 10 numbers of the Fibonacci Sequence using Java 8 Streams [00:00:00].
        //
        // STRATEGY:
        // 1. Initialize an infinite stream of pairs using an integer array `new int[]{0, 1}` 
        //    representing the current two numbers [00:04:12].
        // 2. Compute the next state pair iteratively: `t -> new int[]{t[1], t[0] + t[1]}` [00:04:41].
        // 3. Bound the infinite pipeline sequence using `.limit(10)` [00:04:59].
        // 4. Transform the array stream to numbers using `.map(t -> t[0])` to extract the index-0 
        //    Fibonacci element from each state snapshot [00:05:18, 00:05:57].
        // ====================================================================================

        List<Integer> approach1Result = Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]}) // Infinite stream of pairs [00:04:12]
                .limit(10)          // Caps stream loop safely at 10 items to prevent memory runaway [00:04:59]
                .map(t -> t[0])     // Extracting the actual computed Fibonacci value out of the pair state [00:05:57]
                .collect(Collectors.toList());

        System.out.println("Approach 1 (Video Stream.iterate Pair State Logic):");
        System.out.println(approach1Result); 
        // Output: [0, 1, 1, 2, 3, 5, 8, 13, 21, 34] [00:06:27]


        // ====================================================================================
        // APPROACH 2: Clean Alternative using custom record/state for Readability
        //
        // PROBLEM STATEMENT:
        // Same as above, but avoiding magic array index pointers (`t[0]` and `t[1]`) by substituting 
        // them with a readable state record to represent the sequence tracking snapshot logic cleanly.
        //
        // STRATEGY:
        // Iterate over a local immutable record structure storing explicit `current` and `next` named 
        // variables, enhancing long-term code maintainability.
        // ====================================================================================

        class FibState {
            final int current;
            final int next;
            FibState(int current, int next) {
                this.current = current;
                this.next = next;
            }
        }

        List<Integer> approach2Result = Stream.iterate(new FibState(0, 1), f -> new FibState(f.next, f.current + f.next))
                .limit(10)
                .map(f -> f.current)
                .collect(Collectors.toList());

        System.out.println("\nApproach 2 (Alternative Explicit Named State Object):");
        System.out.println(approach2Result);
        // Output: [0, 1, 1, 2, 3, 5, 8, 13, 21, 34]
    }
}