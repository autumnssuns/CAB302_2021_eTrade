package client.guiControls.adminMain;

import client.Main;
import client.guiControls.MainController;
import common.Request;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

//TODO: Refactor magic numbers & Node creation
//TODO: Commenting & Documenting

public class AdminMainController extends MainController {
    private AdminLocalDatabase adminLocalDatabase;

    //Reusable elements that can be updated
    Pane usersPane;
    Pane organisationUnitsPane;
    Pane assetsPane;
    Pane profilePane;

    //Preset components
    @FXML StackPane displayStack;
    @FXML Pane filterPane;
    @FXML Button assetsButton;
    @FXML Button usersButton;
    @FXML Button organisationUnitsButton;
    @FXML AnchorPane anchorPane;
    @FXML Label userLabel;

    /**
     * Initialises the controller by loading the sub-panes.
     * @throws IOException
     */
    @FXML
    public void initialize() throws IOException {
        // https://stackoverflow.com/questions/14370183/passing-parameters-to-a-controller-when-loading-an-fxml
        // Used to wait until the non-GUI component (controller) is finished, making sure getUser() is not null.
        Platform.runLater(() -> {
            userLabel.setText(getUser().getUsername());
        });

        usersPane = FXMLLoader.load(getClass().getResource("usersController/UsersPage.fxml"));
        organisationUnitsPane = FXMLLoader.load(getClass().getResource("organisationsController/organisationsPage.fxml"));
        assetsPane = FXMLLoader.load(getClass().getResource("assetsController/AssetsPage.fxml"));
        profilePane = new Pane();

        displayStack.getChildren().addAll(usersPane, organisationUnitsPane, assetsPane, profilePane);
        toOrganisationUnits();
    }

    /**
     * Switches the display to the ORGANISATION UNITS pane.
     */
    public void toOrganisationUnits(){
        organisationUnitsPane.toFront();
        organisationUnitsButton.setDisable(true);
        assetsButton.setDisable(false);
        usersButton.setDisable(false);
    }

    /**
     * Switches the display to the USERS pane.
     */
    public void toUsers(){
        usersPane.toFront();
        organisationUnitsButton.setDisable(false);
        assetsButton.setDisable(false);
        usersButton.setDisable(true);
    }

    /**
     * Switches the display to the ASSETS pane.
     */
    public void toAssets(){
        assetsPane.toFront();
        organisationUnitsButton.setDisable(false);
        assetsButton.setDisable(true);
        usersButton.setDisable(false);
    }

    /**
     * Returns the local database for the admin.
     * @return The local database for the admin.
     */
    public AdminLocalDatabase getDatabase(){
        return adminLocalDatabase;
    }

    /**
     * Initialise the database.
     */
    private void initialiseDatabase(){
        Request request = new Request(getUser(), "query users");
        this.sendRequest(request);
    }
}