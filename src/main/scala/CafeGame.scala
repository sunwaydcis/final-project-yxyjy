import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import util.RandomGenerator
import model.{Customer, Order, Item}
import controller.{CustomerController, OrderController}

object CafeGame extends JFXApp3:

  override def start(): Unit =

    val item1 = RandomGenerator.menuItems(0)
    val item2 = RandomGenerator.menuItems(1)

    val order1 = Order(List(item1,item2), 0)
    println (order1)

    val preparedItems = List(List("espresso", "milk"),List("milk", "ice", "espresso"))
    println (preparedItems)

    OrderController().orderCorrect(preparedItems, order1)
    OrderController().orderExpired(order1)

    println(order1.orderStatus)

    println(order1.orderTotal)



end CafeGame
