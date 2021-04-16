package client.guiControls.mainUser;

import client.data.Session;
import client.data.sessionalClasses.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

//TODO: Refactor magic numbers & Node creation
//TODO: Commenting & Documenting

public class MainController {
    private Stage stage;
    private Scene scene;

    //TODO: Replace with data from database (current assets and shipping cart)
    private Stock stock = Session.organisation.stock;
    private Cart shippingCart = Session.shippingCart;

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

    @FXML
    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        userLabel.setText(Session.getUsername());
        sellPane = loader.load(getClass().getResource("saleController/SellPage.fxml"));
        buyPane = loader.load(getClass().getResource("buyController/BuyPage.fxml"));

        displayStack.getChildren().addAll(sellPane, buyPane);
        toHome();
    }

    //TODO: Implement a logout method
    public void LogOut(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../login/Login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        String css = this.getClass().getResource("../client.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    //Pane switching methods
    public void toHome() throws IOException {
        sellPane.toFront();
        homeButton.setDisable(true);
        marketButton.setDisable(false);
        ordersButton.setDisable(false);
    }

    public void toMarket(){
        buyPane.toFront();
        homeButton.setDisable(false);
        marketButton.setDisable(true);
        ordersButton.setDisable(false);
    }

    public void toOrders(){
        historyPane.toFront();
        homeButton.setDisable(false);
        marketButton.setDisable(false);
        ordersButton.setDisable(true);
    }
}