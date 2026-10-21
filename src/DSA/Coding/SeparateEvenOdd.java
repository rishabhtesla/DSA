package DSA.Coding;

/**
 * Partitions an integer array with even values first and odd values second.
 *
 * <p>Examples: {@code [1,2,3,4] -> [4,2,3,1]} (partition order is not
 * guaranteed) and {@code [2,4] -> [2,4]}. A straightforward filter/copy is
 * O(n) time and O(n) space; the two-pointer version runs in O(n) time and
 * O(1) extra space.</p>
 *
 * <p>At every iteration, the prefix before {@code left} is even and the
 * suffix after {@code right} is odd. Already-correct endpoints advance;
 * otherwise an odd left value and even right value are swapped. In the main
 * example, 45 and 8 are exchanged before the pointers continue. Ask whether
 * the relative order of even or odd values must be stable.</p>
 */
public class SeparateEvenOdd {
    public static void segregateEvenOdd(int[] arr) {
        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            // Increment left index if it is even
            while (arr[left] % 2 == 0 && left < right) {
                left++;
            }
            // Decrement right index if it is odd
            while (arr[right] % 2 != 0 && left < right) {
                right--;
            }
            // Swap if left is odd and right is even
            if (left < right) {
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
                left++;
                right--;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {12, 34, 45, 9, 8, 90, 3};
        segregateEvenOdd(arr);

        System.out.println("Rearranged Array (Evens first):");
        for (int val : arr) {
            System.out.print(val + " ");
        }
    }
}