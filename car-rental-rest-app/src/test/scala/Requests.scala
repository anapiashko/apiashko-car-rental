

object Requests {

  val cars = jdbcFeeder("jdbc:mysql://localhost:3306/car_rental_dump", "root", "Nastya123",
    "SELECT id FROM car LIMIT 30")

  val carsListByDate = exec(
    http("CarsListByDate")
      .get("/cars/filter/2020-01-01")
      .check(status.is(200))
  )

  val carsItem = feed(cars).exec(
    http("CarsItem")
      .get("/cars/${id}")
      .check(status.is(200))
  )

  val updateCar = feed(cars)
//    .exec(session => {
//      session
//        .set("brand", UUID.randomUUID().toString)
//        .set("registerNumber", UUID.randomUUID().toString.substring(1, 10))
//        .set("price", 100)
//    })
//    .exec { session =>
//      println(session("brand").as[String])
//      println(session("registerNumber").as[String])
//      println(session("price").as[Int])
//      session
//    }
    .exec(
      http("UpdateCar")
        .put("/cars/${id}")
        .header("Content-Type", "application/json")
        .body(ElFileBody("car.json")).asJson
        .check(status.is(200))
    )

}
