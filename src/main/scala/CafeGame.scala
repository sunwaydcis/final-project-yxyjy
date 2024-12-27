import cafe.controller.{CustomerController, GameController, GameLayoutController, OrderController}
import cafe.model.{Customer, Item, Order}
import cafe.util.RandomGenerator
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import javafx.fxml.FXMLLoader
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes.*
import scalafx.scene as sfxs
import javafx.scene as jfxs
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{AnchorPane, StackPane}
import scalafx.scene.text.Font
import scalafx.stage.{Modality, Stage}


object CafeGame extends JFXApp3:
  var roots: Option[sfxs.layout.AnchorPane] = None

  override def start(): Unit =

    val rootResource = getClass.getResource("cafe.view/GameLayout.fxml")
    val loader = new FXMLLoader(rootResource)
    loader.load()

    val gameController = new GameController()
    loader.getController[GameLayoutController].setGameController(gameController)


    roots = Option(loader.getRoot[jfxs.layout.AnchorPane])
    stage = new PrimaryStage():
      title = "CafeGame"
      scene = new Scene():
        root = roots.get

end CafeGame
