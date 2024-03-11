package org.nazar.service;

import java.io.IOException;
import java.util.List;

/**
 * Represents a strategy for parsing data from a source.
 */
public interface ParserStrategy {
    /**
     * Parses data from web page
     *
     * @return list of strings got from source
     * @throws IOException if an I/O error occurs during parsing
     */
    List<String> getData(String url) throws IOException;

    /**
     * Get filename where unique data will be stored
     *
     * @return name of file
     */
    String getFileName();
}
