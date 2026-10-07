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
