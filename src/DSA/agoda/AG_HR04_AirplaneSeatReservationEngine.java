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
 *   Implement a flight seat reservation processor. You are given:
 *   1. Initial seat inventory `seats[seat_no]` (0 = Free, 1 = Reserved, 2 = Purchased).
 *   2. Stream of sequential requests sorted by `request_id`:
 *      - request_type: 1 (Reserve), 2 (Purchase)
 *      - seat_no
 *      - person_id
 *   Rules:
 *   - Reserve (type 1): Succeeds only if seat is Free (status 0). Becomes Reserved (1).
 *   - Purchase (type 2): Succeeds IF seat is Free (status 0) OR if the seat was 
 *     already Reserved (status 1) by the SAME person_id.
 *   - Any invalid operation is ignored.
 *   Return the final state of all seats.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - State-machine validation per entity.
 *   - Staff-level evaluation criteria:
 *     How cleanly do you encapsulate domain logic? Do you use naked `int[][]` arrays
 *     or clean OOP state encapsulation with explicit error transitions?
 *   - Use explicit enum states: `FREE`, `RESERVED`, `PURCHASED` and map person ownership.
 *
 * COMPLEXITY:
 *   - Time:  O(R) where R is total requests.
 *   - Space: O(S) where S is total airplane seats.
 */
public class AG_HR04_AirplaneSeatReservationEngine {

    public enum SeatStatus {
        FREE(0), RESERVED(1), PURCHASED(2);
        final int code;
        SeatStatus(int code) { this.code = code; }
    }

    public static class Seat {
        SeatStatus status;
        String reservedByPersonId;

        public Seat(SeatStatus status) {
            this.status = status;
            this.reservedByPersonId = null;
        }
    }

    public static class Request {
        int requestId;
        int type; // 1 = Reserve, 2 = Purchase
        int seatNo;
        String personId;

        public Request(int requestId, int type, int seatNo, String personId) {
            this.requestId = requestId;
            this.type = type;
            this.seatNo = seatNo;
            this.personId = personId;
        }
    }

    public static Map<Integer, SeatStatus> processSeatRequests(
            Map<Integer, SeatStatus> initialSeats, List<Request> requests) {

        Map<Integer, Seat> seatMap = new HashMap<>();
        for (Map.Entry<Integer, SeatStatus> entry : initialSeats.entrySet()) {
            seatMap.put(entry.getKey(), new Seat(entry.getValue()));
        }

        // Requests applied in ascending order of request_id
        requests.sort(Comparator.comparingInt(r -> r.requestId));

        for (Request req : requests) {
            Seat seat = seatMap.get(req.seatNo);
            if (seat == null) continue;

            if (req.type == 1) { // Reserve
                if (seat.status == SeatStatus.FREE) {
                    seat.status = SeatStatus.RESERVED;
                    seat.reservedByPersonId = req.personId;
                }
            } else if (req.type == 2) { // Purchase
                if (seat.status == SeatStatus.FREE) {
                    seat.status = SeatStatus.PURCHASED;
                    seat.reservedByPersonId = req.personId;
                } else if (seat.status == SeatStatus.RESERVED && req.personId.equals(seat.reservedByPersonId)) {
                    seat.status = SeatStatus.PURCHASED; // Upgrade reservation to purchase
                }
            }
        }

        Map<Integer, SeatStatus> finalState = new TreeMap<>();
        for (Map.Entry<Integer, Seat> entry : seatMap.entrySet()) {
            finalState.put(entry.getKey(), entry.getValue().status);
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
            new Request(5, 2, 2, "UserD")  // UserD directly purchases seat 2 -> OK
        );

        Map<Integer, SeatStatus> finalSeats = processSeatRequests(initial, requests);
        System.out.println("AG_HR04 Final State: " + finalSeats);
        // Expected: {1=PURCHASED, 2=PURCHASED, 3=RESERVED}
    }
}