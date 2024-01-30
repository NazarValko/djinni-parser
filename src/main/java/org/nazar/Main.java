package org.nazar;

import org.nazar.service.ParserService;
import org.nazar.util.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    private static final ParserService parserService = new ParserService();
    public static void main(String[] args) throws IOException, InterruptedException {
        URL url = new URL(Constants.JOBS_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();


        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        parserService.getJavaOffers();
        in.close();


    }
}