package org.nazar.service;

import org.nazar.service.smtp.SmtpAuthenticator;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Strategy for sending email notification
 */
public class EmailStrategy implements NotificationStrategy {
    private String from;
    private String to;
    private String messageBody;

    /**
     *
     * @param from the sender email address
     * @param to the receiver email address
     * @param messageBody the body of the email message
     */
    public EmailStrategy(String from, String to, String messageBody) {
        this.from = from;
        this.to = to;
        this.messageBody = messageBody;
    }

    /**
     * Sends email
     */
    @Override
    public void send() {
        try {
            MimeMessage message = new MimeMessage(Session.getDefaultInstance(setProperties(),
                    authenticate(to, System.getenv("djinni_parser_password"))));

            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Latest vacancy");
            message.setText(messageBody);
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    /**
     * Process smtp authentication
     *
     * @param username the sender's username
     * @param password the sender's password
     * @return object of Authenticator class
     */
    private Authenticator authenticate(String username, String password) {
        return new SmtpAuthenticator(username, password);
    }

    /**
     * Provides basic smtp configuration
     *
     * @return configured object of Properties class for smtp configuration
     */
    private Properties setProperties() {
        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        return properties;
    }
}
