package Dijkstra;

import java.util.Comparator;
import java.util.PriorityQueue;

public class DijkstraSP {
    public DiEdge[] edgeTo;
    public double[] distTo;
    public PriorityQueue<Integer> pq;

    public DijkstraSP(EWDiGraph G, int s) {
        edgeTo = new DiEdge[G.V()];
        distTo = new double[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        pq = new PriorityQueue<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer v1, Integer v2) {
                if (distTo[v1] < distTo[v2]) return -1;
                else if (distTo[v1] > distTo[v2]) return 1;
                else return 0;
            }
        });

        pq.add(s);
        while (!pq.isEmpty()) {
            int v = pq.poll();
            for (DiEdge e : G.adj(v))
                relax(e);
        }
    }
    public double distTo(int v) {
        return distTo[v];
    }

    private void relax(DiEdge e) {
        int v = e.src(), w = e.dst();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;

            if(pq.contains(w)){
                pq.remove(w);
                pq.add(w);
            }else{
                pq.add(w);
            }
        }
    }
}


