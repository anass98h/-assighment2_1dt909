package test;

import Dijkstra.DijkstraSP;
import Dijkstra.DiEdge;
import Dijkstra.EWDiGraph;
import FloydWarshalls.FloydW;
import FloydWarshalls.FloydWConcurrent;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GraphAlgorithmsTest2 {

    public static void main(String[] args) {
        // Generate a large random graph
        int numVertices = 800; // Adjust the number of vertices
        int numEdges = 80; // Adjust the number of edges
        EWDiGraph graph = generateRandomGraph(numVertices, numEdges);

        // Prepare an array of all vertices for Floyd-Warshall
        int[] vertices = new int[numVertices];
        for (int i = 0; i < numVertices; i++) {
            vertices[i] = i;
        }

        // Serial Floyd-Warshall
        double[][] serialResult =  testSerialFloydWarshall(graph, vertices);

        // Concurrent Floyd-Warshall
        double[][] concurrentResult = testConcurrentFloydWarshall(graph, vertices);

        // Concurrent Dijkstra
        testConcurrentDijkstra(graph, numVertices);
        boolean isEqual = areMatricesEqual(serialResult, concurrentResult); // threshold example
        System.out.println("Matrices are equal: " + isEqual);
    }

    private static boolean areMatricesEqual(double[][] matrix1, double[][] matrix2 ) {
        if (matrix1.length != matrix2.length) return false;
        for (int i = 0; i < matrix1.length; i++) {
            if (matrix1[i].length != matrix2[i].length) return false;
            for (int j = 0; j < matrix1[i].length; j++) {
                if (matrix1[i][j] != matrix2[i][j] ) return false;
            }
        }

        return true;
    }

    private static double[][] testSerialFloydWarshall(EWDiGraph graph, int[] vertices) {
        FloydW serialFW = new FloydW();
        var start1 = System.nanoTime();
        double[][] serialResult = serialFW.floydW(vertices, serialFW.genDistanceMatrix(graph));
        var end1 = System.nanoTime();
        //System.out.println("Serial Floyd-Warshall Result:");
        //printMatrix(serialResult);

        System.out.println("Time for Serial FloWar: " + (end1 - start1));
        return serialResult;
    }

    private static double[][] testConcurrentFloydWarshall(EWDiGraph graph, int[] vertices) {
        FloydWConcurrent concurrentFW = new FloydWConcurrent();
        var start2 = System.nanoTime();
        double[][] concurrentResult = concurrentFW.start(8, graph, vertices);
        var end2 = System.nanoTime();
        //System.out.println("Concurrent Floyd-Warshall Result:");
        //printMatrix(concurrentResult);
        
        System.out.println("Time for Concurrent FloWar: " + (end2 - start2));
        return concurrentResult;
    }

    private static void testConcurrentDijkstra(EWDiGraph graph, int threads) {
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        var start3 = System.nanoTime();
        System.out.println("Dijkstra's Algorithm Result:");
        for (int s = 0; s < graph.V(); s++) {
            final int source = s;
            executor.execute(() -> {
                new DijkstraSP(graph, source);
                //System.out.println("Dijkstra from " + source + ": " + Arrays.toString(dsp.distTo));
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        var end3 = System.nanoTime();
        System.out.println("Time for Concurrent Dijkstra: " + (end3 - start3));
    }

    private static EWDiGraph generateRandomGraph(int numVertices, int numEdges) {
        EWDiGraph graph = new EWDiGraph(numVertices);
        Random rand = new Random();
        for (int i = 0; i < numEdges; i++) {
            int v = rand.nextInt(numVertices);
            int w = rand.nextInt(numVertices);
            double weight = Math.round(rand.nextDouble() * 100.0) / 100.0;
            graph.addEdge(new DiEdge(v, w, weight));
        }
        return graph;
    }

    private static void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }
}