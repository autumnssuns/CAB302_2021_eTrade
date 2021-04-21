package common;

import java.io.Serializable;

public class Response implements Serializable {
    private boolean status;
    private Object attachment;

    /**
     * Initialises a response that can be sent back to the client.
     * @param status The status of whether or not the request was fulfilled.
     * @param attachment The object attached to the response.
     */
    public Response(boolean status, Object attachment){
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
    public Object getAttachment(){
        return attachment;
    }
}