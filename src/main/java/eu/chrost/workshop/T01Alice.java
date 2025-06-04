package eu.chrost.workshop;

import java.time.Duration;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.StructuredTaskScope;

class T01Alice {
    public static void main(String[] args) {
        var alice = new T01Alice();
        try {
            System.out.println(alice.prepareAllMeals());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println(alice.prepareAsManyMealsAsPossible());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> prepareAllMeals() {
        try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.<String>allSuccessfulOrThrow())) {
            scope.fork(() -> salad.run());
            scope.fork(() -> pizza.run());
            //scope.fork(() -> fries.run());
            scope.fork(() -> burnedFries.run());
            return scope.join()
                    .map(StructuredTaskScope.Subtask::get)
                    .toList();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static class AsManyAsPossibleJoiner implements StructuredTaskScope.Joiner<String, List<String>> {
        Queue<String> successfulTasks = new ConcurrentLinkedQueue<>();

        @Override
        public boolean onComplete(StructuredTaskScope.Subtask<? extends String> subtask) {
            if (subtask.state() == StructuredTaskScope.Subtask.State.SUCCESS) {
                successfulTasks.offer(subtask.get());
            }
            return false;
        }

        @Override
        public List<String> result() throws Throwable {
            return successfulTasks.stream().toList();
        }
    }

    public List<String> prepareAsManyMealsAsPossible() {
        try (var scope = StructuredTaskScope.open(new AsManyAsPossibleJoiner())) {
            scope.fork(() -> salad.run());
            scope.fork(() -> pizza.run());
            //scope.fork(() -> fries.run());
            scope.fork(() -> burnedFries.run());
            return scope.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Action<String> salad = new Action<>("preparing salad", "salad is ready", Duration.ofSeconds(1));
    private Action<String> pizza = new Action<>("preparing pizza", "pizza is ready", Duration.ofSeconds(3));
    private Action<String> fries = new Action<>("preparing fries", "fries are ready", Duration.ofSeconds(2));
    private Action<String> burnedFries = new Action<>("preparing fries", "fries are ready", Duration.ofSeconds(2), true);


}
