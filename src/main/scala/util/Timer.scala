package util

import controller.{CustomerController, GameController, OrderController}
import scalafx.animation.AnimationTimer
import scalafx.animation

//creating a new timer object using ScalaFX Animation Timer to coyntdown for the game - method introduced by chatgpt - evaluated a few methods and this seems to be the most appropriate
object Timer:

  //method to start the time and start the game
  def startTimer(
                gameTime: Int,
                orderCtrl: OrderController,
                custCtrl: CustomerController
                ): Unit =

    var timeLeft = gameTime
    var lastUpdateTime: Long = 0

    // Start the timer
    timer.start()

    lazy val timer: AnimationTimer = AnimationTimer { now =>
      if lastUpdateTime == 0 || now - lastUpdateTime >= 1e9 then
        lastUpdateTime = now
        timeLeft -= 1
        println(timeLeft)

        orderCtrl.updateActiveOrders()
        custCtrl.handleCustomerQueue()

        if timeLeft <= 0 then
          println("game over!")
          timer.stop() // Stop the timer when time is up
    }



