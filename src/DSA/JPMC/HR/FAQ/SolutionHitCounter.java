package DSA.JPMC.HR.FAQ;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * ============================================================================
 * HACKERRANK QUESTION: Design Financial API Hit Counter
 * ============================================================================
 *
 * Problem Statement:
 * Design a high-throughput API hit counter for market gateway surveillance.
 * It counts the number of hits received within the past 300 seconds (i.e., the
 * past 5 minutes: [current_timestamp - 299, current_timestamp]).
 *
 * Implement the HitCounter class:
 * - HitCounter(): Initializes the hit counter system.
 * - void hit(int timestamp): Records a hit at given timestamp (in seconds).
 *   Multiple hits can arrive at the same timestamp.
 * - int getHits(int timestamp): Returns the total hits within the past 300 seconds.
 *
 * Timestamps arrive in monotonically non-decreasing order.
 *
 * Constraints:
 * - 1 <= timestamp <= 2 * 10^9
 * - Up to 3 * 10^5 calls to hit and getHits.
 * - Memory must remain O(1) fixed space, even under massive scale.
 *
 * ============================================================================
 * LOGIC & APPROACH (Fixed-Size Circular Array):
 * ============================================================================
 * 1. Why Not A Queue?
 *    A standard Queue<Integer> grows unboundedly during traffic bursts (O(N) memory),
 *    and poll operations take O(k) per query.
 *
 * 2. Fixed-Size Circular Buffers (Size 300):
 *    - `times` array of size 300: stores the latest timestamp observed at bucket `t % 300`.
 *    - `hits` array of size 300: stores the total hit count for that specific second.
 *
 * 3. Operations:
 *    - hit(timestamp):
 *        idx = timestamp % 300
 *        if times[idx] != timestamp: (bucket holds old data from 300+ seconds ago)
 *            times[idx] = timestamp
 *            hits[idx] = 1 (reset bucket)
 *        else:
 *            hits[idx]++ (accumulate concurrent hit)
 *    - getHits(timestamp):
 *        Sum hits[i] for all 0 <= i < 300 where (timestamp - times[i] < 300).
 *
 * Complexity:
 * - hit(): O(1)
 * - getHits(): O(300) = O(1) constant time
 * - Space: O(1) fixed space (two 300-element primitive arrays)
 * ============================================================================
 */
public class SolutionHitCounter {

    private final int[] times;
    private final int[] hits;

    public SolutionHitCounter() {
        this.times = new int[300];
        this.hits = new int[300];
    }

    public void hit(int timestamp) {
        int idx = timestamp % 300;
        if (times[idx] != timestamp) {
            times[idx] = timestamp;
            hits[idx] = 1;
        } else {
            hits[idx]++;
        }
    }

    public int getHits(int timestamp) {
        int totalHits = 0;
        for (int i = 0; i < 300; i++) {
            if (timestamp - times[i] < 300) {
                totalHits += hits[i];
            }
        }
        return totalHits;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String qLine = bufferedReader.readLine();
        if (qLine == null || qLine.trim().isEmpty()) {
            return;
        }

        int q = Integer.parseInt(qLine.trim());
        SolutionHitCounter counter = new SolutionHitCounter();

        for (int i = 0; i < q; i++) {
            String[] tokens = bufferedReader.readLine().trim().split("\\s+");
            String op = tokens[0];
            int timestamp = Integer.parseInt(tokens[1]);

            if (op.equalsIgnoreCase("HIT")) {
                counter.hit(timestamp);
            } else if (op.equalsIgnoreCase("GET")) {
                System.out.println(counter.getHits(timestamp));
            }
        }

        bufferedReader.close();
    }
}