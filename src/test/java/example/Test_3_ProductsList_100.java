/**
 * -----------------------------------------------------------------------------
 * Test_3_ProductsList_100
 * -----------------------------------------------------------------------------
 *
 * Description:
 * Gatling simulation that retrieves a paginated list of products from the
 * e-commerce API under higher concurrent load.
 *
 * Overall API URL (click to test manually):
 * - https://api-ecomm.gatling.io/products?page=1&size=10
 *
 * API Details:
 * - Base URL: https://api-ecomm.gatling.io
 * - Endpoint:
 *   - GET /products
 * - Query Parameters:
 *   - page = 1
 *   - size = 10
 *
 * Load Configuration:
 * - Virtual Users (VUs):
 *   - Default: 100 users
 *   - Can be overridden via JVM system property "vu"
 *     Example: mvn gatling:test -Dvu=150
 * - Injection Model:
 *   - All users are started at once (atOnceUsers)
 *
 * Validation:
 * - Verifies HTTP 200 response
 *
 * Purpose:
 * - Validate product listing API stability under concurrent load
 * - Identify performance degradation or failures at 100 VUs
 * -----------------------------------------------------------------------------
 */


package example;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class Test_3_ProductsList_100 extends Simulation {

    private static final int vu = Integer.getInteger("vu", 100);

    private static final HttpProtocolBuilder httpProtocol = http.baseUrl("https://api-ecomm.gatling.io")
            .acceptHeader("application/json")
            .contentTypeHeader("application/json")
            .userAgentHeader("Gatling Java Products Test");

    private static final ScenarioBuilder scenario = scenario("Get Products List Scenario")
            .exec(
                    http("Get Products")
                            .get("/products")
                            .queryParam("page", 1)
                            .queryParam("size", 10)
                            .check(status().is(200)));

    {
        setUp(
                scenario.injectOpen(atOnceUsers(vu)))
                .protocols(httpProtocol);
    }
}
