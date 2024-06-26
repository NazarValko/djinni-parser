package org.nazar.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Strategy for parsing web page from Dou
 */
public class DouParserStrategy implements ParserStrategy {

    private static final Logger logger = LoggerFactory.getLogger(DouParserStrategy.class);

    /**
     * Parses jobs that match specific criteria
     *
     * @param html html in text format of website to parse
     * @return list of links to found jobs
     */
    public List<String> parse(String html) {
        if (html == null) {
            throw new IllegalArgumentException("HTML page is empty");
        }
        List<String> resultLinks = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements vacancies = document.select("li.l-vacancy");
        for (Element vacancy : vacancies) {
            Element titleElement = vacancy.select("div.title > a.vt").first();
            if (titleElement != null) {
                String titleText = titleElement.text();
                if (titleText.matches(".*\\bJava\\b(?!Script).*")) {
                    String resultLink = titleElement.attr("href");
                    logger.info(resultLink);
                    resultLinks.add(resultLink);
                }
            }
        }
        return resultLinks;
    }

    /**
     * Get data from source
     *
     * @param url url of web page
     * @return list of links
     * @throws IOException if occurs I/O exception
     */
    @Override
    public List<String> getData(String url) throws IOException {
        return parse(Jsoup.connect(url).get().html());
    }

    /**
     * Get Dou's id
     *
     * @return id
     */
    @Override
    public String getResourceId() {
        return "dou";
    }
}
