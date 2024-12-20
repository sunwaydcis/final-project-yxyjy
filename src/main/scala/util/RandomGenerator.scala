import scala.util.Random
import model.{Customer, Item, Order}
import model.orderStatus.inProgress
import model.satisfaction.happy

object RandomGenerator:

  //menu items
  val menuItems: List[Item] = List(
    Item ("Latte", 6.00),
    Item ("Cappuccino", 6.00),
    Item ("Americano", 5.00),
    Item ("Banana Latte", 7.00),
    Item ("Mocha", 7.00),
    Item ("Pistachio Cake", 12.00),
    Item ("Strawberry Shortcake", 12.00),
    Item ("Croissant", 8.00),
    Item ("Tiramisu", 11.00)
  )

  val customerNames: List[String] = List(
    "Neria",
    "Genie",
    "BaekGom",
    "Sekita",
    "Andy",
    "Marie",
    "Chang"
  )

  //generate random menu item
  def generateRandomMenuItem(): Item =
    menuItems(Random.nextInt(menuItems.size))

  //generate random order
  def generateRandomOrder(): Order=
    val item1 = generateRandomMenuItem()
    val item2 = generateRandomMenuItem()
    Order(inProgress, List(item1, item2), 20)

  //generate random customer name
  def generateRandomCustomerName(): String =
    customerNames(Random.nextInt(customerNames.size))

  //generate random customer
  def generateRandomCustomer(): Customer=
    val name = generateRandomCustomerName()
    val order = generateRandomOrder()
    Customer(name, order, happy)
