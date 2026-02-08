/**
 * Test_A5_Spike_Test.java
 *
 * Description:
 * This Gatling simulation performs a spike test against the FakeStore API 
 * (https://fakestoreapi.com/products). It simulates sudden traffic spikes to observe
 * system behavior under stress and then a recovery phase.
 *
 * API Used:
 * Base URL: https://fakestoreapi.com
 *
 * Endpoint Tested:
 * GET /products
 *
 * Complete URL (clickable in IDEs or browsers):
 * https://fakestoreapi.com/products
 *
 * Test Scenario:
 * - Scenario Name: Spike Test Scenario
 * - Step 1: GET /products – checks that response status is 200
 * - Step 2: Pause 1 second
 *
 * Load Simulation:
 * - Warm-up: 10 users at once
 * - Steady state: wait 5 seconds
 * - Spike: sudden jump to 500 users at once
 * - Hold spike: wait 10 seconds
 * - Recovery: drop back to 10 users at once
 *
 * HTTP Protocol Configuration:
 * - Accept: application/json
 * - User-Agent: Gatling Spike Test
 *
 * Assertions:
 * - Failed requests must be less than 5% globally
 * - 95th percentile response time must be below 2000 ms
 *
 * Notes:
 * - Demonstrates a spike test to evaluate system stability under sudden load.
 * - Can be extended with additional endpoints, more complex traffic patterns, or dynamic pauses.
 */

package example;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class Test_A5_Spike_Test extends Simulation {

  private static final HttpProtocolBuilder httpProtocol =
      http.baseUrl("https://fakestoreapi.com")
          .acceptHeader("application/json")
          .userAgentHeader("Gatling Spike Test");

  private static final ScenarioBuilder spikeScenario =
      scenario("Spike Test Scenario")
          .exec(
              http("Get Products")
                  .get("/products")
                  .check(status().is(200))
          )
          .pause(1);

  {
    setUp(
        spikeScenario.injectOpen(
            // Warm-up: 10 users
            atOnceUsers(10),

            // Short steady state before spike
            nothingFor(5),

            // Spike: sudden jump to 500 users
            atOnceUsers(500),

            // Hold spike traffic briefly
            nothingFor(10),

            // Recovery: drop back to 10 users
            atOnceUsers(10)
        )
    )
    .protocols(httpProtocol)
    .assertions(
        global().failedRequests().percent().lt(5.0),
        global().responseTime().percentile(95).lt(2000)
    );
  }
}
