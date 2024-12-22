package util

import controller.{CustomerController, OrderController}
import scalafx.animation.AnimationTimer

//creating a new timer object using ScalaFX Animation Timer to coyntdown for the game - method introdyced by chatgpt - evaluated a few methods and this seems to be the most appropriate
object Timer:

  //method to start the time and start the game
  def startTimer(
                gameTime: Int,
                orderCtrl: OrderController,
                custCtrl: CustomerController
                ): Unit =
    var timeLeft = gameTime
    var lastUpdateTime: Long = 0

    val timer = AnimationTimer{ now =>
      //every second, make the time left decrease by one second
      if lastUpdateTime == 0 ||now - lastUpdateTime >= 1e9 then
        lastUpdateTime = now
        timeLeft -=1

        println("to check time left- time left is")
        println(timeLeft)

    }


