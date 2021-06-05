package client.guiControls;

import client.data.IServerConnection;
import client.guiControls.login.LoginController;
import common.Exceptions.InvalidArgumentValueException;
import common.Request;
import common.Response;
import common.dataClasses.IData;
import common.dataClasses.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main controller acts as the local storage of data, containing the current UserGUI and is able to connect to a server
 * connection class.
 * // TODO: Needs redesign & refactor & documentation.
 */
public abstract class MainController {
    protected LocalDatabase localDatabase;

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
     * Sends a request to the server with an attachment
     * @param action The action to be performed with the attachment
     * @param attachment The attachment
     * @param attachmentType The type of data the request targets
     * @param <T> The type of the attachment
     * @return The server's response for the given request
     */
    public <T extends IData> Response sendRequest(Request.ActionType action, T attachment, Request.ObjectType attachmentType) {
        Request request = new Request(getUser(), action, attachment);
        request.setObjectType(attachmentType);
        request.setPreviousObjectState(findPreviousState(request));
        Response response = new Response(false, null);
        try{
            // Send a requests to the server socket
            serverConnection.Start();
            response = serverConnection.sendRequest(request);
            serverConnection.Close();
            // Report the status
            String message = response.getMessage();
            MessageFactory.MessageType messageType = response.isAccepted() ? MessageFactory.MessageType.SUCCESS : MessageFactory.MessageType.ERROR;
            pushMessage(message, messageType);
            // Update the GUI after the request
            update();
        } catch (IOException | InvalidArgumentValueException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * Send an attachment-less request
     * @param action he action to be performed
     * @param objectType The type of data the request targets
     * @return The server's response for the given request
     */
    public Response sendRequest(Request.ActionType action, Request.ObjectType objectType) {
        Response response = new Response(false, null);
        try{
            serverConnection.Start();
            response = serverConnection.sendRequest(new Request(getUser(), action).setObjectType(objectType));
            serverConnection.Close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidArgumentValueException e) {
            e.printStackTrace();
        }
        return response;
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
    public abstract void fetchDatabase() throws InvalidArgumentValueException;

    /**
     * Finds the version of an object attached in a request that is stored in the local database.
     * Before any update, the local database contains the previous state of the object in request.
     * @param request The request whose attachment is to be found.
     * @return The previous state of the object in request.
     */
    protected abstract <T extends IData> T findPreviousState(Request request);

    /**
     * Returns the local database for the current user.
     * @return The local database for the current user.
     */
    public abstract LocalDatabase getDatabase();

    /**
     * Updates the GUI with new data from server
     * @throws InvalidArgumentValueException
     */
    public abstract void update() throws InvalidArgumentValueException;

    /**
     * Push a message for the user
     * @param message The string containing the message
     * @param type The type of the message (error, success or default)
     */
    public abstract void pushMessage(String message, MessageFactory.MessageType type);

    /**
     * Starts a background thread to continually updates the GUI
     */
    protected void startBackgroundThread(){
        Thread thread = new Thread(() -> {
            while (true) {
                try{
                    // Updates every 10 seconds
                    Thread.sleep(1000*10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    try {
                        update();
                    } catch (InvalidArgumentValueException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}