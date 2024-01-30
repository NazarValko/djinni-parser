package org.nazar.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.nazar.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ParserService {
   private String getHtml(String url) throws IOException {
       return Jsoup.connect(url).get().html();
   }
   public List<String> getJavaOffers() throws IOException, InterruptedException {
       List<String> resultLinks = new ArrayList<>();

       int lastPageNumber = getLastPageNumber(Constants.JOBS_URL);


       for (int pageNumber = 1; pageNumber < lastPageNumber; pageNumber++) {
           String pageUrl = Constants.JOBS_URL + "?page=" + pageNumber;
           Document document = Jsoup.parse(getHtml(pageUrl));
           Elements divs = document.select("div.job-list-item");
           for (Element e : divs) {
               Element temp = e.select("header").first();
               if (temp.text().matches(".*\\bJava\\b.*")) {
                   Element link = temp.select("a.h3.job-list-item__link").first();
                   resultLinks.add(Constants.BASE_URL + link.attr("href"));
               }
           }
           Thread.sleep(1000);
       }


       resultLinks.forEach(System.out::println);
       return resultLinks;
   }

    private int getLastPageNumber(String url) throws IOException {

       List<Integer> nums = new ArrayList<>();

       Document document = Jsoup.parse(getHtml(url));
       Elements pages = document.select("ul.pagination.pagination_with_numbers > li > a");
       for (Element page : pages) {
           if (page.text().matches("\\d+")) {
               String lastPageNumberText = page.text();
               nums.add(Integer.parseInt(lastPageNumberText));
           }
       }
       return Collections.max(nums);
    }
}
