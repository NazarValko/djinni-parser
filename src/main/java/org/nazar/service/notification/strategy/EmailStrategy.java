package org.nazar.service.notification.strategy;

import org.nazar.service.properties.ApplicationProperties;
import org.nazar.service.smtp.SmtpAuthenticator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Strategy for sending email notification
 */
public class EmailStrategy implements NotificationStrategy {

//    @Value("${notification.gmail.sender.email}")
    private String from = "nazar.valko09@gmail.com";

//    @Value("${notification.gmail.receiver.email}")
    private String to = "nazarvlk793@gmail.com";
    private String messageBody;

    /**
     *
     * @param messageBody the body of the email message
     */
    public EmailStrategy(String messageBody) {
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
