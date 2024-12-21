package model

import model.status.inProgress
import controller.OrderController

case class Order(items:List[Item], orderTimeLeft: Int):
  var orderStatus: status = inProgress
  var orderTotal: Double = 0

enum status:
  case inProgress, done, expired




