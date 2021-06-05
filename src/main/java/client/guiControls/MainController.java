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
 * The main controller acts as the local storage of data, containing the current UserGUI
 * and is able to connect to a server connection class.
 * // TODO: Needs redesign & refactor & documentation.
 */
public abstract class MainController extends Controller{
    protected LocalDatabase localDatabase;

    /**
     * Finds the version of an object attached in a request that is stored in the local database.
     * Before any update, the local database contains the previous state of the object in request.
     * @param request The request whose attachment is to be found.
     * @return The previous state of the object in request.
     */
    protected abstract <T extends IData> T findPreviousState(Request request);

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
     * Returns the local database for the current user.
     * @return The local database for the current user.
     */
    public abstract LocalDatabase getDatabase();

    /**
     * Push a message for the user
     * @param message The string containing the message
     * @param type The type of the message (error, success or default)
     */
    public abstract void pushMessage(String message, MessageFactory.MessageType type);

    /**
     * Sets up the view & the controllers by initialising the sub-panes
     * @throws IOException
     */
    protected abstract void setupController() throws IOException, InvalidArgumentValueException;

    /**
     * Starts the controller & scene after setting up and initiating background thread
     */
    protected void start(){
        // https://stackoverflow.com/questions/14370183/passing-parameters-to-a-controller-when-loading-an-fxml
        // Used to wait until the non-GUI component (controller) is finished, making sure getUser() is not null.
        Platform.runLater(() -> {
            try {
                setupController();
            } catch (IOException | InvalidArgumentValueException e) {
                e.printStackTrace();
            }
            startBackgroundThread();
        });
    }
}