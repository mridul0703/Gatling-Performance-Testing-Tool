/**
 * -----------------------------------------------------------------------------
 * Test_7_PauseRequest
 * -----------------------------------------------------------------------------
 *
 * Description:
 * Gatling simulation that performs two sequential GET requests to the products
 * endpoint with a random pause between 1 and 5 seconds.
 *
 * Overall API URL (clickable example):
 * - https://api-ecomm.gatling.io/products
 *
 * API Details:
 * - Base URL: https://api-ecomm.gatling.io
 * - Endpoint:
 *   - GET /products
 *
 * Load Configuration:
 * - Virtual Users (VUs):
 *   - Default: 50 users
 *   - Can be overridden via JVM system property "vu"
 *     Example: mvn gatling:test -Dvu=100
 * - Injection Model:
 *   - All users start at once (atOnceUsers)
 *
 * Scenario Behavior:
 * - First GET /products request
 * - Random pause between 1 and 5 seconds
 * - Second GET /products request
 *
 * Validation:
 * - Ensures HTTP 200 response for both requests
 *
 * Purpose:
 * - Simulate user behavior with delays between requests
 * - Validate API stability under concurrent load with pauses
 * -----------------------------------------------------------------------------
 */


package example;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class Test_7_PauseRequest extends Simulation {

    private static final int vu = Integer.getInteger("vu", 50);

    private static final HttpProtocolBuilder httpProtocol = http.baseUrl("https://api-ecomm.gatling.io")
            .acceptHeader("application/json");

    private static final ScenarioBuilder scenario = scenario("Single GET Request with Pause")
            .exec(
                http("Get Products")
                    .get("/products")
                    .check(status().is(200))
            )
            .pause(1, 5)
            .exec(
                http("Get Products")
                    .get("/products")
                    .check(status().is(200))
            );

    {
        setUp(
            scenario.injectOpen(atOnceUsers(vu))
        ).protocols(httpProtocol);
    }
}
