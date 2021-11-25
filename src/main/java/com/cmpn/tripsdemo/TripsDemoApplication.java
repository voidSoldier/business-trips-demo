package com.cmpn.tripsdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages = "com.cmpn.tripsdemo")
public class TripsDemoApplication implements CommandLineRunner {

    @Autowired
    private ApplicationContext appContext;

    public static void main(String[] args) {
        SpringApplication.run(TripsDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
