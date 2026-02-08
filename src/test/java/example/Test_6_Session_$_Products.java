/**
 * Test_6_Session_$_Products.java
 *
 * Description:
 * This Gatling simulation tests two endpoints of an example e-commerce API in sequence:
 * 1) GET /session – retrieves session information
 * 2) GET /products?page=1 – retrieves the first page of products
 * The simulation includes a 2-second pause between the two requests.
 *
 * API Used:
 * Base URL: https://api-ecomm.gatling.io
 *
 * Endpoints Tested:
 * 1) GET /session
 *    Complete URL: https://api-ecomm.gatling.io/session
 * 2) GET /products?page=1
 *    Complete URL: https://api-ecomm.gatling.io/products?page=1
 *
 * Test Scenario:
 * - Scenario Name: Session and Products Scenario
 * - Step 1: GET /session – checks that response status is 200
 * - Pause: 2 seconds
 * - Step 2: GET /products?page=1 – checks that response status is 200
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
 * - User-Agent: Gatling Java Simple Test
 *
 * Notes:
 * - This simulation demonstrates sequential requests with a pause in between.
 * - Can be extended to include additional endpoints, dynamic query parameters, or performance assertions.
 */

package example;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class Test_6_Session_$_Products extends Simulation {

    private static final int vu = Integer.getInteger("vu", 10);

    private static final HttpProtocolBuilder httpProtocol = http.baseUrl("https://api-ecomm.gatling.io")
            .acceptHeader("application/json")
            .userAgentHeader("Gatling Java Simple Test");

    private static final ScenarioBuilder scenario = scenario("Session and Products Scenario")
           
            .exec(
                    http("GET Session")
                            .get("/session")
                            .check(status().is(200))
            )
            .pause(2)
            .exec(
                    http("GET Products Page 1")
                            .get("/products")
                            .queryParam("page", 1)
                            .check(status().is(200))
            );

    {
        setUp(
                scenario.injectOpen(atOnceUsers(vu))
        ).protocols(httpProtocol);
    }
}
