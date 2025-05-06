package eu.chrost.workshop;

import lombok.Builder;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Builder
@Slf4j
class Resource {
    @NonNull
    private final String name;
    @Builder.Default
    private final Duration timeout = Duration.ZERO;
    @Builder.Default
    private final boolean failing = false;

    @SneakyThrows
    public String book() {
        log.info("{} booking started", name);
        try {
            Thread.sleep(timeout);
            if (failing) {
                log.error("{} booking failed", name);
                throw new RuntimeException(String.format("%s booking failed", name));
            }
        } catch (InterruptedException e) {
            log.error("{} booking interrupted", name);
            throw new RuntimeException(String.format("%s booking interrupted", name));
        }
        log.info("{} booking finished", name);
        return String.format("%s booked", name);
    }

}