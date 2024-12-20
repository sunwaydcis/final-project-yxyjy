package model

case class Order(status:orderStatus, items:List[Item], orderTimeLeft: Int):
  def orderTotal(): Double =
    items.map(_.price).sum

enum orderStatus:
  case inProgress, done, expired


