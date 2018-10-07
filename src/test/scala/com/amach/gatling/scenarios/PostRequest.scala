package com.amach.gatling.scenarios

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object PostRequest {

  val requestString = "{\"clientId\":1,\"name\":\"Pizza\",\"quantity\":33,\"price\":12.00}"

  val postRequestHttp: HttpRequestBuilder = http("post request")
    .post("/api/requests")
    .body(StringBody(requestString))
    .header("Content-Type", "application/json")
    .check(status is 200)

  val postRequest: ScenarioBuilder = scenario("post request")
    .exec(postRequestHttp)
}
