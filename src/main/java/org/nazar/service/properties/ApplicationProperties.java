package org.nazar.service.properties;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * Singleton class, holds required application data
 */
public enum ApplicationProperties {
    INSTANCE;
    private final Map<String, Object> applicationProperties = new HashMap<>();

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

        applicationProperties.put("smtpProps", properties);
    }

    /**
     * Sets emails for sender and receiver
     *
     */
    private void setEmails() {
        applicationProperties.put("senderEmail", "nazar.valko09@gmail.com");
        applicationProperties.put("receiverEmail", "nazarvlk793@gmail.com");
    }

    /**
     * Checks for password presence and put it in data
     *
     * @param passwordForGmail password from program argument
     */
    private void setPassword(String passwordForGmail) {
        String propName = "receiverPassword";
        if (passwordForGmail != null) {
            applicationProperties.put(propName, passwordForGmail);
        } else if (System.getProperty("ParserPassword") != null) {
            applicationProperties.put(propName, System.getProperty("ParserPassword"));
        } else {
            applicationProperties.put(propName, System.getenv("Parser_Password"));
        }
    }

    /**
     * sets all required data
     *
     * @param passwordForGmail password for receiver gmail
     */
    public void setApplicationProperties(String passwordForGmail) {
        setSmtpProperties();
        setEmails();
        setPassword(passwordForGmail);
    }
    
    /**
     * Get data in map
     *
     * @return program data
     */
    public Map<String, Object> getApplicationProperties() {
        return applicationProperties;
    }

}
