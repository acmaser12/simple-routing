/*

Adam Maser
CSC 420 Week 7
2.28.2020

WeightedEdge.java
Class for Weighted Edge. Adapted from textbook.

 */
public class WeightedEdge extends Edge implements Comparable<WeightedEdge> {
    double weight; // The weight on edge (u, v)

    /** Create a weighted edge on (u, v) */
    WeightedEdge(int u, int v, double weight) {
        super(u, v);
        this.weight = weight;
    }

    @Override /* Compare two edges on weights */
    public int compareTo(WeightedEdge edge) {
        return Double.compare(weight, edge.weight);
    }
}