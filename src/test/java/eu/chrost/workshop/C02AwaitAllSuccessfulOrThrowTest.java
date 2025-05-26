package eu.chrost.workshop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;

import static org.awaitility.Awaitility.await;

class C02AwaitAllSuccessfulOrThrowTest extends BaseTest {
    @Test
    @Timeout(5)
    void resourcesShouldBeBookedInParallel() {
        //given
        Resource hotel = new Resource.Builder<String>().name("Hotel").result("Hotel booked").timeout(Duration.ofSeconds(2)).build();
        Resource flight = new Resource.Builder<String>().name("Flight").result("Flight booked").timeout(Duration.ofSeconds(3)).build();

        //when
        getAsyncResult(() -> C02AwaitAllSuccessfulOrThrow.book(hotel, flight));

        //then
        await().atLeast(Duration.ofMillis(2500)).atMost(Duration.ofMillis(3500)).untilAsserted(() -> {
            assertResult("Hotel booked,Flight booked");
        });
    }

    @Test
    @Timeout(5)
    void errorInOneResourceShouldStopBookingImmediately() {
        //given
        Resource hotel = new Resource.Builder<String>().name("Hotel").result("Hotel booked").timeout(Duration.ofSeconds(3)).build();
        Resource flight = new Resource.Builder<String>().name("Flight").result("Flight booked").timeout(Duration.ofSeconds(1)).failing(true).build();

        //when
        getAsyncResult(() -> C02AwaitAllSuccessfulOrThrow.book(hotel, flight));

        //then
        await().atLeast(Duration.ofMillis(500)).atMost(Duration.ofMillis(1500)).untilAsserted(() -> {
            assertError("Flight booking failed");
        });
    }
}
