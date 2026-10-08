package DSA.agoda;

import java.util.*;

/**
 * ============================================================================
 * [AGODA HACKERRANK - 05] REAL-TIME CURRENCY EXCHANGE HISTORICAL LOOKUP
 * ============================================================================
 * 
 * SOURCE:
 *   Agoda HackerRank OA - Senior/Staff Software Engineer (LeetCode Discuss).
 * 
 * PROBLEM:
 *   In an international flight/hotel purchase, prices arrive in foreign currency.
 *   You have a historical table of exchange rates updating dynamically:
 *     `List<ExchangeTick> rates` with `[timestamp, rate]`.
 *   Given a purchase made at `purchaseTimestamp` with `amount`, determine the
 *   internal cost by looking up the exchange rate in effect AT THE TIME of the purchase.
 *   (i.e., the rate with the greatest timestamp <= purchaseTimestamp).
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Range Floor Query:
 *     Rates are piecewise constant until a new tick updates the market rate.
 *   - Data Structure:
 *     Use `TreeMap<Long, Double>` (`floorEntry(timestamp)`) or Binary Search on a sorted array.
 *     `floorEntry(t)` runs in O(log K) time and safely retrieves the latest tick before t.
 *   - Edge Case: If purchase timestamp is earlier than the first known tick, throw 
 *     a domain exception or use base fallback.
 *
 * COMPLEXITY:
 *   - Ingestion: O(K log K) where K is number of rate ticks.
 *   - Query:     O(log K) per lookup.
 *   - Space:     O(K) Red-Black Tree.
 */
public class AG_HR05_CurrencyExchangeHistoricalAPI {

    public static class CurrencyConverter {
        private final TreeMap<Long, Double> historicalRates = new TreeMap<>();

        public void loadExchangeRates(List<long[]> ticks) {
            for (long[] tick : ticks) {
                historicalRates.put(tick[0], (double) tick[1]);
            }
        }

        public double convertPrice(long purchaseTimestamp, double foreignAmount) {
            // Find the most recent rate effective at or before purchaseTimestamp
            Map.Entry<Long, Double> entry = historicalRates.floorEntry(purchaseTimestamp);

            if (entry == null) {
                throw new IllegalStateException("No valid exchange rate found prior to timestamp: " + purchaseTimestamp);
            }

            double effectiveRate = entry.getValue();
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

        // Purchase occurred at 1552122600 (between tick 10 and tick 20)
        long purchaseTime = 1552122600L;
        double price = 100.0;

        double converted = converter.convertPrice(purchaseTime, price);
        System.out.println("AG_HR05 Converted Price: " + converted); 
        // Expected: 1000.0 (uses rate 10)
    }
}