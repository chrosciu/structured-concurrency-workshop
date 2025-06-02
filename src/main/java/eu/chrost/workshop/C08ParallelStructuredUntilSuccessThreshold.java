package eu.chrost.workshop;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

class UntilSuccessThreshold<T> implements Predicate<StructuredTaskScope.Subtask<? extends T>> {
    private final int successThreshold;
    private final AtomicInteger successCount = new AtomicInteger();

    public UntilSuccessThreshold(int successThreshold) {
        this.successThreshold = successThreshold;
    }

    @Override
    public boolean test(StructuredTaskScope.Subtask<? extends T> subtask) {
        return subtask.state() == StructuredTaskScope.Subtask.State.SUCCESS
                && successCount.incrementAndGet() >= successThreshold;
    }
}

class C08ParallelStructuredUntilSuccessThreshold {
    @SafeVarargs
    static <T> List<T> run(int successThreshold, Action<T>... actions) {
        try (var scope= StructuredTaskScope.open(
                StructuredTaskScope.Joiner.<T>allUntil(new UntilSuccessThreshold<>(successThreshold)))) {
            Arrays.stream(actions)
                    .forEach(action -> scope.fork(action::run));
            var subtasksStream = scope.join();
            return subtasksStream
                    .filter(stringSubtask -> stringSubtask.state()
                            == StructuredTaskScope.Subtask.State.SUCCESS)
                    .map(StructuredTaskScope.Subtask::get)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
