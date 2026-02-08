/**
 * Test_A6_Random_Switch.java
 *
 * Description:
 * This Gatling simulation generates random traffic to the FakeStore API 
 * (https://fakestoreapi.com) using a randomSwitch. It simulates realistic traffic
 * by splitting requests between different endpoints according to defined probabilities.
 *
 * API Used:
 * Base URL: https://fakestoreapi.com
 *
 * Endpoints Tested:
 * 1) GET /products           (80% of requests)
 * 2) GET /products/categories (20% of requests)
 *
 * Complete URLs (clickable in IDEs or browsers):
 * - Products: https://fakestoreapi.com/products
 * - Categories: https://fakestoreapi.com/products/categories
 *
 * Test Scenario:
 * - Scenario Name: Random Traffic Scenario
 * - Step 1: Randomly select which request to execute:
 *     • 80% chance → GET /products
 *     • 20% chance → GET /products/categories
 * - Step 2: Pause 1 second between requests
 *
 * Load Simulation:
 * - Number of Virtual Users (VUs): Configurable via system property "vu"
 *   Default: 100 users
 * - Injection profile: All users start at once (atOnceUsers)
 *
 * HTTP Protocol Configuration:
 * - Accept: application/json
 *
 * Notes:
 * - Demonstrates random traffic patterns using randomSwitch().
 * - Can be extended to additional endpoints or probability weights.
 * - Useful for simulating mixed traffic in performance testing.
 */

package example;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class Test_A6_Random_Switch extends Simulation {

    private static final int vu = Integer.getInteger("vu", 100);

    private static final HttpProtocolBuilder httpProtocol =
        http.baseUrl("https://fakestoreapi.com")
            .acceptHeader("application/json");

    private static final ScenarioBuilder randomScenario =
        scenario("Random Traffic Scenario")
            .exec(
                randomSwitch().on(
                    percent(80.0).then(exec(http("Get Products").get("/products"))),
                    percent(20.0).then(exec(http("Get Categories").get("/products/categories")))
                )
            )
            .pause(1);

    {
        setUp(randomScenario.injectOpen(atOnceUsers(vu))).protocols(httpProtocol);
    }
}