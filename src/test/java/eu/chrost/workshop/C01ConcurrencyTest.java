package eu.chrost.workshop;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

import static org.awaitility.Awaitility.await;

class C01ConcurrencyTest extends BaseTest {
    @Test
    @Timeout(5)
    void resourcesShouldBeBookedInParallel() {
        //given
        Resource<String> hotel = new Resource.Builder<String>().name("Hotel").result("Hotel booked").timeout(Duration.ofSeconds(2)).build();
        Resource<String> flight = new Resource.Builder<String>().name("Flight").result("Flight booked").timeout(Duration.ofSeconds(3)).build();

        //when
        getAsyncResult(() -> C01Concurrency.book(hotel, flight));

        //then
        await().atLeast(Duration.ofMillis(2500)).atMost(Duration.ofMillis(3500)).untilAsserted(() -> {
            assertResult("Hotel booked,Flight booked");
        });
    }

    @Test
    @Timeout(5)
    @Disabled("To be done in the future")
    void errorInOneResourceShouldStopBookingImmediately() {
        //given
        Resource<String> hotel = new Resource.Builder<String>().name("Hotel").result("Hotel booked").timeout(Duration.ofSeconds(3)).build();
        Resource<String> flight = new Resource.Builder<String>().name("Flight").result("Flight booked").timeout(Duration.ofSeconds(1)).failing(true).build();
        AtomicReference<Throwable> throwable = new AtomicReference<>();

        //when
        getAsyncResult(() -> C01Concurrency.book(hotel, flight));

        //then
        await().atLeast(Duration.ofMillis(500)).atMost(Duration.ofMillis(1500)).untilAsserted(() -> {
            assertError("Flight booking failed");
        });
    }
}
