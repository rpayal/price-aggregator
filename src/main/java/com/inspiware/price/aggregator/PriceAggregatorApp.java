package com.inspiware.price.aggregator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class PriceAggregatorApp {
    private static final Logger log = LoggerFactory.getLogger(PriceAggregatorApp.class);

    private final Environment env;

    public PriceAggregatorApp(Environment env) {
        this.env = env;
    }

    public static void main(String[] args) {
        SpringApplication.run(PriceAggregatorApp.class, args);
    }
}
