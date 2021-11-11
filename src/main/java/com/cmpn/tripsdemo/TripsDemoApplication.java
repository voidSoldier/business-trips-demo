package com.cmpn.tripsdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cmpn.tripsdemo")
public class TripsDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TripsDemoApplication.class, args);
    }

}
