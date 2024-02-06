package org.nazar;

import org.nazar.service.ParserServiceImpl;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        ParserServiceImpl parserServiceImpl = new ParserServiceImpl();
        parserServiceImpl.start();
    }
}