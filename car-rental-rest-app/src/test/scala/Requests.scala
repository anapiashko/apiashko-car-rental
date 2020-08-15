package com.gatling.git

object Requests {

  val carsListByDate = exec(
    http("CarsListByDate")
      .get("/cars/filter/2020-01-01")
      .check(status.is(200))
  )

//  val productsItem = exec(
//    http("ProductsItem")
//      //    .get("/version/${id}")
//      .get("/version/1")
//      .check(status.is(200))
//  )
//
//  val updateProduct = exec(session =>
//    session.set("name", UUID.randomUUID().toString))
//    .exec(
//      http("UpdateProduct")
//        .put("/version/1")
//        .body(ElFileBody("products.json")).asJson
//        .check(status.is(200))
//    )

}
