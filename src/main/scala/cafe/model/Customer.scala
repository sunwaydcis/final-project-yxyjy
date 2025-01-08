package cafe.model

/**
 * Represents a customer
 *
 * @param name the name of the customer
 * @param order the order of the customer consisting of two items
 */

class Customer(val name: String, val order: Order):
  /**The satisfaction level of the customer, the default being 3*/
  var satisfaction: Int = 3

  /**Calculates payment based on the customer's satisfaction level*/
  def payment:Double =
    satisfaction match
      case 1 => Math.round(order.orderTotal*0.8 * 100.0) / 100.0
      case 2 => Math.round(order.orderTotal*1 * 100.0) / 100.0
      case 3 => Math.round(order.orderTotal*1.2 * 100.0) / 100.0


/**
 * Extends Customer class, represents a Stingy type customer that always pays $5 less
 *
 * @param _name the name of the customer
 * @param _order the order of the customer
 */
class StingyCustomer ( _name:String, _order: Order) extends Customer (_name, _order):
  /**Overrides Customer's payment method, subtracts $5 from calculated payment*/
  override def payment: Double =
    super.payment - 5

/**
 *
 * @param _name the name of the customer
 * @param _order the order of the customer
 */
class VIPCustomer ( _name:String, _order:Order) extends Customer (_name, _order):
  /**Overrides Customer's payment method, adds a 10% tip to the calculated payment*/
  override def payment: Double =
    super.payment*1.1


