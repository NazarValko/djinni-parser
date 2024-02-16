package org.nazar.service;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for DjinniParserStrategy class
 */
class DjinniParserStrategyTest {

    /**
     * Test whether method parse() returns correct result of links for Djinni
     *
     */
    @Test
    void parseTest() {
        String html = """
            <html>
                <body>
                    <div class="job-list-item">
                        <header>
                            <a class="h3 job-list-item__link" href="/job/java-junior-position-xyz">Java Junior Position XYZ</a>
                        </header>
                    </div>
                    <div class="job-list-item">
                        <header>
                            <a class="h3 job-list-item__link" href="/job/java-senior-position-abc">Java Senior Position ABC</a>
                        </header>
                    </div>
                    <div class="job-list-item">
                        <header>
                            <a class="h3 job-list-item__link" href="/job/trainee-java-position-def">Trainee Java Position DEF</a>
                        </header>
                    </div>
                </body>
            </html>
            """;
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
