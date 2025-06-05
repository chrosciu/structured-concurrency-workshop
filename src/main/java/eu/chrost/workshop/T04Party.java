package eu.chrost.workshop;

import java.util.List;
import java.util.concurrent.StructuredTaskScope;

import static eu.chrost.workshop.Utils.tryRun;

class T04Party {
    private final T01Alice alice;
    private final T02Bob bob;
    private final T03Carol carol;

    public static void main(String[] args) {
        var alice = new T01Alice();
        var bob = new T02Bob();
        var carol = new T03Carol();
        var party = new T04Party(alice, bob, carol);
        tryRun(() -> System.out.println(party.start()));
    }

    public T04Party(T01Alice alice, T02Bob bob, T03Carol carol) {
        this.alice = alice;
        this.bob = bob;
        this.carol = carol;
    }

    public List<String> start() {
        try (var scope= StructuredTaskScope.open()) {
            var aliceTask = scope.fork(() -> alice.prepareAllMeals());
            var bobTask = scope.fork(() -> bob.buyDrink());
            var carolTask = scope.fork(() -> carol.chooseOutfit());
            scope.join();
            return List.of(
                    aliceTask.get().toString(),
                    bobTask.get(),
                    carolTask.get(),
                    "party started!"
            );
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
