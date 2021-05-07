package client.guiControls.adminMain;

import common.dataClasses.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Represents a local database for an admin. This local database is initiated using data fetched from server.
 * The AdminMainController can interact with this database to write or read from it.
 */
public class AdminLocalDatabase {

    private ArrayList<User> users;
    private ArrayList<Asset> assets;
    private ArrayList<Organisation> organisations;
    private ArrayList<Stock> stocks;

    /**
     * Initialises the database with initial data (fetched from database)
     * @param users
     * @param assets
     * @param organisations
     * @param stocks
     */
    public AdminLocalDatabase(ArrayList<User> users, ArrayList<Asset> assets, ArrayList<Organisation> organisations, ArrayList<Stock> stocks){
        this.users = users;
        this.assets = assets;
        this.organisations = organisations;
        this.stocks = stocks;
    }

    /**
     * Returns a list the users in the system.
     * @return An ArrayList of the users in the system.
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Returns a list of the assets in the system.
     * @return An ArrayList of the assets in the system.
     */
    public ArrayList<Asset> getAssets() {
        return assets;
    }

    /**
     * Returns a list the organisations in the system.
     * @return An ArrayList of the organisations in the system.
     */
    public ArrayList<Organisation> getOrganisations() {
        return organisations;
    }

    /**
     * Returns a list the stocks in the system.
     * @return An ArrayList of the stocks in the system.
     */
    public ArrayList<Stock> getStocks() {
        return stocks;
    }
}