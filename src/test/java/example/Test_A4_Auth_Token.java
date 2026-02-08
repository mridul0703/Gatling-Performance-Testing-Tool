/**
 * Test_A4_Auth_Token.java
 *
 * Description:
 * This Gatling simulation demonstrates an authentication workflow using a fake store API
 * (https://fakestoreapi.com). It logs in to retrieve a token and then uses that token
 * to call a protected endpoint (/products).
 *
 * API Used:
 * Base URL: https://fakestoreapi.com
 *
 * Endpoints Tested:
 * 1) POST /auth/login
 *    - Request body: { "username": "mor_2314", "password": "83r5^_" }
 *    - Response: JSON containing "token" field
 * 2) GET /products
 *    - Uses Authorization header: Bearer <token>
 *
 * Complete URLs (clickable in IDEs or browsers):
 * - Login: https://fakestoreapi.com/auth/login
 * - Products: https://fakestoreapi.com/products
 *
 * Test Scenario:
 * - Scenario Name: Login and Use Auth Token
 * - Step 1: POST /auth/login – retrieves auth token, checks status 200 or 201, saves token in session
 * - Step 2: Prints auth token to console
 * - Step 3: GET /products – adds Authorization header using token, checks status 200
 * - Step 4: Pause for 1 second
 *
 * Load Simulation:
 * - Number of Virtual Users (VUs): 100 (fixed)
 * - Injection profile: All users start at once (atOnceUsers)
 *
 * HTTP Protocol Configuration:
 * - Accept: application/json
 * - Content-Type: application/json
 *
 * Notes:
 * - Demonstrates using a login token for authenticated requests.
 * - Can be extended to handle multiple users, dynamic tokens, or multiple protected endpoints.
 * - Currently, the Authorization header in the GET request uses a placeholder token "#fgdrggdggr";
 *   replace it with the session token for proper authenticated testing:
 *       .header("Authorization", "Bearer ${authToken}")
 */


package example;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class Test_A4_Auth_Token extends Simulation {

  private static final HttpProtocolBuilder httpProtocol =
      http.baseUrl("https://fakestoreapi.com")
          .acceptHeader("application/json")
          .contentTypeHeader("application/json");

  private static final ScenarioBuilder authScenario =
      scenario("Login and Use Auth Token")
          .exec(
              http("Login to get token")
                  .post("/auth/login")
                  .body(StringBody(
                      "{ \"username\": \"mor_2314\", " +
                      "\"password\": \"83r5^_\" }"
                  )).asJson()
                  .check(status().in(200, 201))
                  .check(jsonPath("$.token").saveAs("authToken"))
          )
          .exec(session -> {
              System.out.println(
                  "AUTH TOKEN => " + session.getString("authToken")
              );
              return session;
          })
          .exec(
              http("Call API with token")
                  .get("/products")
                  .header("Authorization", "Bearer #fgdrggdggr")
                  .check(status().is(200))
          )

          .pause(1);

  {
    setUp(
        authScenario.injectOpen(atOnceUsers(100))
    ).protocols(httpProtocol);
  }
}
