package eu.chrost.workshop;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.StructuredTaskScope;

class T01Alice {
    public static void main(String[] args) {
        var alice = new T01Alice();
        System.out.println(alice.prepareAllMeals());
    }

    public List<String> prepareAllMeals() {
        try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.<String>allSuccessfulOrThrow())) {
            scope.fork(() -> salad.run());
            scope.fork(() -> pizza.run());
            scope.fork(() -> fries.run());
            //scope.fork(() -> burnedFries.run());
            return scope.join()
                    .map(StructuredTaskScope.Subtask::get)
                    .toList();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Action<String> salad = new Action<>("preparing salad", "salad is ready", Duration.ofSeconds(1));
    private Action<String> pizza = new Action<>("preparing pizza", "pizza is ready", Duration.ofSeconds(3));
    private Action<String> fries = new Action<>("preparing fries", "fries are ready", Duration.ofSeconds(2));
    private Action<String> burnedFries = new Action<>("preparing fries", "fries are ready", Duration.ofSeconds(2), true);


}
