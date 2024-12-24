package controller

import model.{Customer, Item, Order}
import util.RandomGenerator
import controller.{CustomerController, OrderController}

import scala.collection.mutable.ArrayBuffer

class GameController:
  //initial game time and money earned
  var gameTimeLeft: Int = 400
  var moneyEarned: Double = 0.0

  // generate customers queue
  var totalCustomerList: List[Customer] = List()
  for i <- 1 to 20 do
    totalCustomerList = totalCustomerList :+ RandomGenerator.generateRandomCustomer()

  //initiate controllers
  val orderCtrl = new OrderController
  val custCtrl = new CustomerController(totalCustomerList, orderCtrl)

  //generate first three active customers
  var activeCustomers: ArrayBuffer[Customer] = custCtrl.activeCustomers
  var nextCustomerIndex: Int = custCtrl.nextCustomerIndex

  //generate orders queue -  the orders of the first three customers
  var activeOrders: ArrayBuffer[Order] = orderCtrl.activeOrders
  for i <- activeCustomers.indices do
    activeOrders += activeCustomers(i).order














