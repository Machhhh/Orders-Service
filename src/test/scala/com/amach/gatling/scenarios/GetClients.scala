package com.amach.gatling.scenarios

import io.gatling.core.Predef.{scenario, _}
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.{http, status}
import io.gatling.http.request.builder.HttpRequestBuilder

object GetClients {

  val getClientsHttp: HttpRequestBuilder = http("Get all clients")
    .get("/api/clients")
    .check(status is 200)

  val getClients: ScenarioBuilder = scenario("Get all clients")
    .exec(getClientsHttp)
}
