package com.amach.gatling.util

object Environemnt {
  val baseURL: String = scala.util.Properties.envOrElse("baseURL", "http://localhost:8090/")
  val users: String = scala.util.Properties.envOrElse("numberOfUsers", "1000")
  val maxResponseTime: String = scala.util.Properties.envOrElse("maxResponseTime", "6000")
}
