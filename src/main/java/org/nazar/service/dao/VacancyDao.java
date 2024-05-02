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
     * @param result string of links
     * @return list
     */
    default List<String> convertStringToList(String result) {
        return result != null ? Arrays.stream(result.substring(1, result.length()-1).split(",\\s*")).toList() : List.of();
    }
}
