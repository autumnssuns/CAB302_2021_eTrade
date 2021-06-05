package client.guiControls.userMain;

import client.guiControls.MainController;
import client.guiControls.userMain.buyController.BuyController;
import client.guiControls.userMain.homeController.HomeController;
import client.guiControls.userMain.notificationController.NotificationFactory;
import client.guiControls.userMain.ordersController.OrdersController;
import client.guiControls.userMain.profileController.ProfileController;
import client.guiControls.userMain.saleController.SaleController;
import common.Exceptions.InvalidArgumentValueException;
import common.Request;
import common.Response;
import common.dataClasses.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
    @FXML private ScrollPane notificationPane;
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
    @FXML private VBox notificationBox;
    @FXML private Label notificationNumberLabel;

    /**
     * Initialise the controller
     * @throws IOException
     */
    @FXML
    public void initialize() throws IOException {
        // Run later to wait for non-GUI thread to finish before loading GUI thread
        Platform.runLater(() -> {
            try {
                setupController();
            } catch (IOException | InvalidArgumentValueException e) {
                e.printStackTrace();
            }
            startBackgroundThread();
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
     */
    public void switchToSellPage() {
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
    public void fetchDatabase() {
        Response response = this.sendRequest(Request.ActionType.READ, Request.ObjectType.ORGANISATIONAL_UNIT);
        OrganisationalUnit organisationalUnit = (OrganisationalUnit) response.getAttachment();
        System.out.println(organisationalUnit.getId() +" "+ organisationalUnit.getName() + " " + getDatabase());

        response = this.sendRequest(Request.ActionType.READ, Request.ObjectType.STOCK);
        Stock stock = (Stock) response.getAttachment();

        response = this.sendRequest(Request.ActionType.READ_ALL, Request.ObjectType.ORDER);
        DataCollection<Order> orders = (DataCollection) response.getAttachment();

        response = this.sendRequest(Request.ActionType.READ_ALL, Request.ObjectType.ASSET);
        DataCollection<Asset> assets = (DataCollection) response.getAttachment();

        response = this.sendRequest(Request.ActionType.READ, Request.ObjectType.NOTIFICATION);
        DataCollection<Notification> notifications = (DataCollection) response.getAttachment();

        localDatabase = new UserLocalDatabase(organisationalUnit, stock, orders, assets, notifications);
    }


    @Override
    public UserLocalDatabase getDatabase(){
        return (UserLocalDatabase) localDatabase;
    }

    @Override
    public void updateLocalDatabase(Request.ObjectType type) throws InvalidArgumentValueException {
        Response response;
        if (type.equals(Request.ObjectType.ORDER)){
            response = this.sendRequest(Request.ActionType.READ_ALL, Request.ObjectType.ORDER);
            DataCollection orders = (DataCollection) response.getAttachment();
            ((UserLocalDatabase) localDatabase).setOrders(orders);
        }
    }

    /**
     * Updates the view of the main page
     */
    @Override
    public void update() throws InvalidArgumentValueException {
        fetchDatabase();
        pushNotifications();
        OrganisationalUnit unit = ((UserLocalDatabase) localDatabase).getOrganisationalUnit();
        organisationalUnitLabel.setText(unit.getName());
        userLabel.setText(getUser().getFullName());
        creditLabel.setText("Balance: $" + unit.getBalance());
        profileController.update();
        homeController.update();
        saleController.update();
        buyController.update();
        ordersController.update();
    }

    /**
     * Show the notification panel. Also marks the current unit as having read the message.
     */
    public void showNotifications() throws InvalidArgumentValueException {
        System.out.println("Showing notification");
        notificationPane.setVisible(true);
        DataCollection<Notification> notifications = ((UserLocalDatabase) localDatabase).getNotifications();
        DataCollection<Notification> overridingNotifications = new DataCollection<>();
        Integer unitId = getUser().getUnitId();
        // Loops through the notifications, if the current unit is not already a reader, add it
        for (Notification notification : notifications){
            if (!notification.containsReader(unitId)){
                notification.addReaderUnit(unitId);
                overridingNotifications.add(notification);
            }
        }

        sendRequest(Request.ActionType.UPDATE, overridingNotifications, Request.ObjectType.NOTIFICATION);
        notificationButton.setOnAction(e -> hideNotifications());
    }

    /**
     * Hide the notification panel
     */
    public void hideNotifications() {
        notificationPane.setVisible(false);
        notificationButton.setOnAction(e -> {
            try {
                showNotifications();
            } catch (InvalidArgumentValueException invalidArgumentValueException) {
                invalidArgumentValueException.printStackTrace();
            }
        });
        try {
            update();
        } catch (InvalidArgumentValueException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pushes the notifications to the notification box, awaiting
     * to be shown
     */
    private void pushNotifications(){
        DataCollection<Notification> notifications = ((UserLocalDatabase) localDatabase).getNotifications();
        notificationBox.getChildren().clear();
        int unreadCount = 0;
        boolean hasRead;
        for (int i = 0; i < notifications.size(); i++){
            Notification notification = notifications.get(notifications.size() - i - 1);
            hasRead = notification.containsReader(getUser().getUnitId());
            if (!hasRead){
                unreadCount++;
            }
            this.notificationBox.getChildren().add(NotificationFactory.createNotification(notification, hasRead));
        }
        notificationNumberLabel.setText(String.valueOf(unreadCount));
    }
}