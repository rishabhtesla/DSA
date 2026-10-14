package DSA.JPMC; /**
 * PROBLEM: Course Schedule
 * There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1.
 * You are given an array prerequisites where prerequisites[i] = [a, b] indicates that you 
 * must take course b first if you want to take course a.
 * Return true if you can finish all courses. Otherwise, return false.
 * 
 * EXAMPLE:
 * Input: numCourses = 2, prerequisites = [[1,0]]
 * Output: true (Take 0, then 1).
 * Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
 * Output: false (Cycle detected. 1 requires 0, 0 requires 1).
 * 
 * LOGIC (Kahn's Algorithm / BFS):
 * 1. Build a directed graph (Adjacency List) and an 'In-Degree' array (count of incoming edges).
 * 2. Any node with an In-Degree of 0 has NO prerequisites. Add these to a Queue.
 * 3. Process the queue: Remove a node, increment 'coursesTaken', and reduce the in-degree of its neighbors.
 * 4. If a neighbor's in-degree hits 0, add it to the queue.
 * 5. If 'coursesTaken' equals 'numCourses', there are no cycles. Return true.
 * 
 * DRY RUN: courses = 2, pre = [[1,0]] (0 -> 1)
 * - In-degrees: [0: 0, 1: 1]. AdjList: {0: [1]}
 * - Queue initialized with [0] (since in-degree is 0).
 * - Pop 0. coursesTaken = 1. Neighbor is 1. Reduce 1's in-degree to 0. 
 * - Since 1's in-degree is 0, add to Queue.
 * - Pop 1. coursesTaken = 2. No neighbors.
 * - Queue empty. coursesTaken (2) == numCourses (2). Return true.
 * 
 * COMPLEXITY: Time: O(V + E) (Vertices + Edges). Space: O(V + E) for Graph and Arrays.
 */
import java.util.*;

public class CourseSchedule {
    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adjList = new ArrayList<>();
        int[] inDegree = new int[numCourses];
        
        for (int i = 0; i < numCourses; i++) {
            adjList.add(new ArrayList<>());
        }
        
        // Build Graph and In-Degree array
        for (int[] pre : prerequisites) {
            int course = pre[0];
            int prerequisite = pre[1];
            adjList.get(prerequisite).add(course);
            inDegree[course]++;
        }
        
        // Add all courses with 0 prerequisites to the queue
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }
        
        int coursesTaken = 0;
        
        while (!queue.isEmpty()) {
            int current = queue.poll();
            coursesTaken++;
            
            // Unlock neighbors
            for (int neighbor : adjList.get(current)) {
                inDegree[neighbor]--;
                // If the neighbor now has 0 prerequisites, it is ready to be taken
                if (inDegree[neighbor] == 0) {
                    queue.add(neighbor);
                }
            }
        }
        
        return coursesTaken == numCourses;
    }

    public static void main(String[] args) {
        int[][] prerequisites = {{1, 0}, {0, 1}};
        System.out.println("Can finish courses? " + canFinish(2, prerequisites)); // Output: false
    }
}