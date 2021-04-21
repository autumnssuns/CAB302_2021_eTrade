package server;

import common.Request;
import common.Response;

/**
 * Interface for server object. Both the mock and real server should implement this.
 */
public interface IServer {
    Response sendResponse(Request request);
}
