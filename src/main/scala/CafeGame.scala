import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import util.RandomGenerator
import model.{Customer, Item, Order}
import view.{CustomerController, GameController, OrderController}
import javafx.fxml.FXMLLoader
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes.*
import scalafx.scene as sfxs
import javafx.scene as jfxs
import scalafx.collections.ObservableBuffer
import scalafx.scene.layout.AnchorPane
import scalafx.stage.{Modality, Stage}


object CafeGame extends JFXApp3:

  var roots: Option[sfxs.layout.AnchorPane] = None

  override def start(): Unit =
    // transform path of RootLayout.fxml to URI for resource location.
    val rootResource = getClass.getResource("view/GameLayout.fxml")
    val loader = new FXMLLoader(rootResource)
    loader.load()
    // retrieve the root component BorderPane from the FXML
    // refer to slides on scala option monad
    roots = Option(loader.getRoot[jfxs.layout.AnchorPane])
    stage = new PrimaryStage():
      title = "CafeGame"
      scene = new Scene():
        root = roots.get

end CafeGame
