package com.amach.gatling.scenarios

import io.gatling.core.Predef.{scenario, _}
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.{http, status}
import io.gatling.http.request.builder.HttpRequestBuilder

object GetRequests {

  val getRequestsHttp: HttpRequestBuilder = http("Get all requests")
    .get("/api/requests")
    .check(status is 200)

  val getRequests: ScenarioBuilder = scenario("Get all requests")
    .exec(getRequestsHttp)
}
