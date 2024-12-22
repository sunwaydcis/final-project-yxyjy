import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import util.RandomGenerator
import model.{Customer, Order, Item}
import controller.{CustomerController, OrderController, GameController}

object CafeGame extends JFXApp3:

  override def start(): Unit =

    val item1 = RandomGenerator.menuItems.head
    val item2 = RandomGenerator.menuItems(1)

    val order1 = Order(List(item1,item2))
    println (order1)

    val preparedItems = List(List("espresso", "milk"),List("milk", "ice", "espresso"))
    println (preparedItems)

    OrderController().orderCorrect(preparedItems, order1)
    OrderController().orderExpired(order1)

    val gameCtrl = new GameController()

    println ("Customers of the day")

    var totalCustomerList: List[Customer] = List()
    for i <- 1 to 20 do
      totalCustomerList = totalCustomerList :+ RandomGenerator.generateRandomCustomer()

    for i <- 0 to 19 do
      println(totalCustomerList(i))




end CafeGame
