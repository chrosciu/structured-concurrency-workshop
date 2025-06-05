package eu.chrost.workshop;

import java.time.Duration;

import static eu.chrost.workshop.Utils.tryRun;

class T03Carol {
    public static void main(String[] args) {
        var carol = new T03Carol();
        tryRun(() -> System.out.println(carol.chooseOutfit()));
    }

    public String chooseOutfit() {
        return outfit.run();
    }

    private Action<String> outfit = new Action<>("choosing outfit", "outfit chosen", Duration.ofSeconds(5));
}
