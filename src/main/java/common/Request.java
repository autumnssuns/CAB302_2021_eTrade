package common;

import common.dataClasses.IData;
import common.dataClasses.User;

import java.io.Serializable;
import java.util.Objects;

/**
 * A package containing the request to be sent over the server, including the sender as a user object,
 * a string containing the action to be comprehended and an attachment containing the data to be interpreted.
 */
public class Request<T extends IData> implements Serializable {
    private User sender;
    private ActionType actionType;
    private T previousObjectState;
    private T attachment;
    private ObjectType objectType;

    /**
     * The types of action a request can carry
     */
    public enum ActionType {
        PING,
        TEST,
        LOGIN,
        READ,
        READ_ALL,
        CREATE,
        UPDATE,
        DELETE,
        CLEAN
    }

    /**
     * The type of the object targeted by the request
     */
    public enum ObjectType {
        STOCK,
        ASSET,
        ORGANISATIONAL_UNIT,
        USER,
        ORDER,
        NOTIFICATION
    }

    /**
     * Sets the type of the request's attachment
     * @param objectType The type of the request's attachment.
     */
    public Request setObjectType(ObjectType objectType){
        this.objectType = objectType;
        return this;
    }

    /**
     * Sets the previous state of the attachment
     * @param previousObjectState The previous state of the attachment
     * @return The current instance for building
     */
    public Request setPreviousObjectState(T previousObjectState){
        this.previousObjectState = previousObjectState;
        return this;
    }

    /**
     * Returns the previous state of the attachment. This can be used to check for conflict when
     * the server handles the request queue.
     * @return The previous state of the attachment
     */
    public T getPreviousObjectState(){
        return previousObjectState;
    }

    /**
     * Gets the attachment's type of this request.
     * @return The attachment's type.
     */
    public ObjectType getObjectType(){
        return objectType;
    }

    /**
     * Initialises a request without attachment (mostly query requests) that can be serialised and sent to the server.
     * @param sender The user sending the request.
     * @param query The query string.
     */
    public Request(User sender, ActionType query){
        this.sender = sender;
        this.actionType = query;
    }

    /**
     * Initialises a request that can be serialised and sent to the server, including the sender's information.
     * @param sender The user sending the request.
     * @param actionType The action as a string (update, commit or delete).
     * @param attachment The data to be sent.
     */
    public Request(User sender, ActionType actionType, T attachment) {
        this.sender = sender;
        this.actionType = actionType;
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
    public ActionType getActionType(){
        return actionType;
    }

    /**
     * The object attached to the attachment.
     * @return The attached object.
     */
    public T getAttachment(){
        return attachment;
    }

    /**
     * Indicates if some object is equal to this instance.
     * @param o The object to compare.
     * @return true if the object is equal to the instance, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request<?> request = (Request<?>) o;
        return Objects.equals(sender, request.sender) && Objects.equals(actionType, request.actionType) && Objects.equals(attachment, request.attachment) && Objects.equals(objectType, request.objectType);
    }

    /**
     * Returns the hashCode of this instance.
     * @return The hashCode of this instance.
     */
    @Override
    public int hashCode() {
        return Objects.hash(sender, actionType, attachment, objectType);
    }
}