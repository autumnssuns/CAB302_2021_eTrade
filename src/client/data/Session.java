package client.data;

import client.data.sessionalClasses.Cart;
import client.data.sessionalClasses.Stock;
import common.Request;
import common.Response;
import common.dataClasses.Organisation;
import common.dataClasses.User;

// Mimics a static class as a temporary data storage
public final class Session {
    // Client - Server Session:
    // 1. User Login with username -> Request login
    // 2. Login successful -> Request user-related information:
    //    User level: employee, manager or administrator, and whether user belongs to an organisation
    //    Market: current assets on sale (view only), trade and assets history
    //    Organisational Data from server: name, balance, assets, orders

    private IServerConnection serverConnection;

    // Client data
    private User user;
    private Organisation organisation;
    private Stock stock;
    private Cart shippingCart;
    private Cart shoppingCart;

    // Initialise the session after user login.
    // TODO: retrieve and add organisation data
    public void startSession(User user){
        setUser(user);
        setOrganisation((Organisation) requestQuery("query organisation"));
        setStock((Stock) requestQuery("query stock"));
        setShippingCart(new Cart());
    }

    private Object requestQuery(String query){
        Request request = new Request(user, query);
        Response response = (Response) serverConnection.sendRequest(request);
        return response.getAttachment();
    }

    public boolean requestLogin(String username, String password) {
        String[] data = new String[]{username, password};
        Request request = new Request("login",data);

        Response response = (Response) serverConnection.sendRequest(request);

        if (response.isFulfilled()){
            startSession((User)response.getAttachment());
        }
        return response.isFulfilled();
    }

    public IServerConnection getServerConnection() {
        return serverConnection;
    }

    public void setServerConnection(IServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Cart getShippingCart() {
        return shippingCart;
    }

    public void setShippingCart(Cart shippingCart) {
        this.shippingCart = shippingCart;
    }

    public Cart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(Cart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}