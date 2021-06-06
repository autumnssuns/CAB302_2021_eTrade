package client.guiControls;

import client.data.IServerConnection;
import common.Exceptions.InvalidArgumentValueException;
import common.Request;
import common.Response;
import common.dataClasses.IData;
import common.dataClasses.User;
import javafx.application.Platform;

import java.io.IOException;

/**
 * A controller that can connects and send requests to a server.
 */
public  abstract class Controller {
    /**
     * The server connection
     */
    protected IServerConnection serverConnection;

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
     * Returns the current user.
     * @return The current user.
     */
    public User getUser(){
        return user;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * Updates the GUI with new data from server
     * @throws InvalidArgumentValueException
     */
    public abstract void update() throws InvalidArgumentValueException;

    /**
     * Starts a background thread to continually updates the GUI
     */
    protected void startBackgroundThread(){
        Thread thread = new Thread(() -> {
            while (true) {
                try{
                    // Updates every 5 minutes
                    Thread.sleep(1000*300);
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
