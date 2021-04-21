package common;

import common.dataClasses.User;

import java.io.Serializable;

/**
 * A package containing the the request to be send over the server.
 */
public class Request implements Serializable {
    private String senderName;
    private String action;
    private Object object;

    /**
     * Initialises a request that can be serialised and sent to the server
     * @param action The action as a string (update, commit or delete).
     * @param object The data to be sent.
     */
    public Request(String action, Object object){
        this.action = action;
        this.object = object;
    }

    /**
     * Initialises a request that can be serialised and sent to the server, including the sender's information.
     * @param sender The user sending the request.
     * @param action The action as a string (update, commit or delete).
     * @param object The data to be sent.
     */
    public Request(User sender, String action, Object object) {
        this(action, object);
        senderName = sender.getUsername();
    }

    /**
     * Returns the action
     * @return The action
     */
    public String getAction(){
        return action;
    }

    public Object getAttachment(){
        return object;
    }
}