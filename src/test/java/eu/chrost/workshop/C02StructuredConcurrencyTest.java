package eu.chrost.workshop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

import static org.awaitility.Awaitility.await;

class C02StructuredConcurrencyTest extends BaseTest {
    @Test
    @Timeout(5)
    void resourcesShouldBeBookedInParallel() throws Exception {
        //given
        Resource hotel = Resource.builder().name("Hotel").timeout(Duration.ofSeconds(2)).build();
        Resource flight = Resource.builder().name("Flight").timeout(Duration.ofSeconds(3)).build();

        //when
        getAsyncResult(() -> C02StructuredConcurrency.book(hotel, flight));

        //then
        await().atLeast(Duration.ofMillis(2500)).atMost(Duration.ofMillis(3500)).untilAsserted(() -> {
            assertResult("Hotel booked,Flight booked");
        });
    }

    @Test
    @Timeout(5)
    void errorInOneResourceShouldStopBookingImmediately() {
        //given
        Resource hotel = Resource.builder().name("Hotel").timeout(Duration.ofSeconds(3)).build();
        Resource flight = Resource.builder().name("Flight").timeout(Duration.ofSeconds(1)).failing(true).build();
        AtomicReference<Throwable> throwable = new AtomicReference<>();

        //when
        getAsyncResult(() -> C02StructuredConcurrency.book(hotel, flight));

        //then
        await().atLeast(Duration.ofMillis(500)).atMost(Duration.ofMillis(1500)).untilAsserted(() -> {
            assertError("Flight booking failed");
        });
    }
}
