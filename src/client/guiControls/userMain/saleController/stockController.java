package client.guiControls.userMain.saleController;
import client.data.sessionalClasses.Cart;
import common.dataClasses.CartItem;
import common.dataClasses.Item;
import common.dataClasses.Stock;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
/*import main.Main;
import main.MyListener;
import model.Fruit;*/

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
public class stockController {
    //Reusable elements that can be updated
    Label cartTotalLabel;
    Cart shippingCart;  // The "sell" cart
    Stock stock;        // The stock of assets by the current organisational unit

    @FXML
    Pane assetsPane;
    @FXML Pane filterPane;
    @FXML
    AnchorPane anchorPane;
    @FXML Pane shippingPane;
    @FXML ScrollPane stockScroller;
    @FXML VBox stockBox;

    @FXML
    public void initialize(){
        stock = new Stock(0);
        Update();
    }

    // Update the two dynamic panes
    public void Update(){
        stockBox.getChildren().clear();
        stock.removeIf(Item::isOutOfStock);
        for (int i = 0; i < stock.size(); i++){
            displayStockItem(i);
        }

        /*shippingPane.getChildren().clear();
        for (int j = 0; j < Main.session.getShippingCart().size(); j++){
            System.out.println(j);
            displayCartItem(j);
        }*/
    }
    // Displays the items on the stock pane
    private void displayStockItem(int displayIndex){
        Item itemToDisplay = stock.get(displayIndex);

        HBox assetBox = new HBox();
        assetBox.setPrefWidth(1000);
        assetBox.setPrefHeight(200);
        assetBox.setLayoutX(50);
        assetBox.setLayoutY(100 + 240* displayIndex);
        assetBox.setSpacing(40);
        assetBox.setAlignment(Pos.CENTER_LEFT);
        assetBox.getStyleClass().add("whitePane");

        //@TODO: Replace _imagePlaceHolder with asset image

        VBox descriptionBox = new VBox();
        descriptionBox.setPrefWidth(140);
        descriptionBox.setMaxHeight(140);
        descriptionBox.setAlignment(Pos.CENTER);
        descriptionBox.setLayoutX(50);
        descriptionBox.getStyleClass().add("assetInfoBox");
        descriptionBox.setSpacing(20);

        Button historyButton = new Button("History");
        historyButton.setPrefWidth(140);
        historyButton.setPrefHeight(40);
        historyButton.getStyleClass().add("greenButton");

        Label nameLabel = new Label(itemToDisplay.getName());
        nameLabel.getStyleClass().add("blackLabel");

        Label quantityLabel = new Label("Quantity " + itemToDisplay.getQuantity());
        quantityLabel.setPrefWidth(315);

        descriptionBox.getChildren().addAll(nameLabel, quantityLabel, historyButton);

        VBox saleBox = new VBox();
        saleBox.setAlignment(Pos.CENTER);
        saleBox.setSpacing(20);

        TextField quantityTextField = new TextField();
        quantityTextField.setPromptText("Quantity");
        quantityTextField.setPrefWidth(315);

        TextField priceTextField = new TextField();
        priceTextField.setPromptText("Price");
        priceTextField.setPrefWidth(315);

        saleBox.getChildren().addAll(quantityTextField, priceTextField);

        Button orderButton = new Button("Sell");
        orderButton.setPrefHeight(56);
        orderButton.setPrefWidth(140);
        orderButton.getStyleClass().add("greenButton");
        orderButton.setOnAction((e) -> {
            try {
                Item itemToAdd = stock.get(displayIndex);
                int quantity = Integer.parseInt(quantityTextField.getText());
                float price = Float.parseFloat(priceTextField.getText());
                placeOrder(e, itemToAdd, quantity, price);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        assetBox.getChildren().addAll(descriptionBox, saleBox, orderButton);
        stockBox.getChildren().addAll(assetBox);
        stockScroller.autosize();
    }

    public void placeOrder(ActionEvent event, Item itemToAdd, int quantity, float price) throws Exception {
        if (itemToAdd.getQuantity() < quantity){
            throw new Exception("Maximum quantity to add is " + itemToAdd.getQuantity());
        }
        CartItem newItem = itemToAdd.moveToCart(quantity, price);
        shippingCart.add(newItem);
        Update();
    }
    public void displayOrder(){
        //CartItem newItem = new CartItem, 50 , 20 );
        //return newItem;
    }
}
