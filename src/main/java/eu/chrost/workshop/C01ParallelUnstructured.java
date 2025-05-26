package eu.chrost.workshop;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class C01ParallelUnstructured {
    static <T, U> String runInParallel(Action<T> actionOne, Action<U> actionTwo) {
        try (ExecutorService executor = Executors.newThreadPerTaskExecutor(Thread.ofVirtual().factory())) {
            CompletableFuture<T> resultOne = CompletableFuture.supplyAsync(actionOne::run, executor);
            CompletableFuture<U> resultTwo = CompletableFuture.supplyAsync(actionTwo::run, executor);
            return String.join(",", resultOne.join().toString(), resultTwo.join().toString());
        }
    }
}

