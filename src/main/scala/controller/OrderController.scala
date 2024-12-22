package controller

import model.status.{done, expired, inProgress}
import model.{Customer, Item, Order}

import scala.collection.mutable.ArrayBuffer

//handles order-related operations, and validating player's orders.

class OrderController:

  //keeps an arrayBuffer of active customers' orders
  var activeOrders: ArrayBuffer[Order] = ArrayBuffer()

  def addOrder(order: Order): Unit =
    activeOrders += order

  def removeOrder(index: Int): Unit =
    //customer removed from active customers list
    activeOrders.remove(index)

  //check if player prepared item correctly
  private def isItemCorrect(preparedItem: List[String], item:Item):Boolean=
    preparedItem.sorted == item.ingredients.sorted

  //check if player prepared order correctly and how many items are correct, then set the order's itemsCorrect and set order's status as done
  def orderCorrect(preparedItems: List[List[String]], order:Order): Unit =
    val item1Correct = isItemCorrect(preparedItems.head, order.items.head)
    val item2Correct = isItemCorrect(preparedItems(1), order.items(1))

    order.orderTotal = 0

    if item1Correct then
      order.orderTotal += order.items.head.price

    if item2Correct then
      order.orderTotal += order.items(1).price

    if order.orderStatus == inProgress then
      order.orderStatus = done

  //set order as expired
  def orderExpired(order:Order):Unit =
    if order.orderTimeLeft == 0 && !(order.orderStatus == expired) then
      order.orderStatus = expired

  //check if order is active
  def isOrderActive(order:Order):Boolean =
    if order.orderStatus == inProgress && !(order.orderStatus == expired) && !(order.orderStatus == done) then
      true
    else
      false

  //update the order's remaining time
  def updateOrderTimeLeft(order:Order): Unit=
    if order.orderTimeLeft > 0 then
      order.orderTimeLeft -= 1



