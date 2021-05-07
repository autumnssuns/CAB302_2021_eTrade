package client.guiControls.adminMain.usersController;

import client.guiControls.DisplayController;
import client.guiControls.adminMain.AdminLocalDatabase;
import common.dataClasses.Asset;
import common.dataClasses.DataCollection;
import common.dataClasses.Organisation;
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
    ComboBox newOrganisationSelectionBox;
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
        String organisation = (String) newOrganisationSelectionBox.getValue();
        String role = (String) newRoleSelectionBox.getValue();

        addUserInfoBox(userId, name, username, password, organisation, role);
        clearAddEntry();
    }

    /**
     * Adds a new entry to the current display.
     * @param userId The ID of the user.
     * @param name The name of the user.
     * @param username The username of the user.
     * @param password The password of the user (this is not viewable, only editable).
     * @param organisation The organisation of the user.
     * @param role The role of the user.
     */
    private void addUserInfoBox(int userId, String name, String username, String password, String organisation, String role){
        UserInfoBox userInfoBox = new UserInfoBox(userId, name, username, password, organisation, role);
        usersDisplayBox.getChildren().add(userInfoBox);
    }

    /**
     * Resets the fields in the new user row.
     */
    private void clearAddEntry(){
        newUserNameTextField.clear();
        newUsernameTextField.clear();
        newPasswordField.clear();
        newOrganisationSelectionBox.valueProperty().set(null);
        newOrganisationSelectionBox.setPromptText("Organisation Unit");
        newRoleSelectionBox.valueProperty().set(null);
        newRoleSelectionBox.setPromptText("Role");
    }

    //TODO: Gets data from database
    @Override
    public void update(){
        AdminLocalDatabase localDatabase = (AdminLocalDatabase) controller.getDatabase();
        DataCollection<User> users = localDatabase.getUsers();
        DataCollection<Organisation> organisations = localDatabase.getOrganisations();

        String[] organisationNames = new String[organisations.size()];
        for (int i = 0; i < organisations.size(); i++){
            organisationNames[i] = organisations.get(i).getName();
        }

        int count = 0;
        for (User user : users){
            int organisationId = user.getOrganisationId();
            String organisation = organisations.get(organisationId).getName();
            addUserInfoBox(count, user.getUsername(), user.getUsername(), user.getPassword(), organisation, user.getAccountType());
            count++;
        }
        newOrganisationSelectionBox.getItems().addAll(organisationNames);
    }
}
