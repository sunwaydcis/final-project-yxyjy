package cafe.controller

import cafe.model.Customer
import cafe.model.status.expired

import scala.collection.mutable.ArrayBuffer

/**
 *
 * @param allCustomers a list of all customers in the current game imported from the GameController
 * @param orderCtrl an instance of OrderController
 */
class CustomerController(allCustomers: List[Customer], orderCtrl : OrderController):
  /**Three active customers*/
  val activeCustomers: ArrayBuffer[Customer] = ArrayBuffer(allCustomers.take(3) *)
  /**Index to identify next customer to be added*/
  var nextCustomerIndex: Int = 3

  /**updates customer's satisfaction based on order's time left */
  def updateCustomerSatisfaction():Unit =
    for customer <- activeCustomers do
      val timeLeft = customer.order.orderTimeLeft
      if timeLeft > 30 && timeLeft <= 50 then
        customer.satisfaction = 3
      else if timeLeft > 10 && timeLeft <= 30 then
        customer.satisfaction = 2
      else if timeLeft > 0 && timeLeft <= 10 then
        customer.satisfaction = 1

  /**listener for customer updates*/
  private var customerUpdateListener: () => Unit = _

  /**setter for customerUpdateListener*/
  def setCustomerUpdateListener(listener: () => Unit): Unit =
    customerUpdateListener = listener

  /** To handle when a customer leaves
   * Remove customer from active customers list.
   * Calls OrderController to remove the customer's associated order from active orders list.
   * Adds next customer to the active customers list, and calls OrderController to append the customer's associated order to the active orders list
   * Updates next customer index
   *
   * @param index index of customer to be handled
   */
  def customerLeaves(index: Int): Unit =
    if index >= 0 && index < activeCustomers.size then
      val leavingCustomer = activeCustomers.remove(index)
      orderCtrl.removeCustomerOrder(leavingCustomer.order)

    if nextCustomerIndex < allCustomers.size && activeCustomers.size < 3 then
      val nextCustomer = allCustomers(nextCustomerIndex)
      activeCustomers.append(nextCustomer)
      orderCtrl.addOrder(nextCustomer.order)

      nextCustomerIndex += 1

    if customerUpdateListener != null then
      customerUpdateListener()

  /**Iterates through active customers list to remove any expired order - method is called in Timer object every second */
  def handleCustomerQueue (): Unit =
    for i <- activeCustomers.indices.reverse do
      val activeCust = activeCustomers(i)
      if activeCust.order.orderStatus == expired then
        customerLeaves(i)

