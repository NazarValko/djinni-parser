package org.nazar.service.properties;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationPropertiesTest {

    /**
     * Tests that when command line arguments contain a password,
     * the password is correctly set in the ApplicationProperties.
     */
    @Test
    public void readArgsFromCommandLine_whenArgsContainPassword_thenPasswordIsSet() {
        String[] args = {"Password"};

        ApplicationProperties.INSTANCE.readArgsFromCommandLine(args);

        assertEquals("Password", ApplicationProperties.INSTANCE.getPassword());
    }

    /**
     * Tests that when the command line arguments are null,
     * the method should fall back to using a system or environment variable for the password.
     */
    @Test
    public void readArgsFromCommandLine_whenArgsAreNull_thenPasswordShouldBeNullAndFallbackToSystemOrEnvironmentVariable() {
        String[] args = null;
        System.setProperty("ParserPassword", "systemFallbackPassword");

        ApplicationProperties.INSTANCE.readArgsFromCommandLine(args);

        assertEquals("systemFallbackPassword", ApplicationProperties.INSTANCE.getPassword());
    }

    /**
     * Tests that when the command line arguments are empty,
     * the method should fall back to using a system or environment variable for the password.
     */
    @Test
    public void readArgsFromCommandLine_whenArgsAreEmpty_thenPasswordShouldBeNullAndFallbackToSystemOrEnvironmentVariable() {
        String[] args = {};
        System.setProperty("ParserPassword", "systemFallbackPassword");

        ApplicationProperties.INSTANCE.readArgsFromCommandLine(args);

        assertEquals("systemFallbackPassword", ApplicationProperties.INSTANCE.getPassword());
    }

    /**
     * Tests that when command line arguments contain a null element,
     * the method should fall back to using a system or environment variable for the password.
     */
    @Test
    public void readArgsFromCommandLine_whenArgsContainNull_thenPasswordShouldBeNullAndFallbackToSystemOrEnvironmentVariable() {
        String[] args = {null};
        System.setProperty("ParserPassword", "systemFallbackPassword");

        ApplicationProperties.INSTANCE.readArgsFromCommandLine(args);

        assertEquals("systemFallbackPassword", ApplicationProperties.INSTANCE.getPassword());
    }
}
