package client.data;

import common.Exceptions.InvalidArgumentValueException;
import common.Request;
import common.Response;
import common.dataClasses.User;
import server.IServer;
import server.MockServer;

import java.util.ArrayList;

public final class MockServerConnection implements IServerConnection{
    IServer server = null;

    public MockServerConnection() {
        server = new MockServer();
    }

    @Override
    public Response sendRequest(Request request) throws InvalidArgumentValueException {
        Response response = server.sendResponse(request);
        return response;
    }

    @Override
    public void Close() {

    }
}