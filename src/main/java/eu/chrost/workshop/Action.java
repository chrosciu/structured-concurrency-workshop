package eu.chrost.workshop;

import java.time.Duration;
import java.util.Objects;

record Action<T>(String name, T result, Duration timeout, boolean failing)  {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Action.class);

    public Action {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(result, "result must not be null");
        if (null == timeout || timeout.isNegative()) {
            throw new IllegalArgumentException("timeout must not be null or negative");
        }
    }

    public Action(String name, T result, Duration timeout) {
        this(name, result, timeout, false);
    }

    public T run() {
        log.info("Action {} started", name);
        try {
            Thread.sleep(timeout);
            if (failing) {
                log.error("Action {} failed", name);
                throw new RuntimeException(String.format("Action %s failed", name));
            }
        } catch (InterruptedException e) {
            log.error("Action {} interrupted", name);
            throw new RuntimeException(String.format("Action %s interrupted", name));
        }
        log.info("Action {} finished", name);
        return result;
    }
}

