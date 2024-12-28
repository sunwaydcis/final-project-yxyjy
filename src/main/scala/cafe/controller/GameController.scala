package cafe.controller

import cafe.model.{Customer, Item, Order}
import cafe.util.{RandomGenerator, Timer}

import scala.collection.mutable.ArrayBuffer
import scala.io.StdIn

//GAME CONTROLLER
//combines order controller and customer controller to handle overall game flow

class GameController:
  //initial game time and money earned
  var gameTimeLeft: Int = 60
  var moneyEarned: Double = 0.0

  //listener to display game countdown
  private var timeUpdateListener:Int => Unit = _
  def setupTimeUpdateListener(listener:Int => Unit): Unit =
    timeUpdateListener = listener
  //update time left via timer
  def updateGameTime(elapsed:Int):Unit =
    gameTimeLeft = gameTimeLeft - elapsed
    if timeUpdateListener != null then
      timeUpdateListener(gameTimeLeft)

  //listener to display order countdown
  private var orderUpdateListener: Int => Unit = _
  def setupOrderUpdateListener(listener: Int => Unit): Unit =
    orderUpdateListener = listener
  //update time left via timer
  def updateOrderTime(elapsed: Int): Unit =
    currentOrder.orderTimeLeft -= elapsed
    if orderUpdateListener != null then
      orderUpdateListener( currentOrder.orderTimeLeft)

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

  //set current order
  var currentOrderIndex: Int = 0 //will be set by game layout controller by clicking on ui buttons
  var currentOrder: Order = null

  custCtrl.setCustomerUpdateListener(() => updateCurrentOrder())

  def updateCurrentOrder(): Unit =
    if currentOrderIndex >= 0 && currentOrderIndex < activeOrders.size then
      this.currentOrder = activeOrders(currentOrderIndex)
    else
      this.currentOrder = null
    println(currentOrder)

  //generate player prepared order
  var playerPreparedOrder: List[List[String]] = List(List())

  //combines OrderController and CustomerController to handle case when order expires, and customer leaves

  //update score
  private def updateScore(order: Order):Unit =
    val orderScore = order.orderTotal
    moneyEarned += orderScore
    println(moneyEarned)

  //combines OrderController and CustomerController to handle gameflow actions when order is served
  def whenOrderDone(): Unit =
    println(s"Before order done - Active Orders: $activeOrders")
    println(s"Before order done - Money Earned: $moneyEarned")
  
    orderCtrl.orderCorrect(playerPreparedOrder, currentOrder) // Checks order and sets it as done
    println(s"Order served! You earned: ${currentOrder.orderTotal}")
    updateScore(currentOrder) // Update the moneyEarned
    println(s"Updated Money Earned: $moneyEarned")

    playerPreparedOrder = List(List()) //reset player prepared order
  
    custCtrl.customerLeaves(currentOrderIndex)
    println("Customer has left.")
  
    println(s"After order done - Active Orders: $activeOrders")
    println(s"After order done - Active Customers: ${custCtrl.activeCustomers}")

  //game start
  def startGame(): Unit =
    // Start the timer asynchronously
    new Thread(() => Timer.startTimer(60, orderCtrl, custCtrl, this)).start()

    // Start the game flow asynchronously
    //new Thread(() => gameFlow()).start()

  //def gameFlow(): Unit =
    //println("Make order: ")
    //val makeOrderIndex: Int = StdIn.readInt()
