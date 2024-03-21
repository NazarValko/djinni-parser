package org.nazar;

import org.nazar.service.properties.ApplicationProperties;
import org.nazar.service.ParserServiceImpl;

public class Main {
    public static void main(String[] args) {
        ParserServiceImpl parserServiceImpl = new ParserServiceImpl();
        if (args == null || args.length == 0) {
            ApplicationProperties.INSTANCE.setApplicationProperties(null);
        } else {
            ApplicationProperties.INSTANCE.setApplicationProperties(args[0]);
        }

        parserServiceImpl.start();
    }
}