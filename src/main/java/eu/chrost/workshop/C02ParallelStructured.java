package eu.chrost.workshop;

import java.util.concurrent.StructuredTaskScope;
import java.util.function.Supplier;

class C02ParallelStructured {
    static <T, U>String run(Action<T> actionOne, Action<U> actionTwo) {
        try (var scope= StructuredTaskScope.open()) {
            Supplier<T> resultOne  = scope.fork(() -> actionOne.run());
            Supplier<U> resultTwo = scope.fork(() -> actionTwo.run());
            scope.join();
            return String.join(",", resultOne.get().toString(), resultTwo.get().toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
