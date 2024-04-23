package org.nazar.service.dao;

import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for write and read links from database
 */
@SpringBootTest
public class VacancyDBDaoTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private VacancyDBDao vacancyDBDao;

    private JdbcTemplate jdbcTemplate;

    /**
     * Creates connection
     */
    @BeforeEach
    void setUp() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Delete test links
     */
    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("delete from parsers_links;");
        jdbcTemplate.execute("delete from link_providers;");
    }

    /**
     * Tests write links of ok then it should be in database
     */
    @Test
    void writeDataTest_IfSucceed_ThenDataShouldBeWritten() {
        String resourceId = "testResource";
        List<String> parsedLinks = Arrays.asList("http://link1.com", "http://link2.com");

        vacancyDBDao.write(parsedLinks, resourceId);

        List<String> actual = vacancyDBDao.read("testResource");

        assertEquals(parsedLinks, actual);
    }

    /**
     * Tests read links if success links should be returned
     */
    @Test
    void readDataTest_IfSucceed_ThenDataShouldBeReturned() {
        String resourceId = "testResource";
        List<String> expectedLinks = Arrays.asList("http://link1.com", "http://link2.com");
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
