package eu.chrost.workshop;

import java.time.Duration;
import java.util.concurrent.StructuredTaskScope;

class T02Bob {
    public static void main(String[] args) {
        var bob = new T02Bob();
        try {
            System.out.println(bob.buyDrink());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println(bob.buyDrinkWithTimeAttack(Duration.ofMillis(500)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println(bob.buyDrinkWithTimeAttack(Duration.ofMillis(1500)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String buyDrink() {
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
    }

    public String buyDrinkWithTimeAttack(Duration duration) {
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
    }

    private Action<String> coke = new Action<>("purchasing Coke", "Coke is purchased", Duration.ofSeconds(2));
    private Action<String> fanta = new Action<>("purchasing Fanta", "Fanta is purchased", Duration.ofSeconds(1));
    private Action<String> sprite = new Action<>("purchasing Sprite", "Sprite is purchased", Duration.ofSeconds(3));
}
