package org.nazar.service.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import org.nazar.service.properties.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Class for managing data from user using h2 database
 */
@Component
public class VacancyDBDao implements VacancyDao {

    private static final Logger logger = LoggerFactory.getLogger(VacancyDBDao.class);

    Connection connection = ApplicationProperties.INSTANCE.connect();

    /**
     * Writes parsed links to database
     * @param parsedLinks links from site
     * @param resourceId id of parsed resource
     */
    @Override
    public void write(List<String> parsedLinks, String resourceId) {
        try(PreparedStatement statement = connection.prepareStatement("insert into parsers_links (FK_provider_id, link) values (?, ?);")) {
            statement.setInt(2, Integer.parseInt(resourceId));
            statement.setString(3, parsedLinks.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Cannot execute sql query");
        }
    }

    /**
     * Read parsed links from database
     * @param resourceId id of parsed resource
     * @return list of links
     */
    @Override
    public List<String> read(String resourceId) {
        try(PreparedStatement statement = connection.prepareStatement("select link from parsers_links where FK_provider_id = ?")) {
            statement.setInt(1, Integer.parseInt(resourceId));
            ResultSet resultSet = statement.executeQuery();

            return Arrays.stream(resultSet.toString().substring(1, resultSet.toString().length()-1).split(",\\s*")).toList();
        } catch (SQLException e) {
            logger.error("Cannot execute sql query");
        }
        return null;
    }
}
