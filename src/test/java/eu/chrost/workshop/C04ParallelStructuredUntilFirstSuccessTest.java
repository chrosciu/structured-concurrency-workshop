package eu.chrost.workshop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;

import static java.time.Duration.ofSeconds;
import static org.awaitility.Awaitility.await;

class C04ParallelStructuredUntilFirstSuccessTest extends BaseTest<String> {
    @Test
    @Timeout(5)
    void resourcesShouldBeBookedInParallelAndTheFastestReturned() {
        //given
        Action<String> hotel = new Action<>("booking hotel", "hotel booked", ofSeconds(3));
        Action<String> flight = new Action<>("booking flight", "flight booked", ofSeconds(2));

        //when
        getAsyncResult(() -> C04ParallelStructuredUntilFirstSuccess.runInParallelStructuredUntilFirstSuccess(hotel, flight));

        //then
        await().atLeast(Duration.ofMillis(1500)).atMost(Duration.ofMillis(2500)).untilAsserted(() -> {
            assertResult("flight booked");
        });
    }

    @Test
    @Timeout(5)
    void errorInOneResourceShouldNotStopBookingImmediately() {
        //given
        Action<String> hotel = new Action<>("booking hotel", "hotel booked", ofSeconds(1), true);
        Action<String> flight = new Action<>("booking flight", "flight booked", ofSeconds(2));

        //when
        getAsyncResult(() -> C04ParallelStructuredUntilFirstSuccess.runInParallelStructuredUntilFirstSuccess(hotel, flight));

        //then
        await().atLeast(Duration.ofMillis(1500)).atMost(Duration.ofMillis(2500)).untilAsserted(() -> {
            assertResult("flight booked");
        });
    }

    @Test
    @Timeout(5)
    void errorInAllResourcesShouldReturnError() {
        //given
        Action<String> hotel = new Action<>("booking hotel", "hotel booked", ofSeconds(1), true);
        Action<String> flight = new Action<>("booking flight", "flight booked", ofSeconds(2), true);

        //when
        getAsyncResult(() -> C04ParallelStructuredUntilFirstSuccess.runInParallelStructuredUntilFirstSuccess(hotel, flight));

        //then
        await().atLeast(Duration.ofMillis(1500)).atMost(Duration.ofMillis(2500)).untilAsserted(() -> {
            assertError("Action booking hotel failed");
        });
    }
}
