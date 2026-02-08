/**
 * BasicSimulation100.java
 *
 * Description:
 * This Gatling simulation tests the "GET /session" endpoint of an example e-commerce API.
 * It performs a simple load test by sending GET requests to the session endpoint and validates responses.
 *
 * API Used:
 * Base URL: https://api-ecomm.gatling.io
 *
 * Endpoint Tested:
 * GET /session
 *
 * Complete URL (clickable in IDEs or browsers):
 * https://api-ecomm.gatling.io/session
 *
 * Test Scenario:
 * - Scenario Name: GET Session API
 * - Executes a single GET request to retrieve session information
 * - Checks that the HTTP response status is 200 OK
 *
 * Load Simulation:
 * - Number of Virtual Users (VUs): Configurable via system property "vu"
 *   Default: 100 users
 *   Example to run with 50 users:
 *     mvn gatling:test -Dvu=50
 * - Injection profile: All users start at once (atOnceUsers)
 *
 * HTTP Protocol Configuration:
 * - Accept: application/json
 * - User-Agent: Gatling Java Test
 *
 * Assertions:
 * - Active Assertion: Total number of failed requests must be 0
 *
 * Notes:
 * - This simulation is a simple template for testing a single GET endpoint.
 * - Can be extended for additional endpoints, complex scenarios, or performance metrics.
 */

package example;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class BasicSimulation100 extends Simulation {

  private static final int vu = Integer.getInteger("vu", 100);

  private static final HttpProtocolBuilder httpProtocol = http.baseUrl("https://api-ecomm.gatling.io")
      .acceptHeader("application/json")
      .userAgentHeader("Gatling Java Test");

  private static final ScenarioBuilder scenario = scenario("GET Session API")
      .exec(
          http("Get Session")
              .get("/session")
              .check(status().is(200)));

  private static final Assertion assertion = global().failedRequests().count().is(0L);

  {
    setUp(
        scenario.injectOpen(atOnceUsers(vu)))
        .protocols(httpProtocol)
        .assertions(assertion);
  }
}
