package org.nazar.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ParserService {
    public static final String BASE_URL = "https://djinni.co";
    public static final String JOBS_URL = "https://djinni.co/jobs/";
    private final EmailService emailService = new EmailService();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void connect() {
        startScheduledJob();
    }

    private void startScheduledJob() {
        Runnable scanner = () -> {
            try {
                getJavaOffers();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        scheduler.scheduleAtFixedRate(scanner, 0, 2, TimeUnit.MINUTES);
    }

    private String getHtml(String url) throws IOException {
        return Jsoup.connect(url).get().html();
    }
    private void getJavaOffers() throws InterruptedException, IOException {
        List<String> resultLinks = new ArrayList<>();

        int lastPageNumber = getLastPageNumber();

        for (int pageNumber = 1; pageNumber < lastPageNumber; pageNumber++) {
            String pageUrl = JOBS_URL + "?page=" + pageNumber;
            Document document = Jsoup.parse(getHtml(pageUrl));
            Elements divs = document.select("div.job-list-item");
            for (Element e : divs) {
                Element temp = e.select("header").first();
                String date = e.select("span.mr-2.nobr").first().text();
                if (temp.text().matches(".*(Java).*(Junior|Trainee).*|.*(Junior|Trainee).*(Java).*") && date.equalsIgnoreCase("сьогодні")) {
                    Element link = temp.select("a.h3.job-list-item__link").first();
                    String resultLink = BASE_URL + link.attr("href");
                    System.out.println(resultLink);
                    resultLinks.add(resultLink);
                }
            }
            Thread.sleep(1000);
        }
        emailService.sendEmail(resultLinks.toString());
    }

    private int getLastPageNumber() throws IOException {

       List<Integer> nums = new ArrayList<>();

       Document document = Jsoup.parse(getHtml(JOBS_URL));
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
