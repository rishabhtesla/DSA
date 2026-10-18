package DSA.JPMC.HR;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * ============================================================================
 * HACKERRANK QUESTION 15: Continuous Median of Financial Trade Stream
 * ============================================================================
 *
 * Problem Statement:
 * The median is the middle value in an ordered integer list. If the size of the
 * list is even, there is no single middle value, and the median is the arithmetic
 * mean of the two middle values.
 *
 * Design a streaming data structure that receives trade prices one by one and
 * dynamically computes the running median after each insertion.
 *
 * Implement the MedianFinder class:
 * - void addNum(int num): Adds integer num from the data stream.
 * - double findMedian(): Returns the current median of all elements.
 *
 * Input Format for Custom Testing:
 * - Line 1: An integer n, denoting the number of incoming stream elements.
 * - Next n lines: Each line contains an integer num added to the stream.
 *
 * Constraints:
 * - 1 <= n <= 10^5
 * - -10^5 <= num <= 10^5
 * - Output each median rounded to one decimal place.
 *
 * Example:
 * Input:
 *   3
 *   1
 *   2
 *   3
 * Output:
 *   1.0
 *   1.5
 *   2.0
 * Explanation:
 * - Stream [1]       -> Median = 1.0
 * - Stream [1, 2]    -> Median = (1 + 2) / 2.0 = 1.5
 * - Stream [1, 2, 3] -> Median = 2.0
 *
 * ============================================================================
 * LOGIC & APPROACH (Dual Heap Balancing):
 * ============================================================================
 * 1. Concept:
 *    Divide the ordered stream into two halves:
 *    - Lower Half: stored in a Max-Heap (`maxHeapLower`).
 *    - Upper Half: stored in a Min-Heap (`minHeapUpper`).
 *
 * 2. Invariants:
 *    - Every element in maxHeapLower <= every element in minHeapUpper.
 *    - Size property: maxHeapLower.size() is either equal to minHeapUpper.size(),
 *      or exactly 1 greater than minHeapUpper.size().
 *
 * 3. Step-by-Step Insertion (addNum):
 *    - Always offer num into `maxHeapLower`.
 *    - Move maxHeapLower's largest element into `minHeapUpper` to preserve the
 *      order invariant.
 *    - If `minHeapUpper.size() > maxHeapLower.size()`, balance sizes by moving
 *      minHeapUpper's smallest element back into `maxHeapLower`.
 *
 * 4. Finding Median (findMedian):
 *    - If maxHeapLower has more elements -> return maxHeapLower.peek().
 *    - If sizes are equal -> return (maxHeapLower.peek() + minHeapUpper.peek()) / 2.0.
 *
 * Complexity:
 * - addNum: O(log n) heap insertions
 * - findMedian: O(1) top-of-heap peek
 * - Space: O(n) auxiliary memory
 *
 * ============================================================================
 * DRY RUN (Stream = [1, 2, 3]):
 * ============================================================================
 * 1. addNum(1):
 *    - maxHeapLower.offer(1) -> [1]
 *    - minHeapUpper.offer(maxHeapLower.poll()) -> maxHeap=[], minHeap=[1]
 *    - minHeap.size() > maxHeap.size() -> balance: maxHeap.offer(minHeap.poll())
 *    - State: maxHeap=[1], minHeap=[]
 *    - findMedian() -> maxHeap has 1, minHeap has 0 -> returns 1.0
 *
 * 2. addNum(2):
 *    - maxHeap.offer(2) -> [2, 1]
 *    - minHeap.offer(maxHeap.poll()) -> maxHeap=[1], minHeap=[2]
 *    - Sizes equal -> balance not needed.
 *    - State: maxHeap=[1], minHeap=[2]
 *    - findMedian() -> (1 + 2) / 2.0 = 1.5
 *
 * 3. addNum(3):
 *    - maxHeap.offer(3) -> [3, 1]
 *    - minHeap.offer(maxHeap.poll()) -> maxHeap=[1], minHeap=[2, 3]
 *    - minHeap.size() (2) > maxHeap.size() (1) -> balance:
 *      maxHeap.offer(minHeap.poll()) -> maxHeap=[2, 1], minHeap=[3]
 *    - State: maxHeap=[2, 1], minHeap=[3]
 *    - findMedian() -> maxHeap has more elements -> returns 2.0
 * ============================================================================
 */
public class SolutionMedianStream {

    private final PriorityQueue<Integer> maxHeapLower;
    private final PriorityQueue<Integer> minHeapUpper;

    public SolutionMedianStream() {
        this.maxHeapLower = new PriorityQueue<>(Collections.reverseOrder());
        this.minHeapUpper = new PriorityQueue<>();
    }

    public void addNum(int num) {
        maxHeapLower.offer(num);
        minHeapUpper.offer(maxHeapLower.poll());

        if (minHeapUpper.size() > maxHeapLower.size()) {
            maxHeapLower.offer(minHeapUpper.poll());
        }
    }

    public double findMedian() {
        if (maxHeapLower.size() > minHeapUpper.size()) {
            return (double) maxHeapLower.peek();
        } else {
            return ((long) maxHeapLower.peek() + (long) minHeapUpper.peek()) / 2.0;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String nLine = bufferedReader.readLine();
        if (nLine == null || nLine.trim().isEmpty()) {
            return;
        }

        int n = Integer.parseInt(nLine.trim());
        SolutionMedianStream medianFinder = new SolutionMedianStream();

        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(bufferedReader.readLine().trim());
            medianFinder.addNum(num);
            System.out.printf("%.1f%n", medianFinder.findMedian());
        }

        bufferedReader.close();
    }
}