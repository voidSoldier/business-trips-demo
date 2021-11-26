package com.cmpn.tripsdemo;

import com.cmpn.tripsdemo.domain.CustomFeignClient;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;
import io.jsonwebtoken.io.IOException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class FeignClientIntegrationTest {

  @Autowired
  CustomFeignClient client;

  static WireMockServer wireMock;

  static final String TEST = "test";

  @BeforeAll
  static void startWireMock() {
    wireMock = new WireMockServer(new WireMockConfiguration().port(8085));
    wireMock.start();
    WireMock.configureFor("localhost", 8085);
  }

  @AfterAll
  static void stopWireMock() {
    wireMock.stop();
  }

  @Test
  void testClient() {
    mockSetUp(wireMock);
    assertTrue(wireMock.isRunning());
    String response = client.getLocationCoordinates(TEST, TEST, TEST);
    assertEquals(response, TEST);
  }

  private void mockSetUp(WireMockServer mockService) throws IOException {

    Map<String, StringValuePattern> params = new HashMap<>();
    params.put("q", matching(TEST));
    params.put("format", matching(TEST));
    params.put("limit", matching(TEST));

    mockService.stubFor(WireMock.get(
        WireMock.urlPathMatching("/search"))
      .withQueryParams(params)
      .willReturn(WireMock.aResponse()
        .withStatus(HttpStatus.OK.value())
        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .withBody(TEST)));
  }
}
