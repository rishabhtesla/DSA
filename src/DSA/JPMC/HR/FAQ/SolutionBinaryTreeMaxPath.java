package DSA.JPMC.HR.FAQ;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * ============================================================================
 * HACKERRANK QUESTION: Maximum Path Sum in Distributed Routing Hierarchy
 * ============================================================================
 *
 * Problem Statement:
 * A path in a binary tree is a sequence of nodes where each pair of adjacent
 * nodes has an edge connecting them. A node can only appear at most once.
 * The path does NOT need to pass through the root.
 *
 * Given the root of a binary tree, return the maximum path sum of any non-empty path.
 *
 * Constraints:
 * - 1 <= Node Count <= 3 * 10^4
 * - -1000 <= Node.val <= 1000
 *
 * Logic:
 * Post-order traversal (bottom-up):
 * At each node, compute:
 * - leftGain = Math.max(0, maxGain(node.left))
 * - rightGain = Math.max(0, maxGain(node.right))
 * - localArchSum = node.val + leftGain + rightGain (update globalMax)
 * - Return to caller: node.val + Math.max(leftGain, rightGain)
 * ============================================================================
 */
public class SolutionBinaryTreeMaxPath {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int val) { this.val = val; }
    }

    private static int globalMax;

    public static int maxPathSum(TreeNode root) {
        globalMax = Integer.MIN_VALUE;
        calculateGain(root);
        return globalMax;
    }

    private static int calculateGain(TreeNode node) {
        if (node == null) return 0;

        int leftGain = Math.max(0, calculateGain(node.left));
        int rightGain = Math.max(0, calculateGain(node.right));

        int archSum = node.val + leftGain + rightGain;
        globalMax = Math.max(globalMax, archSum);

        return node.val + Math.max(leftGain, rightGain);
    }

    public static void main(String[] args) throws IOException {
        // Sample tree build: [-10, 9, 20, null, null, 15, 7]
        TreeNode root = new TreeNode(-10);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        System.out.println(maxPathSum(root)); // Output: 42
    }
}