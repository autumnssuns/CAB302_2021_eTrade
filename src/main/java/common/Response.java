package common;

import common.dataClasses.IData;

import java.io.Serializable;
import java.util.Objects;

/**
 * A package containing a response sent from the server to the client, including the state of whether the request
 * were successfully executed and an optional data-containing object (IData).
 */
public class Response<T extends IData> implements Serializable {
    private boolean accepted;
    private T attachment;
    private String message;

    /**
     * Initialises a response that can be sent back to the client.
     * @param accepted The status of whether or not the request was fulfilled.
     * @param attachment The object attached to the response.
     */
    public Response(boolean accepted, T attachment){
        this.accepted = accepted;
        this.attachment = attachment;
        message = accepted ? "Your request was successfully resolved!" : "Your request was denied!";
    }

    /**
     * Retrieves the status of the request.
     * @return The status of whether or not the request was fulfilled.
     */
    public boolean isAccepted(){
        return accepted;
    }

    /**
     * Retrieves the object attached to the response.
     * @return The object attached to the response.
     */
    public IData getAttachment(){
        return attachment;
    }

    /**
     * Sets a message to be sent with this request.
     * @param message The message enclosed within this request.
     */
    public void setMessage(String message){
        this.message = message;
    }

    /**
     * Retrieves the message sent with this request.
     */
    public String getMessage(){
        return this.message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response<?> response = (Response<?>) o;
        return accepted == response.accepted && Objects.equals(attachment, response.attachment) && Objects.equals(message, response.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accepted, attachment, message);
    }
}