package org.nazar.service;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ParserServiceImplTest {
    @Test
    void testParse() throws IOException {
        ParserStrategy parserStrategyMock = mock(ParserStrategy.class);

        List<String> expectedLinks = Arrays.asList("link1", "link2", "link3");
        when(parserStrategyMock.parse()).thenReturn(expectedLinks);

        ParserService parserService = new ParserServiceImpl();

        List<String> resultLinks = parserService.parse(parserStrategyMock);

        assertEquals(expectedLinks, resultLinks);

        verify(parserStrategyMock).parse();
    }
}