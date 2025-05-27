package eu.chrost.workshop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;

import static java.time.Duration.ofSeconds;
import static org.awaitility.Awaitility.await;

class C00SequentialTest extends BaseTest<String> {
    @Test
    @Timeout(5)
    void actionsShouldBeRunSequentially() {
        //given
        Action<String> hotel = new Action<>("booking hotel", "hotel booked", ofSeconds(2));
        Action<String> flight = new Action<>("booking flight", "flight booked", ofSeconds(2));

        //when
        getAsyncResult(() -> C00Sequential.run(hotel, flight));

        //then
        await().atLeast(Duration.ofMillis(3500)).atMost(Duration.ofMillis(4500)).untilAsserted(() -> {
            assertResult("hotel booked,flight booked");
        });
    }
}
