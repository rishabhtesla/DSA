package DSA.JPMC;

/**
 * PROBLEM: Add Two Numbers
 * You are given two non-empty linked lists representing two non-negative integers. 
 * The digits are stored in reverse order, and each of their nodes contains a single digit. 
 * Add the two numbers and return the sum as a linked list.
 * 
 * EXAMPLE:
 * Input: l1 = [2,4,3], l2 = [5,6,4]  (Represents 342 + 465)
 * Output: [7,0,8]                    (Represents 807)
 * 
 * LOGIC:
 * Traverse both linked lists simultaneously from left to right.
 * At each node, calculate the sum of the two digits plus any 'carry' from the previous step.
 * - The new node's value will be: sum % 10.
 * - The new carry will be: sum / 10.
 * Continue until both lists are exhausted AND the carry is 0.
 * Use a dummy head node to simplify list construction.
 * 
 * DRY RUN: l1 = [2,4,3], l2 = [5,6,4]
 * - Nodes: 2 + 5, carry=0. Sum = 7. Node=7, carry=0.
 * - Nodes: 4 + 6, carry=0. Sum = 10. Node=0, carry=1.
 * - Nodes: 3 + 4, carry=1. Sum = 8. Node=8, carry=0.
 * Result list: 7 -> 0 -> 8
 * 
 * COMPLEXITY: Time: O(max(N, M)) where N and M are the lengths of the lists. Space: O(max(N, M)) for output.
 *
 * ADDITIONAL LEARNING NOTES:
 * A brute-force alternative converts both lists to numbers, which risks
 * overflow and still requires O(N+M) work; digit-by-digit addition is safer.
 * Examples: [0]+[0] -> [0], and [9,9]+[1] -> [0,0,1].
 * Invariant: every output node is the correct digit for the processed
 * position, and carry is the value passed to the next position. For the
 * main input, 2+5=7, then 4+6=10 writes 0/carry 1, and 3+4+1=8.
 * Questions: why must the final carry be processed, and what happens when
 * one list ends first?
 */
public class AddTwoNumbers {
    
    // Definition for singly-linked list.
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // Dummy head simplifies edge cases where the head changes
        ListNode dummyHead = new ListNode(0);
        ListNode current = dummyHead;
        int carry = 0;
        
        while (l1 != null || l2 != null || carry != 0) {
            int x = (l1 != null) ? l1.val : 0;
            int y = (l2 != null) ? l2.val : 0;
            
            int sum = x + y + carry;
            carry = sum / 10;
            
            current.next = new ListNode(sum % 10);
            current = current.next;
            
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }
        
        return dummyHead.next;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);
        
        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);
        
        ListNode result = addTwoNumbers(l1, l2);
        System.out.print("Sum List: ");
        while (result != null) {
            System.out.print(result.val + " ");
            result = result.next;
        }
        // Output: Sum List: 7 0 8 
    }
}