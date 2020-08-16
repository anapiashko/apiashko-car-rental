import scala.concurrent.duration._
import scala.language.postfixOps

class LoadTest extends Simulation {

  val httpConf = http.baseUrl("http://localhost:8088")

  val snc = scenario("LoadTest") randomSwitch(
    (40, carsListByDate),
    (40, carsItem),
    (10, updateCar),
    (10, createAndDeleteCar)
  )

  setUp(snc.inject(constantUsersPerSec(6) during (5 seconds)).protocols(httpConf))
}
