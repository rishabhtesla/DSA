package DSA.Coding.thread;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class SumTask extends RecursiveTask<Long> {
    private final int[] numbers;
    private final int start;
    private final int end;
    private static final int THRESHOLD = 3; // Max size to compute sequentially

    public SumTask(int[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        // Base Condition: If task is small enough, compute directly
        if ((end - start) <= THRESHOLD) {
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += numbers[i];
            }
            return sum;
        }

        // Recursive Step: Split the task in half (Divide)
        int mid = start + (end - start) / 2;
        SumTask leftSubtask = new SumTask(numbers, start, mid);
        SumTask rightSubtask = new SumTask(numbers, mid, end);

        // Fork: Run left task in background
        leftSubtask.fork();

        // Compute right task directly on current thread, then join left
        long rightResult = rightSubtask.compute();
        long leftResult = leftSubtask.join();

        // Combine (Conquer)
        return leftResult + rightResult;
    }
}

public class ForkJoinDemo {
    public static void main(String[] args) {
        int[] data = {1, 2, 3, 4, 5, 6, 7, 8};
        
        ForkJoinPool pool = ForkJoinPool.commonPool();
        SumTask rootTask = new SumTask(data, 0, data.length);

        long totalSum = pool.invoke(rootTask);
        System.out.println("Total calculated sum: " + totalSum); // 36
    }
}