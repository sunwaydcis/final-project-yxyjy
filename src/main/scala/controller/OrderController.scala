package controller

import model.orderStatus.expired
import model.{Item, Order}

//handles order-related operations, and validating player's orders.

class OrderController:
  //check if player prepared item correctly
  def isItemCorrect(preparedItem: List[String], item:Item):Boolean=
    preparedItem.sorted == item.ingredients.sorted

  //check if player prepared order correctly and how many items are correct, then set the order's itemsCorrect
  def orderCorrect(preparedItems: List[List[String]], order:Order): Unit =
    val item1Correct = isItemCorrect(preparedItems.head, order.items.head)
    val item2Correct = isItemCorrect(preparedItems(1), order.items(1))

    if item1Correct && item2Correct then
      order.itemsCorrect = 2
    else if (item1Correct && !item2Correct) || (!item1Correct && item2Correct ) then
      order.itemsCorrect = 1
    else if !item1Correct && !item2Correct then
      order.itemsCorrect = 0

  //check if order is expired
  def orderExpired(order:Order):Unit =
    if order.orderTimeLeft == 0 then
      order.status = expired