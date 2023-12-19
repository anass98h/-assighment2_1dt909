package FloydWarshalls;

import Dijkstra.DiEdge;
import Dijkstra.EWDiGraph;

public class FloydW {
    public double[][] genDistanceMatrix(EWDiGraph g) {
        var dm = new double[g.V()][g.V()];
        for (var i = 0; i < dm.length; i++) {
            for (var j = 0; j < dm.length; j++) {
                if (i == j)
                    dm[i][j] = 0;
                else 
                    dm[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        for (var e : g.edges()) {
            dm[e.src()][e.dst()] = e.weight();
        }

        return dm;
    }

    public double[][] floydW(int[] V, double[][] dm) {
        for (var k : V) {
            for (var i : V) {
                for (var j : V) {
                    dm[i][j] = Math.min(dm[i][j], dm[i][k] + dm[k][j]);
                }
            }
        }

        return dm;
    }

    public void start() {
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

        var dm = floydW(v, genDistanceMatrix(ewd));
    }
}
