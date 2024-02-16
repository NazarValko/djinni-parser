package org.nazar.service;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for DouParserStrategy class
 */
class DouParserStrategyTest {

    /**
     * Test whether method parse() returns correct result of links for Dou
     *
     */
    @Test
    void parseTest() {
        String html = """
                <html>
                    <body>
                        <ul>
                            <li class="l-vacancy">
                                <div class="title">
                                    <a class="vt" href="https://jobs.dou.ua/job1">Java Developer</a>
                                </div>
                            </li>
                            <li class="l-vacancy">
                                <div class="title">
                                    <a class="vt" href="https://jobs.dou.ua/job2">JavaScript Developer</a>
                                </div>
                            </li>
                            <li class="l-vacancy">
                                <div class="title">
                                    <a class="vt" href="https://jobs.dou.ua/job3">Junior Java Developer</a>
                                </div>
                            </li>
                        </ul>
                    </body>
                </html>
                """;
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
