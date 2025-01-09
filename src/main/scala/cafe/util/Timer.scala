package cafe.util

import cafe.controller.{CustomerController, GameController, OrderController}
import scalafx.animation.AnimationTimer
import scalafx.animation

//creating a new timer object using ScalaFX Animation Timer to count down for the game - method introduced by chatgpt - evaluated a few methods and this seems to be the most appropriate

/**
 * TIMER - UTILITY OBJECT TO HANDLE GAME TIME COUNTDOWN
 */
object Timer:
  /** time left (value taken from gameController */
  var timeLeft: Int = 0
  /** tracks the last update time */
  private var lastUpdateTime: Long = 0
  /** true if game is paused */
  private var isPaused: Boolean = false
  /**
   * To start the timer
   * @param gameTime the game time for current game
   * @param orderCtrl the orderController instance for current game
   * @param custCtrl the customerController instance for current game
   * @param gameCtrl the gameController instance for current game
   * when current time - last update time > 1, then last update time is set to now and game time decremented by 1
   * updateActiveOrders, updateCustomerSatisfaction and handleCustomerQueue are all called every second if game is not paused
   * if time left is <= 0, game is over.
   */
  def startTimer(
                gameTime: Int,
                orderCtrl: OrderController,
                custCtrl: CustomerController,
                gameCtrl: GameController
                ): Unit =

    timeLeft = gameTime
    lastUpdateTime = System.nanoTime()
    isPaused = false
    
    timer.start()

    lazy val timer: AnimationTimer = AnimationTimer { now =>
      if !isPaused && (now - lastUpdateTime) >= 1e9.toLong then {
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

  /** to pause the timer */
  def pauseTimer(): Unit =
    isPaused = true
    println("Game Paused")

  /** to resume the timer */
  def resumeTimer(): Unit =
    if isPaused then
      isPaused = false
      lastUpdateTime = System.nanoTime()
    println("Game Resumed")


