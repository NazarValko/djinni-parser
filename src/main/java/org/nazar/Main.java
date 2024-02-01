package org.nazar;

import org.nazar.service.ParserService;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        ParserService parserService = new ParserService();
        parserService.connect();


    }
}