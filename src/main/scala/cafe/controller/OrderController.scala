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

  private def removeOrder(index: Int): Unit = //remove order from activeOrders
    if index >= 0 && index < activeOrders.size then
      activeOrders.remove(index)

  def removeCustomerOrder(order:Order): Unit =
    if activeOrders.contains(order) then
      activeOrders -= order

  //set order statuses
  private def orderDone(order: Order): Unit =
    if isOrderActive(order) && order.orderStatus != expired then
      order.orderStatus = done
      println("Order done!")

  def orderExpired(order:Order): Unit =
    if isOrderActive(order) && order.orderTimeLeft == 0  && order.orderStatus != done then
      order.orderStatus = expired
      println("Order expired!")

  //check if players made orders correctly
  private def isItemCorrect(preparedItem: List[String], item: Item): Boolean = //validate each item
    preparedItem.sorted == item.ingredients.sorted

  def orderCorrect(preparedItems: List[List[String]], order: Order): Unit = //validate entire order : how many items correct, set order as done
    val item1Correct = isItemCorrect(preparedItems.head, order.items.head)
    val item2Correct = isItemCorrect(preparedItems(1), order.items(1))
    order.orderTotal = 0

    if item1Correct then
      order.orderTotal += order.items.head.price
      println("Item served!")
    else
      println("Ugh, not what the customer wanted!")

    if item2Correct then
      order.orderTotal += order.items(1).price
      println("Item served!")
    else
      println("Ugh, not what the customer wanted!")

    orderDone(order)

  //update the order's remaining time if order is in activeOrders list
  private def updateOrderTimeLeft(order: Order): Unit =
    if order.orderTimeLeft > 0 && isOrderActive(order) then
      order.orderTimeLeft -= 1

  //update all active orders' status accordingly - to be called in Timer
  def updateActiveOrders(): Unit =
    for i <- activeOrders.indices.reverse do //using a reverse loop - recommended by chatgpt
      val activeOrder = activeOrders(i)
      if activeOrder.orderTimeLeft == 0 then
        orderExpired(activeOrder)
        removeOrder(i)
      else
        updateOrderTimeLeft(activeOrder)