package cafe.controller

import javafx.fxml.FXML
import javafx.scene.control.{Button, Label}

@FXML
class EndLayoutController:
  @FXML private var totalCustomersServed: Label = _
  @FXML private var totalMoneyEarned: Label = _
  @FXML var backToHome: Button = _

  private var gameCtrl: GameController = _

  def setGameController(controller: GameController): Unit =
    this.gameCtrl = controller

  def totalCustomersServedLabel(): Unit =
    totalCustomersServed.setText(gameCtrl.totalCustomersServed.toString)

  def totalMoneyEarnedLabel(): Unit =
    totalMoneyEarned.setText(gameCtrl.moneyEarned.toString)


