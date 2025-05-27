package eu.chrost.workshop;

import java.util.Arrays;
import java.util.concurrent.StructuredTaskScope;

class C04ParallelStructuredUntilFirstSuccess {
    @SafeVarargs
    static <T> T runInParallelStructuredUntilFirstSuccess(Action<T>... actions) {
        try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.<T>anySuccessfulResultOrThrow())) {
            Arrays.stream(actions)
                    .forEach(action -> scope.fork(action::run));
            var firstResult = scope.join();
            return firstResult;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
