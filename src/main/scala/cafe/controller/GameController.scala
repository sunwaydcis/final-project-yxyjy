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
  def setCurrentOrder(): Unit =
    if currentOrderIndex >= 0 && currentOrderIndex < activeOrders.size then
      this.currentOrder = activeOrders(currentOrderIndex)
    else
      this.currentOrder = null

  //generate player prepared order
  var playerPreparedOrder: List[List[String]] = List(List())

  //combines OrderController and CustomerController to handle case when order expires, and customer leaves
  def whenOrderExpires(): Unit =
    orderCtrl.orderExpired(currentOrder) //set order as expired

    custCtrl.customerLeaves(currentOrderIndex)//remove customer from activeCustomers and remove associated order from activeOrders, add next customer and order, set next customerIndex
    println ("Oh no, the order has expired! Your customer has left.")

    if custCtrl.activeCustomers.nonEmpty then
      println("New order!")
      println(activeOrders)

    currentOrderIndex = -1 //set currentOrderIndex to default before user selects next order to work on
    setCurrentOrder()

  //update score
  private def updateScore(order: Order):Unit =
    val orderScore = order.orderTotal
    moneyEarned = moneyEarned + orderScore

  //combines OrderController and CustomerController to handle gameflow actions when order is served
  def whenOrderDone():Unit =
    orderCtrl.orderCorrect(playerPreparedOrder, currentOrder)//checks order and sets it as done
    println("Order served! You earned: ")
    println(currentOrder.orderTotal)//show money earned from completing this order
    updateScore(currentOrder)//update the moneyEarned by adding this order's money
    println(moneyEarned)

    custCtrl.customerLeaves(currentOrderIndex)
    println("Your customer has left.")

    if custCtrl.activeCustomers.nonEmpty then
      println("New order!")
      println(activeOrders)

    currentOrderIndex = -1
    setCurrentOrder()


  //game start
  def startGame(): Unit =
    // Start the timer asynchronously
    new Thread(() => Timer.startTimer(60, orderCtrl, custCtrl, this)).start()

    // Start the game flow asynchronously
    new Thread(() => gameFlow()).start()

  def gameFlow(): Unit =
    println("Make order: ")
    val makeOrderIndex: Int = StdIn.readInt()























