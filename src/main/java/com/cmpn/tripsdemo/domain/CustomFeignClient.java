package com.cmpn.tripsdemo.domain;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// https://nominatim.org/release-docs/develop/api/Overview/
// https://javatodev.com/how-to-use-feign-client-in-spring-boot/
@FeignClient(value = "geocoding-api", url = "${geocoding.base.url}")
public interface CustomFeignClient {

  // https://nominatim.openstreetmap.org/search?q=berlin&format=json&limit=1
  @GetMapping(path = "/search")
  String getLocationCoordinates(@RequestParam("q") String query,
                                @RequestParam("format") String format,
                                @RequestParam("limit") String limit);

}
