/**
 * Test_A1_CSV_Response.java
 *
 * Description:
 * This Gatling simulation performs a GET request to the "/products?page=1" endpoint
 * of an example e-commerce API and saves the response body to a CSV file.
 * The simulation includes a random pause between requests.
 *
 * API Used:
 * Base URL: https://api-ecomm.gatling.io
 *
 * Endpoint Tested:
 * GET /products?page=1
 *
 * Complete URL (clickable in IDEs or browsers):
 * https://api-ecomm.gatling.io/products?page=1
 *
 * Test Scenario:
 * - Scenario Name: Simple GET + CSV Scenario
 * - Step 1: GET /products?page=1 – checks that response status is 200
 * - Step 2: Saves the response body to "target/Test_A1_CSV_Response.csv"
 * - Pause: random 1–5 seconds
 *
 * Load Simulation:
 * - Number of Virtual Users (VUs): 100 (fixed)
 * - Injection profile: All users start at once (atOnceUsers)
 *
 * HTTP Protocol Configuration:
 * - Accept: application/json
 * - User-Agent: Gatling Simple CSV Test
 *
 * Notes:
 * - Demonstrates saving API responses to a CSV file for later analysis.
 * - Can be extended to handle multiple pages, additional endpoints, or more complex CSV formatting.
 */

package example;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.io.FileWriter;

public class Test_A1_CSV_Response extends Simulation {

    private static final HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://api-ecomm.gatling.io")
            .acceptHeader("application/json")
            .userAgentHeader("Gatling Simple CSV Test");

    private static final int vu = 100;

    private static final ScenarioBuilder scenario = scenario("Simple GET + CSV Scenario")
            .exec(
                http("Get Products Page 1")
                        .get("/products?page=1")
                        .check(status().is(200))
                        .check(bodyString().saveAs("responseBody"))
            )
            .pause(1, 5) 
            .exec(session -> {
                String body = session.getString("responseBody");
                try (FileWriter fw = new FileWriter("target/Test_A1_CSV_Response.csv", true)) {
                    fw.write("");
                    fw.write(body.replace("\n","") + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return session;
            });

    {
        setUp(scenario.injectOpen(atOnceUsers(vu)))
                .protocols(httpProtocol);
    }
}
