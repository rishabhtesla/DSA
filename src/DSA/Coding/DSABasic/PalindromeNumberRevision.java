package DSA.Coding.DSABasic;

public class PalindromeNumberRevision {

    /*
    ================================================================================
    PROBLEM STATEMENT:
    Given an integer `x`, return true if `x` is a palindrome, and false otherwise.
    An integer is a palindrome when it reads the same backward as forward. 
    For example, 121 is a palindrome while 123 is not.

    APPROACH (Mathematical Reversal without String Conversion):
    1. Edge Case Handling: 
       - Any negative number (e.g., -121) cannot be a palindrome because the negative 
         sign '-' when reversed sits at the end (e.g., '121-'), which is invalid [00:01:54].
    2. Since we need to compare the reversed number with the original value `x`, 
       we duplicate `x` into a temporary variable `n`. Modifying `x` directly would 
       wipe out the reference point for comparison [00:08:29].
    3. Extract the last digit repeatedly using the modulo operator: `digit = n % 10` [00:03:12].
    4. Build the reversed number iteratively: `reversedNum = (reversedNum * 10) + digit` [00:04:09].
    5. Reduce `n` after stripping the last digit: `n = n / 10` [00:04:59].
    6. Continue the loop as long as `n > 0`. Finally, check if `reversedNum == x`.

    VISUAL DRY RUN EXAMPLES:
    ----------------------------------------------------------------------------
    Example 1: x = 121
    - Step 0: n = 121, reversedNum = 0
    - Loop 1 (n = 121):
        digit = 121 % 10 = 1                                         [00:06:44]
        reversedNum = (0 * 10) + 1 = 1                               [00:06:50]
        n = 121 / 10 = 12                                            [00:06:56]
    - Loop 2 (n = 12):
        digit = 12 % 10 = 2                                          [00:07:09]
        reversedNum = (1 * 10) + 2 = 12                              [00:07:22]
        n = 12 / 10 = 1                                              [00:07:31]
    - Loop 3 (n = 1):
        digit = 1 % 10 = 1                                           [00:07:41]
        reversedNum = (12 * 10) + 1 = 121                            [00:07:51]
        n = 1 / 10 = 0                                               [00:07:59]
    - Terminate: n is 0. 
    - Compare: reversedNum (121) == x (121) -> returns true         [00:08:17]

    TIME COMPLEXITY: O(log10(N)) - The number of digits in integer N dictates the loop length.
    SPACE COMPLEXITY: O(1) - The space scale remains completely constant without using arrays or strings.
    ================================================================================
    */
    public static boolean isPalindrome(int x) {
        // All negative numbers are not palindromes [00:02:24]
        if (x < 0) {
            return false;
        }

        // Duplicate x into variable 'n' to retain original 'x' for checking [00:04:21]
        int n = x;
        int reversedNum = 0;

        // Strip and reverse math loop [00:04:32]
        while (n > 0) {
            int digit = n % 10;
            reversedNum = (reversedNum * 10) + digit;
            n = n / 10;
        }

        // Return comparison outcome [00:05:12]
        return reversedNum == x;
    }

    // Helper method to visually display test summaries 
    private static void printTestResult(int input, boolean expected) {
        boolean actual = isPalindrome(input);
        System.out.println("Input Number   : " + input);
        System.out.println("Expected Output: " + expected);
        System.out.println("Actual Output  : " + actual);
        System.out.println("Status         : " + (actual == expected ? "PASS ✅" : "FAIL ❌"));
        System.out.println("--------------------------------------------------");
    }

    public static void main(String[] args) {
        System.out.println("=== LEETCODE 9: PALINDROME NUMBER ===\n");

        // Case 1: Standard positive palindrome [00:00:43]
        printTestResult(121, true);

        // Case 2: Standard negative non-palindrome [00:00:57]
        printTestResult(-121, false);

        // Case 3: Positive number trailing with zero [00:01:10]
        printTestResult(10, false);

        // Case 4: Single digit boundary case (always a palindrome)
        printTestResult(7, true);

        // Case 5: Large non-palindrome matching internal ranges
        printTestResult(123431, false);
    }
}