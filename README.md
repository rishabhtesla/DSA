# DSA Interview Practice

This repository is a **pattern-first interview curriculum**, not just a
collection of solutions. The goal is to learn how to recognize a problem,
derive the solution, explain the invariant, and then implement it cleanly.

## How to study every problem

Do not read the solution first. For each class, use this loop:

1. **Understand the contract**: write down the input, output, constraints, and
   whether the input may be modified.
2. **Try a brute-force idea**: describe it and identify its time and space cost.
3. **Find the bottleneck**: ask what repeated work can be avoided.
4. **Choose a pattern**: two pointers, sliding window, greedy, hashing, and so
   on.
5. **State the invariant**: explain what remains true after every iteration.
6. **Dry-run an edge case**: empty input, one item, duplicates, sorted input,
   or the smallest valid example.
7. **Code from memory**: only then compare your implementation with the class.
8. **Review aloud**: explain why the algorithm works, its complexity, and one
   alternative approach.

Every problem class follows the same explanation template:

| Section | Question to answer |
| --- | --- |
| `PROBLEM` | What exactly must be computed? |
| `INTERVIEW INTUITION & "AHA!" MOMENT` | Why does this pattern fit, and what is the key observation? |
| `COMPLEXITY` | How much time and extra memory are required? |
| `main` examples | Can the implementation be dry-run and checked quickly? |

## Recommended progression

Study one pattern at a time. Finish the first pass by solving each problem
without looking at the implementation, then revisit the same problems after a
few days using only the problem name.

| Order | Pattern | Problems |
| ---: | --- | --- |
| 1 | Arrays and strings | `P01`-`P24` |
| 2 | Two pointers | `P29`-`P34` |
| 3 | Sliding window | `P35`-`P38` |
| 4 | Matrix traversal and simulation | `P39`-`P43` |
| 5 | Intervals and scheduling | `P25`-`P28` |
| 6 | Hashing and frequency maps | `P44`-`P52` |
| 7 | Linked lists and pointer manipulation | `P53`-`P65` |
| 8 | Stacks and monotonic stacks | `P66`-`P73` |
| 9 | Company-style practice | `AG01`-`AG06` |

## Pattern recognition guide

### Arrays and strings

Look for in-place updates, sorted input, prefix/suffix information, or a
decision that can be made greedily. Before using sorting or extra storage, ask
whether the array's existing order or unused capacity already provides the
needed structure.

### Two pointers

Use this when pointers can move monotonically through a sequence. Typical
signals are sorted input, a pair/triplet target, or comparing both ends of a
string or array.

### Sliding window

Use a window when the answer concerns a contiguous range. Define exactly what
makes the window valid, expand the right side, and shrink the left side only
when the invariant is violated.

### Hashing

Use a map or set when the problem asks for fast membership, counting, grouping,
or remembering the last position of something. State what each key and value
represent before writing code.

### Intervals

Sort by the boundary that makes future decisions local. Then decide whether the
current interval overlaps, extends, or is independent of the previous one.

### Linked lists

Draw the nodes and arrows before coding. Dummy nodes, fast/slow pointers, and
careful detach-then-attach operations remove most special cases.

### Stacks

Use a stack when the next decision depends on the most recent unresolved item.
For a monotonic stack, define what monotonic property the stack maintains and
what event causes an item to be popped.

## Interview explanation checklist

For any problem, practice answering in this order:

1. “The brute-force approach is ..., but it repeats ...”
2. “I will use ... because ...”
3. “The invariant is ...”
4. “When ..., I update ...”
5. “The answer is correct because ...”
6. “The time complexity is ... and the extra space is ...”
7. “The important edge cases are ...”

## Running a class

The project is plain Java and each class has a small `main` method with sample
inputs. From the repository root, compile the source tree and run a class:

```bash
rm -rf out
mkdir -p out
javac -d out $(find src -name '*.java')
java -cp out DSA.ArraysandStrings.P01_MergeSortedArray
```

The generated `out/` directory is build output and is ignored by Git.

## Progress tracker

When a problem is complete, record it mentally or in your own notes using:

- **Can I identify the pattern from the prompt?**
- **Can I state the invariant without looking?**
- **Can I implement it in 15-25 minutes?**
- **Can I explain the edge cases and complexity?**
- **Can I solve a variation with one constraint changed?**

Do not measure progress only by the number of files read. Interview readiness
comes from recalling the approach and rebuilding it under time pressure.


Part 1: Mathematics & Number Basics (Days 1–5)
Day 1: LeetCode 9 – Palindrome Number

Day 2: LeetCode 231 – Power of Two

Day 3: LeetCode 509 – Fibonacci Number

Day 4: LeetCode 1137 – N-th Tribonacci Number

Day 5: LeetCode 1518 – Water Bottles

Part 2: Arrays & Two Pointers (Days 6–20)
Day 6: LeetCode 1 – Two Sum

Day 7: LeetCode 989 – Add to Array-Form of Integer

Day 8: LeetCode 1464 – Maximum Product of Two Elements in an Array

Day 9: LeetCode 4 – Median of Two Sorted Arrays

Day 10: LeetCode 268 – Missing Number

Day 11: LeetCode 27 – Remove Element

Day 12: LeetCode 977 – Squares of a Sorted Array

Day 13: LeetCode 167 – Two Sum II - Input Array Is Sorted

Day 14: LeetCode 238 – Product of Array Except Self

Day 15: LeetCode 319 – Bulb Switcher

Day 16: LeetCode 747 – Largest Number At Least Twice of Others

Day 17: LeetCode 11 – Container With Most Water

Day 18: LeetCode 42 – Trapping Rain Water

Day 19: LeetCode 189 – Rotate Array

Day 20: LeetCode 1394 – Find Lucky Integer in an Array

