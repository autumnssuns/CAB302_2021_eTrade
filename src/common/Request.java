package common;

import common.dataClasses.IData;
import common.dataClasses.User;

import java.io.Serializable;

/**
 * A package containing the request to be sent over the server.
 */
public class Request implements Serializable {
    private User sender;
    private String action;
    private IData attachment;

    /**
     * Initialises a request without attachment (mostly query requests) that can be serialised and sent to the server.
     * @param sender The user sending the request.
     * @param query The query string.
     */
    public Request(User sender, String query){
        this.sender = sender;
        this.action = query;
    }

    /**
     * Initialises a request that can be serialised and sent to the server, including the sender's information.
     * @param sender The user sending the request.
     * @param action The action as a string (update, commit or delete).
     * @param attachment The data to be sent.
     */
    public Request(User sender, String action, IData attachment) {
        this.sender = sender;
        this.action = action;
        this.attachment = attachment;
    }

    /**
     * Returns the username of the current client's user.
     * @return The username of the current client's user.
     */
    public User getUser(){
        return sender;
    }

    /**
     * Returns the action requested by the client.
     * @return The action requested by the client.
     */
    public String getAction(){
        return action;
    }

    /**
     * The object attached to the attachment.
     * @return The attached object.
     */
    public IData getAttachment(){
        return attachment;
    }
}