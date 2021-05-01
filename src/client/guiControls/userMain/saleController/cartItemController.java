package client.guiControls.userMain.saleController;
import client.Main;
import client.data.MainController;
import client.data.sessionalClasses.Cart;
import common.dataClasses.CartItem;
import common.dataClasses.Asset;
import common.dataClasses.Item;
import common.dataClasses.Stock;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
public class cartItemController {

        //Reusable elements that can be updated
        Label cartTotalLabel;

        @FXML Pane assetsPane;
        @FXML Pane filterPane;
        @FXML
        AnchorPane anchorPane;
        @FXML Pane shippingPane;
        @FXML ScrollPane stockScroller;
        @FXML VBox stockBox;

        /*@FXML
        public void initialize(){
            Update();
        }*/

    // Displays the items on the cart pane

    /**
     * Display the cart item
     * @param displayIndex the stock ID
     */
    private void displayCartItem(int displayIndex){
        CartItem itemToDisplay = Main.mainController.getShippingCart().get(displayIndex);

        HBox cartBox = new HBox();
        cartBox.setPrefHeight(80);
        cartBox.setLayoutX(16);
        cartBox.setLayoutY(100 + 100 * displayIndex);
        cartBox.setAlignment(Pos.CENTER_LEFT);
        cartBox.setSpacing(20);
        cartBox.getStyleClass().add("assetInfoBox");

        ScrollPane scroller = new ScrollPane();
        scroller.getContent();
        scroller.setLayoutX(16);
        scroller.setLayoutY(100);

        VBox cartItemNameBox = new VBox();
        cartItemNameBox.setAlignment(Pos.CENTER);
        cartItemNameBox.setSpacing(10);
        Label itemNameLabel = new Label(itemToDisplay.getName());
        itemNameLabel.getStyleClass().add("blackLabel");
        Button removeButton = new Button("Remove");
        removeButton.getStyleClass().addAll("transparentButton", "smallTextField");
        cartItemNameBox.getChildren().addAll(itemNameLabel, removeButton);

        VBox descriptionBox = new VBox();
        descriptionBox.setPrefWidth(120);
        descriptionBox.setMaxHeight(60);
        descriptionBox.setAlignment(Pos.CENTER_LEFT);

        // TODO: Update when the text field values are changed.
        HBox quantityBox = new HBox();
        Label quantityLabel = new Label("Quantity: ");
        TextField quantityTextField = new TextField(String.valueOf(itemToDisplay.getQuantity()));
        quantityTextField.getStyleClass().addAll("smallTextField");
        quantityTextField.setPrefWidth(30);
        quantityLabel.getStyleClass().add("blackLabel");
        quantityBox.getChildren().addAll(quantityLabel, quantityTextField);

        HBox priceBox = new HBox();
        Label priceLabel = new Label("Price: ");
        TextField priceTextField = new TextField(String.valueOf(itemToDisplay.getPrice()));
        priceTextField.getStyleClass().addAll("smallTextField");
        priceTextField.setPrefWidth(30);
        priceLabel.getStyleClass().add("blackLabel");
        priceBox.getChildren().addAll(priceLabel, priceTextField);

        descriptionBox.getChildren().addAll(quantityBox, priceBox);

        Label itemTotalLabel = new Label("Total: " + itemToDisplay.getTotalPrice());
        itemTotalLabel.setPrefWidth(170);
        itemTotalLabel.setPrefHeight(60);
        itemTotalLabel.getStyleClass().add("itemTotalLabel");
        itemTotalLabel.getStyleClass().add("blackLabel");

        cartBox.getChildren().addAll(cartItemNameBox, descriptionBox, itemTotalLabel);

        this.cartTotalLabel = new Label();
        cartTotalLabel.setPrefWidth(220);
        cartTotalLabel.setPrefHeight(70);
        cartTotalLabel.setLayoutX(16);
        cartTotalLabel.setLayoutY(567);
        cartTotalLabel.getStyleClass().add("cartTotalLabel");
        cartTotalLabel.getStyleClass().add("blackLabel");

        Button checkOutButton = new Button("Check Out");
        checkOutButton.setPrefWidth(166);
        checkOutButton.setPrefHeight(70);
        checkOutButton.setLayoutX(244);
        checkOutButton.setLayoutY(567);
        checkOutButton.getStyleClass().add("greenButton");

        checkOutButton.setOnAction((e) -> {
            checkOut();
        });

        shippingPane.getChildren().addAll(cartBox, cartTotalLabel, checkOutButton);
        cartTotalLabel.setText("TOTAL: " + Main.mainController.getShippingCart().getTotalPrice());
        System.out.println("Success");
    }

    /**
     * Check out functions when the check out button is clicked
     */
    public void checkOut(){
        Main.mainController.getShippingCart().clear();
        //Update();
    }

    private void removeCartItem(){

    }

}
