package eu.chrost.workshop;

import java.util.concurrent.StructuredTaskScope;
import java.util.function.Supplier;

class C02AwaitAllSuccessfulOrThrow {
    static String book(Resource<String> resourceOne, Resource<String> resourceTwo) {
        try (var scope = StructuredTaskScope.open()) {
            Supplier<String> flight  = scope.fork(() -> resourceOne.book());
            Supplier<String> hotel = scope.fork(() -> resourceTwo.book());
            scope.join();
            return String.join(",", flight.get(), hotel.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
