import scalafx.application.Platform
import util.{RandomGenerator, Timer}
import model.{Customer, Item, Order}
import view.{CustomerController, GameController, OrderController}

import scala.collection.mutable.ArrayBuffer
import scala.io.StdIn

object test:

  def main(args: Array[String]): Unit =

    Platform.startup( ()=>

      //test data
      val icedlatte = Item("latte", 6.0, List("espresso", "milk", "ice"))
      val cake = Item("Cake", 10.0, List("cake"))
      val espresso = Item ("Espresso", 4.00, List("espresso"))
      val croissant = Item ("Croissant", 10.0, List("croissant"))
  
      val order1 = Order(List(icedlatte, cake))
      val order2 = Order(List(icedlatte, croissant))
      val order3 = Order(List(espresso, cake))
      val order4 = Order(List(espresso, croissant))
  
      val cust1 = Customer("James", order1, 3)
      val cust2 = Customer("Annie", order2, 3)
      val cust3 = Customer("Sienna", order3, 3)
      val cust4 = Customer("Jenny", order4, 3)
  
      val totalCustomers: List[Customer] = List(cust1, cust2, cust3, cust4)
  
      //adding controllers
      val oCtrl = new OrderController
      val cCtrl = new CustomerController(totalCustomers, oCtrl)
      val gCtrl = new GameController
  
      //initiate active customers and active orders
      var activeCusts: ArrayBuffer[Customer] = ArrayBuffer(cust1, cust2, cust3)
      var activeOrders: ArrayBuffer[Order] =ArrayBuffer(order1, order2, order3)
      var nextCustomerI = gCtrl.nextCustomerIndex
  
      println("Your customers for the day: ")
      for item <- activeCusts do
        println(item)
  
      Timer.startTimer(60, oCtrl, cCtrl)

      println("Your orders of the day: ")
      for item <- activeOrders do
        println(item)

      println("Make order: ")
      val makeOrderIndex: Int = StdIn.readInt()

      oCtrl.evaluatePlayerOrder(makeOrderIndex)

      
      
  )

end test
