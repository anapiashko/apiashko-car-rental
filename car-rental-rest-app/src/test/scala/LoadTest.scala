import scala.concurrent.duration._
import scala.language.postfixOps

class LoadTest extends Simulation {

  val httpConf = http.baseUrl("http://localhost:8088")

 // val snc = scenario("LoadTest").exec(productsList).exec(productsItem)

  val snc = scenario("LoadTest") randomSwitch(
    (50, carsListByDate),
    (40, carsItem),
    (10, updateCar)
  )

  setUp(snc.inject(constantUsersPerSec(6) during(5 seconds)).protocols(httpConf))
}
