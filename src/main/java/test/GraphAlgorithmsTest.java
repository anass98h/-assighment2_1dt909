package test;

import Dijkstra.DijkstraSP;
import Dijkstra.DiEdge;
import Dijkstra.EWDiGraph;
import FloydWarshalls.FloydW;
import FloydWarshalls.FloydWConcurrent;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GraphAlgorithmsTest {

    public static void main(String[] args) {


        EWDiGraph graph = new EWDiGraph(8);
        
        graph.addEdge(new DiEdge(0, 5, 0.34));
        graph.addEdge(new DiEdge(5, 3, 0.34));
        graph.addEdge(new DiEdge(0, 2, 0.64));
        graph.addEdge(new DiEdge(1, 4, 0.34));
        graph.addEdge(new DiEdge(4, 1, 0.64));
        graph.addEdge(new DiEdge(5, 6, 0.34));
        graph.addEdge(new DiEdge(6, 0, 0.64));
        graph.addEdge(new DiEdge(6, 4, 0.34));
        graph.addEdge(new DiEdge(0, 1, 0.34));

        // Run Serial Floyd-Warshall
        
        FloydW serialFW = new FloydW();

        var start1 = System.nanoTime();
        double[][] serialResult = serialFW.floydW(new int[]{0, 1, 2, 3, 4, 5, 6, 7}, serialFW.genDistanceMatrix(graph));
        var end1 = System.nanoTime();
        var time1 = (end1 - start1);
        // System.out.println("Serial Floyd-Warshall Result:");
        // printMatrix(serialResult);

        System.out.println("Time for Serial FloWar: " + time1);
        // Run Concurrent Floyd-Warshall
        
        FloydWConcurrent concurrentFW = new FloydWConcurrent();
        var start2 = System.nanoTime();
        double[][] concurrentResult = concurrentFW.start(8, graph, new int[]{0, 1, 2, 3, 4, 5, 6, 7});
        var end2 = System.nanoTime();
        var time2 = (end2 - start2);
        // System.out.println("Concurrent Floyd-Warshall Result:");
        // printMatrix(concurrentResult);

        System.out.println("Time for Concurrent FloWar: " + time2);
        // Run Dijkstra's algorithm for each vertex, concurrently
        var start3 = System.nanoTime();
        System.out.println("Dijkstra's Algorithm Result:");
        runDijkstraConcurrently(graph, 8);
        var end3 = System.nanoTime();
        var time3 = (end3 - start3);
        System.out.println("Time for Concurrent Dijkstra: " + time3);
    }

    private static void runDijkstraConcurrently(EWDiGraph graph, int threads) {
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        for (int s = 0; s < graph.V(); s++) {
            final int source = s;
            executor.execute(() -> {
                DijkstraSP dsp = new DijkstraSP(graph, source);
                System.out.println("Dijkstra from " + source + ": " + Arrays.toString(dsp.distTo));
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // private static void printMatrix(double[][] matrix) {
    //     for (double[] row : matrix) {
    //         System.out.println(Arrays.toString(row));
    //     }
    // }
}
