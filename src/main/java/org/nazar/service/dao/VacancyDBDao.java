package org.nazar.service.dao;

import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Class for managing data from user using h2 database
 */
@Component
@Primary
public class VacancyDBDao implements VacancyDao {

    private final JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(VacancyDBDao.class);

    public VacancyDBDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Writes parsed links to database
     * @param parsedLinks links from site
     * @param resourceId id of parsed resource
     */
    @Override
    public void write(List<String> parsedLinks, String resourceId) {
        try {
            String checkProviderSql = "SELECT id FROM link_providers WHERE name = ?";
            List<Integer> providerIds = jdbcTemplate.query(checkProviderSql,
                    (rs, rowNum) -> rs.getInt("id"), resourceId);

            Integer providerId = providerIds.isEmpty() ? null : providerIds.get(0);

            if (providerId == null) {
                String insertProviderSql = "INSERT INTO link_providers (name) VALUES (?)";
                jdbcTemplate.update(insertProviderSql, resourceId);

                providerId = jdbcTemplate.query(checkProviderSql,
                        (rs, rowNum) -> rs.getInt("id"), resourceId).get(0);
            }

            String insertLinkSql = "INSERT INTO parsers_links (FK_provider_id, link) VALUES (?, ?)";

            jdbcTemplate.update(insertLinkSql, providerId, parsedLinks.toString());

            logger.info("Data was written successfully");
        } catch (DataAccessException e) {
            logger.error("Cannot execute sql query", e);
        }
    }

    /**
     * Read parsed links from database
     * @param resourceId id of parsed resource
     * @return list of links
     */
    @Override
    public List<String> read(String resourceId) {
        String sql = "SELECT link FROM parsers_links WHERE FK_provider_id = (SELECT id FROM link_providers WHERE name = ?)";
        try {
            String result = jdbcTemplate.query(sql, rs -> rs.next() ? rs.getString("link") : null, resourceId);
            return result != null ? Arrays.stream(result.substring(1, result.length()-1).split(",\\s*")).toList() : List.of();
        } catch (DataAccessException e) {
            logger.error("Cannot execute sql query", e);
            return List.of();
        }
    }
}
