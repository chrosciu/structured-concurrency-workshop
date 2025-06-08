package eu.chrost.workshop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;
import java.util.List;

import static java.time.Duration.ofSeconds;
import static org.awaitility.Awaitility.await;

class C09UntilSuccessThresholdTest extends BaseTest<List<String>> {
    @Test
    @Timeout(5)
    void actionsShouldBeRunInParallelUntilSuccessThreshold() {
        //given
        Action<String> hotel = new Action<>("booking hotel", "hotel booked", ofSeconds(2));
        Action<String> flight = new Action<>("booking flight", "flight booked", ofSeconds(3));
        Action<String> car = new Action<>("booking car", "car booked", ofSeconds(1), true);
        Action<String> table = new Action<>("booking table", "table booked", ofSeconds(4));

        //when
        getAsyncResult(() -> C09UntilSuccessThreshold.run(2,hotel, flight, car, table));

        //then
        await().atLeast(Duration.ofMillis(2500)).atMost(Duration.ofMillis(3500)).untilAsserted(() -> {
            assertResult(List.of("hotel booked","flight booked"));
        });
    }
}
