package client.guiControls.adminMain;

import client.guiControls.DisplayController;
import client.guiControls.MainController;
import client.guiControls.adminMain.assetsController.AssetsController;
import client.guiControls.adminMain.usersController.UsersController;
import common.Request;
import common.Response;
import common.dataClasses.DataCollection;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

//TODO: Refactor magic numbers & Node creation
//TODO: Commenting & Documenting

public class AdminMainController extends MainController {
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
    @FXML Button organisationalUnitsButton;
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
            try {
                setupController();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setupController() throws IOException{
        userLabel.setText(getUser().getUsername());
        initialiseDatabase();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("usersController/UsersPage.fxml"));
        usersPane = fxmlLoader.load();
        DisplayController displayController = fxmlLoader.getController();
        displayController.setController(this);
        displayController.update();

        fxmlLoader = new FXMLLoader(getClass().getResource("organisationalUnitsController/organisationalUnitsPage.fxml"));
        organisationUnitsPane = fxmlLoader.load();
        displayController = fxmlLoader.getController();
        displayController.setController(this);
        displayController.update();

        fxmlLoader = new FXMLLoader(getClass().getResource("assetsController/AssetsPage.fxml"));
        assetsPane = fxmlLoader.load();
        displayController = fxmlLoader.getController();
        displayController.setController(this);
        displayController.update();

        profilePane = new Pane();

        displayStack.getChildren().addAll(usersPane, organisationUnitsPane, assetsPane, profilePane);
        toOrganisationUnits();
    }

    /**
     * Switches the display to the organisational unit UNITS pane.
     */
    public void toOrganisationUnits(){
        organisationUnitsPane.toFront();
        organisationalUnitsButton.setDisable(true);
        assetsButton.setDisable(false);
        usersButton.setDisable(false);
    }

    /**
     * Switches the display to the USERS pane.
     */
    public void toUsers(){
        usersPane.toFront();
        organisationalUnitsButton.setDisable(false);
        assetsButton.setDisable(false);
        usersButton.setDisable(true);
    }

    /**
     * Switches the display to the ASSETS pane.
     */
    public void toAssets(){
        assetsPane.toFront();
        organisationalUnitsButton.setDisable(false);
        assetsButton.setDisable(true);
        usersButton.setDisable(false);
    }

    /**
     * Initialise the database.
     */
    private void initialiseDatabase(){
        Request request = new Request(getUser(), "query users");
        Response response = this.sendRequest(request);
        DataCollection users = (DataCollection) response.getAttachment();

        request = new Request(getUser(), "query assets");
        response = this.sendRequest(request);
        DataCollection assets = (DataCollection) response.getAttachment();

        request = new Request(getUser(), "query organisationalUnits");
        response = this.sendRequest(request);
        DataCollection organisationalUnits = (DataCollection) response.getAttachment();

        request = new Request(getUser(), "query stocks");
        response = this.sendRequest(request);
        DataCollection stocks = (DataCollection) response.getAttachment();

        localDatabase = new AdminLocalDatabase(users, assets, organisationalUnits, stocks);
        System.out.println(localDatabase);
    }
}