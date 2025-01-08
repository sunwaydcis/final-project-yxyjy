package cafe.controller

import cafe.model.{Customer, Order}
import javafx.fxml.FXML
import javafx.scene as jfxs
import javafx.scene.text.Text
import jfxs.control.Label
import scalafx.scene.image.{Image, ImageView}

/** 
 * ORDER LAYOUT CONTROLLER - FXML CONTROLLER FOR ORDER PREVIEW
 */
@FXML
class OrderLayoutController:
  /** game controller instance */
  private var gameCtrl: GameController = _
  /** order instance */
  private var order: Order = _
  /** order update listener */
  private var orderUpdateListener: Int => Unit = _

  /** set game controller for current game */
  def setGameController(controller: GameController): Unit =
    this.gameCtrl = controller

  /** set order to preview and update the order's time left */
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

  /** set name of customer */
  @FXML def orderName (customer:Customer): Unit =
    orderName.setText(customer.name + "'s Order")
  
  /** set item names of order */
  @FXML def orderItemName (order: Order): Unit =
    orderItem1.setText(order.items.head.name)
    orderItem2.setText(order.items(1).name)

  /** set label of customer's satisfaction level */
  @FXML def customerSatisfactionLabelText(customer: Customer): Unit =
    customerSatisfactionLabel.setText(customer.satisfaction.toString)

  /** update time left of order */
  private def updateTimeLeft(time: Int): Unit =
    orderTimeLeft.setText(time.toString)


