package cafe.controller

import javafx.fxml.FXML
import scalafx.scene.image.ImageView
import scalafx.scene.layout.HBox
import scalafx.scene.text.Text

@FXML
class GameLayoutController:

  @FXML private var milk: ImageView = _
  @FXML private var tray: HBox = _

  private var playerItem1: List[String] = List()
  private var playerItem2: List[String] = List()
  private var currentItem: Int = 1

  // The initialize method is called after FXML is loaded and the fields are injected
  def initialize(): Unit =
    // Setup the click action for the milk ingredient
    setupIngredientClick(milk, "Milk")
    // Initialize the tray display with the first item (empty initially)
    updateTrayDisplay(playerItem1)

  // Sets up the click handler for ingredients (e.g., milk)
  private def setupIngredientClick(imageView: ImageView, ingredientName: String): Unit =
    imageView.onMouseClicked = _ => 
      if currentItem == 1then
        playerItem1 = playerItem1 :+ ingredientName
        updateTrayDisplay(playerItem1)
      else
        playerItem2 = playerItem2 :+ ingredientName
        updateTrayDisplay(playerItem2)
      
  // Updates the tray to show the current ingredients
  def updateTrayDisplay(currentIngredients: List[String]): Unit =
    tray.children.clear()  // Clear any existing children
    for ingredient <- currentIngredients do
      tray.children.add(new Text(ingredient))
  
