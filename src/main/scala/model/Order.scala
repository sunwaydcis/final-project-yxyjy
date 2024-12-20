package model

case class Order(status:orderStatus, items:List[Item], orderTimeLeft: Int)

enum orderStatus:
  case inProgress, done, expired
