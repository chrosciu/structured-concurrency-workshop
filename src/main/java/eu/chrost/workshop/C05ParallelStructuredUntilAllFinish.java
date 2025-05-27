package eu.chrost.workshop;

import java.util.concurrent.StructuredTaskScope;

class C05ParallelStructuredUntilAllFinish {
    static <T, U>String run(Action<T> actionOne, Action<U> actionTwo) {
        try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.awaitAll())) {
            StructuredTaskScope.Subtask<T> subtaskOne = scope.fork(() -> actionOne.run());
            StructuredTaskScope.Subtask<U> subtaskTwo = scope.fork(() -> actionTwo.run());
            scope.join();
            return String.join(",", getSubtaskResultAsString(subtaskOne), getSubtaskResultAsString(subtaskTwo));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getSubtaskResultAsString(StructuredTaskScope.Subtask<?> subtask) {
        return switch (subtask.state()) {
            case SUCCESS -> subtask.get().toString();
            case FAILED -> subtask.exception().getMessage();
            default -> throw new IllegalStateException("Unexpected subtask state: " + subtask.state());
        };
    }
}
