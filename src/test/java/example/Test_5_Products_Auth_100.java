/**
 * -----------------------------------------------------------------------------
 * Test_5_Products_Auth_100
 * -----------------------------------------------------------------------------
 *
 * Description:
 * Gatling simulation that retrieves a paginated list of products from the
 * e-commerce API under load (100 virtual users) with an Authorization header.
 *
 * Overall API URL (example):
 * - https://api-ecomm.gatling.io/products
 *
 * API Details:
 * - Base URL: https://api-ecomm.gatling.io
 * - Endpoint:
 *   - GET /products
 * - Headers:
 *   - Authorization: Bearer my-token
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
 * - Validate the products endpoint when called with authentication
 * - Measure API behavior under concurrent authenticated requests
 * -----------------------------------------------------------------------------
 */



package example;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class Test_5_Products_Auth_100 extends Simulation {

  private static final int vu = Integer.getInteger("vu", 100);

  private static final HttpProtocolBuilder httpProtocol =
      http.baseUrl("https://api-ecomm.gatling.io")
          .acceptHeader("application/json")
          .contentTypeHeader("application/json")
          .userAgentHeader("Gatling Java Auth Test");

  private static final ScenarioBuilder scenario =
      scenario("Get Products With Authorization")
          .exec(
              http("Get Products")
                  .get("/products")
                  .header("Authorization", "Bearer my-token")
                  .check(status().is(200))
          );

  {
    setUp(
        scenario.injectOpen(atOnceUsers(vu))
    )
    .protocols(httpProtocol);
  }
}