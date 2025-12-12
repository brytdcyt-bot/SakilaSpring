package main.java.com.pluralsight;

import java.lang.System.Logger;
import java.util.logging.LogManager;

public class LogManager {

    // Private constructor to prevent instantiation
    private LogManager() {}

    /**
     * Returns a logger instance for the given class.
     *
     * @param clazz The class to associate the logger with
     * @return Logger instance
     */
    public static Logger getLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }
}