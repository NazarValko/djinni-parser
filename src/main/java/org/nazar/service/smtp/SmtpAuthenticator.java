package org.nazar.service.smtp;


import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Authenticator for SMTP
 */
public class SmtpAuthenticator extends Authenticator {
    private final String username;
    private final String password;

    /**
     *
     * @param username the username for authentication
     * @param password the password for authentication
     */
    public SmtpAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Provides password authentication for SMTP
     *
     * @return PasswordAuthentication instance with credentials
     */
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        if (username == null || password == null) {
            throw new IllegalArgumentException("Credentials must not be null");
        }
        return new PasswordAuthentication(username, password);
    }
}
