package eu.chrost.workshop;

/*

To dump thread stack in JSON format run:

jcmd <pid> Thread.dump_to_file -format=json -overwrite thread_dump.json

*/


import java.time.Duration;
import java.util.concurrent.StructuredTaskScope;
import java.util.stream.Collectors;

class C99Observability {
    public static void main(String[] args) throws Exception {
        try (var travelScope = StructuredTaskScope.open(
                StructuredTaskScope.Joiner.<String>allSuccessfulOrThrow(),
                config -> config
                        .withName("travel")
                        .withThreadFactory(java.lang.Thread.ofVirtual().name("travel" + "-", 0).factory()))) {
            travelScope.fork(() -> bookHotel());
            travelScope.fork(() -> bookCar());
            travelScope.fork(() -> bookFlight());
            var travel = travelScope.join()
                    .map(StructuredTaskScope.Subtask::get)
                    .collect(Collectors.joining(","));
            System.out.println("Travel: " + travel);
        }
    }

    private static String bookHotel() {
        try {
            java.lang.Thread.sleep(Duration.ofMinutes(2));
            return "Hotel booked";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static String bookCar() {
        try {
            java.lang.Thread.sleep(Duration.ofMinutes(2));
            return "Car booked";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static String bookFlight() {
        try (var flightScope = StructuredTaskScope.open(
                StructuredTaskScope.Joiner.<String>allSuccessfulOrThrow(),
                config -> config
                        .withName("flight")
                        .withThreadFactory(java.lang.Thread.ofVirtual().name("flight" + "-", 0).factory())
        )) {
            flightScope.fork(() -> bookFlightThere());
            flightScope.fork(() -> bookFlightBack());
            var flight = flightScope.join()
                    .map(StructuredTaskScope.Subtask::get)
                    .collect(Collectors.joining(","));
            return flight;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static String bookFlightThere() {
        try {
            java.lang.Thread.sleep(Duration.ofMinutes(2));
            return "Flight there booked";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static String bookFlightBack() {
        try {
            java.lang.Thread.sleep(Duration.ofMinutes(2));
            return "Flight back booked";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
