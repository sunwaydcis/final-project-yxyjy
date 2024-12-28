package cafe.controller

import cafe.model.Order
import javafx.fxml.FXML
import javafx.scene as jfxs
import jfxs.control.Label

@FXML
class OrderLayoutController:

  private var gameCtrl: GameController = _

  def setGameController(controller: GameController): Unit =
    this.gameCtrl = controller
    gameCtrl.setupOrderUpdateListener(updateTimeLeft)

  @FXML private var orderItem1 : Label = _
  @FXML private var orderItem2 : Label = _
  @FXML private var orderTimeLeft: Label = _

  //setter for game controller and injected variables from game controller
  
  @FXML def orderItemName (order: Order): Unit =
    orderItem1.setText(order.items.head.name)
    orderItem2.setText(order.items(1).name)

  private def updateTimeLeft(time: Int): Unit =
    orderTimeLeft.setText(time.toString)


