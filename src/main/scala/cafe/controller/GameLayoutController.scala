package cafe.controller

import cafe.model.{Order, ingredients}
import cafe.util.Sound
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene as jfxs
import javafx.scene.control.{Button, Label, MenuItem}
import javafx.scene.image.{Image, ImageView}
import javafx.scene.layout.HBox
import javafx.util.Duration
import scalafx.Includes.*
import scalafx.animation.PauseTransition
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.scene as sfxs
import scalafx.scene.Scene
import scalafx.stage.{Modality, Stage}

@FXML
class GameLayoutController:

  //fxml
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
  @FXML private var timeLeftLabel: Label =_
  @FXML private var currentOrderText: Label = _
  @FXML private var expiredOrderText: Label = _
  @FXML private var moneyEarned: Label = _
  @FXML var backToHomeBtn: MenuItem = _
  @FXML var viewCookbookBtn: MenuItem = _
  @FXML var howToPlayBtn: MenuItem = _

  //ingredient fxml
  @FXML private var milk: Button = _
  @FXML private var espresso: Button =_
  @FXML private var cup: Button = _
  @FXML private var caramelsyrup: Button = _
  @FXML private var chocolatesyrup: Button =_
  @FXML private var croissant: Button = _
  @FXML private var glass: Button = _
  @FXML private var ice: Button = _
  @FXML private var matchapowder: Button = _
  @FXML private var pistachiocake: Button = _
  @FXML private var strawberryshortcake: Button = _
  @FXML private var tiramisu: Button =_
  @FXML private var whippedcream: Button = _

  private var currentItem: Int = 1
  private var playerItem1: List[String] = List()
  private var playerItem2: List[String] = List()
  private var playerItem1Desc: String = ""
  private var playerItem2Desc: String = ""

  //game controller setup
  private var gameCtrl: GameController = _
  def setGameController(controller: GameController): Unit =
    this.gameCtrl = controller
    gameCtrl.setupTimeUpdateListener(updateTimeLeft)
    gameCtrl.setupOrderExpiredListener(order => updateExpiredOrderText(order))

  //initialize
  def initialize(): Unit =
    println("initialize() called")
    val ingredientButtons: List[Button] = List(milk, espresso, cup, caramelsyrup, chocolatesyrup, croissant, glass, ice, matchapowder, pistachiocake, strawberryshortcake, tiramisu, whippedcream)

    for ingredient <- ingredientButtons do
      setupIngredientClick(ingredient)

    currentlyMakingText.setText("Game Start!")
    moneyEarnedLabel.setText("0.00")
    Sound.playBgm()

    orderPreview1.onAction = (_: ActionEvent) => selectOrder(0)
    orderPreview2.onAction = (_: ActionEvent) => selectOrder(1)
    orderPreview3.onAction = (_: ActionEvent) => selectOrder(2)
    item1Toggle.onAction = (_: ActionEvent) => itemToggle(1)
    item2Toggle.onAction = (_: ActionEvent) => itemToggle(2)

  //toggle between two items
  private def itemToggle(item: Int): Unit =
    currentItem = item
    currentlyMakingText.setText(s"Currently making: Item $item")

    item match
      case 1 => itemDescription.setText(playerItem1Desc)
      case 2 => itemDescription.setText(playerItem2Desc)

    Sound.playPopSound()

  //select and view orders
  private def selectOrder(index: Int): Unit =

    Sound.playPopSound()
    gameCtrl.currentOrderIndex = index
    gameCtrl.updateCurrentOrder()
    val item1Name: String = gameCtrl.currentOrder.items.head.name
    val item2Name: String = gameCtrl.currentOrder.items(1).name
    val custName:String = gameCtrl.activeCustomers(gameCtrl.currentOrderIndex).name
    currentOrderText.setText(custName + "'s order: "+ item1Name + ", "+ item2Name)
    println(gameCtrl.currentOrderIndex)
    println(gameCtrl.currentOrder)

  //view full order (calls OrderLayout)
  @FXML private def showOrderPreview():Unit=
    Sound.playPopSound()
    val resource = getClass.getResource("/cafe.view/OrderLayout.fxml")
    val loader = new FXMLLoader(resource)
    loader.load()
    val orderRoot = loader.getRoot[jfxs.Parent]
    val control = loader.getController[OrderLayoutController]
    val popup: Stage = new Stage():
      initModality(Modality.ApplicationModal)
      scene = new Scene:
        root = orderRoot

    control.orderItemName(gameCtrl.currentOrder)
    control.orderName(gameCtrl.activeCustomers(gameCtrl.currentOrderIndex))
    control.customerSatisfactionLabelText(gameCtrl.activeCustomers(gameCtrl.currentOrderIndex))
    control.setGameController(gameCtrl)
    control.setOrder(gameCtrl.currentOrder)
    popup.showAndWait()

  //clear item from tray
  @FXML private def removeItemBtn(): Unit =
    Sound.playPopSound()

    if currentItem == 1 then
      if item1 != null then
        item1.setImage(null)
      if playerItem1 != null then
        playerItem1 = List()
        playerItem1Desc= ""
      itemDescription.setText("")

    if currentItem == 2 then
      if item2 != null then
        item2.setImage(null)
      if playerItem2 != null then
        playerItem2 = List()
        playerItem2Desc = ""
      itemDescription.setText("")

  //set up click action for each ingredient
  private def setupIngredientClick(ingredient: Button): Unit =
    ingredient.onAction = (_: ActionEvent) =>
      val ingredientName = ingredient.getText
      val selectedIngredient = ingredients.find(_.name == ingredientName)
      Sound.playClickSound()

      selectedIngredient match
        case Some(ingredient) =>
          val image = new Image(ingredient.image)
          if currentItem == 1 then
            item1.setImage(image)
            playerItem1 = playerItem1 :+ ingredientName
            observeItem(1)
            itemDescriptionText()
          else if currentItem == 2 then
            item2.setImage(image)
            playerItem2 = playerItem2 :+ ingredientName
            observeItem(2)
            itemDescriptionText()

        case None =>
          println(s"Ingredient $ingredientName not found")

  //check if player prepared item matches final item
  private def observeItem(itemNumber: Int): Unit =
    val (playerItem, recipeItem, imageView) = itemNumber match
      case 1 => (playerItem1, gameCtrl.currentOrder.items.head, item1)
      case 2 => (playerItem2, gameCtrl.currentOrder.items(1), item2)

    if playerItem.sorted == recipeItem.ingredients.sorted then
      Sound.playCorrectSound()
      imageView.setImage(new Image(recipeItem.finalPic))


  //serve order, update customers and orders
  @FXML private def serveOrderBtn(): Unit =
    Sound.playKachingSound()

    gameCtrl.playerPreparedOrder = List(playerItem1, playerItem2)
    println(gameCtrl.playerPreparedOrder)
    gameCtrl.whenOrderDone()
    moneyEarnedLabelText()

    refreshUI()

  //===UI UPDATES====
  //refresh UI
  private def refreshUI(): Unit =
    // clear fields
    if playerItem1 != null then
      playerItem1 = List()
      playerItem1Desc = ""

    if playerItem2 != null then
      playerItem2 = List()
      playerItem2Desc = ""

    if item1 != null then
      item1.setImage(null)

    if item2 != null then
      item2.setImage(null)

    currentOrderText.setText("Customer's order:")
    itemDescription.setText("")

  //update ingredients selected by the players
  private def itemDescriptionText(): Unit =
    if currentItem == 1 then
      playerItem1Desc = "Item 1: " + playerItem1.mkString(" ")
      itemDescription.setText(playerItem1Desc)
    else if currentItem == 2 then
      playerItem2Desc = "Item 2: " + playerItem2.mkString(" ")
      itemDescription.setText(playerItem2Desc)

  //update expired order
  private def updateExpiredOrderText(order:Order): Unit =
    val item1 = order.items.head.name
    val item2 = order.items(1).name
    expiredOrderText.setText(s"Order " + item1 + ", " + item2 + " has expired!")

    val resetMessage = new PauseTransition(Duration.seconds(3)) // 1-second delay
    resetMessage.onFinished = _ => expiredOrderText.setText("Time is ticking...")
    resetMessage.play()

  //update money earned
  private def moneyEarnedLabelText(): Unit =
    moneyEarnedLabel.setText(gameCtrl.moneyEarned.toString)

  //update timer UI
  private def updateTimeLeft(time: Int): Unit =
    timeLeftLabel.setText(time.toString)



