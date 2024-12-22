package util

import controller.{CustomerController, GameController, OrderController}
import scalafx.animation.AnimationTimer
import model.{Customer, Order}
import model.status.{done, expired, inProgress}
import scalafx.animation

//creating a new timer object using ScalaFX Animation Timer to coyntdown for the game - method introduced by chatgpt - evaluated a few methods and this seems to be the most appropriate
object Timer:

  //method to start the time and start the game
  def startTimer(
                gameTime: Int,
                orderCtrl: OrderController,
                custCtrl: CustomerController,
                gameCtrl: GameController
                ): Unit =
    var timeLeft = gameTime
    var lastUpdateTime: Long = 0

    val timer = AnimationTimer{ now =>
      // Stop the timer when the time is over

      var activeOrders = gameCtrl.activeOrders
      var activeCustomers = gameCtrl.activeCustomers

      //every second, make the time left decrease by one second
      if lastUpdateTime == 0 || now - lastUpdateTime >= 1e9 then
        lastUpdateTime = now
        timeLeft -= 1

        println(timeLeft)

        //decrease each order's time left by 1 second
        for i <- activeOrders.indices do
          val order = activeOrders(i)

          //if orderTimeLeft is not 0 yet
          if activeOrders(i).orderTimeLeft !=0 then

            //if order is in progress
            if orderCtrl.isOrderActive(order) then
              orderCtrl.updateOrderTimeLeft(order)

            //if order is done
            else if activeOrders(i).orderStatus == done then
              //remove order from active list
              orderCtrl.removeOrder(i)
              //customer leaves, adds next customer to active customers list
              custCtrl.customerLeaves(i)
              //add next customer's order to active orders list
              orderCtrl.addOrder(activeCustomers(2).order)

          //else if orderTimeLeft == 0
          else
            orderCtrl.orderExpired(order)
            //remove order from active list
            orderCtrl.removeOrder(i)
            //customer leaves, adds next customer to active customers list
            custCtrl.customerLeaves(i)
            //add next customer's order to active orders list
            orderCtrl.addOrder(activeCustomers(2).order)
    }

    timer.start()


