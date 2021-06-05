package client.guiControls.userMain;

import client.guiControls.MainController;
import client.guiControls.adminMain.AdminLocalDatabase;
import client.guiControls.MessageFactory;
import client.guiControls.MessageViewUnit;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.IOException;

//TODO: Refactor magic numbers & Node creation
//TODO: Commenting & Documenting

public class UserMainController extends MainController {
    // The linked local database
    UserLocalDatabase localDatabase;

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
    @FXML private VBox messageBox;

    /**
     * Initialise the controller
     * @throws IOException
     */
    @FXML
    public void initialize() throws IOException {
        start();
    }

    /**
     * Sets up the view & the controllers
     * @throws IOException
     */
    @Override
    protected void setupController() throws IOException, InvalidArgumentValueException {
        localDatabase = new UserLocalDatabase();
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
     * @throws InvalidArgumentValueException
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
     * @throws InvalidArgumentValueException
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
     * @throws InvalidArgumentValueException
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
     * @throws InvalidArgumentValueException
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

        response = this.sendRequest(Request.ActionType.READ, Request.ObjectType.STOCK);
        Stock stock = (Stock) response.getAttachment();

        response = this.sendRequest(Request.ActionType.READ_ALL, Request.ObjectType.ORDER);
        DataCollection<Order> orders = (DataCollection) response.getAttachment();

        response = this.sendRequest(Request.ActionType.READ_ALL, Request.ObjectType.ASSET);
        DataCollection<Asset> assets = (DataCollection) response.getAttachment();

        response = this.sendRequest(Request.ActionType.READ, Request.ObjectType.NOTIFICATION);
        DataCollection<Notification> notifications = (DataCollection) response.getAttachment();

        localDatabase.setAssets(assets);
        localDatabase.setOrganisationalUnit(organisationalUnit);
        localDatabase.setStock(stock);
        localDatabase.setOrders(orders);
        localDatabase.setNotifications(notifications);
    }

    /**
     * Returns the local database for the current user.
     * @return The local database for the current user.
     */
    @Override
    public UserLocalDatabase getDatabase(){
        return localDatabase;
    }

    /**
     * Updates the view of the main page by fetching the database from server, pushes notification, then
     * updates each controller individually
     */
    @Override
    public void update() throws InvalidArgumentValueException {
        fetchDatabase();
        pushNotifications();
        pushLiveNotifications();
        OrganisationalUnit unit = localDatabase.getOrganisationalUnit();
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
     * Displays a live message to the user
     * @param message The string containing the message
     * @param type The type of the message (error, success or default)
     */
    @Override
    public void pushMessage(String message, MessageFactory.MessageType type) {
        MessageViewUnit messageViewUnit = MessageFactory.createMessage(message, type);
        messageBox.getChildren().add(messageViewUnit);
        messageBox.setVisible(true);
    }

    /**
     * Show the notification panel. Also marks the current unit as having read the message.
     */
    public void showNotifications() throws InvalidArgumentValueException {
        notificationPane.setVisible(true);
        DataCollection<Notification> notifications = localDatabase.getNotifications();
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
        // Counts the number of unread messages
        int unreadCount = 0;
        boolean hasRead;
        for (int i = 0; i < notifications.size(); i++){
            // Retrieve the latest first
            Notification notification = notifications.get(notifications.size() - i - 1);
            hasRead = notification.containsReader(getUser().getUnitId());
            if (!hasRead){
                unreadCount++;
            }
            this.notificationBox.getChildren().add(NotificationFactory.createNotification(notification, hasRead));
        }
        notificationNumberLabel.setText(String.valueOf(unreadCount));
    }

    /**
     * Since a normal user cannot make direct changes to the local database, there is no need
     * to check for conflict.
     * @param request The request whose attachment is to be found.
     * @return The previous states of the request's attached object in the local database.
     */
    @Override
    protected <T extends IData> T findPreviousState(Request request) {
        return null;
    }

    /**
     * Pushes the new notifications (not already read or not already in local database) as a pop up
     */
    private void pushLiveNotifications(){
        DataCollection<Notification> newNotifications = localDatabase.getNewNotifications();
        for (Notification notification : newNotifications){
            pushMessage(notification.getMessage(), MessageFactory.MessageType.DEFAULT);
        }
    }
}