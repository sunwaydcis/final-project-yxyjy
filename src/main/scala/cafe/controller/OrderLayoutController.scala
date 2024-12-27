package cafe.controller

import cafe.model.Order
import javafx.fxml.FXML
import javafx.scene as jfxs
import jfxs.control.Label

@FXML
class OrderLayoutController:

  @FXML private var orderItem1 : Label = _
  @FXML private var orderItem2 : Label = _

  //setter for game controller and injected variables from game controller
  private var gameCtrl: GameController = _

  def setGameController(controller: GameController): Unit =
    this.gameCtrl = controller

  var currentOrderIndex: Int = gameCtrl.currentOrderIndex
  var currentOrder: Order = gameCtrl.currentOrder

  @FXML private def orderItemName (order: Order): Unit =
    orderItem1.setText(currentOrder.items.head.name)
    orderItem2.setText(currentOrder.items(1).name)


