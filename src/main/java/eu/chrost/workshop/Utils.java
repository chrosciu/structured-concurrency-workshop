package eu.chrost.workshop;

import org.slf4j.Logger;

import java.io.IOException;
import java.net.Socket;
import java.time.Duration;

class Utils {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Utils.class);

    public static void sleepCatching(Duration duration) {
        try {
            log.info("Sleeping");
            Thread.sleep(duration);
            log.info("Waking up");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void socketConnectCatching(String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            log.info("Connected");
        } catch (IOException e) {
            log.error("Could not connect", e);
            throw new RuntimeException(e);
        }
    }

    public static void logInfo(String message) {
        log.info(message);
    }

    public static void logError(String message, Throwable throwable) {
        log.error(message, throwable);
    }

    public static void tryRun(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            log.error("Error: ", e);
        }
    }
}
