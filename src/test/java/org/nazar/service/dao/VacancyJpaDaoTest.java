package org.nazar.service.dao;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class VacancyJpaDaoTest {


    @Autowired
    private VacancyJpaDao vacancyJpaDao;

    private static final String RESOURCE_ID = "testResource";

    /**
     * Test that links are written to the database correctly
     */
    @Test
    public void writeDataTest_IfSucceed_ThenDataShouldBeWritten() {
        List<String> parsedLinks = List.of("http://link1.com", "http://link2.com");

        vacancyJpaDao.write(parsedLinks, RESOURCE_ID);

        List<String> actual = vacancyJpaDao.read(RESOURCE_ID);
        assertEquals(parsedLinks.toString(), actual.toString());
    }

    /**
     * Test that links are read from the database correctly
     */
    @Test
    public void readDataTest_IfSucceed_ThenDataShouldBeReturned() {
        List<String> expectedLinks = List.of("http://link1.com", "http://link2.com");
        vacancyJpaDao.write(expectedLinks, RESOURCE_ID);

        List<String> actualLinks = vacancyJpaDao.read(RESOURCE_ID);

        assertEquals(expectedLinks.toString(), actualLinks.toString());
    }

    /**
     * Test that reading non-existing links returns an empty list
     */
    @Test
    public void readDataTest_IfFail_ThenEmptyListShouldBeReturned() {
        String resourceId = "nonExistentResource";
        List<String> result = vacancyJpaDao.read(resourceId);
        assertTrue(result.isEmpty());
    }
}