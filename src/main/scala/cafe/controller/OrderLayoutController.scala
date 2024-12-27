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
  
  @FXML def orderItemName (order: Order): Unit =
    orderItem1.setText(order.items.head.name)
    orderItem2.setText(order.items(1).name)


