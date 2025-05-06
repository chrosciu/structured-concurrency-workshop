package eu.chrost.workshop;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

abstract class BaseTest {
    protected AtomicReference<String> result = new AtomicReference<>();
    protected AtomicReference<Throwable> error = new AtomicReference<>();

    protected void getAsyncResult(Supplier<String> action) {
        new Thread(() -> {
            try {
                result.set(action.get());
            } catch (Throwable t) {
                error.set(t);
            }
        }).start();
    }

    protected void assertResult(String expectedResult) {
        assertThat(result.get()).isEqualTo(expectedResult);
    }

    protected void assertError(String expectedErrorMessage) {
        assertThat(error.get()).hasRootCauseMessage(expectedErrorMessage);
    }
}
