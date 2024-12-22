package controller

import model.{Customer, Item, Order}
import util.RandomGenerator
import controller.{CustomerController, OrderController}

import scala.collection.mutable.ArrayBuffer

class GameController:
  //generate intial game time and money earned 
  var gameTimeLeft: Int = 400
  var moneyEarned: Double = 0.0
  
  // generate customers queue
  var totalCustomerList: List[Customer] = List()
  for i <- 1 to 20 do
    totalCustomerList = totalCustomerList :+ RandomGenerator.generateRandomCustomer()

  val custCtrl = new CustomerController(totalCustomerList)

  var activeCustomers: ArrayBuffer[Customer] = custCtrl.activeCustomers
  var nextCustomerIndex: Int = custCtrl.nextCustomerIndex
  
  //generate orders queue
  val orderCtrl = new OrderController()
  
  var activeOrders: ArrayBuffer[Order] = orderCtrl.activeOrders
  for i <- 0 to activeOrders.size do
    activeOrders += activeCustomers(i).order














