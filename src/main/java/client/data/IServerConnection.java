package client.data;

import common.Exceptions.InvalidArgumentValueException;
import common.Request;
import common.Response;

/**
 * A class to represent a server connection. This is a client side class.
 */
public interface IServerConnection {
    /**
     * Send a request to the server. This should be included in a new thread.
     * @param request The request to be sent.
     * @return The server's response.
     */
    Response sendRequest(Request request) throws InvalidArgumentValueException;

    /**
     * Close the connection to the server.
     */
    void Close();
}
