package cafe.controller

import cafe.model.{Customer, Order}
import cafe.util.{RandomGenerator, Timer}

import scala.collection.mutable.ArrayBuffer

/**
 * GAME CONTROLLER - CONTROLS GAME FLOW
 */

class GameController:
  /** time left for current game - set to 100 seconds */
  private var gameTimeLeft: Int = 200
  /** true if game is paused */
  var isGamePaused: Boolean = false
  /** money earned for current game - starts at 0.0 */
  var moneyEarned: Double = 0.0
  /** total customers served for current game - starts at 0 */
  var totalCustomersServed: Int = 0

  /** listener for timer updates */
  private var timeUpdateListener:Int => Unit = _
  /** setter for timeUpdateListener */
  def setupTimeUpdateListener(listener:Int => Unit): Unit =
    timeUpdateListener = listener
  /** updates gameTimeLeft and calls listener */
  def updateGameTime(elapsed:Int):Unit =
    gameTimeLeft = gameTimeLeft - elapsed
    if timeUpdateListener != null then
      timeUpdateListener(gameTimeLeft)

  /** generates list of all customers in this game  - uses RandomGenerator object*/
  private var allCustomersList: List[Customer] = List()
  for i <- 1 to 20 do
    allCustomersList = allCustomersList :+ RandomGenerator.generateRandomCustomer()

  /** constructs OrderController instance for current game */
  val orderCtrl = new OrderController
  /** constructs CustomerController instance for current game */
  val custCtrl = new CustomerController(allCustomersList, orderCtrl)

  /** set listener to listen when an order expires */
  def setupOrderExpiredListener(updateUI: Order => Unit): Unit =
    orderCtrl.onOrderExpired = updateUI

  /** active customers for current game */
  var activeCustomers: ArrayBuffer[Customer] = custCtrl.activeCustomers
  /** index for next customer for current game */
  var nextCustomerIndex: Int = custCtrl.nextCustomerIndex

  /** active orders for current game */
  var activeOrders: ArrayBuffer[Order] = orderCtrl.activeOrders
  for i <- activeCustomers.indices do
    activeOrders += activeCustomers(i).order

  /** index for current order - default at 0*/
  var currentOrderIndex: Int = 0
  /** current order - default null */
  var currentOrder: Order = null

  custCtrl.setCustomerUpdateListener(() => updateCurrentOrder())
  /** to update the current order */
  def updateCurrentOrder(): Unit =
    if currentOrderIndex >= 0 && currentOrderIndex < activeOrders.size then
      this.currentOrder = activeOrders(currentOrderIndex)
    else
      this.currentOrder = null
    println(currentOrder)

  /** stores the player's prepared order */
  var playerPreparedOrder: List[List[String]] = List(List())

  /** updates the current game's score */
  private def updateScore(customer:Customer):Unit =
    val orderScore = customer.payment
    moneyEarned += orderScore
    moneyEarned = Math.round(moneyEarned * 100.0) / 100.0

  /**
   * Handles operations when an order is done
   * calls OrderController to validate order
   * calls update score method
   * reset player's prepared order
   * calls customerLeaves method
   * increments totalCustomersServed by 1
   */
  def whenOrderDone(): Unit =
    orderCtrl.orderCorrect(playerPreparedOrder, currentOrder)
    updateScore(activeCustomers(currentOrderIndex))
    playerPreparedOrder = List(List())
    custCtrl.customerLeaves(currentOrderIndex)
    totalCustomersServed +=1

  /** pause game */
  def pauseGame(): Unit=
    isGamePaused = true
    Timer.pauseTimer()

  /** resume game */
  def resumeGame(): Unit =
    isGamePaused = false
    Timer.resumeTimer()

  /** start the game - initiate the timer in an asynchronous thread to allow it to run in the background
   *  reference - solution found on stackoverflow
   */
  def startGame(): Unit =
    new Thread(() => Timer.startTimer(gameTimeLeft, orderCtrl, custCtrl, this)).start()

  /** game over listener - referenced from chatgpt */
  private var gameOver:() => Unit = () => ()

  /** set callback for game over listener */
  def setGameOverCallback(callback: () => Unit): Unit =
    gameOver = callback

  /** calls gameOver method - to be called in Timer*/
  def endGame(): Unit =
    gameOver()


