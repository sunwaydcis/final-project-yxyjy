import cafe.controller.{EndLayoutController, GameController, GameLayoutController, WelcomeLayoutController}
import javafx.fxml.FXMLLoader
import javafx.scene as jfxs
import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.event.ActionEvent
import scalafx.scene as sfxs
import scalafx.scene.Scene
import scalafx.scene.layout.AnchorPane
import scalafx.scene.text.Font


object CafeGame extends JFXApp3:
  var roots: Option[sfxs.layout.AnchorPane] = None

  private var gameController: GameController = _

  override def start(): Unit =
    loadWelcomeLayout()


  private def loadWelcomeLayout(): Unit =
    val rootResource = getClass.getResource("cafe.view/WelcomeLayout.fxml")
    val loader = new FXMLLoader(rootResource)
    loader.load()

    val welcomeController = loader.getController[WelcomeLayoutController]
    welcomeController.startGameBtn.onAction = (_: ActionEvent) =>
      welcomeController.playWelcomeSound()
      loadGameLayout()

    roots = Option(loader.getRoot[jfxs.layout.AnchorPane])
    stage = new PrimaryStage():
      title = "CafeGame"
      scene = new Scene():
        root = roots.get

  private def loadGameLayout(): Unit =
    val rootResource = getClass.getResource("cafe.view/GameLayout.fxml")
    val loader = new FXMLLoader(rootResource)
    loader.load()

    gameController = new GameController()
    loader.getController[GameLayoutController].setGameController(gameController)
    gameController.setGameOverCallback(() => loadEndLayout())
    gameController.startGame()

    roots = Option(loader.getRoot[jfxs.layout.AnchorPane])
    stage.scene = new Scene():
      root = roots.get

  private def loadEndLayout(): Unit =
    val rootResource = getClass.getResource("cafe.view/EndLayout.fxml")
    val loader = new FXMLLoader(rootResource)
    loader.load()
    val controller = loader.getController[EndLayoutController]
    controller.backToHome.onAction = (_: ActionEvent) =>
      controller.playPopSound()
      loadWelcomeLayout()
    controller.setGameController(gameController)

    roots = Option(loader.getRoot[jfxs.layout.AnchorPane])
    stage.scene = new Scene():
      root = roots.get

    controller.totalMoneyEarnedLabel()
    controller.totalCustomersServedLabel()

end CafeGame
