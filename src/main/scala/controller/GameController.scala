package controller

import model.{Customer,Order, Item}
import util.RandomGenerator

abstract class GameController:

  // create initial list of customers and use customer controller operations to handle like customer entering and leaving
  private var totalCustomerList: List[Customer] = List()
  for i <- 1 to 20 do
    totalCustomerList = totalCustomerList :+ RandomGenerator.generateRandomCustomer()
    
    
  private var score:Int = 0
  private var timeLeft: Int = 400

