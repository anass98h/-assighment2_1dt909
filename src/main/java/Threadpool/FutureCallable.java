package Threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

public class FutureCallable<C, T extends CompletableFuture<C>> implements Callable<Boolean> {
    Callable<C> cb;
    T future;

    public FutureCallable(Callable<C> cb, T future) {
        this.cb = cb;
        this.future = future;
    }

    @Override
    public Boolean call() throws Exception {
        return future.complete(cb.call());
    }
}
