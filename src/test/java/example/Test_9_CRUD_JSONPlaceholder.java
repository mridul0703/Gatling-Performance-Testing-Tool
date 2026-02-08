/**
 * Test_9_CRUD_JSONPlaceholder.java
 *
 * Description:
 * This Gatling simulation performs CRUD (Create, Read, Update, Delete) operations
 * against the JSONPlaceholder API (https://jsonplaceholder.typicode.com), which is
 * a free fake REST API for testing and prototyping.
 *
 * API Used:
 * Base URL: https://jsonplaceholder.typicode.com
 *
 * Endpoints (examples included):
 * - GET /posts           → Retrieve all posts (active in this simulation)
 * - POST /posts          → Create a new post (commented example)
 * - PUT /posts/1         → Update an existing post (commented example)
 * - PATCH /posts/1       → Update post fields partially (commented example)
 * - DELETE /posts/1      → Delete a post (commented example)
 *
 * Complete URL (clickable in IDEs or browsers):
 * https://jsonplaceholder.typicode.com/posts
 *
 * Test Scenario:
 * - Scenario Name: CRUD Simulation on JSONPlaceholder
 * - Currently active: GET /posts with status check 200
 * - Other CRUD operations can be uncommented for full simulation
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
 * - User-Agent: Gatling Java JSONPlaceholder Test
 *
 * Notes:
 * - This simulation is ideal for testing CRUD operations on a REST API.
 * - Can be extended to include dynamic request bodies, additional endpoints, or performance assertions.
 */

package example;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class Test_9_CRUD_JSONPlaceholder extends Simulation {

    private static final int vu = Integer.getInteger("vu", 100);

    private static final HttpProtocolBuilder httpProtocol = http.baseUrl("https://jsonplaceholder.typicode.com")
            .acceptHeader("application/json")
            .contentTypeHeader("application/json")
            .userAgentHeader("Gatling Java JSONPlaceholder Test");

    private static final ScenarioBuilder scenario = scenario("CRUD Simulation on JSONPlaceholder")
            // ====== POST request example ======
        //     .exec(
        //            http("POST Create Post")
        //                    .post("/posts")
        //                    .body(StringBody("{\"title\": \"foo\", \"body\": \"bar\", \"userId\": 1}"))
        //                    .check(status().is(201))
        //     );

            // ====== PUT request example ======
        //     .exec(
        //            http("PUT Update Post")
        //                    .put("/posts/1")
        //                    .body(StringBody("{\"id\": 1, \"title\": \"updated foo\", \"body\": \"updated bar\", \"userId\": 1}"))
        //                    .check(status().is(200))
        //     );

            // ====== PATCH request example ======
            //.exec(
            //        http("PATCH Update Post Title")
            //                .patch("/posts/1")
            //                .body(StringBody("{\"title\": \"patched foo\"}"))
            //                .check(status().is(200))
            //);

            // ====== DELETE request example ======
        //     .exec(
        //            http("DELETE Post")
        //                    .delete("/posts/1")
        //                    .check(status().is(200))
        //     );

            // ====== GET request example ======
            .exec(
                    http("GET Posts")
                            .get("/posts")
                            .check(status().is(200))
            );

    {
        setUp(
                scenario.injectOpen(atOnceUsers(vu))
        ).protocols(httpProtocol);
    }
}
