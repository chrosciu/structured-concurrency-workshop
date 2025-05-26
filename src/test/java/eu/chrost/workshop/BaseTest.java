package eu.chrost.workshop;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

abstract class BaseTest<T> {
    protected AtomicReference<T> result = new AtomicReference<>();
    protected AtomicReference<Throwable> error = new AtomicReference<>();

    protected void getAsyncResult(Supplier<T> action) {
        Thread.ofVirtual().start(() -> {
            try {
                result.set(action.get());
            } catch (Throwable t) {
                error.set(t);
            }
        });
    }

    protected void assertResult(T expectedResult) {
        assertThat(result.get()).isEqualTo(expectedResult);
    }

    protected void assertError(String expectedErrorMessage) {
        assertThat(error.get()).hasRootCauseMessage(expectedErrorMessage);
    }
}
