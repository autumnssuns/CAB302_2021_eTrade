package client.data.sessionalClasses;

public class Organisation {
    private String orgName;
    public float balance;
    public Stock stock;

    public Organisation(String username){
        // Request data from server
        requestOrganisation(username);
        requestBalance();
        requestStock();
    }

    public void requestOrganisation(String username){
        String request = "getOrganisation";
        String response = "temp";   // TODO: Connect to server
        orgName = response;
    }

    public void requestBalance(){
        String request = "getBalance from " + orgName;
        float response = 0;   // TODO: Connect to server
        balance = response;
    }

    public void requestStock(){
        String request = "getStock from " + orgName;
        Stock response = new Stock();   // TODO: Connect to server
        stock = response;
    }

    public void requestHistory(){}

    // TODO: Retrieve data from other classes
    public Stock getStock(){
        return stock;
    }
}
