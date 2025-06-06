package eu.chrost.workshop;

import java.time.Duration;
import java.util.concurrent.StructuredTaskScope;

import static eu.chrost.workshop.Action.OWNER;
import static eu.chrost.workshop.Utils.tryRun;

class T02Bob {
    public static void main(String[] args) {
        var bob = new T02Bob();
        tryRun(() -> System.out.println(bob.buyDrink()));
        tryRun(() -> System.out.println(bob.buyDrinkWithTimeAttack(Duration.ofMillis(500))));
        tryRun(() -> System.out.println(bob.buyDrinkWithTimeAttack(Duration.ofMillis(1500))));
    }

    public String buyDrink() {
        return ScopedValue.where(OWNER, "Bob").call(() -> {
            try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.<String>anySuccessfulResultOrThrow())) {
                scope.fork(() -> coke.run());
                scope.fork(() -> fanta.run());
                scope.fork(() -> sprite.run());
                try {
                    return scope.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public String buyDrinkWithTimeAttack(Duration duration) {
        return ScopedValue.where(OWNER, "Bob").call(() -> {
            try (var scope = StructuredTaskScope.open(
                    StructuredTaskScope.Joiner.<String>anySuccessfulResultOrThrow(),
                    configuration -> configuration.withTimeout(duration)
            )) {
                scope.fork(() -> coke.run());
                scope.fork(() -> fanta.run());
                scope.fork(() -> sprite.run());
                try {
                    return scope.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private Action<String> coke = new Action<>("purchasing Coke", "Coke is purchased", Duration.ofSeconds(2));
    private Action<String> fanta = new Action<>("purchasing Fanta", "Fanta is purchased", Duration.ofSeconds(1));
    private Action<String> sprite = new Action<>("purchasing Sprite", "Sprite is purchased", Duration.ofSeconds(3));
}
