package org.nazar.service.smtp;


import org.nazar.service.EmailService;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SmtpAuthenticator extends Authenticator {
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        String username = EmailService.TO;
        String password = EmailService.PASSWORD;
        return new PasswordAuthentication(username, password);
    }
}
