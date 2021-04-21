package client.data;

import common.Request;
import common.Response;
import common.dataClasses.User;

import java.util.ArrayList;

public final class MockServerConnection implements IServerConnection{

    @Override
    public void Connect() {

    }

    @Override
    public Response respond(Request request) {
        // Unidentified requests are denied by default
        Response response = new Response(false, null);

        switch (request.getAction()){
            case "login":
                String[] attachment = (String[])request.getAttachment();
                User user = new User(attachment[0], attachment[1], "user",1);
                response = new Response(true, user);
        }

        return response;
    }

    @Override
    public void Close() {

    }
}