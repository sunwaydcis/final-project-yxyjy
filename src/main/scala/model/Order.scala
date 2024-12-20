package model

case class Order(orderID: String, status:orderStatus, items:List[Item], timeLeft: Double)

enum orderStatus:
  case inprogress, done, expired