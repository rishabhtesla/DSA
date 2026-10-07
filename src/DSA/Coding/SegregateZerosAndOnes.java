package DSA.Coding;

public class SegregateZerosAndOnes {
    public static void segregate(int[] arr) {
        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            // Move left pointer forward if it points to 0
            while (arr[left] == 0 && left < right) {
                left++;
            }
            // Move right pointer backward if it points to 1
            while (arr[right] == 1 && left < right) {
                right--;
            }
            // Swap if left points to 1 and right points to 0
            if (left < right) {
                arr[left] = 0;
                arr[right] = 1;
                left++;
                right--;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {0, 1, 0, 1, 1, 1, 0, 0, 1, 0};
        segregate(arr);

        System.out.println("Rearranged Binary Array:");
        for (int val : arr) {
            System.out.print(val + " ");
        }
    }
}