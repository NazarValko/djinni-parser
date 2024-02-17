package org.nazar;

import org.junit.jupiter.api.Test;
import org.nazar.service.properties.ApplicationProperties;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for Main class
 */
public class MainTest {

    /**
     * Test when command line arguments(password) is passed then it should be added to properties map
     */
    @Test
    void mainTest() {
        Main.main(new String[]{"password"});
        assertEquals("password", ApplicationProperties.INSTANCE.getData().get("receiverPassword"));
    }

    /**
     * Test when there is null as argument the program should work.
     * Password in properties should be present(as environmental variable or java system property)
     */
    @Test
    void mainTest_WhenArgumentIsNull_ThenPassNullParameterAsPassword() {
        assertDoesNotThrow(() -> Main.main(null));
    }

    /**
     * Test when command line arguments is empty then program should work
     * and use put in map password from environmental variable or java system property
     */
    @Test
    void mainTest_WhenArgumentListIsEmpty_ThenPassNullParameterAsPassword() {
        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }

    /**
     * Test when command line arguments is empty then system property should be passed
     */
    @Test
    void mainTest_WhenArgumentIsNotPresent_ThenSystemPropertyShouldBeUsed() {
        String originalPassword = System.getProperty("ParserPassword");
        try {
            System.setProperty("ParserPassword", "password");
            Main.main(new String[]{});
            assertEquals(System.getProperty("ParserPassword"), ApplicationProperties.INSTANCE.getData().get("receiverPassword"));
        } finally {
            if (originalPassword != null) {
                System.setProperty("ParserPassword", originalPassword);
            } else {
                System.clearProperty("ParserPassword");
            }
        }
    }
    
}