Part 3: Binary Search & Sorting Patterns (Days 21–36)
Day 21: LeetCode 704 – Binary Search

Day 22: LeetCode 34 – Find First and Last Position of Element in Sorted Array

Day 23: LeetCode 33 – Search in Rotated Sorted Array

Day 24: LeetCode 153 – Find Minimum in Rotated Sorted Array

Day 25: LeetCode 852 – Peak Index in a Mountain Array

Day 26: LeetCode 35 – Search Insert Position

Day 27: LeetCode 540 – Single Element in a Sorted Array

Day 28: LeetCode 2089 – Find Target Indices After Sorting Array

Day 29: LeetCode 69 – Sqrt(x)

Day 30: LeetCode 875 – Koko Eating Bananas

Day 31: LeetCode 75 – Sort Colors

Day 32: LeetCode 179 – Largest Number

Day 33: LeetCode 2529 – Maximum Count of Positive Integer and Negative Integer

Day 34: LeetCode 66 – Plus One

Day 35: LeetCode 215 – Kth Largest Element in an Array

Day 36: LeetCode 347 – Top K Frequent Elements

Part 4: Stacks & Queues (Days 37–44)
Day 37: LeetCode 155 – Min Stack

Day 38: LeetCode 225 – Implement Stack using Queues

Day 39: LeetCode 232 – Implement Queue using Stacks

Day 40: LeetCode 496 – Next Greater Element I

Day 41: LeetCode 503 – Next Greater Element II

Day 42: LeetCode 739 – Daily Temperatures

Day 43: LeetCode 84 – Largest Rectangle in Histogram

Day 44: LeetCode 881 – Boats to Save People

Part 5: Strings & Hashing (Days 45–55)
Day 45: LeetCode 58 – Length of Last Word

Day 46: LeetCode 316 – Remove Duplicate Letters

Day 47: LeetCode 151 – Reverse Words in a String

Day 48: LeetCode 387 – First Unique Character in a String

Day 49: LeetCode 394 – Decode String

Day 50: LeetCode 1897 – Redistribute Characters to Make All Strings Equal

Day 51: LeetCode 1446 – Consecutive Characters

Day 52: LeetCode 921 – Minimum Add to Make Parentheses Valid

Day 53: LeetCode 20 – Valid Parentheses

Day 54: LeetCode 242 – Valid Anagram

Day 55: LeetCode 49 – Group Anagrams

Part 6: 2D Arrays / Matrices (Days 56–60)
Day 56: LeetCode 1572 – Matrix Diagonal Sum

Day 57: LeetCode 867 – Transpose Matrix

Day 58: LeetCode 59 – Spiral Matrix II

Day 59: LeetCode 74 – Search a 2D Matrix

Day 60: LeetCode 48 – Rotate Image

Part 7: Singly & Doubly Linked Lists (Days 61–76)
Day 61: LeetCode 206 – Reverse Linked List

Day 62: LeetCode 21 – Merge Two Sorted Lists

Day 63: LeetCode 83 – Remove Duplicates from Sorted List

Day 64: LeetCode 82 – Remove Duplicates from Sorted List II

Day 65: LeetCode 160 – Intersection of Two Linked Lists

Day 66: LeetCode 141 – Linked List Cycle

Day 67: LeetCode 142 – Linked List Cycle II

Day 68: LeetCode 19 – Remove Nth Node From End of List

Day 69: LeetCode 237 – Delete Node in a Linked List

Day 70: LeetCode 876 – Middle of the Linked List

Day 71: LeetCode 234 – Palindrome Linked List

Day 72: LeetCode 1019 – Next Greater Node In Linked List

Day 73: LeetCode 24 – Swap Nodes in Pairs

Day 74: LeetCode 92 – Reverse Linked List II

Day 75: LeetCode 1669 – Merge In Between Linked Lists

Day 76: LeetCode 23 – Merge k Sorted Lists

Part 8: Binary Trees & Tree Traversals (Days 77–86)
Day 77: LeetCode 144 – Binary Tree Preorder Traversal

Day 78: LeetCode 94 – Binary Tree Inorder Traversal

Day 79: LeetCode 145 – Binary Tree Postorder Traversal

Day 80: LeetCode 102 – Binary Tree Level Order Traversal

Day 81: LeetCode 103 – Binary Tree Zigzag Level Order Traversal

Day 82: LeetCode 104 – Maximum Depth of Binary Tree

Day 83: LeetCode 110 – Balanced Binary Tree

Day 84: LeetCode 101 – Symmetric Tree

Day 85: LeetCode 543 – Diameter of Binary Tree

Day 86: LeetCode 199 – Binary Tree Right Side View

Part 9: Binary Search Trees (BST) (Days 87–94)
Day 87: LeetCode 700 – Search in a Binary Search Tree

Day 88: LeetCode 701 – Insert into a Binary Search Tree

Day 89: LeetCode 450 – Delete Node in a BST

Day 90: LeetCode 108 – Convert Sorted Array to Binary Search Tree

Day 91: LeetCode 230 – Kth Smallest Element in a BST

Day 92: LeetCode 235 – Lowest Common Ancestor of a Binary Search Tree

Day 93: LeetCode 109 – Convert Sorted List to Binary Search Tree

Day 94: LeetCode 1382 – Balance a Binary Search Tree

Part 10: Dynamic Programming & Greedy Essentials (Days 95–100)
Day 95: LeetCode 1038 – Binary Search Tree to Greater Sum Tree

Day 96: LeetCode 70 – Climbing Stairs

Day 97: LeetCode 198 – House Robber

Day 98: LeetCode 53 – Maximum Subarray

Day 99: LeetCode 55 – Jump Game

Day 100: LeetCode 121 – Best Time to Buy and Sell Stock