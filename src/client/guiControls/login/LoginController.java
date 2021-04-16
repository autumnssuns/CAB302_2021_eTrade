package client.guiControls.login;

import client.data.Session;
import client.guiControls.LoginSystem;
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

public class LoginController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML TextField nameTextField;
    @FXML PasswordField passwordField;
    @FXML Label statusLabel;
    @FXML Label userLabel;

    // Attempt to log the user in
    public void attemptLogin(ActionEvent event) throws IOException {
        //TODO: Connect to server to authenticate the user
        String username = nameTextField.getText();
        String password = passwordField.getText();
        boolean loginSuccess = authenticate(username,password);

        //TODO: Wait for response from server
        if(loginSuccess){
            Session.startSession(username);
            switchToMainScreen(event);
        }
        else{
            reset();
        }
    }

    private boolean authenticate(String username, String password){
        LoginSystem newLogin = new LoginSystem();
        boolean loginSuccess = newLogin.login(username, password);
//        boolean loginSuccess = username.equals("admin") || username.equals("root") && password.equals("root");
        return loginSuccess;
    }

    private void switchToMainScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../mainUser/Main.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        String css = this.getClass().getResource("../client.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    private void reset(){
        nameTextField.clear();
        passwordField.clear();
        statusLabel.setText("Incorrect username or password. Please try again!");
    }
}