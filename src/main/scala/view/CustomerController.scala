package view

import model.{Customer, Order}
import view.OrderController
import model.status.{expired, done}
import scala.collection.mutable.ArrayBuffer

//handle customer related operations - customer comes into shop (becomes active - added into active list), customer leaves shop (removed from active list)
class CustomerController(totalCustomers: List[Customer], orderCtrl : OrderController):
  //handle active customers
  val activeCustomers: ArrayBuffer[Customer] = ArrayBuffer(totalCustomers.take(3) *)
  var nextCustomerIndex: Int = 3

  def customerLeaves(index: Int): Unit = //to handle when an active customer leaves. and the next customer is added
    //customer removed from active customers list
    if index >= 0 && index < activeCustomers.size then
      val leavingCustomer = activeCustomers.remove(index)
      orderCtrl.removeCustomerOrder(leavingCustomer.order)
      
    if nextCustomerIndex < totalCustomers.size then
      val nextCustomer = totalCustomers(nextCustomerIndex)
      activeCustomers.append(nextCustomer)
      orderCtrl.addOrder(nextCustomer.order)
      nextCustomerIndex += 1

  //manage customer queue
  def handleCustomerQueue (): Unit =
    for i <- activeCustomers.indices.reverse do
      val activeCust = activeCustomers(i)
      if activeCust.order.orderStatus == done || activeCust.order.orderStatus == expired then
        customerLeaves(i)



















  //use of arrayBuffer recommended by chatgpt, searched online for guide on usage
  //https://www.scala-lang.org/api/3.x/scala/collection/mutable/ArrayBuffer.html


  //if customer leaves, add next customer from total customers list


  //handles if customer is leaving as their order expires / is done and calls customerLeaves()
  def handleCustomerLeaves(i: Int =  -1):Unit =
    if i >= 0 && i< activeCustomers.size then
      if activeCustomers(i).order.orderStatus == done || activeCustomers(i).order.orderStatus == expired then
        customerLeaves(i)







