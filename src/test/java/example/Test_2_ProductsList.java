/**
 * Test_2_ProductsList.java
 *
 * Description:
 * This Gatling simulation tests the "GET /products" endpoint of an example e-commerce API.
 * It retrieves a paginated list of products and validates that the HTTP response is successful (status 200).
 *
 * API Used:
 * Base URL: https://api-ecomm.gatling.io
 *
 * Endpoint Tested:
 * GET /products
 *
 * Complete URL with query parameters (clickable in IDEs or browsers):
 * https://api-ecomm.gatling.io/products?page=1&size=10
 *
 * Test Scenario:
 * - Scenario Name: Get Products List Scenario
 * - Executes a GET request to retrieve a list of products
 * - Query parameters:
 *     * page = 1
 *     * size = 10
 * - Validates that the HTTP response status is 200 OK
 *
 * Load Simulation:
 * - Number of Virtual Users (VUs): Configurable via system property "vu"
 *   Default: 1 user
 *   Example to run with 5 users:
 *     mvn gatling:test -Dvu=5
 * - Injection profile: All users start at once (atOnceUsers)
 *
 * HTTP Protocol Configuration:
 * - Accept: application/json
 * - Content-Type: application/json
 * - User-Agent: Gatling Java Products Test
 *
 * Notes:
 * - This simulation focuses on a simple GET request with pagination.
 * - Can be extended to include multiple pages, additional endpoints, or performance assertions.
 */

package example;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class Test_2_ProductsList extends Simulation {

    private static final int vu = Integer.getInteger("vu", 1);

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
