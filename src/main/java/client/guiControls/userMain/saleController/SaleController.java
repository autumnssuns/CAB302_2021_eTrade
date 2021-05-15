package client.guiControls.userMain.saleController;

import client.guiControls.DisplayController;
import client.guiControls.adminMain.AdminLocalDatabase;
import client.guiControls.adminMain.usersController.UserInfoBox;
import client.guiControls.userMain.UserLocalDatabase;
import common.Exceptions.InvalidArgumentValueException;
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
import java.time.LocalDateTime;

/**
 * A controller to control the SELL Page (which allows the user to sell items from their organisation's stock).
 */
public class SaleController extends DisplayController {
    Cart sellCart = new Cart(Order.Type.SELL);
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
    public void sellItem(Item item, int quantity, float price) throws InvalidArgumentValueException {
        int linkedItemIndex = tempStock.indexOf(item);
        CartItem cartItem = item.moveToCart(quantity, price);
        sellCart.add(cartItem);
        addCartItemInfoBox(cartItem);
        tempStock.set(linkedItemIndex, item);
        refresh();
    }

    /**
     * Removes an item from a cart and adds it back to the stock.
     * @param cartItem The returning cart item.
     */
    public void removeCartItem(CartItem cartItem) throws InvalidArgumentValueException {
        for (int i = 0; i < tempStock.size(); i++){
            if (tempStock.get(i).getId() == cartItem.getId()){
                Item returnItem = tempStock.get(i);
                returnItem.setQuantity(returnItem.getQuantity() + cartItem.getQuantity());
                tempStock.set(i, returnItem);
                break;
            }
        }
        refresh();
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
        sellCartDisplayBox.getChildren().clear();
        for (CartItem cartItem : sellCart){
            addCartItemInfoBox(cartItem);
        }
        updateTotal();
    }

    /**
     * Sells all items in the cart.
     */
    public void checkOut() throws InvalidArgumentValueException {
        int unitId = ((UserLocalDatabase)controller.getDatabase()).getOrganisationalUnit().getId();
        for (CartItem cartItem : sellCart){
            Order newOrder = new Order(-1, Order.Type.SELL, unitId, cartItem.getId(), cartItem.getQuantity(), 0, cartItem.getPrice(),
                    null, LocalDateTime.now(), Order.Status.PENDING);
            controller.sendRequest("add", newOrder, Order.class);
        }
        sellCart.clear();
        update();
    }

    /**
     * Updates with database and displays all items in stock.
     */
    @Override
    public void update(){
        UserLocalDatabase localDatabase = (UserLocalDatabase) controller.getDatabase();
        tempStock = localDatabase.getStock();
        refresh();
    }
}
