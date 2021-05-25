package client.guiControls.userMain.saleController;

import client.IViewUnit;
import client.guiControls.DisplayController;
import client.guiControls.userMain.UserMainController;
import common.Exceptions.InvalidArgumentValueException;
import common.dataClasses.Item;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.LinkedHashMap;

/**
 * A box to display an item information and can be interacted with.
 */
public class ItemInfoBox extends HBox implements IViewUnit {
    private SaleController controller;
    private Item item;
    private LinkedHashMap<LocalDate, Float> priceHistory;

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
    public ItemInfoBox(Item item, LinkedHashMap priceHistory, SaleController controller){
        this.item = item;
        this.controller = controller;
        this.priceHistory = priceHistory;
        initialize();
        load();
    }

    /**
     * Initialise the display elements and their styling.
     */
    @Override
    public void initialize(){
        this.setId("sellItemInfoBox" + item.getId());
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(20);

        nameLabel = new Label(item.getName());
        nameLabel.getStyleClass().add("blackLabel");
        nameLabel.setId("itemNameLabel" + item.getId());

        availabilityLabel = new Label(item.getQuantity() + " available");
        availabilityLabel.getStyleClass().add("blackLabel");
        availabilityLabel.setId("itemAvailabilityLabel" + item.getId());

        quantityTextField = new TextField();
        quantityTextField.setPrefWidth(150);
        quantityTextField.setPromptText("Quantity");
        quantityTextField.setId("itemSellQuantityTextField" + item.getId());

        priceTextField = new TextField();
        priceTextField.setPrefWidth(150);
        priceTextField.setPromptText("Price");
        priceTextField.setId("itemSellPriceTextField" + item.getId());

        sellButton = new Button("Sell");
        sellButton.setOnAction(e -> {
                    try {
                        controller.sellItem(
                                item,
                                Integer.parseInt(quantityTextField.getText()),
                                Float.parseFloat(priceTextField.getText())
                        );
                    } catch (InvalidArgumentValueException invalidArgumentValueException) {
                        invalidArgumentValueException.printStackTrace();
                    }
                }
        );
        sellButton.setId("itemSellButton" + item.getId());

        historyButton = new Button("History");
        historyButton.setOnAction(e -> controller.showHistory(priceHistory));
        historyButton.setId("itemHistoryButton" + item.getId());

        VBox infoGroup = new VBox();
        infoGroup.setAlignment(Pos.CENTER);
        infoGroup.setPrefWidth(200);
        infoGroup.getChildren().addAll(nameLabel, availabilityLabel, historyButton);

        VBox sellInfoGroup = new VBox();
        sellInfoGroup.setAlignment(Pos.CENTER_LEFT);
        sellInfoGroup.getChildren().addAll(quantityTextField, priceTextField);

        this.getChildren().addAll(infoGroup, sellInfoGroup, sellButton);
    }

    /**
     * Set the text in the quantity text field
     * @param quantity The quantity to display
     * @return The current instance to continue building
     */
    public ItemInfoBox setQuantity(int quantity){
        quantityTextField.setText(String.valueOf(quantity));
        return this;
    }

    /**
     * Set the text in the price text field
     * @param price The price to display
     * @return The current instance to continue building
     */
    public ItemInfoBox setPrice(float price){
        priceTextField.setText(String.valueOf(price));
        return this;
    }

    /**
     * Sets the item linked with the display and reset the display.
     * @param item The linked item.
     */
    public void setItem(Item item){
        this.item = item;
        load();
    }

    /**
     * Display the GUI components.
     */
    @Override
    public void load(){
        loadNameLabel();
        loadAvailabilityLabel();
    }

    /**
     * loads a label displaying the item's name.
     */
    private void loadNameLabel(){
        nameLabel.setText(item.getName());
    }

    /**
     * loads a label displaying the item's availability.
     */
    private void loadAvailabilityLabel(){
        availabilityLabel.setText(item.getQuantity() + " available");
    }

    /**
     * Shows the asset's history.
     */
    private void showHistory(){
        //TODO: Design & Implement this
    }
}
