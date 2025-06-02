package eu.chrost.workshop;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.StructuredTaskScope;

class C03ParallelStructuredOfSameType {
    @SafeVarargs
    static <T> List<T> run(Action<T>... actions) {
        try (var scope= StructuredTaskScope.open(StructuredTaskScope.Joiner.<T>allSuccessfulOrThrow())) {
            Arrays.stream(actions)
                    .forEach(action -> scope.fork(action::run));
            var subtasksStream = scope.join();
            return subtasksStream
                    .map(StructuredTaskScope.Subtask::get)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
