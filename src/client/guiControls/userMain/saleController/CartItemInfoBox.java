package client.guiControls.userMain.saleController;

import client.guiControls.DisplayController;
import client.guiControls.userMain.UserMainController;
import common.dataClasses.CartItem;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * A box to display an item information and can be interacted with.
 */
public class CartItemInfoBox extends HBox {
    private SaleController controller;
    private CartItem cartItem;

    private Label nameLabel;
    private TextField quantityTextField;
    private TextField priceTextField;
    private Label totalPriceLabel;

    private Button removeButton;

    /**
     * Initialises an info box for the cartItem in the organisational unit's stock.
     * @param cartItem The linked cartItem.
     * @param controller The associated display controller containing this box.
     */
    public CartItemInfoBox(CartItem cartItem, SaleController controller){
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(20);

        this.cartItem = cartItem;
        this.controller = controller;

        loadView();
    }

    /**
     * Sets the item linked with the display and reset the display.
     * @param cartItem The linked item.
     */
    public void setCartItem(CartItem cartItem){
        this.cartItem = cartItem;
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
        infoGroup.getChildren().addAll(nameLabel, removeButton);
        VBox sellInfoGroup = new VBox();
        sellInfoGroup.setAlignment(Pos.CENTER_LEFT);
        sellInfoGroup.getChildren().addAll(quantityTextField, priceTextField);
        this.getChildren().addAll(infoGroup, sellInfoGroup, totalPriceLabel);
    }

    /**
     * Loads the underlying data to the GUI components.
     */
    private void loadData(){
        createNameLabel();
        createQuantityTextField();
        createPriceTextField();
        createTotalPriceLabel();
        createRemoveButton();
    }

    /**
     * Creates a label displaying the cartItem's name.
     */
    private void createNameLabel(){
        nameLabel = new Label(cartItem.getName());
        nameLabel.getStyleClass().add("blackLabel");
        nameLabel.setId("cartItemNameLabel" + cartItem.getId());
    }

    /**
     * Creates a label displaying the cartItem's name.
     */
    private void createTotalPriceLabel(){
        totalPriceLabel = new Label("Total" + cartItem.getTotalPrice());
        totalPriceLabel.getStyleClass().add("blackLabel");
        totalPriceLabel.setId("cartItemTotalPriceLabel" + cartItem.getId());
    }

    /**
     * Creates a text field to input the amount to sell.
     */
    private void createQuantityTextField(){
        quantityTextField = new TextField();
        quantityTextField.setPrefWidth(150);
        quantityTextField.setText("Quantity: " + cartItem.getQuantity());
        quantityTextField.setId("cartItemSellQuantityTextField" + cartItem.getId());
    }

    /**
     * Creates a text field to input the price.
     */
    private void createPriceTextField(){
        priceTextField = new TextField();
        priceTextField.setPrefWidth(150);
        priceTextField.setText("Price: " + cartItem.getPrice());
        priceTextField.setId("cartItemSellPriceTextField" + cartItem.getId());
    }

    /**
     * Creates a button to show the asset's history.
     */
    private void createRemoveButton(){
        removeButton = new Button("Remove");
        removeButton.setOnAction(e -> removeBox());
        removeButton.setId("cartItemRemoveButton" + cartItem.getId());
    }

    /**
     * Shows the asset's history.
     */
    private void showHistory(){
        //TODO: Design & Implement this
    }

    /**
     * Removes the current box.
     */
    private void removeBox() {
        ((VBox) this.getParent()).getChildren().remove(this);
        controller.removeCartItem(cartItem);
    }
}
