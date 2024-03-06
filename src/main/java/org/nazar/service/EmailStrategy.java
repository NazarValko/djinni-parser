package org.nazar.service;

import org.nazar.service.properties.ApplicationProperties;
import org.nazar.service.smtp.SmtpAuthenticator;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Strategy for sending email notification
 */
public class EmailStrategy implements NotificationStrategy {
    private final String from;
    private final String to;
    private final String messageBody;

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
            MimeMessage message = new MimeMessage(Session.getDefaultInstance((Properties) ApplicationProperties
                            .INSTANCE.getData().get("smtpProps"),
                        authenticate(to, (String) ApplicationProperties.INSTANCE.getData().get("receiverPassword"))));

            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Latest vacancy");
            message.setText(messageBody);
            if (!messageBody.substring(1, messageBody.length()-1).isEmpty()) {
                Transport.send(message);
            }
        } catch (MessagingException mex) {
            System.out.println("Authentication failed. Provide correct credentials");
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


}
