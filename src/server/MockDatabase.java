package server;

import common.dataClasses.Organisation;

import java.util.ArrayList;

public final class MockDatabase {
    public MockDataTable USERS;

    public MockDataTable ASSETS;

    public MockDataTable ORGANISATIONS;

    public MockDataTable STOCK;

    public MockDataTable ORDERS;

    public MockDataTable TRANSACTIONS;

    public MockDatabase(){
        USERS = new MockDataTable();
        ASSETS = new MockDataTable();
        ORGANISATIONS = new MockDataTable();
        STOCK = new MockDataTable();
        ORDERS = new MockDataTable();
        TRANSACTIONS = new MockDataTable();

        fillMockDatabase();
    }

    public void fillMockDatabase(){
        Object[][] users = new Object[][]{
                {"admin", "root", "admin", 0},
                {"dan", "123", "user", 0},
                {"duy", "abcd", "user", 1},
                {"lyn", "password", "user", 1},
                {"rodo", "rodo", "user", 2}
        };
        for (Object[] user: users){
            USERS.add(user);
        }

        Object[][] assets = new Object[][]{
                {0, "CPU Hours", "CPU for rent"},
                {1, "10 GB Database Server", "Remove SQL Server"},
                {2, "A Generic Video Game", "Nothing is more generic than this."},
                {3, "Coffin Dance Video", "You know what this is"}
        };
        for (Object[] asset: assets){
            ASSETS.add(asset);
        }

        Object[][] organisations = new Object[][]{
                {0, "The Justice League", 9999},
                {1, "The supervillains", 5555},
                {2, "The random civilians", 500}
        };
        for (Object[] organisation: organisations){
            ORGANISATIONS.add(organisation);
        }

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
        for (Object[] item: stock){
            STOCK.add(item);
        }
    }

    /**
     * Represents a table with multiple tuples
     */
    public static class MockDataTable extends ArrayList<Object[]>{

    }
}
