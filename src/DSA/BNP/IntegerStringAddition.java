package DSA.BNP;

/**
 * ============================================================================
 * QUESTION 2: Integer String Addition (BNP Paribas HackerRank)
 * ============================================================================
 *
 * PROBLEM DESCRIPTION:
 * Given two non-negative integers `num1` and `num2` represented as strings,
 * return the sum of `num1` and `num2` also represented as a string.
 * You must NOT use any built-in BigInteger library or convert the entire
 * inputs to integers directly.
 *
 * INPUT FORMAT:
 * Two strings `num1` and `num2` containing only numeric characters '0'-'9'.
 *
 * CONSTRAINTS:
 * - 1 <= num1.length, num2.length <= 10^4
 * - num1 and num2 consist of only digits 0-9.
 * - num1 and num2 do not contain any leading zeros except for zero itself.
 *
 * SAMPLE INPUT 0:
 *   num1 = "456"
 *   num2 = "77"
 *
 * SAMPLE OUTPUT 0:
 *   "533"
 *
 * EXPLANATION 0:
 *   456 + 77 = 533, returned as the string "533".
 *
 * ----------------------------------------------------------------------------
 * LOGIC & STEP-BY-STEP DRY RUN (num1 = "456", num2 = "77"):
 * ----------------------------------------------------------------------------
 * Initialize two pointers at the last index (least significant digit) of each
 * string: i = 2 ('6'), j = 1 ('7'), carry = 0, sb = "".
 *
 * Iteration 1: i = 2, j = 1, carry = 0
 *   d1 = '6' - '0' = 6
 *   d2 = '7' - '0' = 7
 *   sum = 6 + 7 + 0 = 13
 *   sb.append(13 % 10) -> sb = "3"
 *   carry = 13 / 10 = 1
 *   i -> 1, j -> 0
 *
 * Iteration 2: i = 1, j = 0, carry = 1
 *   d1 = '5' - '0' = 5
 *   d2 = '7' - '0' = 7
 *   sum = 5 + 7 + 1 = 13
 *   sb.append(13 % 10) -> sb = "33"
 *   carry = 13 / 10 = 1
 *   i -> 0, j -> -1
 *
 * Iteration 3: i = 0, j = -1 (num2 is exhausted, so d2 defaults to 0), carry = 1
 *   d1 = '4' - '0' = 4
 *   d2 = 0
 *   sum = 4 + 0 + 1 = 5
 *   sb.append(5 % 10) -> sb = "335"
 *   carry = 5 / 10 = 0
 *   i -> -1, j -> -2
 *
 * Loop terminates (i < 0 && j < 0 && carry == 0).
 * Reverse sb ("335") -> "533".
 *
 * Time Complexity:  O(max(N, M))
 * Space Complexity: O(max(N, M))
 * ============================================================================
 */
public class IntegerStringAddition {

    public static String addStrings(String num1, String num2) {
        StringBuilder sb = new StringBuilder();
        int i = num1.length() - 1; // Rightmost index of num1
        int j = num2.length() - 1; // Rightmost index of num2
        int carry = 0;

        // Loop while either string still has digits OR a carry remains
        while (i >= 0 || j >= 0 || carry > 0) {
            int digit1 = (i >= 0) ? num1.charAt(i) - '0' : 0;
            int digit2 = (j >= 0) ? num2.charAt(j) - '0' : 0;

            int sum = digit1 + digit2 + carry;
            sb.append(sum % 10); // Append units digit of current column sum
            carry = sum / 10;    // Carry over tens digit to next column

            i--;
            j--;
        }

        // Reverse because digits were collected from right-to-left
        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        System.out.println("Sample 0 (\"456\" + \"77\"): " + addStrings("456", "77"));   // Expected: 533
        System.out.println("Sample 1 (\"999\" + \"1\"):  " + addStrings("999", "1"));     // Expected: 1000
        System.out.println("Sample 2 (\"0\" + \"0\"):    " + addStrings("0", "0"));       // Expected: 0
    }
}