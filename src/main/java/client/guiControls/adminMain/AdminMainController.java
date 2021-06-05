package client.guiControls.adminMain;

import client.guiControls.MainController;
import client.guiControls.MessageFactory;
import client.guiControls.MessageViewUnit;
import client.guiControls.adminMain.assetsController.AssetsController;
import client.guiControls.adminMain.organisationalUnitsController.OrganisationalUnitsController;
import client.guiControls.adminMain.usersController.UsersController;
import client.guiControls.userMain.UserLocalDatabase;
import common.Exceptions.InvalidArgumentValueException;
import common.Request;
import common.Response;
import common.dataClasses.DataCollection;
import common.dataClasses.IData;
import common.dataClasses.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

//TODO: Refactor magic numbers & Node creation
//TODO: Commenting & Documenting

public class AdminMainController extends MainController {
    // The linked local database
    AdminLocalDatabase localDatabase;

    //Reusable elements that can be updated
    private Pane usersPane;
    private UsersController usersController;
    private Pane organisationUnitsPane;
    private OrganisationalUnitsController organisationalUnitsController;
    private Pane assetsPane;
    private AssetsController assetsController;
    private Pane profilePane;

    //Preset components
    @FXML private StackPane displayStack;
    @FXML private Pane filterPane;
    @FXML private Button assetsButton;
    @FXML private Button usersButton;
    @FXML private Button organisationalUnitsButton;
    @FXML private AnchorPane anchorPane;
    @FXML private Label userLabel;
    @FXML private VBox messageBox;

    /**
     * Initialises the controller by loading the sub-panes.
     * @throws IOException
     */
    @FXML
    public void initialize() throws IOException {
        start();
    }

    /**
     * Sets up the controller
     * @throws IOException Required by JavaFX
     */
    @Override
    protected void setupController() throws IOException, InvalidArgumentValueException {
        localDatabase = new AdminLocalDatabase();
        fetchDatabase();

        userLabel.setText(getUser().getUsername());

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("AdminGUI/UsersPage.fxml"));
        usersPane = fxmlLoader.load();
        usersController = fxmlLoader.getController();
        usersController.setController(this);
        usersController.update();

        fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("AdminGUI/OrganisationalUnitsPage.fxml"));
        organisationUnitsPane = fxmlLoader.load();
        organisationalUnitsController = fxmlLoader.getController();
        organisationalUnitsController.setController(this);
        organisationalUnitsController.update();

        fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("AdminGUI/AssetsPage.fxml"));
        assetsPane = fxmlLoader.load();
        assetsController = fxmlLoader.getController();
        assetsController.setController(this);
        assetsController.update();

        profilePane = new Pane();

        displayStack.getChildren().addAll(usersPane, organisationUnitsPane, assetsPane, profilePane);
        toOrganisationUnits();
    }

    /**
     * Switches the display to the organisational unit UNITS pane.
     */
    public void toOrganisationUnits(){
        organisationalUnitsController.update();
        organisationUnitsPane.toFront();
        organisationalUnitsButton.setDisable(true);
        assetsButton.setDisable(false);
        usersButton.setDisable(false);
    }

    /**
     * Switches the display to the USERS pane.
     */
    public void toUsers() throws InvalidArgumentValueException {
        usersController.update();
        usersPane.toFront();
        organisationalUnitsButton.setDisable(false);
        assetsButton.setDisable(false);
        usersButton.setDisable(true);
    }

    /**
     * Switches the display to the ASSETS pane.
     */
    public void toAssets(){
        assetsController.update();
        assetsPane.toFront();
        organisationalUnitsButton.setDisable(false);
        assetsButton.setDisable(true);
        usersButton.setDisable(false);
    }

    /**
     * Update the whole local database with data from server
     */
    @Override
    public void fetchDatabase() throws InvalidArgumentValueException {
        Response response = this.sendRequest(Request.ActionType.READ_ALL, Request.ObjectType.USER);
        DataCollection users = (DataCollection) response.getAttachment();

        response = this.sendRequest(Request.ActionType.READ_ALL, Request.ObjectType.ASSET);
        DataCollection assets = (DataCollection) response.getAttachment();

        response = this.sendRequest(Request.ActionType.READ_ALL, Request.ObjectType.ORGANISATIONAL_UNIT);
        DataCollection organisationalUnits = (DataCollection) response.getAttachment();

        response = this.sendRequest(Request.ActionType.READ_ALL, Request.ObjectType.STOCK);
        DataCollection stocks = (DataCollection) response.getAttachment();

        localDatabase.setUsers(users);
        localDatabase.setAssets(assets);
        localDatabase.setOrganisationalUnits(organisationalUnits);
        localDatabase.setStocks(stocks);
    }

    /**
     * Returns the local database for the current admin.
     * @return The local database for the current admin.
     */
    @Override
    public AdminLocalDatabase getDatabase() {
        return localDatabase;
    }

    /**
     * Find the previous states of the request's attached object in the local database.
     * @param request The request whose attachment is to be found.
     * @return The previous states of the request's attached object in the local database.
     */
    // TODO: Refactor IData
    @Override
    protected <T extends IData> T findPreviousState(Request request) {
        // The table to look into, depending on the object's type
        AdminLocalDatabase localDatabase = (AdminLocalDatabase) this.localDatabase;
        DataCollection table;
        switch (request.getObjectType()){
            case ASSET:
                table = localDatabase.getAssets();
                Asset newAsset = (Asset) request.getAttachment();
                for (Asset previous : (DataCollection<Asset>) table){
                    if (previous.getId().equals(newAsset.getId())){
                        return (T) previous;
                    }
                }
                break;
            case USER:
                table = localDatabase.getUsers();
                User newUser = (User) request.getAttachment();
                for (User previous : (DataCollection<User>) table){
                    if (previous.getId().equals(newUser.getId())){
                        return (T) previous;
                    }
                }
                break;
            case ORGANISATIONAL_UNIT:
                table = localDatabase.getOrganisationalUnits();
                OrganisationalUnit newOrg = (OrganisationalUnit) request.getAttachment();
                for (OrganisationalUnit previous : (DataCollection<OrganisationalUnit>) table){
                    if (previous.getId().equals(newOrg.getId())){
                        return (T) previous;
                    }
                }
                break;
            case STOCK:
                table = localDatabase.getStocks();
                Stock newStock = (Stock) request.getAttachment();
                for (Stock previous : (DataCollection<Stock>) table){
                    if (previous.getUnitId().equals(newStock.getUnitId())){
                        return (T) previous;
                    }
                }
                break;
        }
        return null;
    }

    /**
     * Updates the view of the main page
     */
    @Override
    public void update() throws InvalidArgumentValueException {
        fetchDatabase();
        assetsController.update();
        usersController.update();
        organisationalUnitsController.update();
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
    }
}