package DSA.Coding;

/**
 * Partitions an integer array so negative values precede non-negative values.
 *
 * <p>Examples: {@code [-1,2,-3,4] -> [-1,-3,2,4]} (order within partitions
 * is not guaranteed) and {@code [1,3] -> [1,3]}. A stable copy/filter needs
 * O(n) extra space; scanning from both ends partitions in O(n) time and O(1)
 * extra space.</p>
 *
 * <p>The left/right invariant is that values already outside the active range
 * are in their required partition. When the left value is non-negative and
 * the right value is negative, swap them; otherwise advance the pointer whose
 * side is already correct. In the main input, the first positive is swapped
 * with a later negative, then the active range shrinks. Ask whether stability
 * is required.</p>
 */
public class MoveNegatives {
    public static void separateNegativeAndPositive(int[] arr) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            // Condition 1: Left and Right both negative
            if (arr[left] < 0 && arr[right] < 0) {
                left++;
            }
            // Condition 2: Left positive and Right negative (Swap needed)
            else if (arr[left] >= 0 && arr[right] < 0) {
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
                left++;
                right--;
            }
            // Condition 3: Left and Right both positive
            else if (arr[left] >= 0 && arr[right] >= 0) {
                right--;
            }
            // Condition 4: Left negative and Right positive (Already correct)
            else {
                left++;
                right--;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {-12, 11, -13, -5, 6, -7, 5, -3, -6};
        separateNegativeAndPositive(arr);
        
        System.out.println("Rearranged Array (Negatives first):");
        for (int val : arr) {
            System.out.print(val + " ");
        }
    }
}