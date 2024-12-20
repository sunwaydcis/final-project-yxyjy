package controller

import model.{Customer,Order, Item}
import scala.util.Random

abstract class GameController:

  // initial variables in a new game
  private var customers: List[Customer] = List.empty
  private var score:Int = 0
  private var timeLeft: Int = 400

  //method to generate new order - or use util class to generate random
  def randomOrder():Order
    //set two or three items for the order (randomly)

    //set orderTimeLeft



