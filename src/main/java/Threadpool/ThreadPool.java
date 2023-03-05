package Threadpool;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {
    static final int max_tries = 10;
    int threads;
    LinkedBlockingQueue<FutureCallable<?, ? extends CompletableFuture<?>>> queue;
    Thread[] pool;
    boolean[] exit;

    public ThreadPool(int threads) {
        this.threads = threads;
        queue = new LinkedBlockingQueue<>();
        pool = new Thread[threads];
        exit = new boolean[] {false};
        for (var i = 0; i < threads; i++) {
            pool[i] = new Thread(() -> {
                while (!exit[0]) {
                    try {
                        var fcb = queue.take();
                        fcb.call();
                    } catch (Exception e) {
                        throw new RuntimeException("Exception when calling future callable callback: \n" + e.getMessage()); 
                    }
                }
            });
        }
    }

    public void start() {
        for (var thread : pool) {
            thread.start();
        }
    }

    public void join() throws InterruptedException {
        for (var thread : pool) {
            thread.join();
        }
    }

    public void stop() {
        exit[0] = true;
    }

    public <T, C extends Callable<T>> CompletableFuture<T> queueWork(C cb) {
        var iter = 0;
        Optional<InterruptedException> oiex = null;
        while (iter < max_tries) {
            try {
                var future = new CompletableFuture<T>();
                queue.put(new FutureCallable<T, CompletableFuture<T>>(cb, future));
                return future; // successfully queue:d work
            } catch (InterruptedException e) {
                oiex = Optional.of(e);
            }
        }
        oiex.ifPresent(InterruptedException::printStackTrace); // 😎
        throw new RuntimeException("work was unable to be queue:d"); // work was unable to be queue:d
    }

    protected void finalize() throws Throwable {
        System.out.println("blue was not the impostor");
        stop();
    }
}
