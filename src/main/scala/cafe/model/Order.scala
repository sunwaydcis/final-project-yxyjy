package cafe.model

import cafe.model.status.inProgress

/**
 *
 * @param items list of items in the order, each order has two items
 */
class Order(val items:List[Item]):
  /**The time left for each order, default is 50 seconds*/
  var orderTimeLeft: Int = 50
  /**The status of the order*/
  var orderStatus: status = inProgress
  /**The order total, calculated by the total of the items' prices*/
  var orderTotal: Double = 0

/**Possible statuses of each order*/
enum status:
  case inProgress, done, expired





