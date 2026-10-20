package DSA.JPMC.HR.FAQ;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * ============================================================================
 * HACKERRANK / LEETCODE 362: Design Financial API Hit Counter
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
 * ============================================================================
 * STEP-BY-STEP DRY RUN:
 * ============================================================================
 * Initial State:
 *   times = [0, 0, 0, ..., 0]  (length 300)
 *   hits  = [0, 0, 0, ..., 0]  (length 300)
 *
 * ----------------------------------------------------------------------------
 * Step 1: counter.hit(1)
 *   - idx = 1 % 300 = 1
 *   - times[1] is 0 != 1:
 *       times[1] = 1
 *       hits[1]  = 1
 *
 * ----------------------------------------------------------------------------
 * Step 2: counter.hit(2)
 *   - idx = 2 % 300 = 2
 *   - times[2] is 0 != 2:
 *       times[2] = 2
 *       hits[2]  = 1
 *
 * ----------------------------------------------------------------------------
 * Step 3: counter.hit(3)
 *   - idx = 3 % 300 = 3
 *   - times[3] is 0 != 3:
 *       times[3] = 3
 *       hits[3]  = 1
 *
 * ----------------------------------------------------------------------------
 * Step 4: counter.getHits(4)
 *   - Window evaluated: [4 - 299, 4] = [-295, 4]
 *   - Condition: (4 - times[i] < 300)
 *       i = 1: 4 - times[1] = 4 - 1 = 3 < 300  --> totalHits += hits[1] (1)
 *       i = 2: 4 - times[2] = 4 - 2 = 2 < 300  --> totalHits += hits[2] (1)
 *       i = 3: 4 - times[3] = 4 - 3 = 1 < 300  --> totalHits += hits[3] (1)
 *       All other i: times[i] = 0 -> 4 - 0 = 4 < 300, but hits[i] = 0 -> adds 0
 *   - Output: 3
 *
 * ----------------------------------------------------------------------------
 * Step 5: counter.hit(300)
 *   - idx = 300 % 300 = 0
 *   - times[0] is 0 != 300:
 *       times[0] = 300
 *       hits[0]  = 1
 *
 * ----------------------------------------------------------------------------
 * Step 6: counter.getHits(300)
 *   - Window evaluated: [300 - 299, 300] = [1, 300]
 *   - Check valid entries:
 *       i = 0: times[0] = 300 -> 300 - 300 = 0 < 300  --> +1
 *       i = 1: times[1] = 1   -> 300 - 1 = 299 < 300  --> +1
 *       i = 2: times[2] = 2   -> 300 - 2 = 298 < 300  --> +1
 *       i = 3: times[3] = 3   -> 300 - 3 = 297 < 300  --> +1
 *   - Output: 4
 *
 * ----------------------------------------------------------------------------
 * Step 7: counter.getHits(301)  (Notice timestamp 1 falls out of the 300s window)
 *   - Window evaluated: [301 - 299, 301] = [2, 301]
 *   - Check entries:
 *       i = 0: times[0] = 300 -> 301 - 300 = 1 < 300   --> +1
 *       i = 1: times[1] = 1   -> 301 - 1 = 300 (NOT < 300) -> EXPIRED, ignored!
 *       i = 2: times[2] = 2   -> 301 - 2 = 299 < 300   --> +1
 *       i = 3: times[3] = 3   -> 301 - 3 = 298 < 300   --> +1
 *   - Output: 3
 *
 * ----------------------------------------------------------------------------
 * Step 8: counter.hit(301)  (Overwriting slot index 1)
 *   - idx = 301 % 300 = 1
 *   - times[1] is 1 != 301 (old timestamp overwritten):
 *       times[1] = 301
 *       hits[1]  = 1  (reset from old value)
 *
 * ============================================================================
 * COMPLEXITY:
 * - hit():     O(1)
 * - getHits(): O(300) = O(1) constant time
 * - Space:     O(300) = O(1) fixed auxiliary space
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