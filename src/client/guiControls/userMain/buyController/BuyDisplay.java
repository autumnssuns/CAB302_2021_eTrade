package client.guiControls.userMain.buyController;


import common.dataClasses.Item;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BuyDisplay extends HBox {
    private int stockID;
    private String name;
    private int quantity; //This is the quantity displayed in the basket
    private float price; //This is the price displayed in the basket

    //private VBox stockBox = new VBox();
    @FXML
    TextField quantityTextField;
    @FXML
    TextField orderPrice;
    @FXML
    private Button orderButton;
    @FXML
    private Button historyButton;
    @FXML
    private Label stockName;
    @FXML
    private Label stockQuantity;
    @FXML
    private VBox basket;

    private BuyController controller;

    public BuyDisplay(int stockID, String name, int quantity, float price) {
        this.stockID = stockID;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.getChildren().addAll(stockName, stockQuantity, orderButton, historyButton, quantityTextField, orderPrice);
        initiateNodes();
    }

    private void initiateNodes() {
        // Create stock Box and buy box
        /*VBox descriptionBox = new VBox();
        descriptionBox.setPrefWidth(140);
        descriptionBox.setMaxHeight(140);
        descriptionBox.setAlignment(Pos.CENTER);
        descriptionBox.setLayoutX(50);
        descriptionBox.getStyleClass().add("assetInfoBox");
        descriptionBox.setSpacing(20);*/

        createQuantityTextField();
        createPriceTextField();
        createBuyButton();
        createHistoryButton();
        //displayStock();

        //stockBox.getChildren().addAll(priceTextField, quantityTextField);
    }

    public void createQuantityTextField(){
        TextField priceTextField = new TextField();
        priceTextField.setPromptText("Price");
        priceTextField.setPrefWidth(315);
    }

    public void createPriceTextField(){
        TextField priceTextField = new TextField();
        priceTextField.setPromptText("Price");
        priceTextField.setPrefWidth(315);
    }

    public void createBuyButton(){
        Button orderButton = new Button("Buy");
        orderButton.setPrefHeight(56);
        orderButton.setPrefWidth(140);
        orderButton.getStyleClass().add("greenButton");
        /*orderButton.setOnAction((e) -> {
            try {
                Item itemToAdd = Main.mainController.getStock().get(displayIndex);
                int quantity = Integer.parseInt(quantityTextField.getText());
                float price = Float.parseFloat(priceTextField.getText());
                placeOrder(e, itemToAdd, quantity, price);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });*/
        orderButton.setOnAction((e) -> addToCart());
    }
    public void createHistoryButton(){
        Button historyButton = new Button("History");
        historyButton.setPrefWidth(140);
        historyButton.setPrefHeight(40);
        historyButton.getStyleClass().add("greenButton");

    }
    public void displayStock(){
        Label stockName = new Label("Stock 1");
        stockName.getStyleClass().add("blackLabel");

        Label stockQuantity = new Label("Quantity: 15");
        stockQuantity.setPrefWidth(315);



    }
    public void createBasket(){
        //Item itemToDisplay = Main.mainController.getStock().get(displayIndex);
        HBox basket = new HBox();
        basket.setPrefHeight(80);
        basket.setLayoutX(16);
        basket.setLayoutY(100 + 100 * 10); //Change 10 later
        basket.setAlignment(Pos.CENTER_LEFT);
        basket.setSpacing(20);
        basket.getStyleClass().add("assetInfoBox");

        ScrollPane scroller = new ScrollPane();
        scroller.getContent();
        scroller.setLayoutX(16);
        scroller.setLayoutY(100);

        VBox cartItemNameBox = new VBox();
        cartItemNameBox.setAlignment(Pos.CENTER);
        cartItemNameBox.setSpacing(10);
        Label itemNameLabel = new Label(this.name);
        itemNameLabel.getStyleClass().add("blackLabel");
        //Remove button
        Button removeButton = new Button("Remove");
        removeButton.setOnAction((event) -> {
            System.out.println("Button clicked");
        });
        removeButton.getStyleClass().addAll("transparentButton", "smallTextField");
        cartItemNameBox.getChildren().addAll(itemNameLabel, removeButton);

        //Order info
        VBox descriptionBox = new VBox();
        descriptionBox.setPrefWidth(120);
        descriptionBox.setMaxHeight(60);
        descriptionBox.setAlignment(Pos.CENTER_LEFT);

        // TODO: Update when the text field values are changed.
        HBox quantityBox = new HBox();
        Label quantityLabel = new Label("Quantity: ");
        TextField quantityTextField = new TextField(String. valueOf(this.quantity));
        quantityTextField.getStyleClass().addAll("smallTextField");
        quantityTextField.setPrefWidth(30);
        quantityLabel.getStyleClass().add("blackLabel");
        quantityBox.getChildren().addAll(quantityLabel, quantityTextField);

        HBox priceBox = new HBox();
        Label priceLabel = new Label("Price: ");
        TextField priceTextField = new TextField(String.valueOf(this.price));
        priceTextField.getStyleClass().addAll("smallTextField");
        priceTextField.setPrefWidth(30);
        priceLabel.getStyleClass().add("blackLabel");
        priceBox.getChildren().addAll(priceLabel, priceTextField);

        descriptionBox.getChildren().addAll(quantityBox, priceBox);
        //Total of the order
        Label itemTotalLabel = new Label("Total: " ); //Get total price later
        itemTotalLabel.setPrefWidth(170);
        itemTotalLabel.setPrefHeight(60);
        itemTotalLabel.getStyleClass().add("itemTotalLabel");
        itemTotalLabel.getStyleClass().add("blackLabel");

        //Cart total
        Label cartTotalLabel = new Label();
        cartTotalLabel.setPrefWidth(220);
        cartTotalLabel.setPrefHeight(70);
        cartTotalLabel.setLayoutX(16);
        cartTotalLabel.setLayoutY(567);
        cartTotalLabel.getStyleClass().add("cartTotalLabel");
        cartTotalLabel.getStyleClass().add("blackLabel");

        //Check out button
        Button checkOutButton = new Button("Check Out");
        checkOutButton.setPrefWidth(166);
        checkOutButton.setPrefHeight(70);
        checkOutButton.setLayoutX(244);
        checkOutButton.setLayoutY(567);
        checkOutButton.getStyleClass().add("greenButton");

        checkOutButton.setOnAction((e) -> {
            checkOut();
        });

    }
    private void addToCart() {
        updateValues();
        createBasket();

    }
    private void updateValues(){
        this.quantity = Integer.valueOf(quantityTextField.getText());
        this.price = Integer.valueOf(orderPrice.getText());
    }
    private void checkOut(){

    }
}
