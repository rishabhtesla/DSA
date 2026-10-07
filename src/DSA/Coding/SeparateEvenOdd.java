package DSA.Coding;

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