package DSA.agoda;

import java.util.*;

/**
 * ============================================================================
 * [AGODA HACKERRANK - 04] AIRLINE SEAT RESERVATION STATE ENGINE
 * ============================================================================
 *
 * SOURCE:
 *   Agoda HackerRank OA - Senior/Staff Software Engineer (LeetCode Discuss).
 *
 * PROBLEM:
 *   Implement a deterministic flight seat reservation state processor. You are given:
 *     1. Initial seat inventory `initialSeats` mapping `seat_no -> SeatStatus`:
 *          - `0` (`FREE`)      : Available for reservation or direct purchase.
 *          - `1` (`RESERVED`)  : Temporarily held by a specific passenger.
 *          - `2` (`PURCHASED`) : Permanently sold (terminal state).
 *     2. A list of booking requests, each containing:
 *          - `requestId` : Monotonic sequence/timestamp ID determining execution order.
 *          - `type`      : `1` = Reserve, `2` = Purchase.
 *          - `seatNo`    : Target seat identifier.
 *          - `personId`  : Passenger initiating the request.
 *
 *   State Transition Rules:
 *     - Reserve (`type == 1`):
 *         * Succeeds ONLY if `seat.status == FREE`.
 *         * Transitions seat to `RESERVED` and binds ownership to `personId`.
 *     - Purchase (`type == 2`):
 *         * Succeeds if `seat.status == FREE` (direct purchase), OR
 *         * Succeeds if `seat.status == RESERVED` AND `seat.reservedByPersonId.equals(personId)`
 *           (reservation upgrade by the exact same passenger).
 *         * Transitions seat to `PURCHASED` (terminal state).
 *     - Invalid / Unauthorized / Non-existent Seat Requests:
 *         * Silently ignored (no state change, no crash).
 *
 *   Return a sorted map (`seat_no -> final SeatStatus`) representing the final state
 *   of all seats after processing every request in `requestId` order.
 *
 * CONSTRAINTS:
 *   - 1 <= S (total seats) <= 10^5
 *   - 0 <= R (total requests) <= 10^5
 *   - `requestId` values are unique positive integers (input list may be unsorted).
 *   - `personId` is a non-null alphanumeric string.
 *
 * EXAMPLES:
 *   Example 1:
 *     Initial Seats: {1=FREE, 2=FREE, 3=FREE}
 *     Requests:
 *       (id=1, RESERVE,  seat=3, "UserA") -> Seat 3: FREE -> RESERVED (owner: UserA)
 *       (id=2, RESERVE,  seat=3, "UserB") -> Ignored (Seat 3 is already RESERVED)
 *       (id=3, RESERVE,  seat=1, "UserC") -> Seat 1: FREE -> RESERVED (owner: UserC)
 *       (id=4, PURCHASE, seat=1, "UserC") -> Seat 1: RESERVED -> PURCHASED (owner matches UserC)
 *       (id=5, PURCHASE, seat=2, "UserD") -> Seat 2: FREE -> PURCHASED (direct buy)
 *     Output: {1=PURCHASED, 2=PURCHASED, 3=RESERVED}
 *
 *   Example 2 (Cross-User Hijack & Post-Purchase Attempt):
 *     Initial Seats: {10=FREE}
 *     Requests:
 *       (id=1, RESERVE,  seat=10, "Alice") -> Seat 10: RESERVED (owner: Alice)
 *       (id=2, PURCHASE, seat=10, "Bob")   -> Ignored! (Reserved by Alice, Bob cannot buy)
 *       (id=3, PURCHASE, seat=10, "Alice") -> Seat 10: PURCHASED (Alice upgrades her hold)
 *       (id=4, RESERVE,  seat=10, "Alice") -> Ignored! (PURCHASED is a terminal state)
 *     Output: {10=PURCHASED}
 *
 * ============================================================================
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 * ============================================================================
 *   - Finite State Machine (FSM) + Entity Encapsulation:
 *       * Each seat is an independent Finite State Machine with 3 states and 1 terminal
 *         absorbing state (`PURCHASED`):
 *
 *                        +---[ RESERVE (any user) ]---+
 *                        |                            v
 *                    [ FREE ]                    [ RESERVED ] (owner = X)
 *                        |                            |
 *                        +--[ PURCHASE (any user) ]---+--[ PURCHASE (owner == X) ]--+
 *                                                     |                             |
 *                                                     v                             v
 *                                               [ PURCHASED ] <--- (Terminal: all ops ignored)
 *
 *   - Senior/Staff Evaluation Lens (Why OOP beats parallel arrays):
 *       * Storing state in raw `int[] status` and `String[] owner` arrays invites
 *         desynchronization bugs (e.g., forgetting to check ownership on upgrade).
 *       * Encapsulating `tryReserve(personId)` and `tryPurchase(personId)` inside the
 *         `Seat` domain object guarantees invalid transitions are impossible by design.
 *
 * ============================================================================
 * SOLUTION LOGIC & STEP-BY-STEP ALGORITHM:
 * ============================================================================
 *   1. Hydrate Domain Objects (`initialSeats` -> `Map<Integer, Seat>`):
 *      - Build a `HashMap<Integer, Seat>` for `O(1)` lookup by `seatNo`.
 *      - Wrap each `SeatStatus` inside a mutable `Seat` object holding both
 *        `status` and `ownerPersonId`.
 *
 *   2. Enforce Chronological Event Ordering:
 *      - Copy `requests` into a mutable `ArrayList<>(requests)` (so we never mutate
 *        or fail on unmodifiable caller lists like `List.of(...)`) and sort by
 *        `requestId` ascending (`O(R log R)`).
 *
 *   3. Replay Event Stream Through the State Machine (`O(R)`):
 *      - For each `Request req` in sorted order:
 *          * Lookup `Seat seat = seatMap.get(req.seatNo)`.
 *          * If `seat == null` (invalid seat number), skip safely (`continue`).
 *          * Dispatch by `req.type`:
 *              - `1` (Reserve)  -> call `seat.tryReserve(req.personId)`
 *              - `2` (Purchase) -> call `seat.tryPurchase(req.personId)`
 *              - Any unknown `type` -> ignore safely.
 *
 *   4. Extract Sorted Snapshot (`TreeMap<Integer, SeatStatus>`):
 *      - Collect each `seatNo -> seat.status` into a `TreeMap` so seats are
 *        returned in deterministic ascending seat-number order (`O(S log S)`).
 *
 * ============================================================================
 * VISUAL DRY RUN: Initial = {1:FREE, 2:FREE, 3:FREE}
 * ============================================================================
 *   ReqID | Type     | Seat | Person | Pre-State         | Transition Valid?          | Post-State
 *   ------+----------+------+--------+-------------------+----------------------------+----------------------
 *     1   | RESERVE  |  3   | UserA  | FREE (null)       | Yes (FREE -> RESERVED)     | RESERVED (UserA)
 *     2   | RESERVE  |  3   | UserB  | RESERVED (UserA)  | No  (Not FREE -> Ignored)  | RESERVED (UserA)
 *     3   | RESERVE  |  1   | UserC  | FREE (null)       | Yes (FREE -> RESERVED)     | RESERVED (UserC)
 *     4   | PURCHASE |  1   | UserC  | RESERVED (UserC)  | Yes (Owner matches UserC)  | PURCHASED (UserC)
 *     5   | PURCHASE |  2   | UserD  | FREE (null)       | Yes (Direct FREE -> BUY)   | PURCHASED (UserD)
 *
 *   Final Snapshot: {1=PURCHASED, 2=PURCHASED, 3=RESERVED}
 *
 * ============================================================================
 * COMPLEXITY:
 *   - Time:  O(R log R + S log S)
 *            - Sorting `R` requests by `requestId` takes `O(R log R)` (`O(R)` if pre-sorted).
 *            - Processing `R` requests takes `O(R)` via `O(1)` HashMap lookups.
 *            - Building the sorted `TreeMap` output takes `O(S log S)`.
 *   - Space: O(S + R)
 *            - `O(S)` to store `S` `Seat` objects in `seatMap` and `finalState`.
 *            - `O(R)` for the defensive mutable copy of `requests` during sorting.
 *
 * CRITICAL THINKING CHECKPOINTS:
 *   1. Before coding, what would the brute-force solution do, and where does it repeat work?
 *      (Scanning the seat list linearly per request -> `O(R * S)` instead of `O(1)` HashMap lookup.)
 *   2. What invariant must remain true after every loop iteration?
 *      (1. A `PURCHASED` seat never reverts to `FREE` or `RESERVED`.
 *       2. A `RESERVED` seat always has a non-null `ownerPersonId` matching who reserved it.)
 *   3. Why is each pointer/state update safe, and what counterexample would break it?
 *      (Calling `requests.sort(...)` directly on the caller's list throws `UnsupportedOperationException`
 *       if the caller passes `List.of(...)` or `Collections.unmodifiableList(...)`. Copying into
 *       `new ArrayList<>(requests)` first prevents that crash!)
 *   4. Which edge cases change the control flow (unsorted `requestId`s, `req.personId == null`,
 *      unknown `seatNo` not in `initialSeats`, `initialSeats` already containing `RESERVED` seats,
 *      duplicate/re-attempted purchase by the same user after already purchasing)?
 *   5. Can you derive the time and extra-space complexity without looking at the answer?
 * ============================================================================
 */
