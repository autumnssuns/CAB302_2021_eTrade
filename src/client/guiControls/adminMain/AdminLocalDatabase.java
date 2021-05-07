package client.guiControls.adminMain;

import client.guiControls.ILocalDatabase;
import common.dataClasses.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a local database for an admin. This local database is initiated using data fetched from server.
 * The AdminMainController can interact with this database to write or read from it.
 */
public class AdminLocalDatabase extends ILocalDatabase {

    private DataCollection<User> users;
    private DataCollection<Asset> assets;
    private DataCollection<Organisation> organisations;
    private DataCollection<Stock> stocks;

    /**
     * Initialises the database with initial data (fetched from server)
     * @param users The users in the system
     * @param assets The assets in the system
     * @param organisations The organisations in the system
     * @param stocks The stocks in the system
     */
    public AdminLocalDatabase(DataCollection users, DataCollection assets, DataCollection organisations, DataCollection stocks){
        this.users = users;
        this.assets = assets;
        this.organisations = organisations;
        this.stocks = stocks;
    }

    /**
     * Returns a list the users in the system.
     * @return An ArrayList of the users in the system.
     */
    public DataCollection<User> getUsers() {
        return users;
    }

    /**
     * Returns a list of the assets in the system.
     * @return An ArrayList of the assets in the system.
     */
    public DataCollection<Asset> getAssets() {
        return assets;
    }

    /**
     * Returns a list the organisations in the system.
     * @return An ArrayList of the organisations in the system.
     */
    public DataCollection<Organisation> getOrganisations() {
        return organisations;
    }

    /**
     * Returns a list the stocks in the system.
     * @return An ArrayList of the stocks in the system.
     */
    public DataCollection<Stock> getStocks() {
        return stocks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminLocalDatabase that = (AdminLocalDatabase) o;
        return Objects.equals(users, that.users) && Objects.equals(assets, that.assets) && Objects.equals(organisations, that.organisations) && Objects.equals(stocks, that.stocks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(users, assets, organisations, stocks);
    }
}