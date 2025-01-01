package cafe.controller

import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene as jfxs
import scalafx.scene as sfxs
import javafx.scene.control.Button
import scalafx.Includes.*
import scalafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.media.{Media, MediaPlayer}
import scalafx.stage.Stage

import java.io.File

@FXML
class HelpLayoutController:
  @FXML var nextPage: Button = _
  @FXML var prevPage: Button = _

  private val popSound: MediaPlayer = new MediaPlayer(new Media(new File("src/main/resources/cafe.media/pop.mp3").toURI.toString))

  def playPopSound(): Unit =
    try
      popSound.stop()
      popSound.play()
    catch
      case e: Exception =>
        println(s"Error playing sound: ${e.getMessage}")
