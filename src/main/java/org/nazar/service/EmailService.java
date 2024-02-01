package org.nazar.service;

import org.nazar.service.smtp.SmtpAuthenticator;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {
    private final Authenticator authenticator = new SmtpAuthenticator();

    public static final String TO = "nazarvlk793@gmail.com";
    public static final String FROM = "nazar.valko09@gmail.com";

    public static final String PASSWORD = "gxib gftv jryu noyn";
   public void sendEmail(String text) {
       try {
           MimeMessage message = new MimeMessage(Session.getDefaultInstance(setProperties(),
                   authenticator));

           message.setFrom(new InternetAddress(FROM));
           message.addRecipient(Message.RecipientType.TO, new InternetAddress(TO));
           message.setSubject("Latest vacancy");
           message.setText(text);
           Transport.send(message);
       } catch (MessagingException mex) {
           mex.printStackTrace();
       }
   }
   private Properties setProperties() {

       Properties properties = System.getProperties();

       properties.put("mail.smtp.host", "smtp.gmail.com");
       properties.put("mail.smtp.port", "465");
       properties.put("mail.smtp.ssl.enable", "true");
       properties.put("mail.smtp.auth", "true");

       return properties;
   }
}
