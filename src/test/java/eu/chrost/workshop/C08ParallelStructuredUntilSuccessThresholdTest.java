package eu.chrost.workshop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;
import java.util.List;

import static java.time.Duration.ofSeconds;
import static org.awaitility.Awaitility.await;

class C08ParallelStructuredUntilSuccessThresholdTest extends BaseTest<List<String>> {
    @Test
    @Timeout(5)
    void actionsShouldBeRunInParallelUntilSuccessThreshold() {
        //given
        Action<String> hotel = new Action<>("booking hotel", "hotel booked", ofSeconds(2));
        Action<String> flight = new Action<>("booking flight", "flight booked", ofSeconds(3));
        Action<String> car = new Action<>("booking car", "car booked", ofSeconds(1));
        Action<String> table = new Action<>("booking table", "table booked", ofSeconds(4));

        //when
        getAsyncResult(() -> C08ParallelStructuredUntilSuccessThreshold.run(2,hotel, flight, car, table));

        //then
        await().atLeast(Duration.ofMillis(1500)).atMost(Duration.ofMillis(2500)).untilAsserted(() -> {
            assertResult(List.of("hotel booked","car booked"));
        });
    }
}
