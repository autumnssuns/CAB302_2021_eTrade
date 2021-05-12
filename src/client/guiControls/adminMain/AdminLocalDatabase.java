package client.guiControls.adminMain;

import client.guiControls.ILocalDatabase;
import common.dataClasses.*;

import java.util.Objects;

/**
 * Represents a local database for an admin. This local database is initiated using data fetched from server.
 * The AdminMainController can interact with this database to write or read from it.
 */
public class AdminLocalDatabase extends ILocalDatabase {

    private DataCollection<User> users;
    private DataCollection<Asset> assets;
    private DataCollection<OrganisationalUnit> organisationalUnits;
    private DataCollection<Stock> stocks;

    /**
     * Initialises the database with initial data (fetched from server)
     * @param users The users in the system
     * @param assets The assets in the system
     * @param organisationalUnits The organisationalUnits in the system
     * @param stocks The stocks in the system
     */
    public AdminLocalDatabase(DataCollection users, DataCollection assets, DataCollection organisationalUnits, DataCollection stocks){
        this.users = users;
        this.assets = assets;
        this.organisationalUnits = organisationalUnits;
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
     * Returns a list the organisationalUnits in the system.
     * @return An ArrayList of the organisationalUnits in the system.
     */
    public DataCollection<OrganisationalUnit> getOrganisationalUnits() {
        return organisationalUnits;
    }

    /**
     * Returns a list the stocks in the system.
     * @return An ArrayList of the stocks in the system.
     */
    public DataCollection<Stock> getStocks() {
        return stocks;
    }

    /**
     * Sets the local storage of the system's users.
     * @param users The system's users.
     */
    public void setUsers(DataCollection<User> users) {
        this.users = users;
    }

    /**
     * Sets the local storage of the system's assets.
     * @param assets The system's assets.
     */
    public void setAssets(DataCollection<Asset> assets) {
        this.assets = assets;
    }

    /**
     * Sets the local storage of the system's organisational units.
     * @param organisationalUnits The system's organisational units.
     */
    public void setOrganisationalUnits(DataCollection<OrganisationalUnit> organisationalUnits) {
        this.organisationalUnits = organisationalUnits;
    }

    /**
     * Sets the local storage of the system's stocks.
     * @param stocks The system's stocks.
     */
    public void setStocks(DataCollection<Stock> stocks) {
        this.stocks = stocks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminLocalDatabase that = (AdminLocalDatabase) o;
        return Objects.equals(users, that.users) && Objects.equals(assets, that.assets) && Objects.equals(organisationalUnits, that.organisationalUnits) && Objects.equals(stocks, that.stocks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(users, assets, organisationalUnits, stocks);
    }
}