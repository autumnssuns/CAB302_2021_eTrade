package client.guiControls.login;

import client.data.IServerConnection;
import client.data.MockServerConnection;
import client.guiControls.MainController;
import client.guiControls.adminMain.adminMainController;
import common.Request;
import common.Response;
import common.dataClasses.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A controller for the login screen that authenticate the user and redirect to another main scene.
 */
public class LoginController extends MainController {
    @FXML TextField nameTextField;
    @FXML PasswordField passwordField;
    @FXML Label statusLabel;

    /**
     * Initialise the session by creating a server connection.
     */
    @FXML
    public void initialize(){
        IServerConnection serverConnection = new MockServerConnection();
        this.setServerConnection(serverConnection);
    }

    /**
     * Sends a request to the server, attempt to login with the provided username and password.
     * @param event The event linked with the method (the button click).
     * @throws IOException
     */
    public void attemptLogin(ActionEvent event) throws IOException {
        //TODO: Connect to server to authenticate the user
        String username = nameTextField.getText();
        String password = passwordField.getText();

        User tempUser = new User(username, password);
        Request request = new Request(tempUser, "login");
        Response response = this.sendRequest(request);
        boolean loginSuccess = response.isFulfilled();

        //TODO: Wait for response from server
        if(loginSuccess){
            User currentUser = (User)response.getAttachment();
            this.setUser(currentUser);
            switchToMainScreen(event);
        }
        else{
            reset();
        }
    }

    /**
     * Switch to the main screen, based on the current user.
     * @param event The event triggering the method.
     * @throws IOException
     */
    private void switchToMainScreen(ActionEvent event) throws IOException {
        String resourcePath = "";
        switch(this.getUser().getAccountType()){
            case "user":
                resourcePath = "../userMain/userMain.fxml";
                break;

            case "admin":
                resourcePath = "../adminMain/adminMain.fxml";
        }

        // Gets the loader
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resourcePath));

        // Loads the scene & pass current user and connection to main scene
        Parent root = fxmlLoader.load();
        MainController mainController = fxmlLoader.getController();
        mainController.setUser(this.getUser());
        mainController.setServerConnection(this.getServerConnection());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        // Applies css
        String css = this.getClass().getResource("../client.css").toExternalForm();
        scene.getStylesheets().add(css);

        // Show the scene
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Empties the fields on incorrect password.
     */
    private void reset(){
        nameTextField.clear();
        passwordField.clear();
        statusLabel.setText("Incorrect username or password. Please try again!");
    }
}