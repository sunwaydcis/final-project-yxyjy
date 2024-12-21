package controller

import model.Customer
import util.RandomGenerator

import scala.collection.mutable.ArrayBuffer

//handle customer related operations - customer comes into shop (becomes active - added into active list), customer leaves shop (removed from active list)
class CustomerController(totalCustomers: List[Customer]):
  //use of arrayBuffer recommended by chatgpt, searched online for guide on usage
  //https://www.scala-lang.org/api/3.x/scala/collection/mutable/ArrayBuffer.html
  val activeCustomers: ArrayBuffer[Customer] = ArrayBuffer(totalCustomers.take(3):_*)
  val nextCustomerIndex:Int = 3



