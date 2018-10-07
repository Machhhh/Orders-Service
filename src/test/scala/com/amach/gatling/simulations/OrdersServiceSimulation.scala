package com.amach.gatling.simulations
import com.amach.gatling.scenarios.GetRequests._
import com.amach.gatling.scenarios.PostRequest._
import com.amach.gatling.util.Environemnt._
import com.amach.gatling.util.Headers._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.config.HttpProtocolBuilder

import scala.concurrent.duration._

/**
  * Gatling load test that sends HTTP requests to the same URL.
  */
class OrdersServiceSimulation extends Simulation {

  val httpConf: HttpProtocolBuilder = http.baseURL(baseURL)
    .headers(commonHeader)


  val scenario = List(
/*    getClients.inject(
      atOnceUsers(1),
      rampUsersPerSec(1) to 100 during (30 seconds)),*/
    /*    PostAndGetRequest.postAndGetRequest.inject(atOnceUsers(1)),*/
    getRequests.inject(
      atOnceUsers(1),
      //      rampUsers(100) over(1 seconds),
      //constantUsersPerSec(1000) during(15 seconds)
      rampUsersPerSec(1) to 100 during (30 seconds)
      //rampUsersPerSec(10) to 20 during(10 minutes) randomized,
      //splitUsers(1000) into(rampUsers(10) over(10 seconds)) separatedBy(10 seconds),
      //splitUsers(1000) into(rampUsers(10) over(10 seconds)) separatedBy atOnceUsers(30),
      //heavisideUsers(1000) over(20 seconds)
    ),
/*
    postClient
      .inject(atOnceUsers(users.toInt))
      .throttle(reachRps(600) in (20 seconds),
        holdFor(60 seconds)),
*/

    postRequest
      .inject(atOnceUsers(users.toInt))
      .throttle(reachRps(600) in (30 seconds),
        holdFor(60 seconds))
    /*    GetRequestByName.getRequestByName.inject(
          atOnceUsers(1),
          constantUsersPerSec(300) during (15 seconds)
        )*/
  )

  setUp(scenario)
    .protocols(httpConf)
    .maxDuration(2 minutes)
    .assertions(
      global.responseTime.max.lessThan(maxResponseTime.toInt)
    )
}
