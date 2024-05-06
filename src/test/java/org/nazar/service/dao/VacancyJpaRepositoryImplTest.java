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
public class VacancyJpaRepositoryImplTest {
    @Autowired
    private VacancyJpaRepositoryImpl vacancyJpaRepository;

    private static final String RESOURCE_ID = "testResource";

    /**
     * Test that links are written to the database correctly
     */
    @Test
    void writeDataTest_IfSucceed_ThenDataShouldBeWritten() {
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
