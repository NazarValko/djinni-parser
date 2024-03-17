package org.nazar.service.dao;

import java.util.List;

/**
 * Represents different ways of storing data
 */
public interface VacancyDao {

    /**
     * Writes parsed links to source
     *
     * @param parsedLinks links from site
     * @param filePath path to source
     */
    void write(List<String> parsedLinks, String filePath);

    /**
     * Read parsed links from source
     *
     * @param pathToFile path where links are collected
     * @return return parsed links from source
     */
    List<String> read(String pathToFile);
}
