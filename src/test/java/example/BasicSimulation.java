/**
 * -------------------------------------------------------------------------------
 * BasicSimulation
 * -------------------------------------------------------------------------------
 *
 * Description:
 * This Gatling simulation performs a basic load test against the e-commerce API
 * by sending HTTP GET requests to the /session endpoint.
 *
 * API Details:
 * - Base URL: https://api-ecomm.gatling.io
 * - Endpoint(s):
 *   - GET /session
 * - Clickable API: https://api-ecomm.gatling.io/session
 *
 * Load Profile:
 * - Number of Virtual Users (VUs) = 1 VU
 *
 * Purpose:
 * - Validate basic availability and correctness of the /session endpoint
 * - Serve as a starter/template Gatling Java DSL simulation
 *
 * -------------------------------------------------------------------------------
 */
package example;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class BasicSimulation extends Simulation {

  private static final int vu = Integer.parseInt(System.getProperty("vu", "1"));

  private static final HttpProtocolBuilder httpProtocol = http.baseUrl("https://api-ecomm.gatling.io")
      .acceptHeader("application/json")
      .contentTypeHeader("application/json")
      .userAgentHeader(
          "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36");

  private static final ScenarioBuilder scenario = scenario("GetSessionScenario")
      .exec(http("Session").get("/session"));

  private static final Assertion assertion = global().failedRequests().count().lt(1L);

  {
    setUp(scenario.injectOpen(atOnceUsers(vu))).assertions(assertion).protocols(httpProtocol);
  }
}
