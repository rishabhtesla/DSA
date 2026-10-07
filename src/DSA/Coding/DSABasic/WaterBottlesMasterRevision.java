package DSA.Coding.DSABasic;

public class WaterBottlesMasterRevision {

    /*
    ================================================================================
    PROBLEM STATEMENT:
    There are `numBottles` water bottles that are initially full of water. You can 
    exchange `numExchange` empty water bottles from the market with one full water bottle.
    The operation of drinking a full water bottle turns it into an empty bottle.
    Given the two integers `numBottles` and `numExchange`, return the maximum number 
    of water bottles you can drink.

    APPROACH 1: Loop-Based Simulation (As shown in video)
    - Initialize `ans` with the total starting count `numBottles`, since we can drink 
      all original bottles immediately [00:02:23].
    - Run a loop tracking `numBottles` as long as it is greater than or equal to 
      `numExchange` (meaning we have enough empty bottles to trade) [00:04:41].
    - Inside the loop:
        1. Calculate newly generated full bottles: `newBottles = numBottles / numExchange` [00:05:51].
        2. Calculate leftovers that couldn't be traded: `remainingBottles = numBottles % numExchange` [00:06:17].
        3. Drink the new bottles and add them to the total count: `ans += newBottles` [00:06:38].
        4. Update total empty bottles for the next step: `numBottles = newBottles + remainingBottles` [00:06:51].
    - Terminate once empty bottles drop below the required threshold.

    VISUAL DRY RUN (Simulation Strategy for numBottles = 15, numExchange = 4):
    ----------------------------------------------------------------------------
    - Initial Step: ans = 15                                         [00:08:06]
    - Loop Cycle 1 (numBottles = 15):
        newBottles = 15 / 4 = 3                                      [00:08:29]
        remainingBottles = 15 % 4 = 3                                [00:08:34]
        ans = 15 + 3 = 18                                            [00:08:55]
        numBottles = 3 + 3 = 6                                       [00:09:10]
    - Loop Cycle 2 (numBottles = 6):
        newBottles = 6 / 4 = 1                                       [00:09:37]
        remainingBottles = 6 % 4 = 2                                 [00:09:42]
        ans = 18 + 1 = 19                                            [00:09:52]
        numBottles = 1 + 2 = 3                                       [00:10:03]
    - Exit Check: numBottles (3) < numExchange (4) -> Breaks Loop.   [00:10:07]
    - Final Output: returns ans = 19                                 [00:10:20]

    TIME COMPLEXITY: O(log_{numExchange}(numBottles)) - Linear proportional reduction.
    SPACE COMPLEXITY: O(1) - Evaluated dynamically with fixed memory buffers.
    ================================================================================
    */
    public static int numWaterBottlesSimulation(int numBottles, int numExchange) {
        // We can drink all original starting configurations initially
        int ans = numBottles;

        // Loop runs dynamically while exchange criteria can be met [00:05:33]
        while (numBottles >= numExchange) {
            int newBottles = numBottles / numExchange;
            int remainingBottles = numBottles % numExchange;
            
            ans += newBottles;
            numBottles = newBottles + remainingBottles;
        }

        return ans;
    }

    /*
    ================================================================================
    APPROACH 2: Mathematical O(1) Formula (Alternative Strategy)
    - Effectively, each time we trade for a new bottle, we are giving up 
      `numExchange` empty bottles but receiving 1 full bottle back (which will 
      eventually become 1 empty bottle again).
    - This means each new bottle consumed actually costs us a net total of 
      `(numExchange - 1)` empty bottles.
    - Formula derivation: Total Drank = Initial Full + (Initial Full - 1) / (Exchange - 1)
    - TIME COMPLEXITY: O(1) - Computed instantly in a single arithmetic step.
    - SPACE COMPLEXITY: O(1) - Constant execution trace.
    ================================================================================
    */
    public static int numWaterBottlesMath(int numBottles, int numExchange) {
        return numBottles + (numBottles - 1) / (numExchange - 1);
    }

    private static void verifyBothApproaches(int numBottles, int numExchange, int expected) {
        System.out.println("Testing Configuration (Bottles: " + numBottles + " | Exchange: " + numExchange + ")");
        System.out.println("Expected Output    : " + expected);
        System.out.println("1. Video Simulation: " + numWaterBottlesSimulation(numBottles, numExchange));
        System.out.println("2. Math Reduction  : " + numWaterBottlesMath(numBottles, numExchange));
        System.out.println("Status             : " + 
            (numWaterBottlesSimulation(numBottles, numExchange) == expected && 
             numWaterBottlesMath(numBottles, numExchange) == expected ? "PASS ✅" : "FAIL ❌"));
        System.out.println("--------------------------------------------------");
    }

    public static void main(String[] args) {
        System.out.println("=== LEETCODE 1518: WATER BOTTLES ===\n");

        // Case 1: Standard sample provided in the video [00:01:05]
        verifyBothApproaches(9, 3, 13);

        // Case 2: In-depth execution tracking sample from video logic [00:02:11]
        verifyBothApproaches(15, 4, 19);

        // Case 3: Boundary minimum limit constraints
        verifyBothApproaches(5, 5, 6);

        // Case 4: No exchange loop possible due to insufficient bottles
        verifyBothApproaches(2, 3, 2);
    }
}