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
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * A controller to control the SELL Page (which allows the user to sell items from their organisation's stock).
 */
public class SaleController extends DisplayController {
    Cart sellCart = new Cart(Order.Type.SELL);
    Stock tempStock;
    DataCollection<Order> marketBuyOrders;

    @FXML Pagination marketBuyOrdersDisplay;
    @FXML VBox stockDisplayBox;
    @FXML VBox sellCartDisplayBox;
    @FXML Button sellAllButton;
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
     * @param quantity The quantity to place
     * @param price the price to place
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
     * Attempts to place an order using an asset (that may not be in stock)
     * @param asset The item to sell
     * @param quantity The quantity to place
     * @param price the price to place
     */
    public void sellItem(Asset asset, int quantity, float price) throws InvalidArgumentValueException {
        for (Item i : tempStock){
            if (i.getId() == asset.getId()){
                int sellQuantity = Math.min(quantity, i.getQuantity());
                sellItem(i, sellQuantity, price);
                break;
            }
        }
    }

    /**
     * Copy information from a market's order and allows the user to customise it.
     * @param asset The asset linked with the order
     * @param quantity The quantity of the order
     * @param price The price of the order
     */
    public void customiseItem(Asset asset, int quantity, float price){
        ((ItemInfoBox) stockDisplayBox.lookup("#sellItemInfoBox" + asset.getId()))
                .setQuantity(quantity)
                .setPrice(price);
    }

    /**
     * Removes an item from a cart and adds it back to the stock.
     * @param cartItem The returning cart item.
     */
    public void removeCartItem(CartItem cartItem) throws InvalidArgumentValueException {
        sellCart.remove(cartItem);
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
        marketBuyOrders = localDatabase.getMarketOrders(Order.Type.BUY);
        if(marketBuyOrdersDisplay != null){
            // Resets the page and page factory http://www.java2s.com/Tutorials/Java/JavaFX/0610__JavaFX_Pagination.htm
            marketBuyOrdersDisplay.setPageFactory(pageIndex -> createPage(pageIndex));
            marketBuyOrdersDisplay.setPageCount((int) Math.ceil((float) marketBuyOrders.size() / ordersPerPage));
        }
        refresh();
    }

    // TODO: Refactor
    /**
     * The number of orders to be displayed per page.
     */
    private final int ordersPerPage = 7;

    /**
     * Creates a page at a certain index.
     */
    public VBox createPage(int pageIndex){
        VBox ordersContainerBox = new VBox();
        ordersContainerBox.setSpacing(10);

        int startingOrderIndex = pageIndex * ordersPerPage;
        for (int i = startingOrderIndex; i < startingOrderIndex + ordersPerPage && i < marketBuyOrders.size(); i++){
            MarketOrderInfoBox MarketOrderInfoBox = new MarketOrderInfoBox(marketBuyOrders.get(i), this);
            ordersContainerBox.getChildren().add(MarketOrderInfoBox);
        }
        return ordersContainerBox;
    }
}
