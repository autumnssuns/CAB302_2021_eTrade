package client.guiControls.adminMain.usersController;

import client.guiControls.DisplayController;
import client.guiControls.adminMain.AdminLocalDatabase;
import common.dataClasses.DataCollection;
import common.dataClasses.OrganisationalUnit;
import common.dataClasses.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

/**
 * A controller to control the USERS Page (which allows the admin to add / remove or edit users' information).
 */
public class UsersController extends DisplayController {

    @FXML
    VBox usersDisplayBox;
    @FXML
    TextField newUserNameTextField;
    @FXML
    TextField newUsernameTextField;
    @FXML
    PasswordField newPasswordField;
    @FXML
    ComboBox newOrganisationUnitSelectionBox;
    @FXML
    ComboBox newRoleSelectionBox;

    /**
     * Initialses the page by sending a request to server and add initial data based on response(s).
     */
    @FXML
    public void initialize(){
        newRoleSelectionBox.getItems().addAll("user", "admin");
    }

    /**
     * Adds a new entry, representing a new user.
     */
    public void addEntry(){
        int userId = usersDisplayBox.getChildren().size();
        String name = newUserNameTextField.getText();
        String username = newUsernameTextField.getText();
        String password = newPasswordField.getText();
        String organisationalUnit = (String) newOrganisationUnitSelectionBox.getValue();
        String role = (String) newRoleSelectionBox.getValue();

        addUserInfoBox(userId, name, username, password, organisationalUnit, role);
        clearAddEntry();
    }

    /**
     * Adds a new entry to the current display.
     * @param userId The ID of the user.
     * @param name The name of the user.
     * @param username The username of the user.
     * @param password The password of the user (this is not viewable, only editable).
     * @param organisationalUnit The organisationalUnit of the user.
     * @param role The role of the user.
     */
    private void addUserInfoBox(int userId, String name, String username, String password, String organisationalUnit, String role){
        UserInfoBox userInfoBox = new UserInfoBox(userId, name, username, password, organisationalUnit, role);
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

    //TODO: Gets data from database
    @Override
    public void update(){
        AdminLocalDatabase localDatabase = (AdminLocalDatabase) controller.getDatabase();
        DataCollection<User> users = localDatabase.getUsers();
        DataCollection<OrganisationalUnit> organisationalUnits = localDatabase.getOrganisationalUnits();

        String[] organisationNames = new String[organisationalUnits.size()];
        for (int i = 0; i < organisationalUnits.size(); i++){
            organisationNames[i] = organisationalUnits.get(i).getName();
        }

        int count = 0;
        for (User user : users){
            int unitId = user.getunitId();
            String organisationalUnit = organisationalUnits.get(unitId).getName();
            addUserInfoBox(count, user.getUsername(), user.getUsername(), user.getPassword(), organisationalUnit, user.getAccountType());
            count++;
        }
        newOrganisationUnitSelectionBox.getItems().addAll(organisationNames);
    }
}
