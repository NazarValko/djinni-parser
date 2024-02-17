package org.nazar;

import org.nazar.service.ParserServiceImpl;

public class Main {
    public static void main(String[] args) {
        ParserServiceImpl parserServiceImpl = new ParserServiceImpl();
        if (args.length > 0) {
            parserServiceImpl.start(args[0]);
        } else {
            parserServiceImpl.start(null);
        }
    }
}