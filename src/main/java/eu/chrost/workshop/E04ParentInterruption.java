package eu.chrost.workshop;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.concurrent.StructuredTaskScope;

@Slf4j
class E04ParentInterruption {
    static void main() {
        var thread = new Thread(() -> {
            try (var scope= StructuredTaskScope.open()) {
                scope.fork(() -> {
                    Utils.sleepCatching(Duration.ofSeconds(2));
                });
                scope.join();
            } catch (Exception e) {
                log.error("Error", e);
            }
        });
        thread.start();
        Utils.sleepCatching(Duration.ofSeconds(1));
        thread.interrupt();
    }
}
