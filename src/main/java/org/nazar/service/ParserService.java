package org.nazar.service;

import java.io.IOException;
import java.util.List;

/**
 * Defines the contract for service that start parsing process and get data.
 */
public interface ParserService {

    /**
     * Start parsing
     */
    void start();

    /**
     * Collects data using specified parser strategy
     *
     * @param parserStrategy the strategy and source of parsing
     * @return data represented in a list of strings
     * @throws IOException if an error occurs during parsing
     */
    List<String> parse(ParserStrategy parserStrategy) throws IOException;
}
