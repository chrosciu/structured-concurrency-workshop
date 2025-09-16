package eu.chrost.workshop;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.concurrent.StructuredTaskScope;
import java.util.function.Supplier;

@Slf4j
class E03Violations {
    static void main() {
        try (var scope= StructuredTaskScope.open()) {
            var resultOne  = scope.fork(() -> {
                Utils.sleepCatching(Duration.ofSeconds(3));
                return 1;
            });
            var resultTwo = scope.fork(() -> {
                try {
                    Utils.sleepCatching(Duration.ofSeconds(2));
                    scope.join();
                    return 2;
                } catch (Exception e) {
                    log.error("Error", e);
                    throw e;
                }
            });
            scope.join();
            log.info("Result: {}", resultOne.get() + resultTwo.get());
        } catch (Exception e) {
            log.error("Error", e);
            throw new RuntimeException(e);
        }
    }
}
