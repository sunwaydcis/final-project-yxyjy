import cafe.controller.{CustomerController, GameController, OrderController}
import cafe.model.{Customer, Item, Order}
import cafe.util.{RandomGenerator, Timer}
import scalafx.application.Platform

import scala.collection.mutable.ArrayBuffer
import scala.io.StdIn

object test:

  def main(args: Array[String]): Unit =

    Platform.startup( ()=>

      var totalCustList: List[Customer] = List()
      for i <- 1 to 20 do
        totalCustList = totalCustList :+ RandomGenerator.generateRandomCustomer()

      val oCtrl = new OrderController
      val cCtrl = new CustomerController(totalCustList, oCtrl)
      val gCtrl = new GameController()

      Platform.runLater(
        gCtrl.startGame()
      )



  )

end test
