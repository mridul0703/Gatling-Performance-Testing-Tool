/**
 * Test_4_ProductsList_Response_100.java
 *
 * Description:
 * This Gatling simulation tests the "GET /products" endpoint of an example e-commerce API.
 * It retrieves a paginated list of products, validates that the HTTP response status is 200,
 * and prints the response body to the console.
 *
 * API Used:
 * Base URL: https://api-ecomm.gatling.io
 *
 * Endpoint Tested:
 * GET /products
 *
 * Complete URL with query parameters (clickable in IDEs or browsers):
 * https://api-ecomm.gatling.io/products?page=1&size={productsCount}
 *   - 'productsCount' is configurable via system property (default = 3)
 *   Example to request 5 products: 
 *     mvn gatling:test -DproductsCount=5
 *
 * Test Scenario:
 * - Scenario Name: Get Products List Scenario
 * - Executes a GET request to retrieve a list of products
 * - Query parameters:
 *     * page = 1
 *     * size = productsCount (variable)
 * - Checks that HTTP response status is 200 OK
 * - Saves the response body as a session variable and prints it to the console
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
 * - Content-Type: application/json
 * - User-Agent: Gatling Java Products Test
 *
 * Notes:
 * - This simulation demonstrates response extraction and logging in Gatling.
 * - Can be extended to validate response content, test multiple pages, or add performance assertions.
 */

package example;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class Test_4_ProductsList_Response_100 extends Simulation {

    private static final int vu = Integer.getInteger("vu", 100);

    private static final int productsCount = Integer.getInteger("productsCount", 3);

    private static final HttpProtocolBuilder httpProtocol = http.baseUrl("https://api-ecomm.gatling.io")
            .acceptHeader("application/json")
            .contentTypeHeader("application/json")
            .userAgentHeader("Gatling Java Products Test");

    private static final ScenarioBuilder scenario = scenario("Get Products List Scenario")
            .exec(
                    http("Get Products")
                            .get("/products")
                            .queryParam("page", 1)
                            .queryParam("size", productsCount)
                            .check(status().is(200))
                            .check(bodyString().saveAs("responseBody"))
            )
            .exec(session -> {
                System.out.println("Response Body: " + session.getString("responseBody"));
                return session;
            });

    {
        setUp(
                scenario.injectOpen(atOnceUsers(vu))
        ).protocols(httpProtocol);
    }
}
