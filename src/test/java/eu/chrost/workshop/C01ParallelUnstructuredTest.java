package eu.chrost.workshop;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;

import static java.time.Duration.ofSeconds;
import static org.awaitility.Awaitility.await;

class C01ParallelUnstructuredTest extends BaseTest<String> {
    @Test
    @Timeout(5)
    void resourcesShouldBeBookedInParallel() {
        //given
        Action<String> hotel = new Action<>("booking hotel", "hotel booked", ofSeconds(2));
        Action<String> flight = new Action<>("booking flight", "flight booked", ofSeconds(3));

        //when
        getAsyncResult(() -> C01ParallelUnstructured.runInParallel(hotel, flight));

        //then
        await().atLeast(Duration.ofMillis(2500)).atMost(Duration.ofMillis(3500)).untilAsserted(() -> {
            assertResult("hotel booked,flight booked");
        });
    }

    @Test
    @Timeout(5)
    @Disabled("To be done in the future")
    void errorInOneResourceShouldStopBookingImmediately() {
        //given
        Action<String> hotel = new Action<>("booking hotel", "hotel booked", ofSeconds(3));
        Action<String> flight = new Action<>("booking flight", "flight booked", ofSeconds(1), true);

        //when
        getAsyncResult(() -> C01ParallelUnstructured.runInParallel(hotel, flight));

        //then
        await().atLeast(Duration.ofMillis(500)).atMost(Duration.ofMillis(1500)).untilAsserted(() -> {
            assertError("flight booking failed");
        });
    }
}
