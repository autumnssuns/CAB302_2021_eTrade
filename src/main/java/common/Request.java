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
    private String action;
    private T attachment;
    private Class<T> attachmentType;

    /**
     * Sets the type of the request's attachment
     * @param attachmentType The type of the request's attachment.
     */
    public void setAttachmentType(Class<T> attachmentType){
        this.attachmentType = attachmentType;
    }

    /**
     * Gets the attachment's type of this request.
     * @return The attachment's type.
     */
    public Class<T> getAttachmentType(){
        return attachmentType;
    }

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
    public Request(User sender, String action, T attachment) {
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
        return Objects.equals(sender, request.sender) && Objects.equals(action, request.action) && Objects.equals(attachment, request.attachment) && Objects.equals(attachmentType, request.attachmentType);
    }

    /**
     * Returns the hashCode of this instance.
     * @return The hashCode of this instance.
     */
    @Override
    public int hashCode() {
        return Objects.hash(sender, action, attachment, attachmentType);
    }
}