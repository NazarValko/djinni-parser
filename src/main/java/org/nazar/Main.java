package org.nazar;

import org.nazar.service.ParserServiceImpl;

public class Main {
    public static void main(String[] args) {
        ParserServiceImpl parserServiceImpl = new ParserServiceImpl();
        parserServiceImpl.start();
    }
}