package eu.chrost.workshop;

import java.time.Duration;
import java.util.concurrent.StructuredTaskScope;

import static eu.chrost.workshop.Utils.*;

class E01InterruptingVirtualThread {
    public static void main(String[] args) {
        var scope = StructuredTaskScope.open();
        try {
            scope.fork(() -> {
                socketConnectCatching("example.com", 8080);
            });
            scope.fork(() -> {
                sleepCatching(Duration.ofSeconds(2));
                throw new RuntimeException("Timeout");
            });
            logInfo("Started all tasks");
            scope.join();
            logInfo("Joined");
        } catch (Exception e) {
            logError("Error in scope", e);
        } finally {
            logInfo("Closing scope");
            scope.close();
            logInfo("Scope closed");
        }
    }
}
