package cafe.model

import cafe.controller.OrderController
import status.inProgress

case class Order(items:List[Item]):
  var orderTimeLeft: Int = 20
  var orderStatus: status = inProgress
  var orderTotal: Double = 0

enum status:
  case inProgress, done, expired




