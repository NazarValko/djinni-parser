package org.nazar.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for DjinniParserStrategy class
 */
@SpringBootTest
class DjinniParserStrategyTest {

    /**
     * Test whether method parse() returns correct result of links for Djinni
     */
    @Test
    void parseTest() throws IOException {
        String html = Files.readString(Paths.get("src/test/resources/djinni.txt"));
        DjinniParserStrategy djinniParserStrategy = new DjinniParserStrategy();
        List<String> actual = djinniParserStrategy.parse(html);
        List<String> expected = List.of("https://djinni.co/job/java-junior-position-xyz", "https://djinni.co/job/trainee-java-position-def");

        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertEquals(expected, actual);
    }

    /**
     * Tests when null argument is passed throws IllegalArgumentException
     */
    @Test
    void testParseWithNull() {
        DjinniParserStrategy parser = new DjinniParserStrategy();

        assertThrows(IllegalArgumentException.class, () -> {
            parser.parse(null);
        });
    }

    /**
     * Tests when incorrect html passed fail test
     */
    @Test
    void parseTestFailed() {
        String unexpectedHtmlStructure = "<html><body><div class='wrong-class'>Java Developer</div></body></html>";

        DjinniParserStrategy parser = new DjinniParserStrategy();

        List<String> resultLinks = parser.parse(unexpectedHtmlStructure);

        assertNotNull(resultLinks);
        assertTrue(resultLinks.isEmpty());
    }
}
