package eu.chrost.workshop;

import java.util.Arrays;
import java.util.concurrent.StructuredTaskScope;
import java.util.stream.Collectors;

class C03ParallelStructuredOfSameType {
    static <T> String runInParallelStructuredOfSameType(Action<T>... actions) {
        try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.<T>allSuccessfulOrThrow())) {
            Arrays.stream(actions)
                    .forEach(action -> scope.fork(action::run));
            var subtasksStream = scope.join();
            return subtasksStream
                    .map(StructuredTaskScope.Subtask::get)
                    .map(Object::toString)
                    .collect(Collectors.joining(","));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
