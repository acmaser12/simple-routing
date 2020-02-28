/*

Adam Maser
CSC 420 Week 7
2.28.2020

WeightedGraph.java
Class for Weighted graph. Adapted from textbook.
(unnecessary methods/classes removed)

 */
import java.util.*;

class WeightedGraph<V> extends UnweightedGraph<V> {

    /** Construct a WeightedGraph from vertices 0, 1, and edge array */
    WeightedGraph(List<WeightedEdge> edges,
                  int numberOfVertices) {
        List<V> vertices = new ArrayList<>();
        for (int i = 0; i < numberOfVertices; i++)
            vertices.add((V)(Integer.valueOf(i)));

        createWeightedGraph(vertices, edges);
    }

    /** Create adjacency lists from edge lists */
    private void createWeightedGraph(
            List<V> vertices, List<WeightedEdge> edges) {
        this.vertices = vertices;

        for (int i = 0; i < vertices.size(); i++) {
            neighbors.add(new ArrayList<>()); // Create a list for vertices
        }

        for (WeightedEdge edge: edges) {
            neighbors.get(edge.u).add(edge); // Add an edge into the list
        }
    }

    /** Find single source shortest paths */
    ShortestPathTree getShortestPath(int sourceVertex) {
        // cost[v] stores the cost of the path from v to the source
        double[] cost = new double[getSize()];
        // Initial cost set to infinity
        Arrays.fill(cost, Double.POSITIVE_INFINITY);
        cost[sourceVertex] = 0; // Cost of source is 0

        // parent[v] stores the previous vertex of v in the path
        int[] parent = new int[getSize()];
        parent[sourceVertex] = -1; // The parent of source is set to -1

        // T stores the vertices whose path found so far
        List<Integer> T = new ArrayList<>();

        // Expand T
        while (T.size() < getSize()) {
            // Find smallest cost v in V - T
            int u = -1; // Vertex to be determined
            double currentMinCost = Double.POSITIVE_INFINITY;
            for (int i = 0; i < getSize(); i++) {
                if (!T.contains(i) && cost[i] < currentMinCost) {
                    currentMinCost = cost[i];
                    u = i;
                }
            }

            if (u == -1) break; else T.add(u); // Add a new vertex to T

            // Adjust cost[v] for v that is adjacent to u and v in V - T
            for (Edge e : neighbors.get(u)) {
                if (!T.contains(e.v)
                        && cost[e.v] > cost[u] + ((WeightedEdge)e).weight) {
                    cost[e.v] = cost[u] + ((WeightedEdge)e).weight;
                    parent[e.v] = u;
                }
            }
        } // End of while

        // Create a ShortestPathTree
        return new ShortestPathTree(sourceVertex, parent, T, cost);
    }

    /** ShortestPathTree is an inner class in WeightedGraph */
    class ShortestPathTree extends SearchTree {
        private double[] cost; // cost[v] is the cost from v to source

        /** Construct a path */
        ShortestPathTree(int source, int[] parent,
                         List<Integer> searchOrder, double[] cost) {
            super(source, parent, searchOrder);
            this.cost = cost;
        }

        /** Return the cost for a path from the root to vertex v */
        double getCost(int v) {
            return cost[v];
        }
    }
}