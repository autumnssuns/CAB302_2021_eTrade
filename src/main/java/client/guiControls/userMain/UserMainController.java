package client.guiControls.userMain;

import client.guiControls.MainController;
import client.guiControls.userMain.buyController.BuyController;
import client.guiControls.userMain.homeController.HomeController;
import client.guiControls.userMain.ordersController.OrdersController;
import client.guiControls.userMain.profileController.ProfileController;
import client.guiControls.userMain.saleController.SaleController;
import com.sun.javafx.menu.MenuItemBase;
import common.Exceptions.InvalidArgumentValueException;
import common.Response;
import common.dataClasses.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.io.IOException;

//TODO: Refactor magic numbers & Node creation
//TODO: Commenting & Documenting

public class UserMainController extends MainController {
    // Display controllers
    private HomeController homeController;
    private SaleController saleController;
    private BuyController buyController;
    private OrdersController ordersController;
    private ProfileController profileController;

    //Reusable elements that can be updated
    private Pane homePane;
    private Pane sellPane;
    private Pane buyPane;
    private Pane ordersPane;
    private Pane profilePane;

    //Preset components
    @FXML private StackPane displayStack;
    @FXML private Pane filterPane;
    @FXML private Button homePageButton;
    @FXML private Button buyPageButton;
    @FXML private Button historyButton;
    @FXML private Button sellPageButton;
    @FXML private Button profileButton;
    @FXML private AnchorPane anchorPane;
    @FXML private Label userLabel;
    @FXML private Label creditLabel;
    @FXML private Label organisationalUnitLabel;
    @FXML private Button notificationButton;

    @FXML
    public void initialize() throws IOException {
        // Run later to wait for non-GUI thread to finish before loading GUI thread
        Platform.runLater(() -> {
            try {
                setupController();
            } catch (IOException | InvalidArgumentValueException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Sets up the view & the controllers
     * @throws IOException
     */
    private void setupController() throws IOException, InvalidArgumentValueException {
        fetchDatabase();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("UserGUI/HomePage.fxml"));
        homePane = fxmlLoader.load();
        homeController = fxmlLoader.getController();
        homeController.setController(this);
        homeController.update();

        fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("UserGUI/SellPage.fxml"));
        sellPane = fxmlLoader.load();
        saleController = fxmlLoader.getController();
        saleController.setController(this);
        saleController.update();

        fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("UserGUI/BuyPage.fxml"));
        buyPane = fxmlLoader.load();
        buyController = fxmlLoader.getController();
        buyController.setController(this);
        buyController.update();

        fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("UserGUI/OrdersPage.fxml"));
        ordersPane = fxmlLoader.load();
        ordersController = fxmlLoader.getController();
        ordersController.setController(this);
        ordersController.update();

        fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("UserGUI/ProfilePage.fxml"));
        profilePane = fxmlLoader.load();
        profileController = fxmlLoader.getController();
        profileController.setController(this);
        profileController.update();

        update();
        displayStack.getChildren().addAll(homePane, sellPane, buyPane, ordersPane, profilePane);
        switchToSellPage();
    }

    //Pane switching methods

    /**
     * Switches the display to the ORDERS tab.
     * @throws IOException
     */
    public void switchToHomePage() throws InvalidArgumentValueException {
        homeController.update();
        homePane.toFront();
        homePageButton.setDisable(true);
        sellPageButton.setDisable(false);
        buyPageButton.setDisable(false);
        historyButton.setDisable(false);
        profileButton.setDisable(false);
    }

    /**
     * Switches the display to the SELL tab.
     * @throws IOException
     */
    public void switchToSellPage() throws IOException {
        saleController.update();
        sellPane.toFront();
        homePageButton.setDisable(false);
        sellPageButton.setDisable(true);
        buyPageButton.setDisable(false);
        historyButton.setDisable(false);
        profileButton.setDisable(false);
    }

    /**
     * Switches the display to the BUY tab.
     * @throws IOException
     */
    public void switchToBuyPage() throws InvalidArgumentValueException {
        buyController.update();
        buyPane.toFront();
        homePageButton.setDisable(false);
        sellPageButton.setDisable(false);
        buyPageButton.setDisable(true);
        historyButton.setDisable(false);
        profileButton.setDisable(false);
    }

    /**
     * Switches the display to the ORDERS tab.
     * @throws IOException
     */
    public void switchToHistoryPage() throws InvalidArgumentValueException {
        ordersController.update();
        ordersPane.toFront();
        homePageButton.setDisable(false);
        sellPageButton.setDisable(false);
        buyPageButton.setDisable(false);
        historyButton.setDisable(true);
        profileButton.setDisable(false);
    }

    /**
     * Switches the display to the ORDERS tab.
     * @throws IOException
     */
    public void switchToProfilePage() throws InvalidArgumentValueException {
        profileController.update();
        profilePane.toFront();
        homePageButton.setDisable(false);
        sellPageButton.setDisable(false);
        buyPageButton.setDisable(false);
        historyButton.setDisable(false);
        profileButton.setDisable(true);
    }

    /**
     * Fetch the database from server.
     */
    @Override
    public void fetchDatabase() throws InvalidArgumentValueException {
        Response response = this.sendRequest("query organisational unit");
        OrganisationalUnit organisationalUnit = (OrganisationalUnit) response.getAttachment();
        System.out.println(organisationalUnit.getId() +" "+ organisationalUnit.getName() + " " + getDatabase());

        response = this.sendRequest("query stock");
        Stock stock = (Stock) response.getAttachment();

        response = this.sendRequest("query orders");
        DataCollection<Order> orders = (DataCollection) response.getAttachment();

        response = this.sendRequest("query assets");
        DataCollection<Asset> assets = (DataCollection) response.getAttachment();

        localDatabase = new UserLocalDatabase(organisationalUnit, stock, orders, assets);
    }


    @Override
    public UserLocalDatabase getDatabase(){
        return (UserLocalDatabase) localDatabase;
    }

    @Override
    public <T extends IData> void updateLocalDatabase(Class<T> type) throws InvalidArgumentValueException {
        Response response;
        if (type.equals(Order.class)){
            response = this.sendRequest("query orders");
            DataCollection orders = (DataCollection) response.getAttachment();
            ((UserLocalDatabase) localDatabase).setOrders(orders);
        }
    }

    /**
     * Updates the view of the main page
     */
    public void update() throws InvalidArgumentValueException {
        fetchDatabase();
        OrganisationalUnit unit = ((UserLocalDatabase) localDatabase).getOrganisationalUnit();
        organisationalUnitLabel.setText(unit.getName());
        userLabel.setText(getUser().getFullName());
        creditLabel.setText("Balance: $" + unit.getBalance());
    }

    /**
     * Show the notification panel
     */
    public void showNotifications(){
        System.out.println("Showing notification");
        filterPane.setVisible(true);
        notificationButton.setOnAction(e -> hideNotifications());
    }

    /**
     * Hide the notification panel
     */
    public void hideNotifications() {
        filterPane.setVisible(false);
        notificationButton.setOnAction(e -> showNotifications());
    }
}