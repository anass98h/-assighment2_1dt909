package FloydWarshalls;

import Dijkstra.DiEdge;
import Dijkstra.EWDiGraph;

public class FloydWConcurrent extends FloydW {
    @Override
    public double[][] floydW(int[] V, double[][] dm) { 
        return floydW(V, V, dm);
    }

    public double[][] floydW(int[] V, int[] C, double[][] dm) {
        for (var k : V) {
            for (var i : C) {
                for (var j : V) {
                    dm[i][j] = Math.min(dm[i][j], dm[i][k] + dm[k][j]);
                }
            }
        }
        
        return dm;
    }

    @Override
    public void start() {
        start(8);
    }

    public void start(int threads) {
        EWDiGraph ewd = new EWDiGraph(8);
        ewd.addEdge(new DiEdge(0, 5, 0.34));
        ewd.addEdge(new DiEdge(5, 3, 0.34));
        ewd.addEdge(new DiEdge(0, 2, 0.64));
        ewd.addEdge(new DiEdge(1, 4, 0.34));
        ewd.addEdge(new DiEdge(4, 1, 0.64));
        ewd.addEdge(new DiEdge(5, 6, 0.34));
        ewd.addEdge(new DiEdge(6, 0, 0.64));
        ewd.addEdge(new DiEdge(6, 4, 0.34));
        ewd.addEdge(new DiEdge(0, 1, 0.34));

        var v = new int[] {0, 1, 2, 3, 4, 5, 6};
        var dm = genDistanceMatrix(ewd);

        for (var i = 0; i < (threads <= v.length ? threads : v.length); i++) {
            var c = // split into x chunks, one for each thread, then done
            floydW(v, c, dm);
        }
    }
}