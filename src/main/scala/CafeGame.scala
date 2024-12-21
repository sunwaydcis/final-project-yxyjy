import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import util.RandomGenerator
import model.{Customer, Order, Item}
import controller.CustomerController

object CafeGame extends JFXApp3:

  override def start(): Unit =
    stage = new PrimaryStage()

    val o1 = RandomGenerator.generateRandomOrder()
    val c1 = RandomGenerator.generateRandomCustomer()
    println(o1)
    println(o1.orderTotal())
    println("First Customer")
    println(c1)
    println(c1.payment)

end CafeGame
