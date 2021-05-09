package client.guiControls.userMain;

import client.guiControls.DisplayController;
import client.guiControls.MainController;
import common.Response;
import common.dataClasses.DataCollection;
import common.dataClasses.Order;
import common.dataClasses.OrganisationalUnit;
import common.dataClasses.Stock;
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
    //Reusable elements that can be updated
    Label cartTotalLabel;
    Pane sellPane;
    Pane buyPane;
    Pane historyPane;
    Pane profilePane;

    //Preset components
    @FXML StackPane displayStack;
    @FXML Pane filterPane;
    @FXML Button marketButton;
    @FXML Button ordersButton;
    @FXML Button homeButton;
    @FXML AnchorPane anchorPane;
    @FXML Label userLabel;
    @FXML Label creditLabel;

    @FXML
    public void initialize() throws IOException {
        // Run later to wait for non-GUI thread to finish before loading GUI thread
        Platform.runLater(() -> {
            try {
                setupController();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setupController() throws IOException{
        fetchDatabase();
        userLabel.setText(getUser().getUsername());
        creditLabel.setText("Balance: $" + this.getDatabase().getOrganisationalUnit().getBalance());

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("saleController/SellPage.fxml"));
        sellPane = fxmlLoader.load();
        DisplayController displayController = fxmlLoader.getController();
        displayController.setController(this);
        displayController.update();

//        fxmlLoader = new FXMLLoader(getClass().getResource("buyController/BuyPage.fxml"));
        buyPane = new Pane();
//        displayController = fxmlLoader.getController();
//        displayController.setController(this);
//        displayController.update();

//        fxmlLoader = new FXMLLoader(getClass().getResource("assetsController/AssetsPage.fxml"));
        historyPane = new Pane();
//        displayController = fxmlLoader.getController();
//        displayController.setController(this);
//        displayController.update();

        profilePane = new Pane();

        displayStack.getChildren().addAll(sellPane, buyPane, historyPane, profilePane);
        toHome();
    }

    //Pane switching methods

    /**
     * Switches the display to the SELL tab.
     * @throws IOException
     */
    public void toHome() throws IOException {
        sellPane.toFront();
        homeButton.setDisable(true);
        marketButton.setDisable(false);
        ordersButton.setDisable(false);
    }

    /**
     * Switches the display to the BUY tab.
     * @throws IOException
     */
    public void toMarket(){
        buyPane.toFront();
        homeButton.setDisable(false);
        marketButton.setDisable(true);
        ordersButton.setDisable(false);
    }

    /**
     * Switches the display to the ORDERS tab.
     * @throws IOException
     */
    public void toOrders(){
        historyPane.toFront();
        homeButton.setDisable(false);
        marketButton.setDisable(false);
        ordersButton.setDisable(true);
    }

    /**
     * Fetch the database from server.
     */
    @Override
    public void fetchDatabase(){
        Response response = this.sendRequest("query organisational unit");
        OrganisationalUnit organisationalUnit = (OrganisationalUnit) response.getAttachment();

        response = this.sendRequest("query stock");
        Stock stock = (Stock) response.getAttachment();

        response = this.sendRequest("query orders");
        DataCollection<Order> orders = (DataCollection) response.getAttachment();
        
        localDatabase = new UserLocalDatabase(organisationalUnit, stock, orders);
    }

    @Override
    public UserLocalDatabase getDatabase(){
        return (UserLocalDatabase) localDatabase;
    }
}