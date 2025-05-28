package eu.chrost.workshop;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.atomic.AtomicReference;

import static java.time.Duration.ofSeconds;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class C08ViolationsTest {

    @Test
    void closingScopeWithoutJoiningFirstShouldThrowException() {
        var hotel = new Action<>("booking hotel", "hotel booked", ofSeconds(2));
        var scope =  StructuredTaskScope.open(
                java.util.concurrent.StructuredTaskScope.Joiner.<String>allSuccessfulOrThrow());
        scope.fork(() -> hotel.run());
        assertThatThrownBy(() -> scope.close())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Owner did not join after forking");
    }

    @Test
    void forkingTaskAfterJoiningShouldThrowException() throws Exception {
        var hotel = new Action<>("booking hotel", "hotel booked", ofSeconds(2));
        var scope =  StructuredTaskScope.open(
                java.util.concurrent.StructuredTaskScope.Joiner.<String>allSuccessfulOrThrow());
        scope.join();
        assertThatThrownBy(() -> scope.fork(() -> hotel.run()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Already joined or scope is closed");
        scope.close();
    }

    @Test
    void callingMethodsOnScopeNotByAnOwnerShouldThrowException() throws Exception {
        var exceptionRef = new AtomicReference<Exception>();
        var countDownLatch = new CountDownLatch(1);
        var hotel = new Action<>("booking hotel", "hotel booked", ofSeconds(2));
        var scope =  StructuredTaskScope.open(
                java.util.concurrent.StructuredTaskScope.Joiner.<String>allSuccessfulOrThrow());
        Thread.ofVirtual().start(() -> {
            try {
                scope.fork(() -> hotel.run());
            } catch (Exception e) {
                exceptionRef.set(e);
            } finally {
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
        assertThat(exceptionRef.get())
                .isInstanceOf(WrongThreadException.class)
                .hasMessage("Current thread not owner");
        scope.join();
        scope.close();
    }


}
