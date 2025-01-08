package cafe.controller

import javafx.fxml.FXML
import javafx.scene.control.{Button, Label}

/**
 * END LAYOUT CONTROLLER - FXML CONTROLLER
 */
@FXML
class EndLayoutController:
  @FXML private var totalCustomersServed: Label = _
  @FXML private var totalMoneyEarned: Label = _
  @FXML var backToHome: Button = _

  /** game controller instance */
  private var gameCtrl: GameController = _

  /** set the game controller for current game */
  def setGameController(controller: GameController): Unit =
    this.gameCtrl = controller

  /** set the text of totalCustomersServed label */
  def totalCustomersServedLabel(): Unit =
    totalCustomersServed.setText(gameCtrl.totalCustomersServed.toString)

  /** set the text of totalMoneyEarned label */
  def totalMoneyEarnedLabel(): Unit =
    totalMoneyEarned.setText(gameCtrl.moneyEarned.toString)


