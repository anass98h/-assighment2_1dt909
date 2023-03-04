package org.example;

import Dijkstra.DiEdge;
import Dijkstra.DijkstraSP;
import Dijkstra.EWDiGraph;

public class Main {

    public static void main(String[] args) {
        EWDiGraph ewd = new EWDiGraph(8);
        ewd.addEdge(new DiEdge(0, 5, 0.34));
        ewd.addEdge(new DiEdge(5, 3, 0.34));
        ewd.addEdge(new DiEdge(0, 2, 0.64));
        ewd.addEdge(new DiEdge(1, 4, 0.34));
        ewd.addEdge(new DiEdge(4, 1, 0.64));
        ewd.addEdge(new DiEdge(5, 6, 0.34));
        ewd.addEdge(new DiEdge(6, 0, 0.64));
        ewd.addEdge(new DiEdge(6, 4, 0.34));
        int s = 0;
        DijkstraSP sp = new DijkstraSP(ewd, s);

        for(var v=1; v< ewd.V(); v++){
            if(sp.distTo(v)< Double.POSITIVE_INFINITY){
                System.out.printf("%d to %d (%f): ",s,v,sp.distTo(v));

                
            } else {
                System.out.printf("%d to %d: no path\n ", s, v);
            }
        }
    }
}