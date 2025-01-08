package cafe.util

import cafe.controller.{CustomerController, GameController, OrderController}
import scalafx.animation.AnimationTimer
import scalafx.animation

//creating a new timer object using ScalaFX Animation Timer to count down for the game - method introduced by chatgpt - evaluated a few methods and this seems to be the most appropriate

/**
 * TIMER - UTILITY OBJECT TO HANDLE GAME TIME COUNTDOWN
 */
object Timer:
  /**
   * To start the timer
   * @param gameTime the game time for current game
   * @param orderCtrl the orderController instance for current game
   * @param custCtrl the customerController instance for current game
   * @param gameCtrl the gameController instance for current game
   * when current time - last update time > 1, then last update time is set to now and game time decremented by 1
   * updateActiveOrders, updateCustomerSatisfaction and handleCustomerQueue are all called every second
   * if time left is <= 0, game is over.
   */
  def startTimer(
                gameTime: Int,
                orderCtrl: OrderController,
                custCtrl: CustomerController,
                gameCtrl: GameController
                ): Unit =

    var timeLeft = gameTime
    var lastUpdateTime: Long = System.nanoTime()
    
    timer.start()

    lazy val timer: AnimationTimer = AnimationTimer { now =>
      if (now - lastUpdateTime) >= 1e9.toLong then { 
        lastUpdateTime = now
        timeLeft -= 1
        println(s"Game Time left: $timeLeft")
        gameCtrl.updateGameTime(1)
        orderCtrl.updateActiveOrders()
        custCtrl.updateCustomerSatisfaction()
        custCtrl.handleCustomerQueue()

        if timeLeft <= 0 then
          println("game over!")
          timer.stop()
          gameCtrl.endGame()
      }
    }



