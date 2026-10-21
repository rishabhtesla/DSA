package DSA.JPMC;

/**
 * PROBLEM: Lowest Common Ancestor of a Binary Tree
 * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes p and q.
 * The LCA is the lowest node in T that has both p and q as descendants 
 * (where we allow a node to be a descendant of itself).
 * 
 * EXAMPLE:
 * Input: root = [3,5,1,6,2,0,8], p = 5, q = 1
 * Output: 3 (Node 3 is the lowest node containing both 5 and 1)
 * 
 * LOGIC:
 * We traverse the tree using DFS.
 * 1. Base case: If the current root is null, or it matches p or q, return the root.
 *    (This means we found one of the targets).
 * 2. Recursively search the left subtree and right subtree.
 * 3. If BOTH left and right recursive calls return a non-null value, it means p is in one 
 *    subtree and q is in the other. Thus, the current root IS the lowest common ancestor!
 * 4. If only one side returns a non-null value, it means both nodes are on that side. 
 *    Return that non-null value up the stack.
 * 
 * COMPLEXITY: Time: O(N) to visit all nodes. Space: O(H) where H is the height of the tree (Call stack).
 */
/**
 * Learning supplement: a parent-map solution uses O(N) space; DFS uses O(H)
 * stack space and O(N) time. Examples: root [3,5,1], p=5,q=1 -> 3 and
 * p=5,q=6 -> 5. Invariant: each recursive result is null or the target/LCA
 * found in that subtree. The main left/right calls return 5 and 1, so 3 is
 * selected. Ask what the method assumes when one target is absent.
 */
public class LowestCommonAncestor {
    
    // Definition for a binary tree node.
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // Base case: root is null, or we found p, or we found q.
        if (root == null || root == p || root == q) {
            return root;
        }
        
        // Search left and right subtrees
        TreeNode leftResult = lowestCommonAncestor(root.left, p, q);
        TreeNode rightResult = lowestCommonAncestor(root.right, p, q);
        
        // If both left and right are NOT null, p and q are on different sides.
        // This root is the lowest common ancestor.
        if (leftResult != null && rightResult != null) {
            return root;
        }
        
        // Otherwise, return the non-null result (meaning both nodes are in the same subtree)
        return leftResult != null ? leftResult : rightResult;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        TreeNode p = new TreeNode(5);
        TreeNode q = new TreeNode(1);
        root.left = p;
        root.right = q;
        root.left.left = new TreeNode(6);
        root.left.right = new TreeNode(2);
        
        TreeNode lca = lowestCommonAncestor(root, p, q);
        System.out.println("Lowest Common Ancestor: " + lca.val); // Output: 3
    }
}