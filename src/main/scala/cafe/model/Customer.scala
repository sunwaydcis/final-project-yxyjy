package cafe.model

//each customer orders two items, and starts with a happy satisfaction. satisfaction reduces by 1 level every 5s starting from 15s, and payment is calculated based on satisfaction level - 3 = orderTotal * 1.2, 2 = orderTotal * 1, 1 = orderTotal*0.8
class Customer(val name: String, val order: Order, val charPic: String):
    var satisfaction: Int = 3

    def payment:Double =
      satisfaction match
        case 1 => Math.round(order.orderTotal*0.8 * 100.0) / 100.0
        case 2 => Math.round(order.orderTotal*1 * 100.0) / 100.0
        case 3 => Math.round(order.orderTotal*1.2 * 100.0) / 100.0


//always gives 5 dollars less
class StingyCustomer ( _name:String, _order:Order, _charPic: String) extends Customer (_name, _order, _charPic):
  override def payment: Double =
    super.payment - 5

//pays an extra 10% tip
class VIPCustomer ( _name:String, _order:Order, _charPic: String) extends Customer (_name, _order, _charPic):
  override def payment: Double =
    super.payment*1.1


