package client.guiControls.userMain;

import client.guiControls.MainController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import javax.security.auth.callback.ConfirmationCallback;
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

    @FXML
    public void initialize() throws IOException {
        // Run later to wait for non-GUI thread to finish before loading GUI thread
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader();
            userLabel.setText(getUser().getUsername());
            try {
                sellPane = FXMLLoader.load(getClass().getResource("saleController/SellPage.fxml"));
                buyPane = loader.load(getClass().getResource("buyController/BuyPage.fxml"));

                // TODO: Implement these panes
                historyPane = new Pane();
                profilePane = new Pane();

                displayStack.getChildren().addAll(sellPane, buyPane);
                toHome();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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