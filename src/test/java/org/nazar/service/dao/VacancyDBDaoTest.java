package org.nazar.service.dao;

import org.junit.jupiter.api.Test;
import org.nazar.service.notification.bot.VacancyBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for write and read links from database
 */
@SpringBootTest(value = "dao.type=db")
@ActiveProfiles("test")
public class VacancyDBDaoTest {

    private static final String RESOURCE_ID = "testResource";

    @Autowired
    private VacancyDao vacancyDBDao;

    @MockBean
    private VacancyBot vacancyBot;

    /**
     * Tests write links of ok then it should be in database
     */
    @Test
    void writeDataTest_IfSucceed_ThenDataShouldBeWritten() throws IOException {
        List<String> parsedLinks = List.of("http://link1.com", "http://link2.com");

        vacancyDBDao.write(parsedLinks, RESOURCE_ID);

        List<String> actual = vacancyDBDao.read("testResource");

        assertEquals(parsedLinks, actual);
    }

    /**
     * Tests read links if success links should be returned
     */
    @Test
    void readDataTest_IfSucceed_ThenDataShouldBeReturned() throws IOException {
        List<String> expectedLinks = List.of("http://link1.com", "http://link2.com");
        vacancyDBDao.write(expectedLinks, RESOURCE_ID);

        List<String> actualLinks = vacancyDBDao.read(RESOURCE_ID);

        assertEquals(expectedLinks, actualLinks);
    }

    /**
     * Tests read links if provider is not exist empty list should be returned
     */
    @Test
    void readDataTest_IfFail_ThenEmptyListShouldBeReturned() {
        String resourceId = "nonExistentResource";
        List<String> result = vacancyDBDao.read(resourceId);
        assertTrue(result.isEmpty());
    }
}
