package eu.chrost.workshop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;

import static java.time.Duration.ofSeconds;
import static org.awaitility.Awaitility.await;

class C02ParallelStructuredTest extends BaseTest {
    @Test
    @Timeout(5)
    void resourcesShouldBeBookedInParallel() {
        //given
        Action<String> hotel = new Action<>("booking hotel", "hotel booked", ofSeconds(2));
        Action<String> flight = new Action<>("booking flight", "flight booked", ofSeconds(3));

        //when
        getAsyncResult(() -> C02ParallelStructured.runInParallelStructured(hotel, flight));

        //then
        await().atLeast(Duration.ofMillis(2500)).atMost(Duration.ofMillis(3500)).untilAsserted(() -> {
            assertResult("hotel booked,flight booked");
        });
    }

    @Test
    @Timeout(5)
    void errorInOneResourceShouldStopBookingImmediately() {
        //given
        Action<String> hotel = new Action<>("booking hotel", "hotel booked", ofSeconds(3));
        Action<String> flight = new Action<>("booking flight", "flight booked", ofSeconds(1), true);

        //when
        getAsyncResult(() -> C02ParallelStructured.runInParallelStructured(hotel, flight));

        //then
        await().atLeast(Duration.ofMillis(500)).atMost(Duration.ofMillis(1500)).untilAsserted(() -> {
            assertError("Action booking flight failed");
        });
    }
}
