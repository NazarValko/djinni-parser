package org.nazar.service.config;

import org.nazar.service.properties.ApplicationProperties;

/**
 * Handles pre start configuration
 */
public class ApplicationConfig {

    /**
     * Does all setup
     * @param args args from program
     */
    public static void configureBeforeStart(String[] args) {
        setHeadlessEnvironment();
        getArgs(args);
    }

    /**
     * Set headless for java.awt to work in spring environment.
     */
    private static void setHeadlessEnvironment() {
        System.setProperty("java.awt.headless", "false");
    }

    /**
     * Checks args and stores it in ApplicationProperties class
     * @param args args from program
     */
    private static void getArgs(String[] args) {
        args = args == null ? new String[]{} : args;
        if (args.length == 0) {
            ApplicationProperties.INSTANCE.setPassword(null);
        } else {
            ApplicationProperties.INSTANCE.setPassword(args[0]);
        }
    }
}
