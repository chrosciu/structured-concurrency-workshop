package eu.chrost.workshop;

import java.time.Duration;
import java.util.Objects;

class Resource {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Resource.class);

    public Resource(String name, Duration timeout, boolean failing) {
        this.name = name;
        this.timeout = timeout;
        this.failing = failing;
    }

    private final String name;
    private final Duration timeout;
    private final boolean failing;

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

    public static class Builder {
        private String name;
        private Duration timeout = Duration.ZERO;
        private boolean failing = false;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder timeout(Duration timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder failing(boolean failing) {
            this.failing = failing;
            return this;
        }

        public Resource build() {
            Objects.requireNonNull(name, "name must not be null");
            Objects.requireNonNull(timeout, "timeout must not be null");
            return new Resource(name, timeout, failing);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}

