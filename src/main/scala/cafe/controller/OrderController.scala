package cafe.controller

import cafe.model.{Customer, Item, Order}
import cafe.model.status.{done, expired, inProgress}

import scala.collection.mutable.ArrayBuffer
import scala.io.StdIn

//ORDER CONTROLLER
//handles order-related operations, and validating player's orders.

class OrderController:

  //prepare and handle active orders
  var activeOrders: ArrayBuffer[Order] = ArrayBuffer()

  private def isOrderActive(order:Order): Boolean = //check that order is in active orders list and order is inProgress
    if activeOrders.contains(order) && order.orderStatus == inProgress then
      true
    else
      false

  def addOrder(order: Order): Unit = //add order to activeOrders
    if !activeOrders.contains(order) then
      activeOrders += order

  def removeOrder(index: Int): Unit = //remove order from activeOrders
    if index >= 0 && index < activeOrders.size then
      activeOrders.remove(index)
      println(s"Order at index $index removed. Active orders size: ${activeOrders.size}")

  def removeCustomerOrder(order:Order): Unit =
    if activeOrders.contains(order) then
      activeOrders -= order

  private def setOrderTotal(order:Order, earned: Double): Unit =
    order.orderTotal = earned
    println(order.orderTotal)

  //set order statuses
  private def orderDone(order: Order): Unit =
    if isOrderActive(order) && order.orderStatus != expired then
      order.orderStatus = done
      println("Order done!")

  private def orderExpired(order:Order): Unit =
    if isOrderActive(order) && order.orderTimeLeft == 0  && order.orderStatus != done then
      order.orderStatus = expired
      println("Order expired!")

  //check if players made orders correctly
  private def isItemCorrect(preparedItem: List[String], item: Item): Boolean = //validate each item
    if preparedItem.sorted == item.ingredients.sorted then
      true
    else
      false

  def orderCorrect(preparedItems: List[List[String]], order: Order): Unit = //validate entire order : how many items correct, set order as done
    setOrderTotal(order, 0.0)
    val item1Correct = isItemCorrect(preparedItems.head, order.items.head)
    val item2Correct = isItemCorrect(preparedItems(1), order.items(1))

    if item1Correct then
      setOrderTotal(order, order.orderTotal + order.items.head.price)
      println("Item served!")
    else
      println("item was wrong")

    if item2Correct then
      setOrderTotal(order, order.orderTotal + order.items(1).price)
      println("Item served!")
    else
      println("item was wrong")

    orderDone(order)//set order as done

  //listener to display order countdown
  private var orderUpdateListener: Int => Unit = _
  def setupOrderUpdateListener(listener: Int => Unit): Unit =
    orderUpdateListener = listener

  //update the order's remaining time if order is in activeOrders list
  private def updateOrderTimeLeft(order: Order): Unit =
    if order.orderTimeLeft > 0 && isOrderActive(order) then
      order.orderTimeLeft -= 1
      println("-------")
      println(s"Order Time Left: ${order.orderTimeLeft}")


  var onOrderExpired: Order => Unit = _

  //update all active orders' status accordingly - to be called in Timer
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