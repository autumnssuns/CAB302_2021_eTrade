package common;

import common.dataClasses.IData;

import java.io.Serializable;

/**
 * A package containing a response sent from the server to the client, including the state of whether the request
 * were successfully executed and an optional data-containing object (IData).
 */
public class Response<T extends IData> implements Serializable {
    private boolean status;
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
}