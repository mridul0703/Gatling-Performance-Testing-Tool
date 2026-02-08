/**
 * Test_8_Triple_Requests.java
 *
 * Description:
 * This Gatling simulation tests three sequential endpoints of an example e-commerce API:
 * 1) GET /session – retrieves session information
 * 2) GET /products?page=0 – retrieves the first page of products (page 0)
 * 3) GET /products?page=1 – retrieves the second page of products (page 1)
 * Includes pauses: 2 seconds after the first request and a random 1–5 seconds after the second.
 *
 * API Used:
 * Base URL: https://api-ecomm.gatling.io
 *
 * Endpoints Tested:
 * 1) GET /session
 *    Complete URL: https://api-ecomm.gatling.io/session
 * 2) GET /products?page=0
 *    Complete URL: https://api-ecomm.gatling.io/products?page=0
 * 3) GET /products?page=1
 *    Complete URL: https://api-ecomm.gatling.io/products?page=1
 *
 * Test Scenario:
 * - Scenario Name: Three Different Requests Scenario
 * - Step 1: GET /session – checks that response status is 200
 * - Pause: 2 seconds
 * - Step 2: GET /products?page=0 – checks that response status is 200
 * - Pause: random 1–5 seconds
 * - Step 3: GET /products?page=1 – checks that response status is 200
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
 * - Demonstrates sequential requests with both fixed and random pauses.
 * - Can be extended to include additional pages, endpoints, or performance assertions.
 */

package example;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class Test_8_Triple_Requests extends Simulation {

    private static final int vu = Integer.getInteger("vu", 10);

    private static final HttpProtocolBuilder httpProtocol = http.baseUrl("https://api-ecomm.gatling.io")
            .acceptHeader("application/json")
            .userAgentHeader("Gatling Java Simple Test");

    private static final ScenarioBuilder scenario = scenario("Three Different Requests Scenario")
            .exec(
                    http("GET Session")
                            .get("/session")
                            .check(status().is(200))
            )
            .pause(2) 
            .exec(
                    http("GET Products Page 0")
                            .get("/products")
                            .queryParam("page", 0)
                            .check(status().is(200))
            )
            .pause(1, 5)
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
