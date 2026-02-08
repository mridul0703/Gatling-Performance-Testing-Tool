/**
 * BasicSimulation10.java
 *
 * Description:
 * This is a Gatling performance test simulation written in Java.
 * It tests the "GET /session" endpoint of an example e-commerce API.
 *
 * API Used:
 * Base URL: https://api-ecomm.gatling.io
 *
 * Endpoint Tested:
 * GET /session
 *
 * Complete URL (clickable in IDEs or some browsers):
 * https://api-ecomm.gatling.io/session
 *
 * Test Scenario:
 * - Scenario Name: GET Session API
 * - Performs a single GET request to retrieve session information
 * - Checks that the response status is 200 OK
 *
 * Load Simulation:
 * - Number of Virtual Users (VUs): Configurable via system property "vu"
 *   Default: 10 users
 *   Example to run with 20 users:
 *     mvn gatling:test -Dvu=20
 * - Injection profile: All users start at once (atOnceUsers)
 *
 * HTTP Protocol Configuration:
 * - Accept: application/json
 * - User-Agent: Gatling Java Test
 *
 * Assertions:
 * - Active Assertion: Total number of failed requests must be 0
 * - Other optional assertions (currently commented out):
 *   * Ensure all requests are successful (status 2xx)
 *   * Max response time < 2 seconds
 *   * Mean response time < 500 ms
 *   * 95th percentile response time < 1 second
 *   * At least 90% of requests respond in under 800 ms
 *
 * Notes:
 * - This is a simple template to test a single GET endpoint with configurable load.
 * - Can be extended for multiple endpoints, more complex scenarios, or advanced assertions.
 */

package example;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class BasicSimulation10 extends Simulation {

  private static final int vu = Integer.getInteger("vu", 10);

  private static final HttpProtocolBuilder httpProtocol = http.baseUrl("https://api-ecomm.gatling.io")
      .acceptHeader("application/json")
      .userAgentHeader("Gatling Java Test");

  private static final ScenarioBuilder scenario = scenario("GET Session API")
      .exec(
          http("Get Session")
              .get("/session")
              .check(status().is(200)));

  private static final Assertion assertion = global().failedRequests().count().is(0L);

  // Other useful assertions you can enable by uncommenting:
  // Assert that all requests are successful (status 2xx)
  // private static final Assertion assertion = global().successfulRequests().percent().is(100.0);

  // Assert that max response time is below 2 seconds
  // private static final Assertion assertion = global().responseTime().max().lt(2000);

  // Assert that mean response time is below 500 ms
  // private static final Assertion assertion = global().responseTime().mean().lt(500);

  // Assert that 95th percentile response time is below 1 second
  // private static final Assertion assertion = global().responseTime().percentile3().lt(1000);

  // Assert that at least 90% of requests have response time under 800 ms
  // private static final Assertion assertion = global().responseTime().percentile1().lt(800);

  {
    setUp(
        scenario.injectOpen(atOnceUsers(vu)))
        .protocols(httpProtocol)
        .assertions(assertion);
  }
}
