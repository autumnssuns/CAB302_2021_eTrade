package client.guiControls.adminMain.usersController;

import client.guiControls.DisplayController;
import client.guiControls.adminMain.AdminLocalDatabase;
import common.Response;
import common.dataClasses.DataCollection;
import common.dataClasses.OrganisationalUnit;
import common.dataClasses.User;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * A controller to control the USERS Page (which allows the admin to add / remove or edit users' information).
 */
public class UsersController extends DisplayController {
    @FXML
    private VBox usersDisplayBox;
    @FXML
    private TextField newUserNameTextField;
    @FXML
    private TextField newUsernameTextField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private ComboBox<String> newOrganisationUnitSelectionBox;
    @FXML
    private ComboBox<String> newRoleSelectionBox;

    /**
     * Initialises the page by sending a request to server and add initial data based on response(s).
     */
    @FXML
    public void initialize(){
        newRoleSelectionBox.getItems().addAll("user", "admin");
    }

    /**
     * Adds a new entry, representing a new user.
     */
    public void addEntry() throws Exception {
        int userId = usersDisplayBox.getChildren().size();
        String name = newUserNameTextField.getText();
        String username = newUsernameTextField.getText();
        String password = newPasswordField.getText();
        String organisationalUnit = newOrganisationUnitSelectionBox.getValue();
        String role = newRoleSelectionBox.getValue();

        int unitId = 0;
        for (OrganisationalUnit unit : ((AdminLocalDatabase)controller.getDatabase()).getOrganisationalUnits()){
            if (unit.getName().equals(organisationalUnit)){
                unitId = unit.getId();
                break;
            }
        }
        User newUser = new User(userId, name, username, password, role, unitId);
        Response response = controller.sendRequest("add", newUser, User.class);
        update();
        if (response.isFulfilled()){
            addUserInfoBox(newUser);
            clearAddEntry();
        }
    }

    /**
     * Adds a new entry to the current display.
     * @param user The linked user.
     */
    private void addUserInfoBox(User user) throws Exception {
        UserInfoBox userInfoBox = new UserInfoBox(user, this);
        usersDisplayBox.getChildren().add(userInfoBox);
    }

    /**
     * Resets the fields in the new user row.
     */
    private void clearAddEntry(){
        newUserNameTextField.clear();
        newUsernameTextField.clear();
        newPasswordField.clear();
        newOrganisationUnitSelectionBox.valueProperty().set(null);
        newOrganisationUnitSelectionBox.setPromptText("OrganisationalUnit Unit");
        newRoleSelectionBox.valueProperty().set(null);
        newRoleSelectionBox.setPromptText("Role");
    }

    /**
     * Updates the view with new data
     */
    @Override
    public void update() throws Exception {
        usersDisplayBox.getChildren().clear();
        AdminLocalDatabase localDatabase = (AdminLocalDatabase) controller.getDatabase();
        DataCollection<User> users = localDatabase.getUsers();
        DataCollection<OrganisationalUnit> organisationalUnits = localDatabase.getOrganisationalUnits();

        newOrganisationUnitSelectionBox.getItems().clear();
        for (int i = 0; i < organisationalUnits.size(); i++){
            newOrganisationUnitSelectionBox.getItems()
                    .add(organisationalUnits.get(i).getName());
        }

        for (User user : users){
            addUserInfoBox(user);
        }
    }

    /**
     * Get the organisational unit given the user
     * @param user The user to find the unit
     * @return The organisational unit linked to the user
     */
    public OrganisationalUnit getOrganisation(User user){
        for (OrganisationalUnit unit : ((AdminLocalDatabase) controller.getDatabase()).getOrganisationalUnits()){
            if (unit.getId().equals(user.getUnitId())){
                return unit;
            }
        }
        return null;
    }

    /**
     * Get the organisational unit given its name
     * @param name the name of the organisational unit
     * @return The organisational unit with given name
     */
    public OrganisationalUnit getOrganisation(String name){
        for (OrganisationalUnit unit : ((AdminLocalDatabase) controller.getDatabase()).getOrganisationalUnits()){
            if (unit.getName().equals(name)){
                return unit;
            }
        }
        return null;
    }

    /**
     * Query all the organisational units in the system. This is necessary to
     * know which organisational units are available.
     * @return All the organisational units
     */
    public DataCollection<OrganisationalUnit> getOrganisationalUnits(){
        return ((AdminLocalDatabase) controller.getDatabase()).getOrganisationalUnits();
    }
}