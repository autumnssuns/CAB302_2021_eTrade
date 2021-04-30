package common;

import common.dataClasses.IData;

import java.io.Serializable;

/**
 * A package containing a response sent from the server to the client, including the state of whether the request
 * were successfully executed and an optional data-containing object (IData).
 */
public class Response implements Serializable {
    private boolean status;
    private IData attachment;

    /**
     * Initialises a response that can be sent back to the client.
     * @param status The status of whether or not the request was fulfilled.
     * @param attachment The object attached to the response.
     */
    public Response(boolean status, IData attachment){
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