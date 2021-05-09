package client.guiControls.userMain.saleController;

import client.guiControls.DisplayController;
import client.guiControls.userMain.UserMainController;
import common.dataClasses.Item;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * A box to display an item information and can be interacted with.
 */
public class ItemInfoBox extends HBox {
    private SaleController controller;
    private Item item;

    private Label nameLabel;
    private Label availabilityLabel;
    private TextField quantityTextField;
    private TextField priceTextField;

    private Button sellButton;
    private Button historyButton;

    /**
     * Initialises an info box for the item in the organisational unit's stock.
     * @param item The linked item.
     * @param controller The associated display controller containing this box.
     */
    public ItemInfoBox(Item item, SaleController controller){
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(20);

        this.item = item;
        this.controller = controller;

        loadView();
    }

    /**
     * Sets the item linked with the display and reset the display.
     * @param item The linked item.
     */
    public void setItem(Item item){
        this.item = item;
        loadView();
    }

    /**
     * Display the GUI components.
     */
    private void loadView(){
        loadData();
        this.getChildren().clear();
        VBox infoGroup = new VBox();
        infoGroup.setAlignment(Pos.CENTER);
        infoGroup.setPrefWidth(100);
        infoGroup.getChildren().addAll(nameLabel, availabilityLabel, historyButton);
        VBox sellInfoGroup = new VBox();
        sellInfoGroup.setAlignment(Pos.CENTER_LEFT);
        sellInfoGroup.getChildren().addAll(quantityTextField, priceTextField);
        this.getChildren().addAll(infoGroup, sellInfoGroup, sellButton);
    }

    /**
     * Loads the underlying data to the GUI components.
     */
    private void loadData(){
        createNameLabel();
        createAvailabilityLabel();
        createQuantityTextField();
        createPriceTextField();
        createSellButton();
        createHistoryButton();
    }

    /**
     * Creates a label displaying the item's name.
     */
    private void createNameLabel(){
        nameLabel = new Label(item.getName());
        nameLabel.getStyleClass().add("blackLabel");
        nameLabel.setId("itemNameLabel" + item.getId());
    }

    /**
     * Creates a label displaying the item's availability.
     */
    private void createAvailabilityLabel(){
        availabilityLabel = new Label(item.getQuantity() + " available");
        availabilityLabel.getStyleClass().add("blackLabel");
        availabilityLabel.setId("itemAvailabilityLabel" + item.getId());
    }

    /**
     * Creates a text field to input the amount to sell.
     */
    private void createQuantityTextField(){
        quantityTextField = new TextField();
        quantityTextField.setPrefWidth(200);
        quantityTextField.setPromptText("Quantity to sell");
        quantityTextField.setId("itemSellQuantityTextField" + item.getId());
    }

    /**
     * Creates a text field to input the price.
     */
    private void createPriceTextField(){
        priceTextField = new TextField();
        priceTextField.setPrefWidth(200);
        priceTextField.setPromptText("Price");
        priceTextField.setId("itemSellPriceTextField" + item.getId());
    }

    /**
     * Creates a button to sell the item.
     */
    private void createSellButton(){
        sellButton = new Button("Sell");
        sellButton.setOnAction(e -> controller.sellItem(
                item,
                Integer.parseInt(quantityTextField.getText()),
                Float.parseFloat(priceTextField.getText()),
                this
                )
            );
        sellButton.setId("itemSellButton" + item.getId());
    }

    /**
     * Creates a button to show the asset's history.
     */
    private void createHistoryButton(){
        historyButton = new Button("History");
        historyButton.setOnAction(e -> showHistory());
        historyButton.setId("itemHistoryButton" + item.getId());
    }

    /**
     * Shows the asset's history.
     */
    private void showHistory(){
        //TODO: Design & Implement this
    }
}
