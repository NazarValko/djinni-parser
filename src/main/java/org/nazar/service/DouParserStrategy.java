package org.nazar.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Strategy for parsing web page from Dou
 */
public class DouParserStrategy implements ParserStrategy {
    public static final String BASE_URL = "https://jobs.dou.ua/first-job/";

    /**
     * Parses jobs that match specific criteria
     *
     * @return list of links to found jobs
     * @throws IOException if exception occurs during parsing
     */
    @Override
    public List<String> parse() throws IOException {
        List<String> resultLinks = new ArrayList<>();

        Document document = Jsoup.parse(Jsoup.connect(BASE_URL).get().html());
        Elements divs = document.select("li.l-vacancy");
        for (Element e : divs) {
            Element temp = e.select("div.title").first();
            if (temp.text().matches(".*\bJava\b(?!Script).*")) {
                Element link = temp.select("a.vt").first();
                String resultLink = link.attr("href");
                System.out.println(resultLink);
                resultLinks.add(resultLink);
            }
        }
        return resultLinks;
    }
}
