package cafe.controller

import cafe.model.{Customer, Item, Order}
import cafe.model.status.{done, expired, inProgress}
import scala.collection.mutable.ArrayBuffer
import scala.io.StdIn

/**
 * ORDER CONTROLLER - HANDLES ORDER-RELATED OPERATIONS
 */
class OrderController:
  /** ArrayBuffer to hold a list of 3 active orders */
  var activeOrders: ArrayBuffer[Order] = ArrayBuffer()

  /**
   * checks if order is active - if order is in activeOrders list and status is inProgress
   * @param order the order to check
   * @return true if order is active
   */
  private def isOrderActive(order:Order): Boolean = 
    if activeOrders.contains(order) && order.orderStatus == inProgress then
      true
    else
      false

  /** add order to activeOrders */
  def addOrder(order: Order): Unit = 
    if !activeOrders.contains(order) then
      activeOrders += order

  /** remove order from activeOrders 
   * @param index the index of the order to remove
   */
  private def removeOrder(index: Int): Unit =
    if index >= 0 && index < activeOrders.size then
      activeOrders.remove(index)

  /** remove customer's order from activeOrders
   * @param order the order to remove
   */
  def removeCustomerOrder(order:Order): Unit =
    if activeOrders.contains(order) then
      activeOrders -= order

  /**
   * set the orderTotal
   * @param order the order to be set
   * @param earned the amount to be set
   */
  private def setOrderTotal(order:Order, earned: Double): Unit =
    order.orderTotal = earned

  /**
   * set the order status as done
   * @param order the order to be set
   */
  private def orderDone(order: Order): Unit =
    if isOrderActive(order) && order.orderStatus != expired then
      order.orderStatus = done

  /**
   * set the order status as expired
   * @param order the order to be set
   */
  private def orderExpired(order:Order): Unit =
    if isOrderActive(order) && order.orderTimeLeft == 0  && order.orderStatus != done then
      order.orderStatus = expired
      println("Order expired!")

  /**
   * validate item prepared by player
   * @param preparedItem the list of ingredients in the item prepared by the player
   * @param item the menu item to be checked against
   * @return true if ingredients in player's prepared item matches the ingredients in the menu item
   */
  private def isItemCorrect(preparedItem: List[String], item: Item): Boolean = 
    if preparedItem.sorted == item.ingredients.sorted then
      true
    else
      false

  /**
   * validate order prepared by player
   * @param preparedItems list of items prepared by the player
   * @param order the order to be checked against
   * resets order total to 0
   * checks both items in the player prepared order by calling isItemCorrect
   * adds the price of correct items to the order total
   * sets order status as done by calling orderDone
   */
  def orderCorrect(preparedItems: List[List[String]], order: Order): Unit =
    setOrderTotal(order, 0.0)
    val item1Correct = isItemCorrect(preparedItems.head, order.items.head)
    val item2Correct = isItemCorrect(preparedItems(1), order.items(1))

    if item1Correct then
      setOrderTotal(order, order.orderTotal + order.items.head.price)

    if item2Correct then
      setOrderTotal(order, order.orderTotal + order.items(1).price)

    orderDone(order)

  /** listener for updates to order's time left */
  private var orderUpdateListener: Int => Unit = _
  /** set up listener for orderUpdateListener */
  def setupOrderUpdateListener(listener: Int => Unit): Unit =
    orderUpdateListener = listener

  /**
   * update order's remaining time left
   * @param order order to be updated
   * if order is active, decrement order's time left by 1
   * (to be called every second by the Timer
   */
  private def updateOrderTimeLeft(order: Order): Unit =
    if order.orderTimeLeft > 0 && isOrderActive(order) then
      order.orderTimeLeft -= 1
      println("-------")
      println(s"Order Time Left: ${order.orderTimeLeft}") // to confirm that order time is running correctly using the console 
  
  /** listener for when an order expires - used to update the UI */
  var onOrderExpired: Order => Unit = _

  /** Update active orders
   * reiterates through active orders to list to remove expired orders or reduce the order's time left
   * to be called every second by the Timer
   */
  def updateActiveOrders(): Unit =
    for i <- activeOrders.indices.reverse do //using a reverse loop - recommended by chatgpt
      val activeOrder = activeOrders(i)
      if activeOrder.orderTimeLeft == 0 then
        orderExpired(activeOrder)
        if onOrderExpired != null then onOrderExpired(activeOrder)
        removeOrder(i)
      else
        updateOrderTimeLeft(activeOrder)
        if orderUpdateListener != null then
          orderUpdateListener(activeOrder.orderTimeLeft)