package org.nazar.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for DouParserStrategy class
 */
@SpringBootTest
class DouParserStrategyTest {

    /**
     * Test whether method parse() returns correct result of links for Dou
     */
    @Test
    void parseTest() throws IOException {
        String html = Files.readString(Paths.get("src/test/resources/dou.txt"));
        DouParserStrategy douParserStrategy = new DouParserStrategy();
        List<String> actual = douParserStrategy.parse(html);
        List<String> expected = List.of("https://jobs.dou.ua/job1", "https://jobs.dou.ua/job3");

        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertEquals(expected, actual);
    }

    /**
     * Tests when null argument is passed throws IllegalArgumentException
     */
    @Test
    void testParseWithNull() {
        DouParserStrategy parser = new DouParserStrategy();

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

        DouParserStrategy parser = new DouParserStrategy();

        List<String> resultLinks = parser.parse(unexpectedHtmlStructure);

        assertNotNull(resultLinks);
        assertTrue(resultLinks.isEmpty());
    }
}
