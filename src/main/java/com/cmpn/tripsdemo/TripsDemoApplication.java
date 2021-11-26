package com.cmpn.tripsdemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.cmpn.tripsdemo")
@EnableFeignClients
public class TripsDemoApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(TripsDemoApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {

  }
}
