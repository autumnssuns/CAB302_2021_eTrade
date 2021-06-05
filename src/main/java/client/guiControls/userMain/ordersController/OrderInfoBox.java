package client.guiControls.userMain.ordersController;

import client.IViewUnit;
import client.guiControls.DisplayController;
import common.Exceptions.InvalidArgumentValueException;
import common.Request;
import common.dataClasses.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

/**
 * A GUI representation of the
 */
public class OrderInfoBox extends HBox implements IViewUnit {
    private OrdersController controller;
    private Order order;

    private Label id;
    private Label assetName;
    private Label placedQuantity;
    private Label resolvedQuantity;
    private Label price;
    private Label orderDate;
    private Label finishDate;
    private Label status;
    private Button cancelButton;

    public OrderInfoBox(Order order, OrdersController controller) {
        this.controller = controller;
        this.order = order;
        initialize();
        load();
    }

    /**
     * Initialise the display elements and their styling.
     */
    @Override
    public void initialize(){
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(10);
        this.setPrefHeight(80);
        this.getStyleClass().add("whitePane");

        id = new Label();
        id.getStyleClass().add("blackLabel");
        id.setPrefWidth(100);

        assetName = new Label();
        assetName.getStyleClass().add("blackLabel");
        assetName.setPrefWidth(200);

        placedQuantity = new Label();
        placedQuantity.getStyleClass().add("blackLabel");
        placedQuantity.setPrefWidth(100);

        resolvedQuantity = new Label();
        resolvedQuantity.getStyleClass().add("blackLabel");
        resolvedQuantity.setPrefWidth(100);

        price = new Label();
        price.getStyleClass().add("blackLabel");
        price.setPrefWidth(100);

        orderDate = new Label();
        orderDate.getStyleClass().add("blackLabel");
        orderDate.setPrefWidth(190);

        finishDate = new Label();
        finishDate.getStyleClass().add("blackLabel");
        finishDate.setPrefWidth(190);

        status = new Label();
        status.getStyleClass().add("blackLabel");
        status.setPrefWidth(120);

        this.getChildren().addAll(id, assetName, placedQuantity, resolvedQuantity, price, orderDate, finishDate, status);

        if (order.getStatus().equals(Order.Status.PENDING)){
            cancelButton = new Button("Cancel");
            cancelButton.setOnAction(e -> {
                try {
                    cancelOrder();
                } catch (InvalidArgumentValueException invalidArgumentValueException) {
                    invalidArgumentValueException.printStackTrace();
                }
            });
            cancelButton.setPrefWidth(100);
            this.getChildren().add(cancelButton);
        }
    }

    private void loadId(){
        id.setText(String.valueOf(order.getOrderId()));
    }

    private void loadAssetName(){
        assetName.setText(String.valueOf(order.getAssetId()));
    }

    private void loadPlacedQuantity(){
        placedQuantity.setText(String.valueOf(order.getPlacedQuantity()));
    }

    private void loadResolvedQuantity(){
        resolvedQuantity.setText(String.valueOf(order.getResolvedQuantity()));
    }

    private void loadPriceLabel(){
        price.setText(String.valueOf(order.getPrice()));
    }

    private void loadOrderDateLabel(){
        orderDate.setText(String.valueOf(order.getOrderDate()));
    }

    private void loadFinishDateLabel(){
        finishDate.setText(String.valueOf(order.getFinishDate()));
    }

    private void loadStatusLabel(){
        status.setText(String.valueOf(order.getStatus()));
    }

    public void setController(OrdersController ordersController) {
        this.controller = ordersController;
    }

    /**
     * Request to cancel the current order (changing status to "cancelled").
     */
    public void cancelOrder() throws InvalidArgumentValueException {
        order.setStatus(Order.Status.CANCELLED);
        this.getChildren().remove(cancelButton);
        controller.sendRequest(Request.ActionType.UPDATE, order, Request.ObjectType.ORDER);
        controller.update();
    }

    @Override
    public void load() {
        loadId();
        loadAssetName();
        loadPlacedQuantity();
        loadPriceLabel();
        loadResolvedQuantity();
        loadStatusLabel();
        loadOrderDateLabel();
        loadFinishDateLabel();
    }
}
