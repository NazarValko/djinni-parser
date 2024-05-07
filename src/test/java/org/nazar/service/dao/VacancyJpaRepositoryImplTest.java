package org.nazar.service.dao;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.nazar.service.notification.bot.VacancyBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest("dao.type=jpa")
@ActiveProfiles("test")
public class VacancyJpaRepositoryImplTest {
    @Autowired
    private VacancyDao vacancyJpaRepository;

    private static final String RESOURCE_ID = "testResource";

    @MockBean
    private VacancyBot vacancyBot;
    /**
     * Test that links are written to the database correctly
     */
    @Test
    void writeDataTest_IfSucceed_ThenDataShouldBeWritten() throws IOException {
        List<String> parsedLinks = List.of("http://link1.com", "http://link2.com");

        vacancyJpaRepository.write(parsedLinks, RESOURCE_ID);

        List<String> actual = vacancyJpaRepository.read(RESOURCE_ID);
        assertEquals(parsedLinks, actual);
    }

    /**
     * Test that links are read from the database correctly
     */
    @Test
    void readDataTest_IfSucceed_ThenDataShouldBeReturned() {
        List<String> expectedLinks = List.of("http://link1.com", "http://link2.com");

        List<String> actualLinks = vacancyJpaRepository.read(RESOURCE_ID);

        assertEquals(expectedLinks, actualLinks);
    }

    /**
     * Test that reading non-existing links returns an empty list
     */
    @Test
    void readDataTest_IfFail_ThenEmptyListShouldBeReturned() {
        String resourceId = "nonExistentResource";
        List<String> result = vacancyJpaRepository.read(resourceId);
        assertTrue(result.isEmpty());
    }
}