public class AG_HR04_AirplaneSeatReservationEngine {

    public enum SeatStatus {
        FREE(0),
        RESERVED(1),
        PURCHASED(2);

        final int code;

        SeatStatus(int code) {
            this.code = code;
        }
    }

    // Domain Entity encapsulating both state AND valid state transitions
    public static class Seat {
        private SeatStatus status;
        private String ownerPersonId;

        public Seat(SeatStatus initialStatus) {
            this.status = initialStatus;
            this.ownerPersonId = null;
        }

        // Transition 1: FREE -> RESERVED
        public boolean tryReserve(String personId) {
            if (personId == null) return false;

            if (this.status == SeatStatus.FREE) {
                this.status = SeatStatus.RESERVED;
                this.ownerPersonId = personId;
                return true;
            }
            return false; // Already RESERVED or PURCHASED -> ignore
        }

        // Transition 2: FREE -> PURCHASED  OR  RESERVED (by same owner) -> PURCHASED
        public boolean tryPurchase(String personId) {
            if (personId == null) return false;

            if (this.status == SeatStatus.FREE) {
                this.status = SeatStatus.PURCHASED;
                this.ownerPersonId = personId;
                return true;
            }
            if (this.status == SeatStatus.RESERVED && personId.equals(this.ownerPersonId)) {
                this.status = SeatStatus.PURCHASED;
                return true;
            }
            return false; // Reserved by someone else, or already PURCHASED -> ignore
        }

