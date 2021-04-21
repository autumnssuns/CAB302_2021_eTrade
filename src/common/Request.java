package common;

import common.dataClasses.User;

import java.io.Serializable;

/**
 * A package containing the the request to be send over the server.
 */
public class Request implements Serializable {
    private String senderName;
    private String action;
    private Object attachment;

    /**
     * Initialises a request without attachment (mostly query requests) that can be serialised and sent to the server.
     * @param sender The user sending the request.
     * @param query The query string.
     */
    public Request(User sender, String query){
        this.senderName = sender.getUsername();
        this.action = query;
    }

    /**
     * Initialises a request that can be serialised and sent to the server
     * @param action The action as a string (update, commit or delete).
     * @param attachment The data to be sent.
     */
    public Request(String action, Object attachment){
        this.action = action;
        this.attachment = attachment;
    }

    /**
     * Initialises a request that can be serialised and sent to the server, including the sender's information.
     * @param sender The user sending the request.
     * @param action The action as a string (update, commit or delete).
     * @param attachment The data to be sent.
     */
    public Request(User sender, String action, Object attachment) {
        this(action, attachment);
        senderName = sender.getUsername();
    }

    /**
     * Returns the username of the current client's user.
     * @return The username of the current client's user.
     */
    public String getSenderName(){
        return senderName;
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
    public Object getAttachment(){
        return attachment;
    }
}