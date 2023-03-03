package Dijkstra;

public class DiEdge implements Comparable<DiEdge> {
    private final int v;
    private final int w;
    private final double weight;

    public DiEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int src() {
        return v;
    }

    public int dst() {
        return w;
    }

    public double weight() {
        return weight;
    }

    @Override
    public int compareTo(DiEdge that) {
        if (this.weight < that.weight) return -1;
        else if (this.weight > that.weight) return 1;
        else return 0;
    }

    @Override
    public String toString() {
        return String.format("DiEdge(%d, %d, %.2f)", v, w, weight);
    }
}

