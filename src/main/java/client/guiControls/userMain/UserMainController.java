package client.guiControls.userMain;

import client.guiControls.MainController;
import client.guiControls.userMain.buyController.BuyController;
import client.guiControls.userMain.ordersController.OrdersController;
import client.guiControls.userMain.profileController.ProfileController;
import client.guiControls.userMain.saleController.SaleController;
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
    private SaleController saleController;
    private BuyController buyController;
    private OrdersController ordersController;
    private ProfileController profileController;

    //Reusable elements that can be updated
    Label cartTotalLabel;
    Pane sellPane;
    Pane buyPane;
    Pane ordersPane;
    Pane profilePane;

    //Preset components
    @FXML StackPane displayStack;
    @FXML Pane filterPane;
    @FXML Button marketButton;
    @FXML Button ordersButton;
    @FXML Button homeButton;
    @FXML Button profileButton;
    @FXML AnchorPane anchorPane;
    @FXML Label userLabel;
    @FXML Label creditLabel;
    @FXML Label organisationalUnitLabel;

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

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("SellPage.fxml"));
        sellPane = fxmlLoader.load();
        saleController = fxmlLoader.getController();
        saleController.setController(this);
        saleController.update();

        fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("BuyPage.fxml"));
        buyPane = fxmlLoader.load();
        buyController = fxmlLoader.getController();
        buyController.setController(this);
        buyController.update();

        fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("OrdersPage.fxml"));
        ordersPane = fxmlLoader.load();
        ordersController = fxmlLoader.getController();
        ordersController.setController(this);
        ordersController.update();

        fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("ProfilePage.fxml"));
        profilePane = fxmlLoader.load();
        profileController = fxmlLoader.getController();
        profileController.setController(this);
        profileController.update();

        update();
        displayStack.getChildren().addAll(sellPane, buyPane, ordersPane, profilePane);
        toHome();
    }

    //Pane switching methods

    /**
     * Switches the display to the SELL tab.
     * @throws IOException
     */
    public void toHome() throws IOException {
        saleController.update();
        sellPane.toFront();
        homeButton.setDisable(true);
        marketButton.setDisable(false);
        ordersButton.setDisable(false);
        profilePane.setDisable(false);
    }

    /**
     * Switches the display to the BUY tab.
     * @throws IOException
     */
    public void toMarket() throws InvalidArgumentValueException {
        buyController.update();
        buyPane.toFront();
        homeButton.setDisable(false);
        marketButton.setDisable(true);
        ordersButton.setDisable(false);
        profilePane.setDisable(false);
    }

    /**
     * Switches the display to the ORDERS tab.
     * @throws IOException
     */
    public void toOrders() throws InvalidArgumentValueException {
        ordersController.update();
        ordersPane.toFront();
        homeButton.setDisable(false);
        marketButton.setDisable(false);
        ordersButton.setDisable(true);
        profilePane.setDisable(false);
    }

    /**
     * Switches the display to the ORDERS tab.
     * @throws IOException
     */
    public void toProfile() throws InvalidArgumentValueException {
        profileController.update();
        profilePane.toFront();
        homeButton.setDisable(false);
        marketButton.setDisable(false);
        ordersButton.setDisable(false);
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
     * Updates the view
     */
    public void update() throws InvalidArgumentValueException {
        fetchDatabase();
        OrganisationalUnit unit = ((UserLocalDatabase) localDatabase).getOrganisationalUnit();
        organisationalUnitLabel.setText(unit.getName());
        userLabel.setText(getUser().getFullName());
        creditLabel.setText("Balance: $" + unit.getBalance());
    }
}