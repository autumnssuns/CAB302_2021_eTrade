package client.guiControls.userMain;

import common.Exceptions.InvalidArgumentValueException;
import common.dataClasses.Cart;
import client.guiControls.ILocalDatabase;
import common.dataClasses.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Represents a local database for a user. This local database is initiated using data fetched from server.
 * The UserMainController can interact with this database to write or read from it.
 */
public class UserLocalDatabase extends ILocalDatabase {
    private OrganisationalUnit organisationalUnit;
    private Stock stock;
    private DataCollection<Order> orders;
    private DataCollection<Asset> assets;

    /**
     * Initialises the database with initial data (fetched from server)
     * @param organisationalUnit The organisational unit associated with the user.
     * @param stock The stock associated with the current user's organisational unit.
     * @param orders The current orders in the system.
     * @param assets The current assets in the system.
     */
    public UserLocalDatabase(OrganisationalUnit organisationalUnit, Stock stock, DataCollection<Order> orders, DataCollection<Asset> assets) {
        setAssets(assets);
        setOrganisationalUnit(organisationalUnit);
        setStock(stock);
        setOrders(orders);
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

    /**
     * Returns the current orders in the system.
     * @return The current orders in the system.
     */
    public DataCollection<Asset> getAsset() {
        return assets;
    }

    /**
     * Replaces the current organisational unit data.
     * @param organisationalUnit The new organisational unit data.
     */
    public void setOrganisationalUnit(OrganisationalUnit organisationalUnit) {
        this.organisationalUnit = organisationalUnit;
    }

    /**
     * Replaces the current stock data.
     * @param stock The new stock data.
     */
    public void setStock(Stock stock) {
        this.stock = stock;
    }

    /**
     * Replaces the current orders data.
     * @param orders The new orders data.
     */
    public void setOrders(DataCollection<Order> orders) {
        this.orders = new DataCollection<Order>();
        for (Order order : orders){
            order.setAsset(assets.get(order.getAssetId()));
            this.orders.add(order);
        }
    }

    /**
     * Replaces the current assets data.
     * @param assets The new assets data.
     */
    public void setAssets(DataCollection<Asset> assets) {
        this.assets = assets;
    }

    /**
     * Returns the market containing all assets in the systems, event if they are not on sale.
     * @return The market as a stock.
     */
    public Stock getMarket() throws InvalidArgumentValueException {
        Stock marketStock = new Stock(-1);
        for (Asset asset : assets){
            boolean orderExists = false;

            // Loop through all orders and check if:
            // It is a sell order
            // It is not sold by current organisational unit
            // It is still pending
            for (Order order : orders){
                compareOrder:
                if (order.getOrderType().equals(Order.Type.SELL) && order.getUnitId() != organisationalUnit.getId() && order.getStatus().equals(Order.Status.PENDING)){
                    if (order.getAssetId() == asset.getId()){
                        Item marketItem = new Item(asset, (order.getPlacedQuantity() - order.getResolvedQuantity()));
                        marketStock.add(marketItem);
                        orderExists = true;
                        break compareOrder;
                    }
                }
            }

            // If the asset is not placed in any order, display it with quantity 0
            if (!orderExists){
                Item marketItem = new Item(asset, 0);
                marketStock.add(marketItem);
            }
        }
        return marketStock;
    }

    /**
     * Returns the collection of market orders (excluding the current organisational unit)
     * @param type The order type
     * @return The collection of market orders.
     */
    public DataCollection<Order> getMarketOrders(Order.Type type){
        DataCollection<Order> marketOrders = new DataCollection<Order>();
        for (Order order : orders){
            if (order.getUnitId() != organisationalUnit.getId() && order.getOrderType() == type && order.getStatus() == Order.Status.PENDING){
                marketOrders.add(order);
            }
        }
        return mergeOrders(marketOrders);
    }

    /**
     * Merges similar orders (same asset with the same price and type) in a given collection.
     * @return
     */
    private DataCollection<Order> mergeOrders(DataCollection<Order> unmergedOrders){
        DataCollection<Order> mergedOrders = new DataCollection<Order>();
        for (Order unmerged : unmergedOrders){
            boolean isNew = true;
            for (Order merged : mergedOrders){
                // If the two orders are similar, merge their price and quantity
                if (unmerged.isSimilarTo(merged)) {
                    int mergedIndex = mergedOrders.indexOf(merged);
                    merged.setPlacedQuantity(merged.getPlacedQuantity() + unmerged.getPlacedQuantity());
                    merged.setResolvedQuantity(merged.getResolvedQuantity() + unmerged.getResolvedQuantity());
                    mergedOrders.set(mergedIndex, merged);
                    isNew = false;
                }
            }
            if (isNew){
                mergedOrders.add(unmerged);
            }
        }
        return mergedOrders;
    }
}
