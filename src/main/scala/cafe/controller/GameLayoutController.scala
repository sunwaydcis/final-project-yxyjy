package cafe.controller

import cafe.model.ingredients
import javafx.fxml.FXML
import scalafx.Includes.*
import scalafx.event.ActionEvent
import javafx.scene.control.{Button, Label}
import javafx.scene.image.{Image, ImageView}
import javafx.scene.layout.HBox
import javafx.scene.text.Text

@FXML
class GameLayoutController:

  @FXML private var tray: HBox = _
  @FXML private var item1Toggle:Button = _
  @FXML private var item2Toggle:Button = _
  @FXML private var item1: ImageView =_
  @FXML private var item2: ImageView =_
  @FXML private var currentlyMakingText: Label = _
  @FXML private var serve: Button = _
  @FXML private var removeItem: Button = _

  //ingredient fxml
  @FXML private var milk: Button = _

  private var currentItem: Int = 1
  private var playerItem1: List[String] = List()
  private var playerItem2: List[String] = List()

  def initialize(): Unit =
    println("initialize() called")
    setupIngredientClick(milk)
    currentlyMakingText.setText("Game Start!")

  @FXML private def itemToggle(): Unit =
    item1Toggle.onAction = (_:ActionEvent) =>
      currentItem = 1
      currentlyMakingText.setText("Currently making: Item 1")

    item2Toggle.onAction = (_:ActionEvent)=>
      currentItem = 2
      currentlyMakingText.setText("Currently making: Item 2")

  @FXML private def serveOrderBtn(): Unit =
    item1.setImage(null)
    item2.setImage(null)

  @FXML private def removeItemBtn(): Unit =
    if currentItem == 1 then
      item1.setImage(null)
    if currentItem == 2 then
      item2.setImage(null)

  private def setupIngredientClick(ingredient: Button): Unit =
    ingredient.onAction = (_: ActionEvent) =>
      val ingredientName = ingredient.getText
      val selectedIngredient = ingredients.find(_.name == ingredientName)

      selectedIngredient.match
        case Some(ingredient) =>
          val image = new Image(ingredient.image)
          if currentItem == 1 then
            item1.setImage(image)
            playerItem1 = playerItem1 :+ ingredientName
          else if currentItem == 2 then
            item2.setImage(image)
            playerItem2 = playerItem2 :+ ingredientName

        case None =>
          println(s"Ingredient $ingredientName not found")







  
