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
