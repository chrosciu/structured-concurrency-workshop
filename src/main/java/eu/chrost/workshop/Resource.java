package eu.chrost.workshop;

import java.time.Duration;
import java.util.Objects;

class Resource<T> {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Resource.class);

    public Resource(String name, T result, Duration timeout, boolean failing) {
        this.name = name;
        this.result = result;
        this.timeout = timeout;
        this.failing = failing;
    }

    private final String name;
    private final T result;
    private final Duration timeout;
    private final boolean failing;

    public T book() {
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
        return result;
    }

    static class Builder<T> {
        private String name;
        private T result;
        private Duration timeout = Duration.ZERO;
        private boolean failing = false;

        public Builder<T> name(String name) {
            this.name = name;
            return this;
        }

        public Builder<T> result(T result) {
            this.result = result;
            return this;
        }

        public Builder<T> timeout(Duration timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder<T> failing(boolean failing) {
            this.failing = failing;
            return this;
        }

        public Resource<T> build() {
            Objects.requireNonNull(name, "name must not be null");
            Objects.requireNonNull(result, "result must not be null");
            Objects.requireNonNull(timeout, "timeout must not be null");
            return new Resource<T>(name, result, timeout, failing);
        }
    }
}

