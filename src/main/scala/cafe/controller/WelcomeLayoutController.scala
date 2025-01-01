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
  @FXML var helpBtn: Button = _
  @FXML var cookbookBtn: Button = _
  private val welcomeSound: MediaPlayer = new MediaPlayer(new Media(new File("src/main/resources/cafe.media/welcome.mp3").toURI.toString))
  private val popSound: MediaPlayer = new MediaPlayer(new Media(new File("src/main/resources/cafe.media/pop.mp3").toURI.toString))
  private val bgm: MediaPlayer = new MediaPlayer(new Media(new File("src/main/resources/cafe.media/bgm.mp3").toURI.toString))

  def playWelcomeSound(): Unit =
    try
      welcomeSound.stop()
      welcomeSound.play()
    catch
      case e: Exception =>
        println(s"Error playing sound: ${e.getMessage}")

  def playPopSound(): Unit =
    try
      popSound.stop()
      popSound.play()
    catch
      case e: Exception =>
        println(s"Error playing sound: ${e.getMessage}")

  def playBgm(): Unit =
    try
      bgm.cycleCount = 99
      bgm.play()
    catch
      case e: Exception =>
        println(s"Error playing sound: ${e.getMessage}")

  def stopBgm(): Unit =
    bgm.stop()

  def initialize(): Unit =
    playBgm()

