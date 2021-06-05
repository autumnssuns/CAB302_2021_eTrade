package client.data;

import common.Exceptions.InvalidArgumentValueException;
import common.Request;
import common.Response;
import server.IServer;
import server.MockServer;

import java.io.IOException;

public final class MockServerConnection implements IServerConnection{
    IServer server = null;

    public MockServerConnection() throws InvalidArgumentValueException {
        server = new MockServer();
    }

    @Override
    public Response sendRequest(Request request) throws InvalidArgumentValueException {
        Response response = server.createResponse(request);
        return response;
    }

    @Override
    public void Start() throws IOException {

    }

    @Override
    public void Close() {

    }

    @Override
    public boolean pingServer() {
        return true;
    }
}