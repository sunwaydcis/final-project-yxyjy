package cafe.controller

import javafx.fxml.FXML
import javafx.scene.control.{Button, Label}
import scalafx.scene.media.{Media, MediaPlayer}

import java.io.File

@FXML
class EndLayoutController:
  @FXML private var totalCustomersServed: Label = _
  @FXML private var totalMoneyEarned: Label = _
  @FXML var backToHome: Button = _

  private var gameCtrl: GameController = _
  private val popSound: MediaPlayer = new MediaPlayer(new Media(new File("src/main/resources/cafe.media/pop.mp3").toURI.toString))

  def setGameController(controller: GameController): Unit =
    this.gameCtrl = controller

  def totalCustomersServedLabel(): Unit =
    totalCustomersServed.setText(gameCtrl.totalCustomersServed.toString)

  def totalMoneyEarnedLabel(): Unit =
    totalMoneyEarned.setText(gameCtrl.moneyEarned.toString)

  def playPopSound(): Unit =
    try
      popSound.stop()
      popSound.play()
    catch
      case e: Exception =>
        println(s"Error playing sound: ${e.getMessage}")





