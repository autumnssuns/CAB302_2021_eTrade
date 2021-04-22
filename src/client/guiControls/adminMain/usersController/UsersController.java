package client.guiControls.adminMain.usersController;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

/**
 * A controller to control the USERS Page (which allows the admin to add / remove or edit users' information).
 */
public class UsersController {

    @FXML
    VBox usersDisplayBox;
    @FXML
    TextField newNameTextField;
    @FXML
    TextField newUsernameTextField;
    @FXML
    PasswordField newPasswordField;
    @FXML
    ComboBox newOrganisationSelectionBox;
    @FXML
    ComboBox newRoleSelectionBox;

    /**
     * Initialses the page.
     */
    @FXML
    public void initialize(){
        newOrganisationSelectionBox.getItems().addAll("TestOrg", "The Justice League", "The supervillains", "The random civilians");
        newRoleSelectionBox.getItems().addAll("user", "admin");
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
        newNameTextField.clear();
        newUsernameTextField.clear();
        newPasswordField.clear();
        newOrganisationSelectionBox.valueProperty().set(null);
        newOrganisationSelectionBox.setPromptText("Organisation Unit");
        newRoleSelectionBox.valueProperty().set(null);
        newRoleSelectionBox.setPromptText("Role");
    }

    /**
     * Adds a new entry, representing a new user.
     */
    public void addEntry(){
        int userId = usersDisplayBox.getChildren().size();
        String name = newNameTextField.getText();
        String username = newUsernameTextField.getText();
        String password = newPasswordField.getText();
        String organisation = (String) newOrganisationSelectionBox.getValue();
        String role = (String) newRoleSelectionBox.getValue();

        addUserInfoBox(userId, name, username, password, organisation, role);
        clearAddEntry();
    }

    //TODO: Gets data from database
    public void update(){

    }
}
