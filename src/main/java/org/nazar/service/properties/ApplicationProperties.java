package org.nazar.service.properties;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * Singleton class, holds required application data
 */
public enum ApplicationProperties {
    INSTANCE;
    private final Map<String, Object> data = new HashMap<>();

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
     * sets all required data
     *
     * @param passwordForGmail password for receiver gmail
     */
    public void setData(String passwordForGmail) {
        setSmtpProperties();
        setEmails();
        setPassword(passwordForGmail);
    }
    
    /**
     * Get data in map
     *
     * @return program data
     */
    public Map<String, Object> getData() {
        return data;
    }

}
