package org.nazar.service;

import org.junit.jupiter.api.Test;
import org.nazar.service.smtp.SmtpAuthenticator;

import javax.mail.PasswordAuthentication;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SmtpAuthenticatorTest {
    @Test
    void testGetPasswordAuthentication() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String expectedUsername = "testUser";
        String expectedPassword = "testPassword";

        SmtpAuthenticator authenticator = new SmtpAuthenticator(expectedUsername, expectedPassword);

        Method getPasswordAuthentication = SmtpAuthenticator.class.getDeclaredMethod("getPasswordAuthentication");
        getPasswordAuthentication.setAccessible(true); // Make the method accessible

        PasswordAuthentication result = (PasswordAuthentication) getPasswordAuthentication.invoke(authenticator);

        assertNotNull(result, "PasswordAuthentication should not be null");
        assertEquals(expectedUsername, result.getUserName(), "Username does not match the expected value");

    }
}
