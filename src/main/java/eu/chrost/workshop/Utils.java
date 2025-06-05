package eu.chrost.workshop;

class Utils {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Utils.class);

    public static void tryRun(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            log.error("Error: ", e);
        }
    }
}
