package cafe.controller

import scalafx.scene as sfxs
import javafx.scene as jfxs
import cafe.model.{Order, ingredients}
import javafx.fxml.{FXML, FXMLLoader}
import scalafx.Includes.*
import scalafx.event.ActionEvent
import javafx.scene.control.{Button, Label}
import javafx.scene.image.{Image, ImageView}
import javafx.scene.layout.HBox
import scalafx.scene.{Parent, Scene}
import scalafx.scene.media.{Media, MediaPlayer}
import scalafx.stage.{Modality, Stage}

import java.io.File

@FXML
class GameLayoutController:

  @FXML private var tray: HBox = _
  @FXML private var item1Toggle:Button = _
  @FXML private var item2Toggle:Button = _
  @FXML private var item1: ImageView =_
  @FXML private var item2: ImageView =_
  @FXML private var currentlyMakingText: Label = _
  @FXML private var itemDescription: Label =_
  @FXML private var serve: Button = _
  @FXML private var removeItem: Button = _
  @FXML private var orderPreview1: Button = _
  @FXML private var orderPreview2: Button = _
  @FXML private var orderPreview3: Button = _
  @FXML private var viewOrder: Button =_
  @FXML private var moneyEarnedLabel: Label =_

  //ingredient fxml
  @FXML private var milk: Button = _
  @FXML private var espresso: Button =_

  private var currentItem: Int = 1
  private var playerItem1: List[String] = List()
  private var playerItem2: List[String] = List()
  private var playerItem1Desc: String = ""
  private var playerItem2Desc: String = ""
  private val clickSound: MediaPlayer = new MediaPlayer(new Media(new File("src/main/resources/cafe.media/click.mp3").toURI.toString))
  private val popSound: MediaPlayer = new MediaPlayer(new Media(new File("src/main/resources/cafe.media/pop.mp3").toURI.toString))

  //setter for game controller and injected variables from game controller
  private var gameCtrl: GameController = _
  var currentOrderIndex: Int = _
  var currentOrder: Order = _
  var playerPreparedOrder: List[List[String]] = _

  def setGameController(controller: GameController): Unit =
    this.gameCtrl = controller
    currentOrderIndex= gameCtrl.currentOrderIndex
    currentOrder= gameCtrl.currentOrder
    playerPreparedOrder = gameCtrl.playerPreparedOrder

  //initialize
  def initialize(): Unit =
    println("initialize() called")
    setupIngredientClick(milk)
    setupIngredientClick(espresso)
    currentlyMakingText.setText("Game Start!")

  //sound effects
  private def playClickSound(): Unit =
    try
      clickSound.stop()
      clickSound.play()
    catch
      case e: Exception =>
        println(s"Error playing sound: ${e.getMessage}")
  private def playPopSound(): Unit =
    try
      popSound.stop()
      popSound.seek
      popSound.play()
    catch
      case e: Exception =>
        println(s"Error playing sound: ${e.getMessage}")

  //refresh UI
  private def refreshUI(): Unit =
    // clear fields
    if item1 != null then
      item1.setImage(null)
    if item2 != null then
      item2.setImage(null)
    if playerItem1 != null then
      playerItem1 = List()
    if playerItem2 != null then
      playerItem2 = List()
    itemDescription.setText("")

  //toggle between two items
  @FXML private def itemToggle(): Unit =
    item1Toggle.onAction = (_:ActionEvent) =>
      currentItem = 1
      currentlyMakingText.setText("Currently making: Item 1")
      itemDescription.setText(playerItem1Desc)
      playPopSound()

    item2Toggle.onAction = (_:ActionEvent)=>
      currentItem = 2
      currentlyMakingText.setText("Currently making: Item 2")
      itemDescription.setText(playerItem2Desc)
      playPopSound()

  //select and view orders
  @FXML private def selectOrder1(): Unit =
    orderPreview1.onAction = (_: ActionEvent) =>
      gameCtrl.currentOrderIndex = 0
      gameCtrl.setCurrentOrder()
      println(gameCtrl.currentOrderIndex)
      println(gameCtrl.currentOrder)
  @FXML private def selectOrder2(): Unit =
    orderPreview2.onAction = (_: ActionEvent) =>
      gameCtrl.currentOrderIndex = 1
      gameCtrl.setCurrentOrder()
      println(gameCtrl.currentOrderIndex)
      println(gameCtrl.currentOrder)
  @FXML private def selectOrder3(): Unit =
    orderPreview3.onAction = (_: ActionEvent) =>
      gameCtrl.currentOrderIndex = 2
      gameCtrl.setCurrentOrder()
      println(gameCtrl.currentOrderIndex)
      println(gameCtrl.currentOrder)

  //view full order and calling it when pressing the view order button
  @FXML private def showOrderPreview():Unit=
    val resource = getClass.getResource("/cafe.view/OrderLayout.fxml")
    if (resource == null) {
      println("FXML file not found!")
    } else {
      println(s"FXML file found at: $resource")
    }
    val loader = new FXMLLoader(resource)
    loader.load()
    val orderRoot = loader.getRoot[jfxs.Parent]
    val control = loader.getController[OrderLayoutController]
    val popup: Stage = new Stage():
      initModality(Modality.ApplicationModal)
      scene = new Scene:
        root = orderRoot

    control.orderItemName(gameCtrl.currentOrder)
    popup.showAndWait()
  @FXML private def viewOrderBtn(): Unit =
    viewOrder.onAction = (_:ActionEvent) =>
      showOrderPreview()

  @FXML private def removeItemBtn(): Unit =
    playPopSound()

    if currentItem == 1 then
      if item1 != null then
        item1.setImage(null)
      if playerItem1 != null then
        playerItem1 = playerItem1.init
      itemDescription.setText("")

    if currentItem == 2 then
      if item2 != null then
        item2.setImage(null)
      if playerItem2 != null then
        playerItem1 = playerItem2.init
      itemDescription.setText("")

  //set up click action for each ingredient
  private def setupIngredientClick(ingredient: Button): Unit =
    ingredient.onAction = (_: ActionEvent) =>
      val ingredientName = ingredient.getText
      val selectedIngredient = ingredients.find(_.name == ingredientName)
      playClickSound()

      selectedIngredient match
        case Some(ingredient) =>
          val image = new Image(ingredient.image)
          if currentItem == 1 then
            item1.setImage(image)
            playerItem1 = playerItem1 :+ ingredientName
            itemDescriptionText()
          else if currentItem == 2 then
            item2.setImage(image)
            playerItem2 = playerItem2 :+ ingredientName
            itemDescriptionText()

        case None =>
          println(s"Ingredient $ingredientName not found")

  //show the ingredients selected by the players
  private def itemDescriptionText(): Unit =
    if currentItem == 1 then
      playerItem1Desc = "Item 1: " + playerItem1.mkString(" ")
      itemDescription.setText(playerItem1Desc)
    else if currentItem == 2 then
      playerItem2Desc = "Item 2: " + playerItem2.mkString(" ")
      itemDescription.setText(playerItem2Desc)

  private def moneyEarnedLabelText(): Unit =
    moneyEarnedLabel.setText(gameCtrl.moneyEarned.toString)

  //serve order, update customers and orders
  @FXML private def serveOrderBtn(): Unit =
    playerPreparedOrder = playerPreparedOrder:+ playerItem1 :+ playerItem2
    gameCtrl.orderCtrl.orderCorrect(playerPreparedOrder, gameCtrl.activeOrders(currentOrderIndex))
    moneyEarnedLabelText()

    gameCtrl.orderCtrl.updateActiveOrders()

    refreshUI()












  
