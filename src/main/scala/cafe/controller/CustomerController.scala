package cafe.controller

import cafe.model.{Customer, Order}
import cafe.model.status.{expired, done}
import scala.collection.mutable.ArrayBuffer

//handle customer related operations - customer comes into shop (becomes active - added into active list), customer leaves shop (removed from active list)
class CustomerController(totalCustomers: List[Customer], orderCtrl : OrderController):
  //handle active customers
  val activeCustomers: ArrayBuffer[Customer] = ArrayBuffer(totalCustomers.take(3) *)
  var nextCustomerIndex: Int = 3

  private var customerUpdateListener: () => Unit = _

  def setCustomerUpdateListener(listener: () => Unit): Unit =
    customerUpdateListener = listener

  def customerLeaves(index: Int): Unit = //to handle when an active customer leaves. and the next customer is added
    //customer removed from active customers list
    if index >= 0 && index < activeCustomers.size then
      val leavingCustomer = activeCustomers.remove(index)
      orderCtrl.removeCustomerOrder(leavingCustomer.order)
      
    if nextCustomerIndex < totalCustomers.size && activeCustomers.size < 3 then
      val nextCustomer = totalCustomers(nextCustomerIndex)
      activeCustomers.append(nextCustomer)
      orderCtrl.addOrder(nextCustomer.order)

      nextCustomerIndex += 1

    if customerUpdateListener != null then
      customerUpdateListener()

  //manage customer queue
  def handleCustomerQueue (): Unit =
    for i <- activeCustomers.indices.reverse do
      val activeCust = activeCustomers(i)
      if activeCust.order.orderStatus == expired then
        customerLeaves(i)

