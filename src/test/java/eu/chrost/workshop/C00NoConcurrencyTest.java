package eu.chrost.workshop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;

import static org.awaitility.Awaitility.await;

class C00NoConcurrencyTest extends BaseTest {
    @Test
    @Timeout(5)
    void resourcesShouldBeBookedSequentially() {
        //given
        Resource hotel = Resource.builder().name("Hotel").timeout(Duration.ofSeconds(2)).build();
        Resource flight = Resource.builder().name("Flight").timeout(Duration.ofSeconds(2)).build();

        //when
        getAsyncResult(() -> C00NoConcurrency.book(hotel, flight));

        //then
        await().atLeast(Duration.ofMillis(3500)).atMost(Duration.ofMillis(4500)).untilAsserted(() -> {
            assertResult("Hotel booked,Flight booked");
        });
    }
}
