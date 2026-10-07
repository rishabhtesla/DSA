package DSA.Coding;

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