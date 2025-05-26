package eu.chrost.workshop;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class C01Concurrency {
    static String book(Resource<String> resourceOne, Resource<String> resourceTwo) {
        try (ExecutorService executor = Executors.newThreadPerTaskExecutor(Thread.ofVirtual().factory())) {
            CompletableFuture<String> bookingOne = CompletableFuture.supplyAsync(resourceOne::book, executor);
            CompletableFuture<String> bookingTwo = CompletableFuture.supplyAsync(resourceTwo::book, executor);
            return String.join(",", bookingOne.join(), bookingTwo.join());
        }
    }
}

