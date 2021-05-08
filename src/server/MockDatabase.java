package server;

import common.Request;
import common.Response;
import common.dataClasses.OrganisationalUnit;
import common.dataClasses.*;

public final class MockDatabase {
    Object[][] users = new Object[][]{
            {0, "Admin", "admin", "root", "admin", 0},
            {1, "Dan Tran", "dan", "123", "user", 0},
            {2, "Daniel Pham", "duy", "abcd", "user", 1},
            {3, "Linh Hoang", "lyn", "password", "user", 1},
            {4, "Rodo Nguyen", "rodo", "rodo", "user", 2}
    };

    Object[][] assets = new Object[][]{
            {0, "CPU Hours", "CPU for rent"},
            {1, "10 GB Database Server", "Remove SQL Server"},
            {2, "A Generic Video Game", "Nothing is more generic than this."},
            {3, "Coffin Dance Video", "You know what this is"}
    };

    Object[][] organisationalUnits = new Object[][]{
            {0, "The Justice League", 9999},
            {1, "The supervillains", 5555},
            {2, "The random civilians", 500},
    };

    Object[][] stock = new Object[][]{
            {0, 0, 99},
            {0, 1, 99},
            {0, 2, 99},
            {0, 3, 99},
            {1, 0, 55},
            {1, 1, 55},
            {1, 2, 55},
            {1, 3, 55},
            {2, 0, 10},
            {2, 1, 10},
            {2, 2, 10},
            {2, 3, 10},
    };

    public Response login(Request request){
        User sender = request.getUser();
        for (Object[] user : users){
            if (user[2].equals(sender.getUsername()) && user[3].equals(sender.getPassword())){
                return new Response(true, new User((int)user[0], (String)user[1], (String) user[2], (String) user[3], (String) user[4], (int) user[5]));
            }
        }
        return new Response(false, null);
    }

    public Response queryUsers(Request request) {
        DataCollection<User> attachedUsers = new DataCollection<>();
        for (Object[] user : users){
            attachedUsers.add(new User((int)user[0], (String)user[1], (String) user[2], (String) user[3], (String) user[4], (int) user[5]));
        }
        return new Response(true, attachedUsers);
    }

    public Response queryAssets(Request request) {
        DataCollection<Asset> attachedAssets = new DataCollection<>();
        for (Object[] asset : assets){
            attachedAssets.add(new Asset((int) asset[0], (String) asset[1], (String) asset[2]));
        }
        return new Response(true, attachedAssets);
    }

    public Response queryOrganisations(Request request) {
        DataCollection<OrganisationalUnit> attachedOrganisationalUnit = new DataCollection<>();
        for (Object[] organisationalUnit : organisationalUnits){
            attachedOrganisationalUnit.add(new OrganisationalUnit((int) organisationalUnit[0], (String) organisationalUnit[1], (int) organisationalUnit[2]));
        }
        return new Response(true, attachedOrganisationalUnit);
    }

    public Response queryStocks(Request request) {
        DataCollection<Stock> attachedStocks = new DataCollection<>();
        for (Object[] organisationalUnit : organisationalUnits){
            attachedStocks.add(new Stock((int) organisationalUnit[0]));
        }
        for (Object[] organisationAsset : stock){
            Object[] asset = assets[(int) organisationAsset[1]];
            Asset newAsset = new Asset((int) asset[0], (String) asset[1], (String) asset[2]);
            attachedStocks.get((int) organisationAsset[0]).add(new Item(newAsset, (int) organisationAsset[2]));
        }
        return new Response(true, attachedStocks);
    }
}
