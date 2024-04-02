package org.nazar;

import jakarta.annotation.PostConstruct;
import org.nazar.service.ParserService;
import org.nazar.service.properties.ApplicationProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ParserApplication implements CommandLineRunner {

    /**
     * Set headless for java.awt to work in spring environment
     */
    @PostConstruct
    public void setup() {
        System.setProperty("java.awt.headless", "false");
    }

    private final ParserService parserService;

    public ParserApplication(ParserService parserService) {
        this.parserService = parserService;
    }

    public static void main(String[] args) {
        args = args == null ? new String[]{} : args;
        if (args.length == 0) {
            ApplicationProperties.INSTANCE.setPassword(null);
        } else {
            ApplicationProperties.INSTANCE.setPassword(args[0]);
        }
        SpringApplication.run(ParserApplication.class, args);
    }

    @Override
    public void run(String... args) {
        parserService.start();
    }
}
