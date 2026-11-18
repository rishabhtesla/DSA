package DSA.BNP;

import java.util.*;

/**
 * ============================================================================
 * QUESTION 1: Polynomial String to Coefficient Array (BNP Paribas HackerRank)
 * ============================================================================
 *
 * PROBLEM DESCRIPTION:
 * You are given a mathematical polynomial expression represented as a string
 * `poly` in variable `x`. Your task is to parse the expression and return a
 * list/array of strings representing the coefficients [a_0, a_1, ..., a_n],
 * where a_i is the coefficient of x^i and n is the highest degree of the
 * polynomial. Each coefficient must be formatted to 5 decimal places.
 *
 * INPUT FORMAT:
 * A single string `poly` containing terms separated by '+' or '-' operators,
 * constants, 'x', and powers 'x^k'.
 *
 * CONSTRAINTS:
 * - 1 <= poly.length <= 1000
 * - Exponents k are non-negative integers (0 <= k <= 50).
 * - Coefficients can be integers or decimals.
 *
 * SAMPLE INPUT 0:
 *   x^2 - 3x^3 + 4
 *
 * SAMPLE OUTPUT 0:
 *   [4.00000, 0.00000, 1.00000, -3.00000]
 *
 * EXPLANATION 0:
 * - Constant term (x^0):  4  -> 4.00000
 * - Linear term (x^1):   missing -> 0.00000
 * - Quadratic term (x^2): 1  -> 1.00000
 * - Cubic term (x^3):    -3  -> -3.00000
 *
 * ----------------------------------------------------------------------------
 * LOGIC & STEP-BY-STEP DRY RUN (Input: "x^2 - 3x^3 + 4"):
 * ----------------------------------------------------------------------------
 * 1. Strip all whitespace:
 *      "x^2 - 3x^3 + 4"  -->  "x^2-3x^3+4"
 * 2. Replace "-" with "+-" (after index 0) so splitting on "\\+" keeps signs:
 *      "x^2+-3x^3+4"  -->  split("\\+")  -->  ["x^2", "-3x^3", "4"]
 * 3. Parse each token into (exponent, coefficient):
 *      - Token "x^2":   contains 'x' and '^'. Part before 'x' is "" -> coeff = 1.0, exp = 2
 *      - Token "-3x^3": contains 'x' and '^'. Part before 'x' is "-3" -> coeff = -3.0, exp = 3
 *      - Token "4":     no 'x'. Constant term -> coeff = 4.0, exp = 0
 * 4. Track highest exponent seen: maxExp = 3.
 * 5. Build result from degree 0 to 3:
 *      - i = 0 -> map.getOrDefault(0, 0.0) = 4.0   -> "4.00000"
 *      - i = 1 -> map.getOrDefault(1, 0.0) = 0.0   -> "0.00000"
 *      - i = 2 -> map.getOrDefault(2, 0.0) = 1.0   -> "1.00000"
 *      - i = 3 -> map.getOrDefault(3, 0.0) = -3.0  -> "-3.00000"
 *
 * Time Complexity:  O(N) where N is length of string
 * Space Complexity: O(D) where D is maximum polynomial degree
 * ============================================================================
 */
public class PolynomialToCoefficients {

    public static List<String> parsePolynomial(String poly) {
        // Map stores: Exponent -> Summed Coefficient (handles duplicate degrees like "2x + 3x")
        Map<Integer, Double> termMap = new HashMap<>();
        int maxExp = 0;

        // Step 1: Remove all spaces
        String cleaned = poly.replaceAll("\\s+", "");
        if (cleaned.isEmpty()) return Collections.emptyList();

        // Step 2: Normalize '-' into '+-' (preserving any leading '-' at index 0)
        String normalized = cleaned.charAt(0) + cleaned.substring(1).replace("-", "+-");
        String[] terms = normalized.split("\\+");

        // Step 3: Parse coefficient and exponent for every term
        for (String term : terms) {
            if (term.isEmpty()) continue;

            double coeff;
            int exp;

            if (!term.contains("x")) {
                // Constant term (e.g., "4" or "-7.5") -> degree is 0
                coeff = Double.parseDouble(term);
                exp = 0;
            } else {
                // Variable term (e.g., "x", "-x", "3x", "-3x^3", "x^2")
                int xIndex = term.indexOf('x');
                String coeffPart = term.substring(0, xIndex);

                if (coeffPart.isEmpty() || coeffPart.equals("+")) {
                    coeff = 1.0;
                } else if (coeffPart.equals("-")) {
                    coeff = -1.0;
                } else {
                    coeff = Double.parseDouble(coeffPart);
                }

                if (term.contains("^")) {
                    exp = Integer.parseInt(term.substring(term.indexOf('^') + 1));
                } else {
                    exp = 1;
                }
            }

            maxExp = Math.max(maxExp, exp);
            termMap.put(exp, termMap.getOrDefault(exp, 0.0) + coeff);
        }

        // Step 4: Format coefficients from x^0 up to x^maxExp to 5 decimal places
        List<String> result = new ArrayList<>();
        for (int i = 0; i <= maxExp; i++) {
            double val = termMap.getOrDefault(i, 0.0);
            // Prevent "-0.00000" formatting artifact
            if (Math.abs(val) < 1e-9) val = 0.0;
            result.add(String.format(Locale.US, "%.5f", val));
        }

        return result;
    }

    public static void main(String[] args) {
        String sample0 = "x^2 - 3x^3 + 4";
        System.out.println("Sample 0 Input:  " + sample0);
        System.out.println("Sample 0 Output: " + parsePolynomial(sample0));
        // Expected: [4.00000, 0.00000, 1.00000, -3.00000]

        String sample1 = "-x + 2.5x^4 - 3 + 4x";
        System.out.println("\nSample 1 Input:  " + sample1);
        System.out.println("Sample 1 Output: " + parsePolynomial(sample1));
        // Expected: [-3.00000, 3.00000, 0.00000, 0.00000, 2.50000]
    }
}