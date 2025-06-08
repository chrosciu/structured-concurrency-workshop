package eu.chrost.workshop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;
import java.util.List;

import static java.time.Duration.ofSeconds;
import static org.awaitility.Awaitility.await;

class C07NestedTest extends BaseTest<List<String>> {
    @Test
    @Timeout(5)
    void actionsShouldBeRunInParallelAndCanBeNested() {
        //given
        ActionNode<String> travel = new ActionNode<>(
                "travel",
                List.of(
                        new Action<>("booking hotel", "hotel booked", ofSeconds(3)),
                        new Action<>("booking car", "car booked", ofSeconds(1))
                ),
                List.of(new ActionNode<>(
                        "flight",
                        List.of(
                                new Action<>("booking flight there", "flight there booked", ofSeconds(2)),
                                new Action<>("booking flight back", "flight back booked", ofSeconds(2)))
                        )
                )
        );

        //when
        getAsyncResult(() -> C07Nested.run(travel).toList());

        //then
        await().atLeast(Duration.ofMillis(2500)).atMost(Duration.ofMillis(3500)).untilAsserted(() -> {
            assertResult(List.of("hotel booked", "car booked", "flight there booked", "flight back booked"));
        });
    }

    @Test
    @Timeout(5)
    void errorInOuterScopeShouldCancelTheInnerOne() {
        //given
        ActionNode<String> travel = new ActionNode<>(
                "travel",
                List.of(
                        new Action<>("booking hotel", "hotel booked", ofSeconds(3)),
                        new Action<>("booking car", "car booked", ofSeconds(1), true)
                ),
                List.of(new ActionNode<>(
                                "flight",
                                List.of(
                                        new Action<>("booking flight there", "flight there booked", ofSeconds(2)),
                                        new Action<>("booking flight back", "flight back booked", ofSeconds(2)))
                        )
                )
        );

        //when
        getAsyncResult(() -> C07Nested.run(travel).toList());

        //then
        await().atLeast(Duration.ofMillis(500)).atMost(Duration.ofMillis(1500)).untilAsserted(() -> {
            assertError("Action booking car failed");
        });
    }
}
