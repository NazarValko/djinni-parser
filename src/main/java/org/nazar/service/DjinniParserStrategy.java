package org.nazar.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Strategy for parsing web page from Djinni
 */
public class DjinniParserStrategy implements ParserStrategy {
    private static final String BASE_URL = "https://djinni.co";


    /**
     * Parses jobs that match specific criteria
     *
     * @param html html in text format of page to parse
     * @return a list of URLs to jobs
     */
    public List<String> parse(String html) {
        if (html == null) {
            throw new IllegalArgumentException("HTML page is empty");
        }
        List<String> resultLinks = new ArrayList<>();

        Document document = Jsoup.parse(html);
        Elements divs = document.select("div.job-list-item");
        for (Element e : divs) {
            Element temp = e.select("header").first();
            if (temp != null && (temp.text().matches(".*(Java).*(Junior|Trainee).*|.*(Junior|Trainee).*(Java).*"))) {
                Element link = temp.select("a.h3.job-list-item__link").first();
                if (link != null) {
                    String resultLink = BASE_URL + link.attr("href");
                    System.out.println(resultLink);
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
    public List<String> getData(String url) throws IOException {
        return parse(Jsoup.connect(url).get().html());
    }

    /**
     * Get Djinni's id
     *
     * @return id
     */
    @Override
    public String getResourceId() {
        return "djinni";
    }
}
