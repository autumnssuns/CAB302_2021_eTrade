package common;

import common.dataClasses.IData;

import java.io.Serializable;
import java.util.Objects;

/**
 * A package containing a response sent from the server to the client, including the state of whether the request
 * were successfully executed and an optional data-containing object (IData).
 */
public class Response<T extends IData> implements Serializable {
    private boolean status;
    private T attachment;
    private Request.ObjectType attachmentType;

    /**
     * Sets the type of the request's attachment
     * @param attachmentType The type of the request's attachment.
     */
    public void setAttachmentType(Request.ObjectType attachmentType){
        this.attachmentType = attachmentType;
    }

    /**
     * Gets the attachment's type of this request.
     * @return The attachment's type.
     */
    public Request.ObjectType getAttachmentType(){
        return attachmentType;
    }

    /**
     * Initialises a response that can be sent back to the client.
     * @param status The status of whether or not the request was fulfilled.
     * @param attachment The object attached to the response.
     */
    public Response(boolean status, T attachment){
        this.status = status;
        this.attachment = attachment;
    }

    /**
     * Retrieves the status of the request.
     * @return The status of whether or not the request was fulfilled.
     */
    public boolean isFulfilled(){
        return status;
    }

    /**
     * Retrieves the object attached to the response.
     * @return The object attached to the response.
     */
    public IData getAttachment(){
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
        Response<?> response = (Response<?>) o;
        return status == response.status && Objects.equals(attachment, response.attachment) && Objects.equals(attachmentType, response.attachmentType);
    }

    /**
     * Returns the hashCode of this instance.
     * @return The hashCode of this instance.
     */
    @Override
    public int hashCode() {
        return Objects.hash(status, attachment, attachmentType);
    }
}