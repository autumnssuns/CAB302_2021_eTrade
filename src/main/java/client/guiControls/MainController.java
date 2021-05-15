package client.guiControls;

import client.data.IServerConnection;
import client.guiControls.login.LoginController;
import common.Exceptions.InvalidArgumentValueException;
import common.Request;
import common.Response;
import common.dataClasses.IData;
import common.dataClasses.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main controller acts as the local storage of data, containing the current User and is able to connect to a server
 * connection class.
 * // TODO: Needs redesign & refactor & documentation.
 */
public class MainController {
    protected ILocalDatabase localDatabase;

    /**
     * The server connection
     */
    private IServerConnection serverConnection;

    /**
     * The current user
     */
    private User user;

    /**
     * Sets the user that is currently using the application.
     * @param user The current user.
     */
    public void setUser(User user){
        this.user = user;
    }

    /**
     * Sets the server connection that connects the controller to the server.
     * @param serverConnection The server connection.
     */
    public void setServerConnection(IServerConnection serverConnection){
        this.serverConnection = serverConnection;
    }

    /**
     * Returns the current server connection.
     * @return The current server connection.
     */
    public IServerConnection getServerConnection(){
        return serverConnection;
    }

    /**
     * Asks the server connection to send a request to the server.
     * @param action
     * @param attachment
     */
    public <T extends IData> Response sendRequest(String action, T attachment, Class<T> attachmentType) throws InvalidArgumentValueException {
        Request request = new Request(getUser(), action, attachment);
        request.setAttachmentType(attachmentType);
        Response response = serverConnection.sendRequest(request);
        updateLocalDatabase(attachmentType);
        return response;
    }

    public Response sendRequest(String action) throws InvalidArgumentValueException {
        return serverConnection.sendRequest(new Request(getUser(), action));
    }

    /**
     * Returns the current user.
     * @return The current user.
     */
    public User getUser(){
        return user;
    }

    /**
     * Logs the current user out of the session.
     * @param event The event triggering the method.
     * @throws IOException
     */
    public void logOut(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Login.fxml"));

        // Sets the loader
        Parent root = loader.load();
        LoginController controller = loader.getController();
        controller.setServerConnection(this.getServerConnection());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        // Applies CSS
        String css = this.getClass().getClassLoader().getResource("client.css").toExternalForm();
        scene.getStylesheets().add(css);

        // Shows the scene
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Fetch the local database from the server.
     */
    public void fetchDatabase() throws InvalidArgumentValueException {

    }

    /**
     * Update the local database with that from the server
     */
    public <T extends IData> void updateLocalDatabase(Class<T> type) throws InvalidArgumentValueException {}

    /**
     * Returns the local database for the admin.
     * @return The local database for the admin.
     */
    public ILocalDatabase getDatabase(){
        return localDatabase;
    }
}