package org.nazar.service.dao;

import java.io.IOException;
import java.util.List;

/**
 * Represents different ways of storing data
 */
public interface VacancyDao {

    /**
     * Writes parsed links to source
     *
     * @param parsedLinks links from site
     * @param resourceId id of parsed resource
     */
    void write(List<String> parsedLinks, String resourceId) throws IOException;

    /**
     * Read parsed links from source
     *
     * @param resourceId id of parsed resource
     * @return return parsed links from source
     */
    List<String> read(String resourceId);
}
