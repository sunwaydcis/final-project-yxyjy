package controller

import model.status.expired
import model.{Item, Order}

//handles order-related operations, and validating player's orders.

class OrderController:
  //check if player prepared item correctly
  private def isItemCorrect(preparedItem: List[String], item:Item):Boolean=
    preparedItem.sorted == item.ingredients.sorted

  //check if player prepared order correctly and how many items are correct, then set the order's itemsCorrect
  def orderCorrect(preparedItems: List[List[String]], order:Order): Unit =
    val item1Correct = isItemCorrect(preparedItems.head, order.items.head)
    val item2Correct = isItemCorrect(preparedItems(1), order.items(1))

    order.orderTotal = 0

    if item1Correct then
      order.orderTotal += order.items.head.price

    if item2Correct then
      order.orderTotal += order.items(1).price

  //check if order is expired
  def orderExpired(order:Order):Unit =
    if order.orderTimeLeft == 0 then
      order.orderStatus = expired