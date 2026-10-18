package DSA.JPMC.HR.FAQ;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ============================================================================
 * HACKERRANK QUESTION: Payload Dictionary Validator / Word Break
 * ============================================================================
 *
 * Problem Statement:
 * A financial payload parser receives a contiguous alphanumeric string s and a
 * dictionary of approved protocol tokens wordDict.
 *
 * Determine if s can be segmented into a space-separated sequence of one or more
 * dictionary tokens.
 *
 * You may reuse the same token in wordDict multiple times.
 *
 * Input Format for Custom Testing:
 * - Line 1: String s.
 * - Line 2: An integer m, denoting the size of wordDict.
 * - Next m lines: Each line contains a string representing a dictionary token.
 *
 * Constraints:
 * - 1 <= s.length() <= 300
 * - 1 <= wordDict.size() <= 1000
 * - 1 <= wordDict[i].length() <= 20
 * - s and wordDict[i] consist only of lowercase English letters.
 * - All tokens in wordDict are unique.
 *
 * Example 1:
 * Input:
 *   applepenapple
 *   2
 *   apple
 *   pen
 * Output:
 *   true
 * Explanation: "applepenapple" can be segmented as "apple pen apple".
 *
 * Example 2:
 * Input:
 *   catsandog
 *   5
 *   cats
 *   dog
 *   sand
 *   and
 *   cat
 * Output:
 *   false
 *
 * ============================================================================
 * LOGIC & APPROACH (Bottom-Up 1D Dynamic Programming):
 * ============================================================================
 * 1. State Definition:
 *    Let dp[i] be true if substring s[0...i-1] can be segmented into valid
 *    dictionary tokens, and false otherwise.
 *
 * 2. Base Case:
 *    dp[0] = true (an empty prefix is trivially valid).
 *
 * 3. Transitions:
 *    For every ending position i from 1 to s.length():
 *      For every starting position j from 0 to i - 1:
 *        If dp[j] is true AND s.substring(j, i) exists in wordDict:
 *          dp[i] = true
 *          break (move to next i)
 *
 * 4. Optimization:
 *    Store wordDict in a HashSet for O(1) average lookup, and restrict j to
 *    max(0, i - maxWordLength) to avoid inspecting substrings longer than the
 *    longest word in the dictionary.
 *
 * Complexity:
 * - Time: O(n * L^2) where n = s.length(), L = maximum word length in dictionary.
 * - Space: O(n + total dictionary characters) for DP table and HashSet.
 *
 * ============================================================================
 * DRY RUN (s = "applepenapple", dict = {"apple", "pen"}):
 * ============================================================================
 * dp array size 14 initialized to false, dp[0] = true.
 * - i = 5:  j = 0 -> s[0..5] = "apple" (in dict), dp[0] is true -> dp[5] = true
 * - i = 8:  j = 5 -> s[5..8] = "pen" (in dict), dp[5] is true -> dp[8] = true
 * - i = 13: j = 8 -> s[8..13] = "apple" (in dict), dp[8] is true -> dp[13] = true
 *
 * Final dp[13] = true
 * Output: true
 * ============================================================================
 */
public class SolutionWordBreak {

    public static boolean wordBreak(String s, List<String> wordDict) {
        if (s == null || s.isEmpty()) {
            return false;
        }

        Set<String> dict = new HashSet<>(wordDict);
        int maxLen = 0;
        for (String w : wordDict) {
            maxLen = Math.max(maxLen, w.length());
        }

        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;

        for (int i = 1; i <= n; i++) {
            int startBound = Math.max(0, i - maxLen);
            for (int j = i - 1; j >= startBound; j--) {
                if (dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[n];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String s = bufferedReader.readLine();
        if (s == null || s.trim().isEmpty()) {
            return;
        }

        int m = Integer.parseInt(bufferedReader.readLine().trim());
        List<String> wordDict = new ArrayList<>(m);

        for (int i = 0; i < m; i++) {
            wordDict.add(bufferedReader.readLine().trim());
        }

        boolean result = wordBreak(s.trim(), wordDict);
        System.out.println(result);

        bufferedReader.close();
    }
}