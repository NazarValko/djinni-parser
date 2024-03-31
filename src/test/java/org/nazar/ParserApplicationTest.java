package org.nazar;

import org.junit.jupiter.api.Test;
import org.nazar.service.properties.ApplicationProperties;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for Main class
 */
public class ParserApplicationTest {

    /**
     * Test when command line arguments(password) is passed then it should be added to properties map
     */
    @Test
    void mainTest() {
        ParserApplication.main(new String[]{"password"});
        assertEquals("password", ApplicationProperties.INSTANCE.getPassword());
    }

    /**
     * Test when there is null as argument the program should work.
     * Password in properties should be present(as environmental variable or java system property)
     */
    @Test
    void mainTest_WhenArgumentIsNull_ThenPassNullParameterAsPassword() {
        assertDoesNotThrow(() -> ParserApplication.main(null));
    }

    /**
     * Test when command line arguments is empty then program should work
     * and use put in map password from environmental variable or java system property
     */
    @Test
    void mainTest_WhenArgumentListIsEmpty_ThenPassNullParameterAsPassword() {
        assertDoesNotThrow(() -> ParserApplication.main(new String[]{}));
    }

    /**
     * Test when command line arguments is empty then system property should be passed
     */
    @Test
    void mainTest_WhenArgumentIsNotPresent_ThenSystemPropertyShouldBeUsed() {
        String originalPassword = System.getProperty("ParserPassword");
        try {
            System.setProperty("ParserPassword", "password");
            ParserApplication.main(new String[]{});
            assertEquals(System.getProperty("ParserPassword"), ApplicationProperties.INSTANCE.getPassword());
        } finally {
            if (originalPassword != null) {
                System.setProperty("ParserPassword", originalPassword);
            } else {
                System.clearProperty("ParserPassword");
            }
        }
    }
    
}
