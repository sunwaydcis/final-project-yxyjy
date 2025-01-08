package cafe.controller

import cafe.util.Sound
import javafx.fxml.FXML
import javafx.scene.control.Button
import scalafx.event.ActionEvent
import scalafx.Includes.*
import scalafx.scene.media.{Media, MediaPlayer}

import java.io.File

/**
 * WELCOME LAYOUT CONTROLLER - FXML CONTROLLER
 */
@FXML
class WelcomeLayoutController:
  @FXML var startGameBtn: Button = _
  @FXML var helpBtn: Button = _
  @FXML var cookbookBtn: Button = _
  @FXML var aboutBtn: Button = _

  /** initialize - play bgm */
  def initialize(): Unit =
    Sound.playBgm()

