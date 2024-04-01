package org.nazar.service.properties;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Singleton class, holds required application data
 */
public enum ApplicationProperties {
    INSTANCE;

    private String password;

    /**
     * Checks for password presence and put it in data
     *
     * @param passwordForGmail password from program argument
     */
    public void setPassword(String passwordForGmail) {
        if (passwordForGmail != null) {
            password = passwordForGmail;
        } else if (System.getProperty("ParserPassword") != null) {
            password = System.getProperty("ParserPassword");
        } else {
            password = System.getenv("Parser_Password");
        }
    }

    /**
     * Getter for password
     *
     * @return field password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Provides basic smtp configuration
     *
     */
    public Properties getSmtpProperties() {
        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        return properties;
    }

}
