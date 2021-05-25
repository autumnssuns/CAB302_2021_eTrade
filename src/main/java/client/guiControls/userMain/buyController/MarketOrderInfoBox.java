package client.guiControls.userMain.buyController;

import client.guiControls.DataBox;
import common.Exceptions.InvalidArgumentValueException;
import common.dataClasses.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

/**
 * A GUI representation of the
 */
public class MarketOrderInfoBox extends HBox implements DataBox {
    private BuyController controller;
    private Order order;

    private Label assetName;
    private Label quantity;
    private Label price;
    private Button buyNowButton;
    private Button customButton;

    public MarketOrderInfoBox(Order order, BuyController controller) {
        this.controller = controller;
        this.order = order;
        initialize();
        load();
    }

    /**
     * Initialise the display elements and their styling.
     */
    private void initialize(){
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(20);
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

        buyNowButton = new Button("Add to Cart");
        buyNowButton.setOnAction(e -> {
            try {
                controller.buyItem(order.getAsset(), order.getPlacedQuantity() - order.getResolvedQuantity(), order.getPrice());
            } catch (InvalidArgumentValueException invalidArgumentValueException) {
                invalidArgumentValueException.printStackTrace();
            }
        });
        buyNowButton.setPrefWidth(100);

        customButton = new Button("Customise");
        customButton.setOnAction(e -> controller.customiseItem(order.getAsset(), order.getPlacedQuantity() - order.getResolvedQuantity(), order.getPrice()));
        customButton.setPrefWidth(100);

        this.getChildren().addAll(assetName, quantity, price, buyNowButton, customButton);
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

    /**
     * Request to cancel the current order (changing status to "cancelled").
     */
    public void cancelOrder() throws InvalidArgumentValueException {
        order.setStatus(Order.Status.CANCELLED);
        this.getChildren().remove(buyNowButton);
        controller.sendRequest("edit", order, Order.class);
        controller.update();
    }

    @Override
    public void load() {
        loadAssetName();
        loadQuantity();
        loadPriceLabel();
    }
}
