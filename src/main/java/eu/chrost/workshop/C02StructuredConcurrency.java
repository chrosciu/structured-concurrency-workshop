package eu.chrost.workshop;

import java.util.concurrent.StructuredTaskScope;
import java.util.function.Supplier;

class C02StructuredConcurrency {
    static String book(Resource resourceOne, Resource resourceTwo) {
        try (var scope = StructuredTaskScope.<String>open()) {
            Supplier<String> flight  = scope.fork(() -> resourceOne.book());
            Supplier<String> hotel = scope.fork(() -> resourceTwo.book());
            scope.join();
            return String.join(",", flight.get(), hotel.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
