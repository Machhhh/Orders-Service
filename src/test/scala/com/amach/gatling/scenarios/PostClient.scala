package com.amach.gatling.scenarios

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object PostClient {

  val clientString = "{\"clientId\":1,\"name\":\"Popey\"}"

  val clientRequestHttp: HttpRequestBuilder = http("post client")
    .post("/api/clients")
    .body(StringBody(clientString))
    .header("Content-Type", "application/json")
    .check(status is 200)

  val postClient: ScenarioBuilder = scenario("post client")
    .exec(clientRequestHttp)
}
