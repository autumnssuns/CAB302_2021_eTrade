package client.guiControls.userMain;

import common.dataClasses.Cart;
import client.guiControls.ILocalDatabase;
import common.dataClasses.*;

/**
 * Represents a local database for a user. This local database is initiated using data fetched from server.
 * The UserMainController can interact with this database to write or read from it.
 */
public class UserLocalDatabase extends ILocalDatabase {
    private OrganisationalUnit organisationalUnit;
    private Stock stock;
    private DataCollection<Order> orders;

    /**
     * Initialises the database with initial data (fetched from server)
     * @param organisationalUnit The organisational unit associated with the user.
     * @param stock The stock associated with the current user's organisational unit.
     * @param orders The current orders in the system.
     */
    public UserLocalDatabase(OrganisationalUnit organisationalUnit, Stock stock, DataCollection<Order> orders) {
        this.organisationalUnit = organisationalUnit;
        this.stock = stock;
        this.orders = orders;
    }

    /**
     * Returns the organisational unit associated with the current user.
     * @return The organisational unit associated with the current user.
     */
    public OrganisationalUnit getOrganisationalUnit() {
        return organisationalUnit;
    }

    /**
     * Returns the stock associated with the current user's organisational unit.
     * @return The stock associated with the current user's organisational unit.
     */
    public Stock getStock() {
        return stock;
    }

    /**
     * Returns the current orders in the system.
     * @return The current orders in the system.
     */
    public DataCollection<Order> getOrders() {
        return orders;
    }

    public void setOrganisationalUnit(OrganisationalUnit organisationalUnit) {
        this.organisationalUnit = organisationalUnit;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public void setOrders(DataCollection<Order> orders) {
        this.orders = orders;
    }
}
