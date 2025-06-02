package eu.chrost.workshop;

import java.util.concurrent.StructuredTaskScope;
import java.util.stream.Stream;

class C07ParallelStructuredNested {
    static <T> Stream<T> run(ActionNode<T> node) {
        try (var scope= StructuredTaskScope.open(
                StructuredTaskScope.Joiner.<Stream<T>>allSuccessfulOrThrow(),
                configuration -> configuration
                        .withName(node.name())
                        .withThreadFactory(Thread.ofVirtual().name(node.name() + "-", 0).factory())
        )) {
            node.actions().forEach(action -> scope.fork(() -> Stream.of(action.run())));
            node.childNodes().forEach(an -> scope.fork(() -> run(an)));
            var subtasksResults = scope.join()
                    .flatMap(StructuredTaskScope.Subtask::get);
            return subtasksResults;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
