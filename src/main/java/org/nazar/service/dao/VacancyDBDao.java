package org.nazar.service.dao;

import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
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

    JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(VacancyDBDao.class);

    public VacancyDBDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Writes parsed links to database
     * @param parsedLinks links from site
     * @param resourceId id of parsed resource
     */
    @Override
    public void write(List<String> parsedLinks, String resourceId) {
        try {
            String checkProviderSql = "select id from link_providers where name = ?";
            List<Integer> providerIds = jdbcTemplate.query(checkProviderSql,
                    (rs, rowNum) -> rs.getInt("id"), resourceId);

            Integer providerId = providerIds.isEmpty() ? null : providerIds.get(0);

            if (providerId == null) {
                String insertProviderSql = "insert into link_providers (name) values (?)";
                jdbcTemplate.update(insertProviderSql, resourceId);

                providerId = jdbcTemplate.query(checkProviderSql,
                        (rs, rowNum) -> rs.getInt("id"), resourceId).get(0);
            }

            String insertLinkSql = "insert into parsers_links (FK_provider_id, link) values (?, ?)";

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
        String sql = "select link from parsers_links where FK_provider_id = (select id from link_providers where name = ?)";
        try {
            String result = jdbcTemplate.query(sql, rs -> rs.next() ? rs.getString("link") : null, resourceId);
            return result != null ? Arrays.stream(result.substring(1, result.length()-1).split(",\\s*")).toList() : List.of();
        } catch (DataAccessException e) {
            logger.error("Cannot execute sql query", e);
            return List.of();
        }
    }
}
