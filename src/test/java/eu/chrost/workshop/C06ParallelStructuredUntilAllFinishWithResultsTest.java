package eu.chrost.workshop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;
import java.util.List;

import static java.time.Duration.ofSeconds;
import static org.awaitility.Awaitility.await;

class C06ParallelStructuredUntilAllFinishWithResultsTest extends BaseTest<List<String>> {
    @Test
    @Timeout(5)
    void actionsShouldBeRunInParallel() {
        //given
        Action<String> hotel = new Action<>("booking hotel", "hotel booked", ofSeconds(2));
        Action<String> flight = new Action<>("booking flight", "flight booked", ofSeconds(3));

        //when
        getAsyncResult(() -> C06ParallelStructuredUntilAllFinishWithSuccessfulResults.run(hotel, flight));

        //then
        await().atLeast(Duration.ofMillis(2500)).atMost(Duration.ofMillis(3500)).untilAsserted(() -> {
            assertResult(List.of("hotel booked","flight booked"));
        });
    }

    @Test
    @Timeout(5)
    void allSuccessfulResultsShouldBeReturnedAndRestIgnored() {
        //given
        Action<String> hotel = new Action<>("booking hotel", "hotel booked", ofSeconds(1), true);
        Action<String> flight = new Action<>("booking flight", "flight booked", ofSeconds(2));

        //when
        getAsyncResult(() -> C06ParallelStructuredUntilAllFinishWithSuccessfulResults.run(hotel, flight));

        //then
        await().atLeast(Duration.ofMillis(1500)).atMost(Duration.ofMillis(2500)).untilAsserted(() -> {
            assertResult(List.of("flight booked"));
        });
    }
}
