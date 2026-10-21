package DSA.JPMC.path; /**
 * PROBLEM: Dijkstra's Algorithm
 * Find the shortest path from a starting node to all other nodes in a weighted graph.
 * 
 * LOGIC:
 * 1. Maintain an array `distances` tracking the shortest known distance to every node. 
 *    Initialize the start node's distance to 0, and all others to Infinity (Integer.MAX_VALUE).
 * 2. Use a Min-Heap (PriorityQueue) to constantly fetch the node with the smallest known distance.
 * 3. Pop a node from the heap. Iterate through all its neighboring edges.
 * 4. Relaxation Step: If (Distance to current node + weight of edge to neighbor) is LESS 
 *    than the currently known distance to the neighbor, update the neighbor's distance and 
 *    push the neighbor into the PriorityQueue.
 * 5. Repeat until the PriorityQueue is empty.
 * 
 * COMPLEXITY: Time: O(E log V) where E is edges, V is vertices. Space: O(V).
 */
import java.util.*;

/**
 * Learning supplement: trying every path can be exponential; with
 * non-negative edge weights, a min-heap relaxation algorithm costs
 * O((V+E) log V) time and O(V+E) space. Examples: edges 0->1 (4),
 * 0->2 (1), 2->1 (2) give dist(1)=3; an unreachable node remains infinity.
 * Invariant: when the smallest queued distance is removed (and stale entries
 * are ignored), it is final. Dry run: start 0, discover 1=4 and 2=1, then
 * relax 2->1 to 3. Ask why negative edges invalidate the greedy choice.
 */
public class DijkstraAlgorithm {
    
    // Class representing an edge connecting a node to a neighbor with a specific weight
    static class Edge {
        int targetNode;
        int weight;
        public Edge(int targetNode, int weight) {
            this.targetNode = targetNode;
            this.weight = weight;
        }
    }

    // Class representing a node in the PriorityQueue
    static class Node implements Comparable<Node> {
        int id;
        int distance;
        public Node(int id, int distance) {
            this.id = id;
            this.distance = distance;
        }

        // This ensures the PriorityQueue is a Min-Heap sorted by shortest distance
        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.distance, other.distance);
        }
    }

    public static void dijkstra(Map<Integer, List<Edge>> graph, int startNode, int numNodes) {
        int[] distances = new int[numNodes];
        Arrays.fill(distances, Integer.MAX_VALUE); // Initialize to Infinity
        distances[startNode] = 0;

        PriorityQueue<Node> minHeap = new PriorityQueue<>();
        minHeap.add(new Node(startNode, 0));

        while (!minHeap.isEmpty()) {
            // Fetch the node with the shortest known path so far
            Node currentNode = minHeap.poll();
            int currentId = currentNode.id;

            // Minor optimization: if we pulled an outdated (longer) path from the heap, skip it
            if (currentNode.distance > distances[currentId]) {
                continue; 
            }

            // Iterate over all neighbors of the current node
            List<Edge> neighbors = graph.getOrDefault(currentId, new ArrayList<>());
            for (Edge edge : neighbors) {
                int neighborId = edge.targetNode;
                int newDistance = distances[currentId] + edge.weight;

                // RELAXATION: If we found a strictly shorter path to the neighbor
                if (newDistance < distances[neighborId]) {
                    distances[neighborId] = newDistance; // Update shortest distance
                    minHeap.add(new Node(neighborId, newDistance)); // Push to heap for processing
                }
            }
        }

        // Print final shortest distances
        System.out.println("Shortest distances from Node " + startNode + ":");
        for (int i = 0; i < numNodes; i++) {
            System.out.println("To Node " + i + " -> Distance: " + distances[i]);
        }
    }

    public static void main(String[] args) {
        int numNodes = 5;
        Map<Integer, List<Edge>> graph = new HashMap<>();
        
        // Building the graph (Adjacency List)
        // 0 -> (1, weight 4), (2, weight 1)
        graph.put(0, Arrays.asList(new Edge(1, 4), new Edge(2, 1)));
        // 1 -> (3, weight 1)
        graph.put(1, Arrays.asList(new Edge(3, 1)));
        // 2 -> (1, weight 2), (3, weight 5)
        graph.put(2, Arrays.asList(new Edge(1, 2), new Edge(3, 5)));
        // 3 -> (4, weight 3)
        graph.put(3, Arrays.asList(new Edge(4, 3)));
        graph.put(4, new ArrayList<>());

        // Run Dijkstra starting from node 0
        dijkstra(graph, 0, numNodes);
        
        /* Expected Output:
           To Node 0 -> Distance: 0
           To Node 1 -> Distance: 3  (0 -> 2 -> 1)
           To Node 2 -> Distance: 1  (0 -> 2)
           To Node 3 -> Distance: 4  (0 -> 2 -> 1 -> 3)
           To Node 4 -> Distance: 7  (0 -> 2 -> 1 -> 3 -> 4)
        */
    }
}