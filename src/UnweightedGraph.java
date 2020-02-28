/*

Adam Maser
CSC 420 Week 7
2.28.2020

UnweightedGraph.java
Class for Unweighted graph. Adapted from textbook.
(unnecessary methods removed)

 */
import java.util.*;

public class UnweightedGraph<V> implements Graph<V> {
    List<V> vertices = new ArrayList<>(); // Store vertices
    List<List<Edge>> neighbors
            = new ArrayList<>(); // Adjacency lists

    /** Construct an empty graph */
    UnweightedGraph() {
    }

    @Override /* Return the number of vertices in the graph */
    public int getSize() {
        return vertices.size();
    }

    @Override /* Return the vertices in the graph */
    public List<V> getVertices() {
        return vertices;
    }

    @Override /* Return the object for the specified vertex */
    public V getVertex(int index) {
        return vertices.get(index);
    }

    @Override /* Return the index for the specified vertex object */
    public int getIndex(V v) {
        return vertices.indexOf(v);
    }

    @Override /* Return the neighbors of the specified vertex */
    public List<Integer> getNeighbors(int index) {
        List<Integer> result = new ArrayList<>();
        for (Edge e: neighbors.get(index))
            result.add(e.v);

        return result;
    }

    @Override /* Return the degree for a specified vertex */
    public int getDegree(int v) {
        return neighbors.get(v).size();
    }

    @Override /* Print the edges */
    public void printEdges() {
        for (int u = 0; u < neighbors.size(); u++) {
            System.out.print(getVertex(u) + " (" + u + "): ");
            for (Edge e: neighbors.get(u)) {
                System.out.print("(" + getVertex(e.u) + ", " +
                        getVertex(e.v) + ") ");
            }
            System.out.println();
        }
    }

    @Override /* Clear the graph */
    public void clear() {
        vertices.clear();
        neighbors.clear();
    }

    @Override /* Add a vertex to the graph */
    public boolean addVertex(V vertex) {
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
            neighbors.add(new ArrayList<>());
            return true;
        }
        else {
            return false;
        }
    }

    @Override /* Add an edge to the graph */
    public boolean addEdge(Edge e) {
        if (e.u < 0 || e.u > getSize() - 1)
            throw new IllegalArgumentException("No such index: " + e.u);

        if (e.v < 0 || e.v > getSize() - 1)
            throw new IllegalArgumentException("No such index: " + e.v);

        if (!neighbors.get(e.u).contains(e)) {
            neighbors.get(e.u).add(e);
            return true;
        }
        else {
            return false;
        }
    }

    @Override /* Add an edge to the graph */
    public boolean addEdge(int u, int v) {
        return addEdge(new Edge(u, v));
    }

    @Override /* Obtain a DFS tree starting from vertex u */
    /* To be discussed in Section 28.7 */
    public SearchTree dfs(int v) {
        List<Integer> searchOrder = new ArrayList<>();
        int[] parent = new int[vertices.size()];
        // Initialize parent[i] to -1
        Arrays.fill(parent, -1);

        // Mark visited vertices
        boolean[] isVisited = new boolean[vertices.size()];

        // Recursively search
        dfs(v, parent, searchOrder, isVisited);

        // Return a search tree
        return new SearchTree(v, parent, searchOrder);
    }

    /** Recursive method for DFS search */
    private void dfs(int v, int[] parent, List<Integer> searchOrder,
                     boolean[] isVisited) {
        // Store the visited vertex
        searchOrder.add(v);
        isVisited[v] = true; // Vertex v visited

        for (Edge e : neighbors.get(v)) { // Note that e.u is v
            int w = e.v; // e.v is w in Listing 28.8
            if (!isVisited[w]) {
                parent[w] = v; // The parent of w is v
                dfs(w, parent, searchOrder, isVisited); // Recursive search
            }
        }
    }

    @Override /* Starting bfs search from vertex v */
    /* To be discussed in Section 28.9 */
    public SearchTree bfs(int v) {
        List<Integer> searchOrder = new ArrayList<>();
        int[] parent = new int[vertices.size()];
        // Initialize parent[i] to -1
        Arrays.fill(parent, -1);

        java.util.LinkedList<Integer> queue =
                new java.util.LinkedList<>(); // list used as a queue
        boolean[] isVisited = new boolean[vertices.size()];
        queue.offer(v); // Enqueue v
        isVisited[v] = true; // Mark it visited

        while (!queue.isEmpty()) {
            int u = queue.poll(); // Dequeue to u
            searchOrder.add(u); // u searched
            for (Edge e: neighbors.get(u)) { // Note that e.u is u
                int w = e.v; // e.v is w in Listing 28.8
                if (!isVisited[w]) {
                    queue.offer(w); // Enqueue w
                    parent[w] = u; // The parent of w is u
                    isVisited[w] = true; // Mark w visited
                }
            }
        }

        return new SearchTree(v, parent, searchOrder);
    }

    /* Tree inner class inside the AbstractGraph class */
    /** To be discussed in Section 28.6 */
    class SearchTree {
        private int root; // The root of the tree
        private int[] parent; // Store the parent of each vertex
        private List<Integer> searchOrder; // Store the search order

        /** Construct a tree with root, parent, and searchOrder */
        SearchTree(int root, int[] parent,
                   List<Integer> searchOrder) {
            this.root = root;
            this.parent = parent;
            this.searchOrder = searchOrder;
        }
        /** Return the path of vertices from a vertex to the root */
        ArrayList<V> getPath(int index) {
            ArrayList<V> path = new ArrayList<>();

            do {
                path.add(vertices.get(index));
                index = parent[index];
            }
            while (index != -1);

            return path;
        }
    }

    @Override /* Remove vertex v and return true if successful */
    public boolean remove(V v) {
        return true; // Implementation left as an exercise
    }

    @Override /* Remove edge (u, v) and return true if successful */
    public boolean remove(int u, int v) {
        return true; // Implementation left as an exercise
    }
}