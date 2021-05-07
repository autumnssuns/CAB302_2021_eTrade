package server;

import common.Request;
import common.Response;
import common.dataClasses.Organisation;
import common.dataClasses.*;

import java.util.ArrayList;

public final class MockDatabase {
    Object[][] users = new Object[][]{
            {"admin", "root", "admin", 0},
            {"dan", "123", "user", 0},
            {"duy", "abcd", "user", 1},
            {"lyn", "password", "user", 1},
            {"rodo", "rodo", "user", 2}
    };

    Object[][] assets = new Object[][]{
            {0, "CPU Hours", "CPU for rent"},
            {1, "10 GB Database Server", "Remove SQL Server"},
            {2, "A Generic Video Game", "Nothing is more generic than this."},
            {3, "Coffin Dance Video", "You know what this is"}
    };

    Object[][] organisations = new Object[][]{
            {0, "The Justice League", 9999},
            {1, "The supervillains", 5555},
            {2, "The random civilians", 500}
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
            if (user[0].equals(sender.getUsername()) && user[1].equals(sender.getPassword())){
                return new Response(true, new User((String) user[0], (String) user[1], (String) user[2], (int) user[3]));
            }
        }
        return new Response(false, null);
    }

    public Response queryUsers(Request request) {
        DataCollection<User> attachedUsers = new DataCollection<>();
        for (Object[] user : users){
            attachedUsers.add(new User((String) user[0], (String) user[1], (String) user[2], (int) user[3]));
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
        DataCollection<Organisation> attachedOrganisation = new DataCollection<>();
        for (Object[] organisation : organisations){
            attachedOrganisation.add(new Organisation((int) organisation[0], (String) organisation[1], (int) organisation[2]));
        }
        return new Response(true, attachedOrganisation);
    }

    public Response queryStocks(Request request) {
        DataCollection<Stock> attachedStocks = new DataCollection<>();
        for (Object[] organisation : organisations){
            attachedStocks.add(new Stock((int) organisation[0]));
        }
        for (Object[] organisationAsset : stock){
            Object[] asset = assets[(int) organisationAsset[1]];
            Asset newAsset = new Asset((int) asset[0], (String) asset[1], (String) asset[2]);
            attachedStocks.get((int) organisationAsset[0]).add(new Item(newAsset, (int) organisationAsset[2]));
        }
        return new Response(true, attachedStocks);
    }
}
