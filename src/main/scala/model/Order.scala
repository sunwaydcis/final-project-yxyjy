package model

import model.orderStatus.inProgress

case class Order(items:List[Item], orderTimeLeft: Int):
  var status: orderStatus = inProgress
  var itemsCorrect: Int = 0
  def orderTotal(): Double =
    items.map(_.price).sum

enum orderStatus:
  case inProgress, done, expired


