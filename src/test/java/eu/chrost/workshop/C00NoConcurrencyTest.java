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
        Resource<String> hotel = new Resource.Builder<String>().name("Hotel").result("Hotel booked").timeout(Duration.ofSeconds(2)).build();
        Resource<String> flight = new Resource.Builder<String>().name("Flight").result("Flight booked").timeout(Duration.ofSeconds(2)).build();

        //when
        getAsyncResult(() -> C00NoConcurrency.book(hotel, flight));

        //then
        await().atLeast(Duration.ofMillis(3500)).atMost(Duration.ofMillis(4500)).untilAsserted(() -> {
            assertResult("Hotel booked,Flight booked");
        });
    }
}
