/**
 * -----------------------------------------------------------------------------
 * Test_A2_Feeder_FakeStore
 * -----------------------------------------------------------------------------
 *
 * Description:
 * Gatling simulation demonstrating data-driven testing using a CSV feeder.
 * - Reads product IDs from a CSV file ("products.csv")
 * - Fetches each product by ID from the FakeStore API
 * - Prints the fetched product response for inspection
 *
 * Overall API URL (clickable example):
 * - GET /products/{productId}: https://fakestoreapi.com/products/{productId}
 *
 * API Details:
 * - Base URL: https://fakestoreapi.com
 * - Endpoint:
 *   - GET /products/{productId}
 * - Query Parameters: None
 * - Headers:
 *   - Accept: application/json
 *   - Content-Type: application/json
 *
 * Load Configuration:
 * - Virtual Users (VUs):
 *   - Default: 5 users
 *   - Can be overridden via JVM system property "vu"
 *     Example: mvn gatling:test -Dvu=10
 * - Injection Model:
 *   - All users start at once (atOnceUsers)
 *
 * Feeder Behavior:
 * - Reads product IDs from "products.csv" (column name: productId)
 * - Uses a circular feeder, repeating data if VUs exceed rows
 * - Dynamically injects productId into each request
 *
 * Scenario Behavior:
 * 1. Feeds a productId from the CSV
 * 2. Logs the productId to console
 * 3. Executes GET /products/{productId}
 * 4. Saves and prints the JSON response
 *
 * Purpose:
 * - Demonstrate CSV-based data-driven testing
 * - Validate API responses for multiple products under concurrent load
 * - Serve as a template for other data-driven API tests
 * -----------------------------------------------------------------------------
 */


// productId
// 1
// 2
// 3
// 4
// 5
// 6
// 7
// 8
// 9
// 10


package example;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.io.FileWriter;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class Test_A2_Feeder_FakeStore extends Simulation {

    private static final int vu = Integer.getInteger("vu", 15);

    private static final HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://fakestoreapi.com")
            .acceptHeader("application/json")
            .contentTypeHeader("application/json")
            .userAgentHeader("Gatling/PerformanceTest");

    private static final FeederBuilder<String> feeder = csv("data/products.csv").circular();

    private static final ScenarioBuilder scenario = scenario("FakeStore Feeder Scenario")
            .feed(feeder)
            .exec(
                http("GET Product by ID")
                    .get("/products/#{productId}")
                    // .get("/products/${productId}")
                    .check(status().is(200))
                    .check(bodyString().saveAs("responseBody"))
            )
            .exec(session -> {
                String body = session.getString("responseBody");
                try (FileWriter fw = new FileWriter("target/Test_A2_Feeder_FakeStore.csv", true)) {
                    fw.write("");
                    fw.write(body.replace("\n","") + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return session;
            });

    {
        setUp(
            scenario.injectOpen(atOnceUsers(vu))
        ).protocols(httpProtocol);
    }
}