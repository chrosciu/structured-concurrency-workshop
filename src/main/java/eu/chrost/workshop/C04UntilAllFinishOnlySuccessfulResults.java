package eu.chrost.workshop;

import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.StructuredTaskScope;
import java.util.stream.Stream;

class UntilAllFinishOnlySuccesfulResultsJoiner<T> implements StructuredTaskScope.Joiner<T, Stream<T>> {
    Queue<T> results = new ConcurrentLinkedQueue<>();

    @Override
    public boolean onComplete(StructuredTaskScope.Subtask<? extends T> subtask) {
        if (subtask.state() == StructuredTaskScope.Subtask.State.SUCCESS) {
            results.offer(subtask.get());
        }
        return false;
    }

    @Override
    public Stream<T> result() throws Throwable {
        return results.stream();
    }
}

class C04UntilAllFinishOnlySuccessfulResults {
    @SafeVarargs
    static <T> List<T> run(Action<T>... actions) {
        try (var scope= StructuredTaskScope.open(new UntilAllFinishOnlySuccesfulResultsJoiner<T>())) {
            Arrays.stream(actions)
                    .forEach(action -> scope.fork(action::run));
            var results= scope.join();
            return results.toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
