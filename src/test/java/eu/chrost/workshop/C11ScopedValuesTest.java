package eu.chrost.workshop;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.concurrent.StructuredTaskScope;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class C11ScopedValuesTest {
    private static final ScopedValue<String> NAME = ScopedValue.newInstance();

    @Test
    void scopedValueShouldBeAccessibleInsideRunBlock() {
        ScopedValue.where(NAME, "Marcin").run(() -> {
            assertThat(NAME.get()).isEqualTo("Marcin");
        });
    }

    @Test
    void scopedValueShouldNotBeAccessibleOutsideRunBlock() {
        ScopedValue.where(NAME, "Marcin").run(() -> {
            //do nothing
        });
        assertThatThrownBy(() -> NAME.get()).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void scopedValueRebindingIsAllowed() {
        ScopedValue.where(NAME, "Marcin").run(() -> {
            ScopedValue.where(NAME, "Tomasz").run(() -> {
                assertThat(NAME.get()).isEqualTo("Tomasz");
            });
        });
    }

    @Test
    void scopedValueRebindingDoesNotOutliveItsBlock() {
        ScopedValue.where(NAME, "Marcin").run(() -> {
            ScopedValue.where(NAME, "Tomasz").run(() -> {
                //do nothing
            });
            assertThat(NAME.get()).isEqualTo("Marcin");
        });
    }

    @Test
    void scopedValueIsInheritedInChildThread() {
        ScopedValue.where(NAME, "Marcin").run(() -> {
            try (var scope = StructuredTaskScope.open()) {
                var subtask = scope.fork(() -> NAME.get());
                scope.join();
                assertThat(subtask.get()).isEqualTo("Marcin");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
