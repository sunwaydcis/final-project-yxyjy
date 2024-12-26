package cafe.controller

import cafe.model.{Order, ingredients}
import javafx.fxml.FXML
import scalafx.Includes.*
import scalafx.event.ActionEvent
import javafx.scene.control.{Button, Label}
import javafx.scene.image.{Image, ImageView}
import javafx.scene.layout.HBox
import scalafx.scene.media.{Media, MediaPlayer}

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

  //ingredient fxml
  @FXML private var milk: Button = _

  private var currentItem: Int = 1
  private var playerItem1: List[String] = List()
  private var playerItem2: List[String] = List()
  private var playerPreparedOrder: List[List[String]] = List(List())
  private var playerItem1Desc: String = ""
  private var playerItem2Desc: String = ""
  private val clickSound: MediaPlayer = new MediaPlayer(new Media(new File("src/main/resources/cafe.media/click.mp3").toURI.toString))
  private val popSound: MediaPlayer = new MediaPlayer(new Media(new File("src/main/resources/cafe.media/pop.mp3").toURI.toString))
  private val orderCtrl: OrderController = new OrderController
  private var activeOrders: List[Order] = List()
  private var currentOrder: Order = activeOrders.head

  def initialize(): Unit =
    println("initialize() called")
    setupIngredientClick(milk)
    currentlyMakingText.setText("Game Start!")
    

  private def playClickSound(): Unit =
    try
      clickSound.stop()
      clickSound.play()
    catch
      case e: Exception =>
        println(s"Error playing sound: ${e.getMessage}")

  private def playPopSound(): Unit =
    try
      popSound.play()
      popSound.stop()
    catch
      case e: Exception =>
        println(s"Error playing sound: ${e.getMessage}")

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

  private def itemDescriptionText():Unit =
    if currentItem == 1 then
      playerItem1Desc = "Item 1: " + playerItem1.mkString(" ")
      itemDescription.setText(playerItem1Desc)
    else if currentItem == 2 then
      playerItem2Desc = "Item 2: " + playerItem2.mkString(" ")
      itemDescription.setText(playerItem2Desc)

  @FXML private def selectOrder1(): Unit =
    orderPreview1.onAction = (_:ActionEvent) =>
      if activeOrders.head != null then
        currentOrder = activeOrders.head
        println("Order 1 selected")
      else
        println("No order found!")

  @FXML private def selectOrder2(): Unit =
    orderPreview2.onAction = (_: ActionEvent) =>
      if activeOrders(1) != null then
        currentOrder = activeOrders(1)
        println("Order 2 selected")
      else
        println("No order found!")

  @FXML private def selectOrder3(): Unit =
    orderPreview3.onAction = (_: ActionEvent) =>
      if activeOrders(2) != null then
        currentOrder = activeOrders(2)
        println("Order 3 selected")
      else
        println("No order found!")

  @FXML private def serveOrderBtn(): Unit =
    playerPreparedOrder = playerPreparedOrder:+ playerItem1 :+ playerItem2
    orderCtrl.orderCorrect(playerPreparedOrder, currentOrder)

    if item1 != null then
      item1.setImage(null)
    if item2 != null then
      item2.setImage(null)
    if playerItem1 != null then
      playerItem1 = List(null)
    if playerItem2 != null then
      playerItem2 = List(null)
    itemDescription.setText("")

  @FXML private def removeItemBtn(): Unit =
    if currentItem == 1 then
      if item1 != null then
        item1.setImage(null)
      if playerItem1 != null then
        playerItem1 = playerItem1.drop(playerItem1.size-1)
      itemDescription.setText("")
      playPopSound()

    if currentItem == 2 then
      if item2 != null then
        item2.setImage(null)
      if playerItem2 != null then
        playerItem2 = playerItem2.drop(playerItem2.size - 1)
      itemDescription.setText("")
      playPopSound()

  private def setupIngredientClick(ingredient: Button): Unit =
    ingredient.onAction = (_: ActionEvent) =>
      val ingredientName = ingredient.getText
      val selectedIngredient = ingredients.find(_.name == ingredientName)
      playClickSound()

      selectedIngredient.match
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







  
