package eu.chrost.workshop;

import lombok.SneakyThrows;

import java.util.concurrent.StructuredTaskScope;
import java.util.function.Supplier;

class C02StructuredConcurrency {
    @SneakyThrows
    static String book(Resource resourceOne, Resource resourceTwo) {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure("Booking scope", Thread.ofVirtual().factory())) {
            Supplier<String> flight  = scope.fork(() -> resourceOne.book());
            Supplier<String> hotel = scope.fork(() -> resourceTwo.book());
            scope.join().throwIfFailed();
            return String.join(",", flight.get(), hotel.get());
        }
    }
}
