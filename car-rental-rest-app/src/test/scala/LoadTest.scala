package com.gatling.git

import scala.concurrent.duration._

class LoadTest extends Simulation {

  val httpConf = http.baseUrl("http://localhost:8088")

 // val snc = scenario("LoadTest").exec(productsList).exec(productsItem)

  val snc = scenario("LoadTest") randomSwitch(
    (100, carsListByDate)
//    (45, productsItem),
//    (5, updateProduct)
  )

  setUp(snc.inject(constantUsersPerSec(10) during(5 seconds)).protocols(httpConf))
}
