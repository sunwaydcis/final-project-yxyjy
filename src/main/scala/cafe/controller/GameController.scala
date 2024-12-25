package cafe.controller

import cafe.model.{Customer, Item, Order}
import cafe.util.{RandomGenerator, Timer}

import scala.collection.mutable.ArrayBuffer
import scala.io.StdIn

class GameController:
  //initial game time and money earned
  var gameTimeLeft: Int = 60
  var moneyEarned: Double = 0.0

  // generate customers queue
  var totalCustomerList: List[Customer] = List()
  for i <- 1 to 20 do
    totalCustomerList = totalCustomerList :+ RandomGenerator.generateRandomCustomer()

  //initiate controllers
  val orderCtrl = new OrderController
  val custCtrl = new CustomerController(totalCustomerList, orderCtrl)

  //generate first three active customers
  var activeCustomers: ArrayBuffer[Customer] = custCtrl.activeCustomers
  var nextCustomerIndex: Int = custCtrl.nextCustomerIndex

  //generate orders queue -  the orders of the first three customers
  var activeOrders: ArrayBuffer[Order] = orderCtrl.activeOrders
  for i <- activeCustomers.indices do
    activeOrders += activeCustomers(i).order

  //game start
  def startGame(): Unit =
    // Start the timer asynchronously
   // new Thread(() => Timer.startTimer(60, orderCtrl, custCtrl)).start()

    // Start the game flow asynchronously
    new Thread(() => gameFlow()).start()

  def gameFlow(): Unit =
    println("Your orders of the day: ")
    for order <- activeOrders do
      println("Order: ")
      for item <- order.items do
        println(item.name)

    println("Make order: ")
    val makeOrderIndex: Int = StdIn.readInt()

    orderCtrl.evaluatePlayerOrder(makeOrderIndex)



















