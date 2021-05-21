package client.guiControls.userMain.profileController;

import client.guiControls.DisplayController;
import client.guiControls.userMain.UserLocalDatabase;
import common.Exceptions.InvalidArgumentValueException;
import common.dataClasses.OrganisationalUnit;
import common.dataClasses.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * A controller for the profile page
 */
public class ProfileController extends DisplayController {
    @FXML
    private Label fullNameLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label unitLabel;
    @FXML
    private Label roleLabel;
    @FXML
    private TextField currentPasswordField;
    @FXML
    private TextField newPasswordField;
    @FXML
    private TextField confirmNewPasswordField;

    private User user;

    /**
     * Updates with database and displays user's information
     */
    @Override
    public void update() throws InvalidArgumentValueException{
        UserLocalDatabase localDatabase = (UserLocalDatabase) controller.getDatabase();
        OrganisationalUnit unit = localDatabase.getOrganisationalUnit();

        user = controller.getUser();
        fullNameLabel.setText(user.getFullName());
        usernameLabel.setText(user.getUsername());
        unitLabel.setText(unit.getName());
        roleLabel.setText(user.getAccountType());
    }

    /**
     * Changes the user's password.
     */
    public void changePassword() throws InvalidArgumentValueException {
        User tempUser = new User(user.getUserId(), user.getFullName(), user.getUsername(),
                currentPasswordField.getText(), user.getAccountType(), user.getUnitId());
        tempUser.setPassword(tempUser.getPassword());
        // Checks if the password entered is correct
        // and the new password is confirmed
        if (tempUser.equals(user) && confirmPassword()){
            user.setPassword(newPasswordField.getText());
            controller.sendRequest("edit", user, User.class);
            currentPasswordField.clear();
            newPasswordField.clear();
            confirmNewPasswordField.clear();
        }
    }

    /**
     * Checks if the password in the confirm new password text field is the same
     * as the new password text field
     * @return
     */
    public boolean confirmPassword(){
        if (newPasswordField.getText().equals(confirmNewPasswordField.getText())){
            confirmNewPasswordField.setStyle("-fx-control-inner-background: lightgreen");
        }
        else{
            confirmNewPasswordField.setStyle("-fx-control-inner-background: lightcoral ");
        }
        return newPasswordField.getText().equals(confirmNewPasswordField.getText());
    }
}
