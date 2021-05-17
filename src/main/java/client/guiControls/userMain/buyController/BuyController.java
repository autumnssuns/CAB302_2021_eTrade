package client.guiControls.userMain.buyController;

import client.guiControls.DisplayController;
import client.guiControls.userMain.UserLocalDatabase;
import client.guiControls.userMain.UserMainController;
import common.Exceptions.InvalidArgumentValueException;
import common.dataClasses.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.*;

import java.time.LocalDateTime;

/**
 * A controller to control the SELL Page (which allows the user to sell items from their organisation's stock).
 */
public class BuyController extends DisplayController {
    Cart buyCart = new Cart(Order.Type.BUY);
    Stock marketStock;
    DataCollection<Order> marketSellOrders;

    @FXML Pagination marketSellOrdersDisplay;
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
     * Sell an associated asset and remove it from stock.
     * @param asset The asset to sell
     */
    public void buyItem(Asset asset, int quantity, float price) throws InvalidArgumentValueException {
        CartItem cartItem = new CartItem(asset, quantity, price);
        buyCart.add(cartItem);
        refresh();
    }

    /**
     * Copy information from a market's order and allows the user to customise it.
     * @param asset The asset linked with the order
     * @param quantity The quantity of the order
     * @param price The price of the order
     */
    public void customiseItem(Asset asset, int quantity, float price){
        ((ItemInfoBox) marketDisplayBox.lookup("#sellItemInfoBox" + asset.getId()))
                .setQuantity(quantity)
                .setPrice(price);
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
    public void checkOut() throws InvalidArgumentValueException {
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
    public void update() throws InvalidArgumentValueException {
        UserLocalDatabase localDatabase = (UserLocalDatabase) controller.getDatabase();
        marketStock = localDatabase.getMarket();
        marketSellOrders = localDatabase.getMarketOrders(Order.Type.SELL);
        if(marketSellOrders != null){
            // Resets the page and page factory http://www.java2s.com/Tutorials/Java/JavaFX/0610__JavaFX_Pagination.htm
            marketSellOrdersDisplay.setPageFactory(pageIndex -> createPage(pageIndex));
            marketSellOrdersDisplay.setPageCount((int) Math.ceil((float) marketSellOrders.size() / ordersPerPage));
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
        for (int i = startingOrderIndex; i < startingOrderIndex + ordersPerPage && i < marketSellOrders.size(); i++){
            MarketOrderInfoBox MarketOrderInfoBox = new MarketOrderInfoBox(marketSellOrders.get(i), this);
            ordersContainerBox.getChildren().add(MarketOrderInfoBox);
        }
        return ordersContainerBox;
    }
}
