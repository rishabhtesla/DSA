package DSA.Coding.DSABasic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AddToArrayFormRevision {

    /*
    ================================================================================
    PROBLEM STATEMENT:
    The array-form of an integer `num` is an array representing its digits in left-to-right order.
    For example, for `num = 1321`, the array form is `[1,3,2,1]`.
    Given `num`, the array-form of an integer, and an integer `k`, return the array-form 
    of the integer `num + k`.

    EXAMPLES & EXPLANATION:
    ----------------------------------------------------------------------------
    Example A: num = [1, 2, 0, 0], k = 34 [00:01:14]
    - Direct addition: 1200 + 34 = 1234
    - Output Array Form: [1, 2, 3, 4]

    Example B: num = [2, 7, 4], k = 181 [00:01:46]
    - Direct addition: 274 + 181 = 455
    - Output Array Form: [4, 5, 5]

    CRITICAL CONSTRAINT NOTE [00:03:37]:
    The length of `num` can be up to 10,000 digits. Standard types like `int` (up to 9 digits) 
    or `long` (up to 19 digits) will immediately throw overflow errors if you try to 
    convert the array directly into a single primitive number. We must process digit by digit.

    APPROACH 1: Inplace Simulation with List Reversal (As shown in video)
    - Set a pointer `p` at the last index of `num` (`num.length - 1`) [00:05:14].
    - Maintain a variable `carry = 0` [00:09:23].
    - Loop continuously as long as `p >= 0` OR `k > 0` [00:09:33].
    - Inside the loop:
        1. Extract the current element digit from `num[p]` if `p >= 0`, otherwise treat it as `0` [00:11:54].
        2. Extract the last digit from `k` using `k % 10` [00:13:00].
        3. Sum these digits along with the `carry` [00:13:20].
        4. Append `sum % 10` to the result list [00:13:53].
        5. Update `carry = sum / 10` [00:13:44].
        6. Shift pointer `p` leftward (`p--`) and chop off the last digit of `k` (`k /= 10`) [00:14:04].
    - After the loop, if any `carry > 0` remains, append it to the list [00:20:58].
    - Since items are gathered backwards from the rightmost positions, reverse the entire collection using `Collections.reverse()` before returning [00:21:12].

    LOGIC BEHIND THIS APPROACH:
    ----------------------------------------------------------------------------
    1. Elementary Math Emulation: This approach structurally mirrors how humans perform 
       columnar addition on paper: working strictly from right to left (lowest place 
       value to highest place value) and maintaining a carry register.
    2. Decoupled Digit Extraction: 
       - For the array `num`, an explicit tracking pointer (`p`) steps backward index by index.
       - For the scalar integer `k`, base-10 arithmetic is used: `k % 10` slices off the unit digit 
         for immediate addition, while `k / 10` strips that unit digit away to prepare for the 
         next cycle.
    3. Structural Size Asymmetry Handling: The `OR` condition (`p >= 0 || k > 0`) allows the loop 
       to handle cases where one input has vastly more digits than the other. If the array pointer 
       runs out of bounds (`p < 0`) but `k` still contains value data (or vice versa), the missing 
       digit defaults to `0`, allowing addition to continue seamlessly without causing NullPointer or 
       IndexOutOfBounds exceptions.
    4. Base-10 Arithmetic Clamping: Because a single array position can only hold a single digit (0-9), 
       the modulo operator (`sum % 10`) drops the tens component, isolating the exact unit value for 
       the current column. The division operator (`sum / 10`) isolates the tens component, forwarding it 
       as a mathematical `carry` to the next higher place-value column.
    5. Appending Complexity Optimization: Dynamically inserting elements at the beginning of an ArrayList 
       forces a linear array copy operation ($O(N)$) each time to shift existing elements. By always appending 
       to the *end* of the list ($O(1)$) and executing a single $O(N)$ collection reversal at the very end, 
       we dramatically optimize the overall performance runtime.

    VISUAL DRY RUN (Simulation Strategy for num = [9, 6, 1], k = 532):
    ----------------------------------------------------------------------------
    - Initial: p = 2, carry = 0, ans = []
    - Loop 1 (p = 2, k = 532):
        numVal = num[2] = 1;  kDigit = 532 % 10 = 2
        sum = 1 + 2 + 0 = 3
        ans.add(3 % 10) -> ans = [3]
        carry = 3 / 10 = 0;   p = 1;  k = 532 / 10 = 53
    - Loop 2 (p = 1, k = 53):
        numVal = num[1] = 6;  kDigit = 53 % 10 = 3
        sum = 6 + 3 + 0 = 9
        ans.add(9 % 10) -> ans = [3, 9]
        carry = 9 / 10 = 0;   p = 0;  k = 53 / 10 = 5
    - Loop 3 (p = 0, k = 5):
        numVal = num[0] = 9;  kDigit = 5 % 10 = 5
        sum = 9 + 5 + 0 = 14
        ans.add(14 % 10) -> ans = [3, 9, 4]
        carry = 14 / 10 = 1;  p = -1; k = 5 / 10 = 0
    - Loop terminates because p = -1 and k = 0.
    - Post-Loop Check: carry is 1 -> ans.add(1) -> ans = [3, 9, 4, 1] [00:20:26]
    - Reverse Call: Collections.reverse(ans) -> ans = [1, 4, 9, 3]    [00:20:35]

    TIME COMPLEXITY: O(max(N, log K)) - Where N is the array size.
    SPACE COMPLEXITY: O(max(N, log K)) - Auxiliary space required to store the output collection.
    ================================================================================
    */
    public static List<Integer> addToArrayFormSimulation(int[] num, int k) {
        List<Integer> ans = new ArrayList<>();
        int p = num.length - 1;
        int carry = 0;

        // Continue processing if digits exist in array OR parts remain in k [00:09:33]
        while (p >= 0 || k > 0) {
            int numVal = 0;
            if (p >= 0) {
                numVal = num[p]; // Extract digit from array if valid [00:11:59]
            }

            int kDigit = k % 10; // Extract last digit from k [00:13:00]
            int sum = numVal + kDigit + carry; // Compute sum [00:13:20]

            ans.add(sum % 10); // Record rightmost single unit digit [00:13:53]
            carry = sum / 10;  // Carry over the remaining tens value [00:13:44]

            p--;      // Advance pointer leftward [00:14:04]
            k /= 10;  // Trim processed digit out of k [00:14:11]
        }

        // If a lingering carry value exists after processing both sources [00:20:58]
        if (carry > 0) {
            ans.add(carry);
        }

        // Flip the inverted output collection to establish normal order [00:21:12]
        Collections.reverse(ans);
        return ans;
    }

    /*
    ================================================================================
    APPROACH 2: K-Injected Accumulation (Alternative Strategy)
    - Instead of extracting digits out of both elements and tracking carry values, 
      inject the array element values straight into `k`.
    - At each position `num[i]`, add it to `k`, append `k % 10` to the result, and set `k = k / 10`.
    - This implicitly handles the carry calculation inside the `k` variable.
    - TIME COMPLEXITY: O(max(N, log K))
    - SPACE COMPLEXITY: O(max(N, log K))
    ================================================================================
    */
    public static List<Integer> addToArrayFormKInjection(int[] num, int k) {
        List<Integer> ans = new ArrayList<>();
        int i = num.length - 1;

        // Loop while array values remain OR k still holds accumulated scalar data
        while (i >= 0 || k > 0) {
            if (i >= 0) {
                k += num[i];
                i--;
            }
            ans.add(k % 10);
            k /= 10;
        }

        Collections.reverse(ans);
        return ans;
    }

    private static void verifyBothApproaches(int[] num, int k, List<Integer> expected) {
        System.out.println("Input Array: " + Arrays.toString(num) + " | K: " + k);
        System.out.println("Expected   : " + expected);
        System.out.println("1. Video Simulation : " + addToArrayFormSimulation(num, k));
        System.out.println("2. K-Injection Math : " + addToArrayFormKInjection(num, k));
        System.out.println("Status              : " +
                (addToArrayFormSimulation(num, k).equals(expected) &&
                        addToArrayFormKInjection(num, k).equals(expected) ? "PASS ✅" : "FAIL ❌"));
        System.out.println("--------------------------------------------------");
    }

    public static void main(String[] args) {
        System.out.println("=== LEETCODE 989: ADD TO ARRAY-FORM OF INTEGER ===\n");

        // Case 1: Standard basic validation [00:01:14]
        verifyBothApproaches(new int[]{1, 2, 0, 0}, 34, List.of(1, 2, 3, 4));

        // Case 2: Multi-digit index expansion with carry shifts [00:01:46]
        verifyBothApproaches(new int[]{2, 7, 4}, 181, List.of(4, 5, 5));

        // Case 3: K value digit size scales larger than target array bounds
        verifyBothApproaches(new int[]{9}, 99, List.of(1, 0, 8));

        // Case 4: Edge Case - Multi-digit carry expands array bounds
        verifyBothApproaches(new int[]{9, 9, 9}, 1, List.of(1, 0, 0, 0));
    }
}