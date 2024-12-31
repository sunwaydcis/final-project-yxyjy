package cafe.controller

import cafe.model.{Customer, Item, Order}
import cafe.util.{RandomGenerator, Timer}

import scala.collection.mutable.ArrayBuffer
import scala.io.StdIn

//GAME CONTROLLER
//combines order controller and customer controller to handle overall game flow

class GameController:
  //initial game time and money earned
  var gameTimeLeft: Int = 100
  var moneyEarned: Double = 0.0
  var totalCustomersServed: Int = 0

  //listener to display game countdown
  private var timeUpdateListener:Int => Unit = _
  def setupTimeUpdateListener(listener:Int => Unit): Unit =
    timeUpdateListener = listener
  //update time left via timer
  def updateGameTime(elapsed:Int):Unit =
    gameTimeLeft = gameTimeLeft - elapsed
    if timeUpdateListener != null then
      timeUpdateListener(gameTimeLeft)

  // generate customers queue
  private var totalCustomerList: List[Customer] = List()
  for i <- 1 to 20 do
    totalCustomerList = totalCustomerList :+ RandomGenerator.generateRandomCustomer()

  //initiate controllers
  val orderCtrl = new OrderController
  val custCtrl = new CustomerController(totalCustomerList, orderCtrl)

  //orderExpired listner
  def setupOrderExpiredListener(updateUI: Order => Unit): Unit =
    orderCtrl.onOrderExpired = updateUI

  //generate first three active customers
  var activeCustomers: ArrayBuffer[Customer] = custCtrl.activeCustomers
  var nextCustomerIndex: Int = custCtrl.nextCustomerIndex

  //generate orders queue -  the orders of the first three customers
  var activeOrders: ArrayBuffer[Order] = orderCtrl.activeOrders
  for i <- activeCustomers.indices do
    activeOrders += activeCustomers(i).order

  //initiate current order
  var currentOrderIndex: Int = 0 //will be set by game layout controller by clicking on ui buttons
  var currentOrder: Order = null

  // a listener listens to changes in customer updates and sets the current order
  custCtrl.setCustomerUpdateListener(() => updateCurrentOrder())
  def updateCurrentOrder(): Unit =
    if currentOrderIndex >= 0 && currentOrderIndex < activeOrders.size then
      this.currentOrder = activeOrders(currentOrderIndex)
    else
      this.currentOrder = null
    println(currentOrder)

  //generate player prepared order
  var playerPreparedOrder: List[List[String]] = List(List())

  //update score
  private def updateScore(customer:Customer):Unit =
    val orderScore = customer.payment
    moneyEarned += orderScore
    Math.round(moneyEarned * 100.0) / 100.0
    println(moneyEarned)

  //when order is served
  def whenOrderDone(): Unit =
    println(s"You prepared: $playerPreparedOrder")
    orderCtrl.orderCorrect(playerPreparedOrder, currentOrder) // checks order and sets it as done

    println(s"Order served! You earned: ${currentOrder.orderTotal}")
    updateScore(activeCustomers(currentOrderIndex)) // Update the moneyEarned
    println(s"Updated Money Earned: $moneyEarned")

    playerPreparedOrder = List(List()) //reset player prepared order
  
    custCtrl.customerLeaves(currentOrderIndex)//make customer leave
    println("Customer has left.")
  
    println(s"After order done - Active Orders: $activeOrders")
    println(s"After order done - Active Customers: ${custCtrl.activeCustomers}")
    totalCustomersServed +=1

  //game start
  def startGame(): Unit =
    // Start the timer asynchronously
    new Thread(() => Timer.startTimer(gameTimeLeft, orderCtrl, custCtrl, this)).start()

  private var gameOver:() => Unit = () => ()
  def setGameOverCallback(callback: () => Unit): Unit =
    gameOver = callback

  def endGame(): Unit =
    println("Ending game...")
    gameOver()


