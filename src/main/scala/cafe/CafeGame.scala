package cafe

import cafe.controller.{EndLayoutController, GameController, GameLayoutController, HelpLayoutController, WelcomeLayoutController}
import cafe.util.Sound
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
import scalafx.stage.{Modality, Stage}

/**
 * CAFE GAME OBJECT - MAIN APP 
 */
object CafeGame extends JFXApp3:
  /** the root pane for the stage - default set to none */
  private var roots: Option[sfxs.layout.AnchorPane] = None

  /** initiate GameController instance */
  private var gameController: GameController = _
  /** initiate popup stage */
  private var popupStage: Stage = _

  /** start method calls welcome layout upon running */
  override def start(): Unit =
    loadWelcomeLayout()

  /**
   * load welcome layout
   * set button actions to navigate to other pages
   */
  private def loadWelcomeLayout(): Unit =
    val rootResource = getClass.getResource("/cafe.view/WelcomeLayout.fxml")
    val loader = new FXMLLoader(rootResource)
    loader.load()

    val welcomeController = loader.getController[WelcomeLayoutController]
    welcomeController.startGameBtn.onAction = (_: ActionEvent) =>
      Sound.playWelcomeSound()
      Sound.stopBgm()
      loadGameLayout()
    welcomeController.helpBtn.onAction = (_: ActionEvent) =>
      Sound.playPopSound()
      loadHelp1Layout()
    welcomeController.cookbookBtn.onAction = (_:ActionEvent) =>
      Sound.playPopSound()
      loadCookbookLayout()

    roots = Option(loader.getRoot[jfxs.layout.AnchorPane])
    stage = new PrimaryStage():
      title = "CafeGame"
      scene = new Scene():
        root = roots.get

  /**
   * load game layout
   * set game controllers and set menu item actions
   */
  private def loadGameLayout(): Unit =
    val rootResource = getClass.getResource("/cafe.view/GameLayout.fxml")
    val loader = new FXMLLoader(rootResource)
    loader.load()

    gameController = new GameController()
    val control = loader.getController[GameLayoutController]
    control.setGameController(gameController)
    control.backToHomeBtn.onAction = (_:ActionEvent) =>
      loadWelcomeLayout()
    control.howToPlayBtn.onAction = (_:ActionEvent) =>
      loadHelp1Layout()
    control.viewCookbookBtn.onAction = (_:ActionEvent) =>
      loadCookbookLayout()
    gameController.setGameOverCallback(() => loadEndLayout())
    gameController.startGame()

    roots = Option(loader.getRoot[jfxs.layout.AnchorPane])
    stage.scene = new Scene():
      root = roots.get

  /**
   * load end layout
   * set button action
   */
  private def loadEndLayout(): Unit =
    val rootResource = getClass.getResource("/cafe.view/EndLayout.fxml")
    val loader = new FXMLLoader(rootResource)
    loader.load()
    Sound.playWelcomeSound()
    val controller = loader.getController[EndLayoutController]
    controller.backToHome.onAction = (_: ActionEvent) =>
      Sound.stopBgm()
      Sound.playPopSound()
      loadWelcomeLayout()
    controller.setGameController(gameController)

    roots = Option(loader.getRoot[jfxs.layout.AnchorPane])
    stage.scene = new Scene():
      root = roots.get

    controller.totalMoneyEarnedLabel()
    controller.totalCustomersServedLabel()

  /**
   * load page 1 of how-to-play layout
   * sets next page button action
   * opens the popup stage
   */
  private def loadHelp1Layout(): Unit =
    val rootResource = getClass.getResource("/cafe.view/HelpLayout.fxml")
    val loader = new FXMLLoader(rootResource)
    loader.load()

    val helpRoot = loader.getRoot[jfxs.Parent]
    val control = loader.getController[HelpLayoutController]

    control.nextPage.onAction = (_:ActionEvent) =>
      Sound.playPopSound()
      loadHelp2Layout()

    if popupStage == null then
      popupStage = new Stage():
        initModality(Modality.ApplicationModal)
    popupStage.scene = new Scene(helpRoot)

    if !popupStage.isShowing then
      popupStage.showAndWait()

  /**
   * load page 2 of how-to-play layout
   * sets previous page button action
   * switches scene for popup stage
   */
  private def loadHelp2Layout(): Unit =
    val rootResource = getClass.getResource("/cafe.view/HelpLayout2.fxml")
    val loader = new FXMLLoader(rootResource)
    loader.load()

    val helpRoot = loader.getRoot[jfxs.Parent]
    val control = loader.getController[HelpLayoutController]

    control.prevPage.onAction = (_: ActionEvent) =>
      Sound.playPopSound()
      loadHelp1Layout()

    if popupStage == null then
      popupStage = new Stage():
        initModality(Modality.ApplicationModal)
    popupStage.scene = new Scene(helpRoot)
    popupStage.showAndWait()

  /**
   * loads cookbook layout
   */
  private def loadCookbookLayout(): Unit =
    val rootResource = getClass.getResource("/cafe.view/CookbookLayout.fxml")
    val loader = new FXMLLoader(rootResource)
    loader.load()

    val cookbookRoot = loader.getRoot[jfxs.Parent]

    if popupStage == null then
      popupStage = new Stage():
        initModality(Modality.ApplicationModal)
    popupStage.scene = new Scene(cookbookRoot)

    popupStage.showAndWait()

end CafeGame
