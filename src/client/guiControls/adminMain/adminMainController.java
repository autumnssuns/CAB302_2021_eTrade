package client.guiControls.adminMain;

import client.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

//TODO: Refactor magic numbers & Node creation
//TODO: Commenting & Documenting

public class adminMainController {
    private Stage stage;
    private Scene scene;

    //Reusable elements that can be updated
    Label cartTotalLabel;
    Pane usersPane;
    Pane organisationPane;
    Pane assetsPane;
    Pane profilePane;

    //Preset components
    @FXML StackPane displayStack;
    @FXML Pane filterPane;
    @FXML Button assetsButton;
    @FXML Button usersButton;
    @FXML Button organisationsButton;
    @FXML AnchorPane anchorPane;
    @FXML Label userLabel;

    @FXML
    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        userLabel.setText(Main.mainController.getUser().getUsername());
        usersPane = loader.load(getClass().getResource("usersController/UsersPage.fxml"));

        // TODO: Implement these panes
        organisationPane = loader.load(getClass().getResource("organisationsController/organisationsPage.fxml"));
        assetsPane = loader.load(getClass().getResource("assetsController/AssetsPage.fxml"));
        profilePane = new Pane();

        displayStack.getChildren().addAll(usersPane, organisationPane, assetsPane, profilePane);
        toOrganisations();
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
    public void toOrganisations(){
        organisationPane.toFront();
        organisationsButton.setDisable(true);
        assetsButton.setDisable(false);
        usersButton.setDisable(false);
    }

    public void toUsers() throws IOException {
        usersPane.toFront();
        organisationsButton.setDisable(false);
        assetsButton.setDisable(false);
        usersButton.setDisable(true);
    }

    public void toAssets(){
        assetsPane.toFront();
        organisationsButton.setDisable(false);
        assetsButton.setDisable(true);
        usersButton.setDisable(false);
    }
}