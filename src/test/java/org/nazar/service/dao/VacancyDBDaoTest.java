package org.nazar.service.dao;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for write and read links from database
 */
@SpringBootTest
@ActiveProfiles("test")
public class VacancyDBDaoTest {

    private final String resourceId = "testResource";

    @Autowired
    private VacancyDBDao vacancyDBDao;

    /**
     * Tests write links of ok then it should be in database
     */
    @Test
    void writeDataTest_IfSucceed_ThenDataShouldBeWritten() {
        List<String> parsedLinks = List.of("http://link1.com", "http://link2.com");

        vacancyDBDao.write(parsedLinks, resourceId);

        List<String> actual = vacancyDBDao.read("testResource");

        assertEquals(parsedLinks, actual);
    }

    /**
     * Tests read links if success links should be returned
     */
    @Test
    void readDataTest_IfSucceed_ThenDataShouldBeReturned() {
        List<String> expectedLinks = List.of("http://link1.com", "http://link2.com");
        vacancyDBDao.write(expectedLinks, resourceId);

        List<String> actualLinks = vacancyDBDao.read(resourceId);

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
