package cafe.util

import cafe.model.{Customer, Item, Order}
import scala.util.Random

object RandomGenerator:

  //menu items
  val menuItems: List[Item] = List(
    Item ("Latte", 6.00, List("espresso", "milk")),
    Item ("Iced Latte", 6.50, List("espresso", "milk", "ice")),
    Item ("Espresso", 4.00, List("espresso")),
    Item ("Americano", 5.00, List("espresso", "water")),
    Item ("Iced Americano", 5.50, List("espresso", "water","ice")),
    Item ("Mocha", 7.00, List("espresso", "milk", "chocolatesyrup")),
    Item ("Iced Mocha", 7.50, List("espresso", "milk", "chocolatesyrup", "ice")),
    Item ("Matcha Latte", 8.00, List("espresso", "milk", "matchapowder")),
    Item ("Matcha Latte", 8.50, List("espresso", "milk", "matchapowder", "ice")),
    Item ("Pistachio Cake", 12.00,List ("pistachiocake")),
    Item ("Strawberry Shortcake", 12.00, List("strawberryshortcake")),
    Item ("Croissant", 8.00,List("croissant")),
    Item ("Tiramisu", 11.00,List("tiramisu"))
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
    Order(List(item1, item2))

  //generate random customer name
  def generateRandomCustomerName(): String =
    customerNames(Random.nextInt(customerNames.size))

  //generate random customer
  def generateRandomCustomer(): Customer=
    val name = generateRandomCustomerName()
    val order = generateRandomOrder()
    Customer(name, order, 3)
