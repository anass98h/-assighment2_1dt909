package FloydWarshalls;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import Dijkstra.DiEdge;
import Dijkstra.EWDiGraph;

public class FloydWConcurrent extends FloydW {
    private ExecutorService executorService;

    public double[][] start(int threads, EWDiGraph ewd, int[] v) {
        var dm = genDistanceMatrix(ewd);
        executorService = Executors.newFixedThreadPool(threads);

        for (int k = 0; k < v.length; k++) {
            final int K = k;
            executorService.submit(() -> {
                for (int i = 0; i < v.length; i++) {
                    for (int j = 0; j < v.length; j++) {
                        synchronized (dm) {
                            dm[i][j] = Math.min(dm[i][j], dm[i][K] + dm[K][j]);
                        }
                    }
                }
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return dm;
    }
}
