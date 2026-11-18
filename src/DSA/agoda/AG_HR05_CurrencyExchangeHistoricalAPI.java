package DSA.agoda;

import java.util.*;

/**
 * ============================================================================
 * [AGODA HACKERRANK - 05] REAL-TIME CURRENCY EXCHANGE HISTORICAL LOOKUP (LC 981 Variant)
 * ============================================================================
 *
 * SOURCE:
 *   Agoda HackerRank OA - Senior/Staff Software Engineer (LeetCode Discuss).
 *
 * PROBLEM:
 *   In an international flight/hotel booking platform, supplier prices arrive in
 *   foreign currencies alongside a purchase timestamp. You are given a stream or
 *   historical log of market exchange rate updates ("ticks"):
 *     - Each tick consists of `(timestamp, rate)` where `timestamp` is a Unix epoch
 *       (in seconds/milliseconds) and `rate` is the multiplier to convert foreign
 *       currency into the base reporting currency.
 *     - A rate takes effect at its `timestamp` and remains active (piecewise constant)
 *       until superseded by a newer tick at a later timestamp.
 *
 *   Design a `CurrencyConverter` service supporting:
 *     1. `loadExchangeRates(ticks)` : Ingests historical or out-of-order rate ticks.
 *     2. `convertPrice(purchaseTimestamp, foreignAmount)` : Looks up the active
 *        exchange rate at `purchaseTimestamp` (the tick with the greatest timestamp
 *        `<= purchaseTimestamp`) and returns `foreignAmount * effectiveRate`.
 *        If no rate exists at or before `purchaseTimestamp`, throw an exception.
 *
 * CONSTRAINTS:
 *   - 1 <= K (number of ticks) <= 10^5
 *   - 1 <= Q (number of conversion queries) <= 10^5
 *   - 0 <= timestamp <= 10^12 (requires `long`)
 *   - `rate > 0`, `foreignAmount >= 0`
 *   - Ticks may arrive out of chronological order; duplicate timestamps overwrite
 *     with the latest ingested rate for that instant.
 *
 * EXAMPLES:
 *   Example 1 (Between Two Ticks):
 *     Ticks:
 *       t = 1552122000 -> rate = 10.0
 *       t = 1552125600 -> rate = 20.0
 *       t = 1552129200 -> rate = 30.0
 *     Query: convertPrice(1552122600, 100.0)
 *     Output: 1000.0
 *     Explanation: 1552122600 falls in [1552122000, 1552125600), so rate 10.0 applies.
 *                  100.0 * 10.0 = 1000.0.
 *
 *   Example 2 (Exact Boundary Match):
 *     Query: convertPrice(1552125600, 50.0)
 *     Output: 1000.0
 *     Explanation: Timestamp matches tick 1552125600 exactly (`<=` condition), so
 *                  the new rate 20.0 applies immediately: 50.0 * 20.0 = 1000.0.
 *
 *   Example 3 (Query Before Earliest Known Tick):
 *     Query: convertPrice(1552120000, 100.0)
 *     Output: Throws IllegalStateException (no rate active prior to 1552122000).
 *
 * ============================================================================
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 * ============================================================================
 *   - Step-Function / Piecewise-Constant Timeline:
 *       * Exchange rates do not interpolate between ticks—they form a flat step
 *         function on a timeline:
 *
 *           Rate
 *            30 |                                    +----------->
 *            20 |                  +-----------------+
 *            10 |  +---------------+
 *               +--+---------------+-----------------+-----------> Time
 *                 t0              t1                t2
 *                          ^
 *                     purchaseTime (snaps LEFT to t0 via floorEntry!)
 *
 *   - Why `TreeMap<Long, Double>` (`floorEntry`) vs. Binary Search?
 *       * Static batch data -> Sort array + custom `upperBound - 1` binary search (`O(1)` memory overhead).
 *       * Real-time / streaming ticks -> `TreeMap` (Red-Black Tree) handles dynamic
 *         out-of-order inserts AND predecessor (`<=`) queries in `O(log K)` natively
 *         via `floorEntry(purchaseTimestamp)`—eliminating off-by-one binary search bugs.
 *
 * ============================================================================
 * SOLUTION LOGIC & STEP-BY-STEP ALGORITHM:
 * ============================================================================
 *   1. Data Structure (`TreeMap<Long, Double> historicalRates`):
 *      - Key   : `Long` timestamp when the rate became effective.
 *      - Value : `Double` exchange rate (`BigDecimal` in production financial systems).
 *      - Automatically maintains all ingested ticks sorted by timestamp ascending.
 *
 *   2. Ingestion (`loadExchangeRates` / `addTick`):
 *      - Validate input list/tick is non-null.
 *      - Insert each `(timestamp, rate)` into `historicalRates` (`O(log K)` per tick).
 *      - Out-of-order ticks are automatically placed into their exact chronological spot.
 *
 *   3. Point-in-Time Lookup (`convertPrice(purchaseTimestamp, foreignAmount)`):
 *      - Call `Map.Entry<Long, Double> activeTick = historicalRates.floorEntry(purchaseTimestamp)`.
 *        * `floorEntry(t)` returns the entry with the **greatest key `<= t`**, or `null`
 *          if every key in the map is strictly greater than `t` (or the map is empty).
 *      - Guard Missing Rate:
 *        * If `activeTick == null`, no exchange rate existed when the purchase happened ->
 *          throw `IllegalStateException` with diagnostic context.
 *      - Compute & Return:
 *        * `return foreignAmount * activeTick.getValue()`.
 *
 * ============================================================================
 * VISUAL DRY RUN:
 * ============================================================================
 *   Step 1: Ingest Ticks into Red-Black Tree (`historicalRates`)
 *     Ticks loaded:
 *       [ 1552122000 -> 10.0,  1552125600 -> 20.0,  1552129200 -> 30.0 ]
 *
 *   Step 2: Trace Queries via `historicalRates.floorEntry(queryTime)`
 *     Query # | queryTime  | Greatest Key <= queryTime | Effective Rate | Amount | Result
 *     --------+------------+---------------------------+----------------+--------+-------------------
 *        1    | 1552122600 | 1552122000                |     10.0       | 100.0  | 1000.0
 *        2    | 1552125600 | 1552125600 (exact match!) |     20.0       |  50.0  | 1000.0
 *        3    | 1552135000 | 1552129200 (latest tick)  |     30.0       |  10.0  |  300.0
 *        4    | 1552120000 | null (before first tick)  |      N/A       | 100.0  | IllegalStateException
 *
 * ============================================================================
 * COMPLEXITY:
 *   - Time:
 *       * Ingestion: `O(K log K)` to insert `K` ticks into the Red-Black Tree.
 *       * Query:     `O(log K)` per `floorEntry` lookup.
 *   - Space:
 *       * `O(K)` to store `K` timestamp-rate nodes in the `TreeMap`.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *      (Scanning all `K` ticks linearly per query to track `max(t <= purchaseTimestamp)` -> `O(Q * K)`.)
 *   2. What invariant must remain true after every loop iteration?
 *      (For any query timestamp `t`, the active rate is uniquely determined by the single
 *       rightmost tick at or to the left of `t` on the sorted timeline.)
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *      (Using `lowerEntry(t)` instead of `floorEntry(t)` would fail on exact timestamp matches
 *       `t == tickTimestamp` because `lowerEntry` enforces strict `<` instead of `<=`!)
 *   4. Which edge cases change the control flow (empty rate table, query strictly earlier than
 *      the first tick, query matching a tick timestamp exactly, query far in the future after
 *      the last tick, out-of-order tick arrival, `long[]` vs `double` rate precision)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 * ============================================================================
 */
