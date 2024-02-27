package org.nazar.service;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public final class ApplicationProperties implements Serializable {
    private static ApplicationProperties applicationProperties;
    private final Map<String, Object> data = new HashMap<>();

    /**
     * Private constructor. If will be called throw RuntimeException
     *
     */
    private ApplicationProperties() {
        if (applicationProperties != null) {
            throw new RuntimeException("Access denied!");
        }
    }

    /**
     * Provides basic smtp configuration
     *
     */
    private void setSmtpProperties() {
        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        data.put("smtpProps", properties);
    }

    /**
     * Sets emails for sender and receiver
     *
     */
    private void setEmails() {
        data.put("senderEmail", "nazar.valko09@gmail.com");
        data.put("receiverEmail", "nazarvlk793@gmail.com");
    }

    /**
     * Checks for password presence and put it in data
     *
     * @param passwordForGmail password from program argument
     */
    private void setPassword(String passwordForGmail) {
        String propName = "receiverPassword";
        if (passwordForGmail != null) {
            data.put(propName, passwordForGmail);
        } else if (System.getProperty("ParserPassword") != null) {
            data.put(propName, System.getProperty("ParserPassword"));
        } else {
            data.put(propName, System.getenv("Parser_Password"));
        }
    }

    /**
     * Get data in map
     *
     * @return program data
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * Returns instance of singleton ApplicationProperties.
     * Calls all required methods to set data
     *
     * @param passwordForGmail password from program argument
     * @return instance of ApplicationProperties
     */
    public static ApplicationProperties getInstance(String passwordForGmail) {
        if (applicationProperties != null) {
            applicationProperties = new ApplicationProperties();
            applicationProperties.setSmtpProperties();
            applicationProperties.setEmails();
            applicationProperties.setPassword(passwordForGmail);
        }
        return applicationProperties;
    }

    /**
     * Prevents a new instance of ApplicationProperties from being created via serialization mechanisms.
     *
     * @return the existing instance of ApplicationProperties
     */
    @Serial
    public Object readResolve() {
        return applicationProperties;
    }

    /**
     * Prevents cloning of the singleton instance.
     *
     * @return nothing since it throws an exception
     * @throws CloneNotSupportedException if clone attempt is made
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
