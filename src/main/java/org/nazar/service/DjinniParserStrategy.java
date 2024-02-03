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
    public static final String BASE_URL = "https://djinni.co/jobs/?primary_keyword=Java&exp_level=no_exp";
    public static final String JOBS_URL = "https://djinni.co";


    /**
     * Parses jobs that match specific criteria
     *
     * @return a list of URLs to jobs
     * @throws IOException if error occurs during parsing
     */
    public List<String> parse() throws IOException {
        List<String> resultLinks = new ArrayList<>();

        Document document = Jsoup.parse(Jsoup.connect(BASE_URL).get().html());
        Elements divs = document.select("div.job-list-item");
        for (Element e : divs) {
            Element temp = e.select("header").first();
            String date = e.select("span.mr-2.nobr").first().text();
            if (temp.text().matches(".*(Java).*(Junior|Trainee).*|.*(Junior|Trainee).*(Java).*") && date.equalsIgnoreCase("сьогодні")) {
                Element link = temp.select("a.h3.job-list-item__link").first();
                String resultLink = JOBS_URL + link.attr("href");
                System.out.println(resultLink);
                resultLinks.add(resultLink);
            }
        }
        return resultLinks;
    }
}
