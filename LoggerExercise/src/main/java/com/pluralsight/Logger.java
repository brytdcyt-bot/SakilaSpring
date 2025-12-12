package main.java.com.pluralsight;

public class Logger {

    private final Logger logger;

    // Private constructor to bind logger to a specific class
    private Logger(Class<?> clazz) {
        this.logger = (Logger) LogManager.getLogger(clazz);
    }

    // Factory method to create Logger instance for any class
    public static Logger getLogger(Class<?> clazz) {
        return new Logger(clazz);
    }

    // Debug level logging
    public void debug(String message) {
        if (logger.isDebugEnabled()) {
            logger.debug(message);
        }
    }

    private boolean isDebugEnabled() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isDebugEnabled'");
    }

    // Info level logging
    public void info(String message) {
        if (logger.isInfoEnabled()) {
            logger.info(message);
        }
    }

    // Warn level logging
    public void warn(String message) {
        logger.warn(message);
    }

    // Error level logging
    public void error(String message) {
        logger.error(message);
    }

    // Fatal level logging
    public void fatal(String message) {
        logger.fatal(message);
    }
}