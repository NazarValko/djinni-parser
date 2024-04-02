package org.nazar;

import org.junit.jupiter.api.Test;
import org.nazar.service.properties.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for Main class
 */
@SpringBootTest
public class ParserApplicationTest {

    @Autowired
    private ParserApplication parserApplication;

    /**
     * Test when command line arguments(password) is passed then it should be added to properties map
     */
    @Test
    void runTest() {
        parserApplication.run("password");
        assertEquals("password", ApplicationProperties.INSTANCE.getPassword());
    }

    /**
     * Test when there is null as argument the program should work.
     * Password in properties should be present(as environmental variable or java system property)
     */
    @Test
    void runTest_WhenArgumentIsNull_ThenPassNullParameterAsPassword() {
        assertDoesNotThrow(() -> parserApplication.run(null));
    }

    /**
     * Test when command line arguments is empty then program should work
     * and use put in map password from environmental variable or java system property
     */
    @Test
    void runTest_WhenArgumentListIsEmpty_ThenPassNullParameterAsPassword() {
        assertDoesNotThrow(() -> parserApplication.run());
    }

    /**
     * Test when command line arguments is empty then system property should be passed
     */
    @Test
    void runTest_WhenArgumentIsNotPresent_ThenSystemPropertyShouldBeUsed() {
        String originalPassword = System.getProperty("ParserPassword");
        try {
            System.setProperty("ParserPassword", "password");
            parserApplication.run();
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
