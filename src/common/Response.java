package common;

import common.dataClasses.User;

import java.io.Serializable;

public class Response implements Serializable {
    private boolean status;
    private Object object;

    /**
     * Initialises a response that can be sent back to the client.
     * @param status The status of whether or not the request was fulfilled.
     * @param object The object attached to the response.
     */
    public Response(boolean status, Object object){
        this.status = status;
        this.object = object;
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
    public Object getObject(){
        return object;
    }
}