package client.guiControls.userMain.ordersController;

import client.guiControls.DisplayController;
import client.guiControls.userMain.UserLocalDatabase;
import client.guiControls.userMain.UserMainController;
import common.Exceptions.InvalidArgumentValueException;
import common.dataClasses.Asset;
import common.dataClasses.DataCollection;
import common.dataClasses.Order;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.layout.VBox;
import javafx.util.Callback;


public class OrdersController extends DisplayController {
    @FXML
    Pagination ordersDisplay;
    @FXML
    Button sellFilterButton;
    @FXML
    Button buyFilterButton;

    DataCollection<Order> orders;

    private Order.Type typeFilter = Order.Type.BUY;;

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
        for (int i = startingOrderIndex; i < startingOrderIndex + ordersPerPage && i < orders.size(); i++){
            OrderInfoBox orderInfoBox = new OrderInfoBox(orders.get(i), this);
            ordersContainerBox.getChildren().add(orderInfoBox);
        }
        return ordersContainerBox;
    }

    /**
     * Updates with database and displays all items in stock.
     */
    @Override
    public void update() throws InvalidArgumentValueException {
        UserLocalDatabase localDatabase = (UserLocalDatabase) controller.getDatabase();
        orders = new DataCollection<>();
        for (Order order : localDatabase.getOrders()){
            if (order.getUnitId().equals(controller.getUser().getUnitId()) && order.getOrderType().equals(typeFilter)){
                orders.add(order);
            }
        }
        if(orders != null){
            // Resets the page and page factory http://www.java2s.com/Tutorials/Java/JavaFX/0610__JavaFX_Pagination.htm
            ordersDisplay.setPageFactory(pageIndex -> createPage(pageIndex));
            ordersDisplay.setPageCount((int) Math.ceil((float) orders.size() / ordersPerPage));
        }
    }

    /**
     * Change the order type displayed
     */
    public void changeTypeFilter() throws InvalidArgumentValueException {
        if (typeFilter.equals(Order.Type.SELL)){
            typeFilter = Order.Type.BUY;
            buyFilterButton.setDisable(true);
            sellFilterButton.setDisable(false);
        }
        else{
            typeFilter = Order.Type.SELL;
            sellFilterButton.setDisable(true);
            buyFilterButton.setDisable(false);
        }
        update();
    }
}
