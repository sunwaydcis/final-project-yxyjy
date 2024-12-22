package controller

import model.{Customer, Order}
import util.RandomGenerator
import controller.OrderController
import model.status.{expired, done}

import scala.collection.mutable.ArrayBuffer

//handle customer related operations - customer comes into shop (becomes active - added into active list), customer leaves shop (removed from active list)
class CustomerController(totalCustomers: List[Customer]):
  //use of arrayBuffer recommended by chatgpt, searched online for guide on usage
  //https://www.scala-lang.org/api/3.x/scala/collection/mutable/ArrayBuffer.html
  val activeCustomers: ArrayBuffer[Customer] = ArrayBuffer(totalCustomers.take(3)*)
  var nextCustomerIndex:Int = 3

  //if customer leaves, add next customer from total customers list
  def customerLeaves(index: Int): Unit =
    //customer removed from active customers list
    activeCustomers.remove(index)
    if nextCustomerIndex < totalCustomers.size then
      activeCustomers.append(totalCustomers(nextCustomerIndex))
      nextCustomerIndex += 1




