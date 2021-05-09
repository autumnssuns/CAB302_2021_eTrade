package client.guiControls.userMain.saleController;

import client.guiControls.DisplayController;
import client.guiControls.adminMain.AdminLocalDatabase;
import client.guiControls.adminMain.usersController.UserInfoBox;
import client.guiControls.userMain.UserLocalDatabase;
import common.dataClasses.*;
import client.guiControls.MainController;
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
/**
 * A controller to control the SELL Page (which allows the user to sell items from their organisation's stock).
 */
public class SaleController extends DisplayController {
    Cart sellCart = new Cart("sell");
    Stock tempStock;

    @FXML VBox stockDisplayBox;
    @FXML VBox sellCartDisplayBox;
    @FXML Button checkOutButton;
    @FXML Label saleTotalLabel;

    /**
     * Displays a new box containing an item's information in the stock.
     * @param item The linked item.
     */
    private void addItemInfoBox(Item item){
        ItemInfoBox itemInfoBox = new ItemInfoBox(item, this);
        stockDisplayBox.getChildren().add(itemInfoBox);
    }

    /**
     * Displays a new box containing a cartItem's information in the cart.
     * @param cartItem The linked cartItem.
     */
    private void addCartItemInfoBox(CartItem cartItem){
        CartItemInfoBox cartItemInfoBox = new CartItemInfoBox(cartItem, this);
        sellCartDisplayBox.getChildren().add(cartItemInfoBox);
    }

    /**
     * Sell an associated item and remove it from stock.
     * @param item The item to sell
     */
    public void sellItem(Item item, int quantity, float price, ItemInfoBox caller){
        int linkedItemIndex = tempStock.indexOf(item);
        CartItem cartItem = item.moveToCart(quantity, price);
        sellCart.add(cartItem);
        addCartItemInfoBox(cartItem);
        caller.setItem(item);
        tempStock.set(linkedItemIndex, item);
        updateTotal();
    }

    /**
     * Removes an item from a cart and adds it back to the stock.
     * @param cartItem The returning cart item.
     */
    public void removeCartItem(CartItem cartItem){
        for (int i = 0; i < tempStock.size(); i++){
            if (tempStock.get(i).getId() == cartItem.getId()){
                Item returnItem = tempStock.get(i);
                returnItem.setQuantity(returnItem.getQuantity() + cartItem.getQuantity());
                tempStock.set(i, returnItem);
                break;
            }
        }
        refresh();
        sellCart.remove(cartItem);
        updateTotal();
    }

    /**
     * Updatees the total price of the cart.
     */
    private void updateTotal(){
        saleTotalLabel.setText("Total: " + sellCart.getTotalPrice());
    }

    /**
     * Refreshes the view.
     */
    public void refresh(){
        stockDisplayBox.getChildren().clear();
        for (Item item : tempStock){
            addItemInfoBox(item);
        }
    }

    /**
     * Sells all items in the cart.
     */
    public void checkOut(){
        controller.sendRequest("sell", sellCart);
        sellCart.clear();
        controller.fetchDatabase();
    }

    /**
     * Updates with database and displays all items in stock.
     */
    @Override
    public void update(){
        UserLocalDatabase localDatabase = (UserLocalDatabase) controller.getDatabase();
        tempStock = localDatabase.getStock();

        for (Item item : tempStock){
            addItemInfoBox(item);
        }
    }
}
