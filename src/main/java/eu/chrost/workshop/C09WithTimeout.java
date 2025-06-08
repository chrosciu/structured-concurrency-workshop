package eu.chrost.workshop;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.StructuredTaskScope;

class C09WithTimeout {
    @SafeVarargs
    static <T> List<T> run(Duration timeout, Action<T>... actions) {
        try (var scope= StructuredTaskScope.open(
                StructuredTaskScope.Joiner.<T>allSuccessfulOrThrow(),
                configuration -> configuration.withTimeout(timeout))) {
            Arrays.stream(actions)
                    .forEach(action -> scope.fork(action::run));
            var subtasksStream = scope.join();
            return subtasksStream
                    .map(StructuredTaskScope.Subtask::get)
                    .toList();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
