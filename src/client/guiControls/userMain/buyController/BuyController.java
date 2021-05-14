package client.guiControls.userMain.buyController;

import client.guiControls.DisplayController;
import client.guiControls.userMain.UserLocalDatabase;
import client.guiControls.userMain.UserMainController;
import common.dataClasses.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.time.LocalDateTime;

/**
 * A controller to control the SELL Page (which allows the user to sell items from their organisation's stock).
 */
public class BuyController extends DisplayController {
    Cart buyCart = new Cart(Order.Type.BUY);
    Stock marketStock;

    @FXML VBox marketDisplayBox;
    @FXML VBox buyCartDisplayBox;
    @FXML Button buyAllButton;
    @FXML Label buyTotalLabel;

    /**
     * Displays a new box containing an item's information in the stock.
     * @param item The linked item.
     */
    private void addItemInfoBox(Item item){
        ItemInfoBox itemInfoBox = new ItemInfoBox(item, this);
        marketDisplayBox.getChildren().add(itemInfoBox);
    }

    /**
     * Displays a new box containing a cartItem's information in the cart.
     * @param cartItem The linked cartItem.
     */
    private void addCartItemInfoBox(CartItem cartItem){
        CartItemInfoBox cartItemInfoBox = new CartItemInfoBox(cartItem, this);
        buyCartDisplayBox.getChildren().add(cartItemInfoBox);
    }

    /**
     * Sell an associated item and remove it from stock.
     * @param item The item to sell
     */
    public void buyItem(Item item, int quantity, float price){
        CartItem cartItem = new CartItem(item, quantity, price);
        buyCart.add(cartItem);
        refresh();
    }

    /**
     * Removes an item from a cart and adds it back to the stock.
     * @param cartItem The returning cart item.
     */
    public void removeCartItem(CartItem cartItem){
        buyCart.remove(cartItem);
        refresh();
    }

    /**
     * Updates the total price of the cart.
     */
    private void updateTotal(){
        buyTotalLabel.setText("Total: " + buyCart.getTotalPrice());
    }

    /**
     * Refreshes the view.
     */
    public void refresh(){
        marketDisplayBox.getChildren().clear();
        for (Item item : marketStock){
            addItemInfoBox(item);
        }
        buyCartDisplayBox.getChildren().clear();
        for (CartItem cartItem : buyCart){
            addCartItemInfoBox(cartItem);
        }
        updateTotal();
    }

    /**
     * Sells all items in the cart.
     */
    public void checkOut(){
        int unitId = ((UserLocalDatabase)controller.getDatabase()).getOrganisationalUnit().getId();
        for (CartItem cartItem : buyCart){
            Order newOrder = new Order(-1, Order.Type.BUY, unitId, cartItem.getId(), cartItem.getQuantity(), 0, cartItem.getPrice(),
                    null, LocalDateTime.now(), Order.Status.PENDING);
            controller.sendRequest("add", newOrder, Order.class);
        }
        buyCart.clear();
        ((UserMainController) controller).update();
        update();
    }

    /**
     * Updates with database and displays all items in stock.
     */
    @Override
    public void update(){
        UserLocalDatabase localDatabase = (UserLocalDatabase) controller.getDatabase();
        marketStock = localDatabase.getMarket();
        refresh();
    }

    /**
     * Returns the lowest current market price of an asset, given it's id
     * @param id The asset's id
     * @return The lowest price of the asset
     */
    public String getCurrentLowestPrice(int id) {
        return String.valueOf(((UserLocalDatabase)controller.getDatabase()).getCurrentLowestSellPrice(id));
    }
}
