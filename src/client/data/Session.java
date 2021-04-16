package client.data;

import client.data.sessionalClasses.*;

// Mimics a static class as a temporary data storage
public final class Session {
    // Client - Server Session:
    // 1. User Login with username -> Request login
    // 2. Login successful -> Request user-related information:
    //    User level: employee, manager or administrator, and whether user belongs to an organisation
    //    Market: current assets on sale (view only), trade and assets history
    //    Organisational Data from server: name, balance, assets, orders

    // Client data
    private static String username;
    public static Organisation organisation;
    public static Cart shippingCart;

    // Initialise the session after user login.
    // TODO: retrieve and add organisation data
    public static void startSession(String username){
        Session.username = username;
        Session.organisation = new Organisation(username);
        Session.shippingCart = new Cart();
    }

    // TODO: access data from other classes
    public static String getUsername(){
        return username;
    }
}