        public SeatStatus getStatus() {
            return status;
        }
    }

    public static class Request {
        final int requestId;
        final int type; // 1 = Reserve, 2 = Purchase
        final int seatNo;
        final String personId;

        public Request(int requestId, int type, int seatNo, String personId) {
            this.requestId = requestId;
            this.type = type;
            this.seatNo = seatNo;
            this.personId = personId;
        }
    }

    public static Map<Integer, SeatStatus> processSeatRequests(
            Map<Integer, SeatStatus> initialSeats, List<Request> requests) {

        if (initialSeats == null || initialSeats.isEmpty()) {
            return new TreeMap<>();
        }

        // Step 1: Build O(1) lookup table of stateful Seat entities
        Map<Integer, Seat> seatMap = new HashMap<>(initialSeats.size());
        for (Map.Entry<Integer, SeatStatus> entry : initialSeats.entrySet()) {
            seatMap.put(entry.getKey(), new Seat(entry.getValue()));
        }

        // Step 2: Sort a defensive copy of requests by requestId ascending
        if (requests != null && !requests.isEmpty()) {
            List<Request> sortedRequests = new ArrayList<>(requests);
            sortedRequests.sort(Comparator.comparingInt(r -> r.requestId));

            // Step 3: Replay each request through the Seat state machine
            for (Request req : sortedRequests) {
                Seat seat = seatMap.get(req.seatNo);
                if (seat == null) {
                    continue; // Unknown seat number -> safely ignore
                }

                if (req.type == 1) {
                    seat.tryReserve(req.personId);
                } else if (req.type == 2) {
                    seat.tryPurchase(req.personId);
                }
            }
        }

        // Step 4: Return deterministic sorted snapshot of final seat states
        Map<Integer, SeatStatus> finalState = new TreeMap<>();
        for (Map.Entry<Integer, Seat> entry : seatMap.entrySet()) {
            finalState.put(entry.getKey(), entry.getValue().getStatus());
        }
        return finalState;
    }

    public static void main(String[] args) {
        Map<Integer, SeatStatus> initial = new HashMap<>();
        initial.put(1, SeatStatus.FREE);
        initial.put(2, SeatStatus.FREE);
        initial.put(3, SeatStatus.FREE);

        List<Request> requests = Arrays.asList(
                new Request(1, 1, 3, "UserA"), // UserA reserves seat 3 -> OK
                new Request(2, 1, 3, "UserB"), // UserB reserves seat 3 -> IGNORED (already reserved)
                new Request(3, 1, 1, "UserC"), // UserC reserves seat 1 -> OK
                new Request(4, 2, 1, "UserC"), // UserC purchases seat 1 -> OK (reserved by same user)
                new Request(5, 2, 2, "UserD"), // UserD directly purchases seat 2 -> OK
                new Request(6, 2, 3, "UserB")  // UserB tries to buy UserA's seat 3 -> IGNORED
        );

        Map<Integer, SeatStatus> finalSeats = processSeatRequests(initial, requests);
        System.out.println("AG_HR04 Final State: " + finalSeats);
        // Expected: {1=PURCHASED, 2=PURCHASED, 3=RESERVED}
    }
}