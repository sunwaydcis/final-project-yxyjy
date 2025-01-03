package cafe.util

import cafe.model.{Customer, Item, Order, StingyCustomer, VIPCustomer}

import scala.util.Random

object RandomGenerator:

  //menu items
  val menuItems: List[Item] = List(
    Item ("Latte", 6.00, List("cup", "espresso", "milk"),"cafe.media/latte.png"),
    Item ("Iced Latte", 6.50, List("glass", "espresso", "milk", "ice"), "cafe.media/icedlatte.png"),
    Item ("Espresso", 4.00, List("espresso"),"cafe.media/espressodone.png"),
    Item ("Mocha", 7.00, List("cup","espresso", "milk", "chocolatesyrup"),"cafe.media/mocha.png"),
    Item ("Iced Mocha", 7.50, List("glass","espresso", "milk", "chocolatesyrup", "ice", "whippedcream"),"cafe.media/icedmocha.png"),
    Item ("Caramel Macchiato",  7.00, List("cup","espresso", "milk", "caramelsyrup"),"cafe.media/caramelmacchiato.png"),
    Item ("Iced Caramel Macchiato",  7.00, List("glass", "espresso", "milk", "caramelsyrup", "ice", "whippedcream"),"cafe.media/icedcaramelmacchiato.png"),
    Item ("Matcha Latte", 8.00, List("cup","espresso", "milk", "matchapowder"),"cafe.media/matchalatte.png"),
    Item ("Iced Matcha Latte", 8.50, List("glass", "espresso", "milk", "matchapowder", "ice"),"cafe.media/icedmatchalatte.png"),
    Item ("Pistachio Cake", 12.00,List ("pistachiocake"),"cafe.media/pistachiocakedone.png"),
    Item ("Strawberry Shortcake", 12.00, List("strawberryshortcake"),"cafe.media/strawberryshortcakedone.png"),
    Item ("Croissant", 8.00,List("croissant"),"cafe.media/croissantdone.png"),
    Item ("Tiramisu", 11.00,List("tiramisu"),"cafe.media/tiramisudone.png")
  )

  private val customerNamesPics: List[List[String]] = List(
    List("Gina","cafe.media/2.png"),
    List("Antonio","cafe.media/1.png"),
    List("Genie","cafe.media/3.png"),
    List("Ellie","cafe.media/4.png"),
    List("Sekita","cafe.media/5.png"),
    List("Frank","cafe.media/6.png")
  )

  //generate random menu item
  private def generateRandomMenuItem(): Item =
    menuItems(Random.nextInt(menuItems.size))

  //generate random order
  private def generateRandomOrder(): Order=
    val item1 = generateRandomMenuItem()
    val item2 = generateRandomMenuItem()
    Order(List(item1, item2))

  //generate random customer name
  private def generateRandomCustomerName(): List[String] =
    customerNamesPics(Random.nextInt(customerNamesPics.size))

  //generate random customer
  def generateRandomCustomer(): Customer=
    val custType = Random.nextInt(10)
    val name = generateRandomCustomerName().head
    val order = generateRandomOrder()
    val pic = generateRandomCustomerName().last

    custType match
      case x if x < 7 => new Customer(name, order, pic)
      case x if x < 9 => new StingyCustomer(name,order,pic)
      case _ => new VIPCustomer(name, order, pic)

