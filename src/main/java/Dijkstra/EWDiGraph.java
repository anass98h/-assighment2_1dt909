package Dijkstra;

import java.util.ArrayList;

public class EWDiGraph {
    private final int V;
    private ArrayList<DiEdge>[] adj;

    public EWDiGraph(int V) {
        this.V = V;
        adj = (ArrayList<DiEdge>[]) new ArrayList[V];
        for (int v = 0; v < V; v++)
            adj[v] = new ArrayList<DiEdge>();
    }

    public int V() {
        return V;
    }

    public void addEdge(DiEdge e) {
        adj[e.src()].add(e);
    }

    public Iterable<DiEdge> adj(int v) {
        return adj[v];
    }

    public Iterable<DiEdge> edges() {
        ArrayList<DiEdge> list = new ArrayList<>();
        for (int v = 0; v < V; v++)
            for (DiEdge e : adj(v))
                list.add(e);
        return list;
    }
}