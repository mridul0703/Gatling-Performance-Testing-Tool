/**
 * Test_A3_Feeder_GitHub_API.java
 *
 * Description:
 * This Gatling simulation tests the GitHub REST API (https://api.github.com) using a CSV feeder
 * that provides repository owner and repository name values. Each response is saved to a CSV file.
 *
 * API Used:
 * Base URL: https://api.github.com
 *
 * Endpoint Tested:
 * GET /repos/{owner}/{repo}
 *
 * Complete URL example (clickable in IDEs or browsers):
 * https://api.github.com/repos/octocat/Hello-World
 * (owner and repo are dynamically read from "data/github_repos.csv")
 *
 * Test Scenario:
 * - Scenario Name: GitHub API Simulation
 * - Step 1: Feed session with owner and repo from CSV (circular feeder)
 * - Step 2: GET /repos/{owner}/{repo} – checks that:
 *     • Status code is 200
 *     • JSON field "name" matches the repo name from feeder
 * - Step 3: Saves the response body to "target/Test_A3_Feeder_GitHub_API.csv"
 *
 * Feeder:
 * - CSV file: data/github_repos.csv
 * - Columns: owner, repo
 * - Circular feeder: repeats values when all rows are consumed
 *
 * Load Simulation:
 * - Number of Virtual Users (VUs): 3 (fixed)
 * - Injection profile: All users start at once (atOnceUsers)
 *
 * HTTP Protocol Configuration:
 * - Accept: application/vnd.github.v3+json
 * - User-Agent: Gatling-Java-Simulation
 *
 * Notes:
 * - Demonstrates API testing with a CSV feeder and JSON assertions.
 * - Saves each response to CSV for later analysis.
 * - Can be extended to multiple endpoints, dynamic feeders, or additional performance assertions.
 */


// owner,repo
// gatling,gatling
// microsoft,vscode
// octocat,Spoon-Knife
// facebook,react
// google,guava
// kubernetes,kubernetes
// apache,spark
// tensorflow,tensorflow
// twbs,bootstrap
// microsoft,terminal
// freeCodeCamp,freeCodeCamp
// ansible,ansible
// flutter,flutter
// nodejs,node
// python,cpython
// golang,go
// torvalds,linux
// django,django
// pallets,flask
// mrdoob,three.js





package example;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.io.FileWriter;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class Test_A3_Feeder_GitHub_API extends Simulation {

    HttpProtocolBuilder httpProtocol = http
        .baseUrl("https://api.github.com")
        .acceptHeader("application/vnd.github.v3+json")
        .userAgentHeader("Gatling-Java-Simulation");

    FeederBuilder.Batchable<String> repoFeeder = csv("data/github_repos.csv").circular();

    ScenarioBuilder scn = scenario("GitHub API Simulation")
        .feed(repoFeeder)
        .exec(http("Get a Repository Details")
            // .get("/repos/${owner}/${repo}")
            .get("/repos/#{owner}/#{repo}")
            .check(
                status().is(200),
                jsonPath("$.name").is(session -> session.getString("repo")),
                bodyString().saveAs("responseBody")
            )
        )
        .exec(session -> {
                String body = session.getString("responseBody");
                try (FileWriter fw = new FileWriter("target/Test_A3_Feeder_GitHub_API.csv", true)) {
                    fw.write("");
                    fw.write(body.replace("\n","") + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return session;
        });

    {
        setUp(
            scn.injectOpen(atOnceUsers(3))
        ).protocols(httpProtocol);
    }
}
