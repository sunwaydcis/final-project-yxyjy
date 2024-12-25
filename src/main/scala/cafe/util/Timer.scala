package cafe.util

import cafe.controller.{CustomerController, GameController, OrderController}
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
    var lastUpdateTime: Long = System.nanoTime()

    // Start the timer
    timer.start()

    lazy val timer: AnimationTimer = AnimationTimer { now =>
      if (now - lastUpdateTime) >= 1e9.toLong then { // If 1 second has passed
        lastUpdateTime = now
        timeLeft -= 1
        println(s"Time left: $timeLeft")

        orderCtrl.updateActiveOrders()
        custCtrl.handleCustomerQueue()

        if timeLeft <= 0 then
          println("game over!")
          timer.stop() // Stop the timer when time is up
      }
    }



