package org.nazar.service.dao;

import java.io.IOException;
import java.util.Arrays;
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

    /**
     * Converts string of links to list of links
     * @param links string of links
     * @return list
     */
    default List<String> convertStringToList(String links) {
        return links != null ? Arrays.stream(links.substring(1, links.length()-1).split(",\\s*")).toList() : List.of();
    }
}
