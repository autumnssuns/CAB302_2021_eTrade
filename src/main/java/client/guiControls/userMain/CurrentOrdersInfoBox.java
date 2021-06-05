package client.guiControls.userMain;

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
public class CurrentOrdersInfoBox extends HBox implements IViewUnit {
    private DisplayController controller;
    private Order order;

    private Label assetName;
    private Label quantity;
    private Label price;
    private Button cancelButton;
    private Button customButton;

    public CurrentOrdersInfoBox(Order order, DisplayController controller) {
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
        this.setSpacing(3);
        this.setPrefHeight(80);
        this.getStyleClass().add("whitePane");

        assetName = new Label();
        assetName.getStyleClass().add("blackLabel");
        assetName.setPrefWidth(200);

        quantity = new Label();
        quantity.getStyleClass().add("blackLabel");
        quantity.setPrefWidth(75);

        price = new Label();
        price.getStyleClass().add("blackLabel");
        price.setPrefWidth(75);

        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            try {
                cancelOrder();
            } catch (InvalidArgumentValueException invalidArgumentValueException) {
                invalidArgumentValueException.printStackTrace();
            }
        });
        cancelButton.setPrefWidth(80);
        cancelButton.setStyle("-fx-font-size: 10");

        this.getChildren().addAll(assetName, quantity, price, cancelButton);
    }

    private void loadAssetName(){
        assetName.setText(String.valueOf(order.getAsset().getName()));
    }

    private void loadQuantity(){
        quantity.setText(String.valueOf(order.getPlacedQuantity() - order.getResolvedQuantity()));
    }

    private void loadPriceLabel(){
        price.setText(String.valueOf(order.getPrice()));
    }

    @Override
    public void load() {
        loadAssetName();
        loadQuantity();
        loadPriceLabel();
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
}