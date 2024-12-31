package cafe.controller

import cafe.model.{Customer, Order}
import javafx.fxml.FXML
import javafx.scene as jfxs
import javafx.scene.text.Text
import jfxs.control.Label
import scalafx.scene.image.{Image, ImageView}

@FXML
class OrderLayoutController:

  private var gameCtrl: GameController = _
  private var order: Order = _
  private var orderUpdateListener: Int => Unit = _

  def setGameController(controller: GameController): Unit =
    this.gameCtrl = controller

  def setOrder(order: Order): Unit =
    this.order = order
    updateTimeLeft(order.orderTimeLeft)

    orderUpdateListener = (remainingTime: Int) =>
      if order == this.order then
        updateTimeLeft(remainingTime)
    gameCtrl.orderCtrl.setupOrderUpdateListener(orderUpdateListener)

  @FXML private var orderItem1 : Label = _
  @FXML private var orderItem2 : Label = _
  @FXML private var orderTimeLeft: Label = _
  @FXML private var customerPic: ImageView = _
  @FXML private var orderName: Text = _
  @FXML private var customerSatisfactionLabel: Label = _

  //setter for game controller and injected variables from game controller

  @FXML def orderName (customer:Customer): Unit =
    orderName.setText(customer.name + "'s Order")
  
  @FXML def orderItemName (order: Order): Unit =
    orderItem1.setText(order.items.head.name)
    orderItem2.setText(order.items(1).name)

  @FXML def customerSatisfactionLabelText(customer: Customer): Unit =
    customerSatisfactionLabel.setText(customer.satisfaction.toString)

  private def updateTimeLeft(time: Int): Unit =
    orderTimeLeft.setText(time.toString)


