package org.nazar.service.notification.strategy;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.nazar.service.properties.ApplicationProperties;
import org.nazar.service.smtp.SmtpAuthenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Strategy for sending email notification
 */
public class EmailStrategy implements NotificationStrategy {
    private final String from;
    private final String to;
    private final String messageBody;

    private static final Logger logger = LoggerFactory.getLogger(EmailStrategy.class);

    /**
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
            MimeMessage message = new MimeMessage(Session.getDefaultInstance(ApplicationProperties.INSTANCE.getSmtpProperties(),
                    authenticate(to, ApplicationProperties.INSTANCE.getPassword())));

            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Latest vacancy");
            message.setText(messageBody);
            if (!messageBody.substring(1, messageBody.length() - 1).isEmpty()) {
                Transport.send(message);
            }
        } catch (MessagingException mex) {
            logger.error("Authentication failed. Provide correct credentials");
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
