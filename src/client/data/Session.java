package client.data;

import client.data.sessionalClasses.*;
import common.Request;
import common.Response;
import common.dataClasses.User;

// Mimics a static class as a temporary data storage
public final class Session {
    // Client - Server Session:
    // 1. User Login with username -> Request login
    // 2. Login successful -> Request user-related information:
    //    User level: employee, manager or administrator, and whether user belongs to an organisation
    //    Market: current assets on sale (view only), trade and assets history
    //    Organisational Data from server: name, balance, assets, orders

    private static IServerConnection serverConnection = null;

    // Client data
    private static String username;
    public static Organisation organisation;
    public static Cart shippingCart;

    private static User user;

    // Initialise the session after user login.
    // TODO: retrieve and add organisation data
    public static void startSession(User user){
        Session.username = user.getUsername();
        Session.organisation = new Organisation(username);
        Session.shippingCart = new Cart();
    }

    public static boolean attemptLogin(String username, String password){
        serverConnection = new MockServerConnection();
        String[] data = new String[]{username, password};
        Request request = new Request("login",data);
        Response response = (Response) serverConnection.respond(request);

        if (response.isFulfilled()){
            startSession((User)response.getObject());
        }
        return response.isFulfilled();
    }

    // TODO: access data from other classes
    public static String getUsername(){
        return username;
    }
}