public class AG_HR05_CurrencyExchangeHistoricalAPI {

    public static class CurrencyConverter {
        // Red-Black Tree sorted by timestamp ascending -> O(log K) floor lookup
        private final TreeMap<Long, Double> historicalRates = new TreeMap<>();

        // Supports batch ingestion (even if ticks arrive out of chronological order)
        public void loadExchangeRates(List<long[]> ticks) {
            if (ticks == null) return;
            for (long[] tick : ticks) {
                if (tick != null && tick.length >= 2) {
                    addTick(tick[0], (double) tick[1]);
                }
            }
        }

        // Supports real-time streaming tick updates
        public void addTick(long timestamp, double rate) {
            historicalRates.put(timestamp, rate);
        }

        public double convertPrice(long purchaseTimestamp, double foreignAmount) {
            // Find the tick with the GREATEST timestamp <= purchaseTimestamp
            Map.Entry<Long, Double> activeEntry = historicalRates.floorEntry(purchaseTimestamp);

            // Guard: Purchase occurred before any exchange rate was recorded
            if (activeEntry == null) {
                throw new IllegalStateException(
                        "No valid exchange rate found at or prior to timestamp: " + purchaseTimestamp
                );
            }

            double effectiveRate = activeEntry.getValue();
            return foreignAmount * effectiveRate;
        }
    }

    public static void main(String[] args) {
        CurrencyConverter converter = new CurrencyConverter();

        // Historical exchange rate ticks [timestamp, rate]
        List<long[]> ticks = Arrays.asList(
                new long[]{1552122000L, 10},
                new long[]{1552125600L, 20},
                new long[]{1552129200L, 30}
        );
        converter.loadExchangeRates(ticks);

        // Test 1: Between tick 1 and tick 2 -> uses rate 10
        System.out.println("Test 1 (t=1552122600, amt=100): " + converter.convertPrice(1552122600L, 100.0) + " | Expected: 1000.0");

        // Test 2: Exact match on tick 2 -> uses new rate 20
        System.out.println("Test 2 (t=1552125600, amt=50):  " + converter.convertPrice(1552125600L, 50.0)  + " | Expected: 1000.0");

        // Test 3: After last tick -> uses latest rate 30
        System.out.println("Test 3 (t=1552135000, amt=10):  " + converter.convertPrice(1552135000L, 10.0)  + " | Expected: 300.0");

        // Test 4: Before first tick -> throws IllegalStateException
        try {
            converter.convertPrice(1552120000L, 100.0);
        } catch (IllegalStateException ex) {
            System.out.println("Test 4 (t=1552120000, early):   Caught expected -> " + ex.getMessage());
        }
    }
}