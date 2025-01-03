package cafe.model

import cafe.controller.OrderController
import status.inProgress

class Order(val items:List[Item]):
  var orderTimeLeft: Int = 50
  var orderStatus: status = inProgress
  var orderTotal: Double = 0

enum status:
  case inProgress, done, expired





