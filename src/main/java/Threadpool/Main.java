package Threadpool;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {
        var threadPool = new ThreadPool(8);
        var totalWorks = 120;
        var futures = new ArrayList<CompletableFuture<Integer>>();

        int[] funnyVar = new int[] {0};
        for (var i = 0; i < totalWorks; i++) {
            futures.add(threadPool.queueWork(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    return ++funnyVar[0]; // heavy workload
                }
            }));
        }

        threadPool.start();

        for (var future : futures) {
            try {
                System.out.println(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        threadPool.stop();
    }
}
