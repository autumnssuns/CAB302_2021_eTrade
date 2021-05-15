package client.guiControls.userMain.buyController;

import client.data.sessionalClasses.Cart;
import client.guiControls.adminMain.usersController.UserInfoBox;
import client.guiControls.userMain.saleController.cartItemController;
import client.guiControls.userMain.saleController.stockController;
import common.dataClasses.Asset;
import common.dataClasses.CartItem;
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

import java.io.IOException;


public class BuyController {
    Cart shippingCart;  // The "sell" cart
    Stock stock;        // The stock of assets by the current organisation

    //Reusable elements that can be updated
    Label cartTotalLabel;

    @FXML Pane assetsPane;
    @FXML Pane filterPane;
    @FXML
    AnchorPane anchorPane;
    @FXML Pane shippingPane;
    @FXML ScrollPane stockScroller;
    @FXML VBox stockBox;
    @FXML Button checkOutButton;

    @FXML
    public void initialize(){
        shippingCart = new Cart("sell");
        stock = new Stock(0);   //TODO: link the stock to the organisation
        Update();
    }

    //TODO: Connect and display asset based on database.
    public void addAsset(){
        Asset asset = new Asset(0, "Item" + stock.size(), "");
        stock.add(new Item(asset, 99));
        checkOutButton.setVisible(true);
        Update();
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

    // Displays the items on the cart pane
    private void displayCartItem(int displayIndex){
        CartItem itemToDisplay = shippingCart.get(displayIndex);

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
        removeButton.setOnAction((event) -> {
            System.out.println("Button clicked");
        });
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
        cartTotalLabel.setText("TOTAL: " + shippingCart.getTotalPrice());
        System.out.println("Success");
    }

    // Update the two dynamic panes
    public void Update(){
        stockBox.getChildren().clear();
        stock.removeIf(Item::isOutOfStock);
        for (int i = 0; i < stock.size(); i++){
            displayStockItem(i);
        }

        //shippingPane.getChildren().clear();
        for (int j = 0; j < shippingCart.size(); j++){
            System.out.println(j);
            displayCartItem(j);
        }
    }

    //TODO: Connect and make change to database.
    public void startOrder(ActionEvent event, int assetIndex, int quantity, float price) throws IOException {
        Item itemToAdd = stock.get(assetIndex);
        filterPane.setVisible(true);

        Pane orderPane = new Pane();
        orderPane.setPrefWidth(1250);
        orderPane.setPrefHeight(700);
        orderPane.setLayoutX((1920 - 1250)/2);
        orderPane.setLayoutY((1080 - 700)/2);
        orderPane.getStyleClass().add("bodyPane");

        Rectangle _imagePlaceholder = new Rectangle();
        _imagePlaceholder.setHeight(250);
        _imagePlaceholder.setWidth(250);
        _imagePlaceholder.setLayoutX((1250 - 250)/2);
        _imagePlaceholder.setLayoutY(30);

        VBox infoBox = new VBox();
        infoBox.setPrefWidth(1060);
        infoBox.setLayoutX(106);
        infoBox.setLayoutY(319);
        infoBox.setSpacing(50);
        infoBox.setAlignment(Pos.CENTER);

        Label nameLabel = new Label(itemToAdd.getName());
        nameLabel.getStyleClass().add("blackLabel");

        TextField quantityTextField = new TextField();

        TextField priceTextField = new TextField();

        infoBox.getChildren().addAll(nameLabel, quantityTextField, priceTextField);

        Button placeOrderButton = new Button("Place Order");
        placeOrderButton.setPrefWidth(200);
        placeOrderButton.setPrefHeight(70);
        placeOrderButton.setLayoutX(530);
        placeOrderButton.setLayoutY(610);
        placeOrderButton.getStyleClass().add("greenButton");
        placeOrderButton.setOnAction((e) -> {
            try {
                placeOrder(e, itemToAdd, quantity, price);
                anchorPane.getChildren().remove(orderPane);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (Exception exception){
                exception.printStackTrace();
            }
            //TODO: Catch any custom exception and display message to user.
        });

        orderPane.getChildren().addAll(_imagePlaceholder, infoBox, placeOrderButton);
        anchorPane.getChildren().add(orderPane);
    }

    public void placeOrder(ActionEvent event, Item itemToAdd, int quantity, float price) throws Exception {
        if (itemToAdd.getQuantity() < quantity){
            throw new Exception("Maximum quantity to add is " + itemToAdd.getQuantity());
        }
        CartItem newItem = itemToAdd.moveToCart(quantity, price);
        shippingCart.add(newItem);
        Update();
    }

    //TODO: Add to trades database when checkout.
    public void checkOut(){
        shippingCart.clear();
        Update();
    }


}
