package DSA.LinkedList;

/**
 * ============================================================================
 * [56 / 65] - COPY LIST WITH RANDOM POINTER (LeetCode 138)
 * ============================================================================
 * 
 * PROBLEM:
 *   Construct a deep copy of a linked list where each node contains an additional
 *   `random` pointer that could point to any node in the list or null.
 *
 * INTERVIEW INTUITION & "AHA!" MOMENT:
 *   - Hash Map Approach: `Map<Node, Node>` clone map uses O(n) auxiliary space.
 *   - Optimal O(1) Auxiliary Space (Interweaving Nodes):
 *     Step 1: Clone each node and insert it immediately after the original node:
 *             A -> A' -> B -> B' -> C -> C'
 *     Step 2: Assign random pointers for cloned nodes:
 *             `curr.next.random = (curr.random != null) ? curr.random.next : null`
 *     Step 3: Unweave the combined list back into original and copied lists.
 *
 * COMPLEXITY:
 *   - Time:  O(n) - Three linear passes.
 *   - Space: O(1) - In-place pointer manipulation without a hash table.
 */
public class P56_CopyListWithRandomPointer {

    static class Node {
        int val;
        Node next;
        Node random;
        Node(int val) { this.val = val; }
    }

    public static Node copyRandomList(Node head) {
        if (head == null) return null;

        // Step 1: Duplicate nodes and interleave
        Node curr = head;
        while (curr != null) {
            Node copy = new Node(curr.val);
            copy.next = curr.next;
            curr.next = copy;
            curr = copy.next;
        }

        // Step 2: Set random pointers for copy nodes
        curr = head;
        while (curr != null) {
            if (curr.random != null) {
                curr.next.random = curr.random.next;
            }
            curr = curr.next.next;
        }

        // Step 3: Unweave original and copied list
        curr = head;
        Node copyHead = head.next;
        Node copyCurr = copyHead;

        while (curr != null) {
            curr.next = curr.next.next;
            curr = curr.next;

            if (copyCurr.next != null) {
                copyCurr.next = copyCurr.next.next;
                copyCurr = copyCurr.next;
            }
        }

        return copyHead;
    }

    public static void main(String[] args) {
        Node n1 = new Node(7);
        Node n2 = new Node(13);
        Node n3 = new Node(11);
        n1.next = n2; n2.next = n3;
        n2.random = n1;
        n3.random = n2;

        Node cloned = copyRandomList(n1);
        System.out.println("P56 Cloned Node 1 Val: " + cloned.val);
        System.out.println("P56 Cloned Node 2 Random Val: " + cloned.next.random.val); // Expected: 7
    }
}