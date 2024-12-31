package cafe.controller

import javafx.fxml.FXML
import javafx.scene.control.Button
import scalafx.event.ActionEvent
import scalafx.Includes.*
import scalafx.scene.media.{Media, MediaPlayer}

import java.io.File

@FXML
class WelcomeLayoutController:
  @FXML var startGameBtn: Button = _
  private val welcomeSound: MediaPlayer = new MediaPlayer(new Media(new File("src/main/resources/cafe.media/welcome.mp3").toURI.toString))
  private val bgm: MediaPlayer = new MediaPlayer(new Media(new File("src/main/resources/cafe.media/bgm.mp3").toURI.toString))

  def playWelcomeSound(): Unit =
    try
      welcomeSound.stop()
      welcomeSound.play()
    catch
      case e: Exception =>
        println(s"Error playing sound: ${e.getMessage}")

  def initialize(): Unit =
    bgm.setCycleCount(MediaPlayer.Indefinite)
    bgm.play()

