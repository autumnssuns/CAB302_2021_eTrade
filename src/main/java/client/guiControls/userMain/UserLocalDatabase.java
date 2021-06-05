package client.guiControls.userMain;

import common.Exceptions.InvalidArgumentValueException;
import common.dataClasses.Cart;
import client.guiControls.ILocalDatabase;
import common.dataClasses.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Represents a local database for a user. This local database is initiated using data fetched from server.
 * The UserMainController can interact with this database to write or read from it.
 */
public class UserLocalDatabase extends ILocalDatabase {
    private OrganisationalUnit organisationalUnit;
    private Stock stock;
    private DataCollection<Order> orders;
    private DataCollection<Asset> assets;
    private DataCollection<Notification> notifications;

    /**
     * Initialises the database with initial data (fetched from server)
     * @param organisationalUnit The organisational unit associated with the user.
     * @param stock The stock associated with the current user's organisational unit.
     * @param orders The current orders in the system.
     * @param assets The current assets in the system.
     */
    public UserLocalDatabase(OrganisationalUnit organisationalUnit,
                             Stock stock,
                             DataCollection<Order> orders,
                             DataCollection<Asset> assets,
                             DataCollection<Notification> notifications) {
        setAssets(assets);
        setOrganisationalUnit(organisationalUnit);
        setStock(stock);
        setOrders(orders);
        setNotifications(notifications);
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
     * Gets the notifications for the current organisational unit
     * @return The notifications for the current organisational unit
     */
    public DataCollection<Notification> getNotifications() {
        return notifications;
    }

    /**
     * Sets the notifications for the current organisational unit
     * @param notifications The new notifications for the current organisational unit
     */
    public void setNotifications(DataCollection<Notification> notifications) {
        this.notifications = notifications;
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
                if (order.getOrderType().equals(Order.Type.SELL) && !order.getUnitId().equals(organisationalUnit.getId()) && order.getStatus().equals(Order.Status.PENDING)){
                    if (order.getAssetId().equals(asset.getId())){
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
            if (!order.getUnitId().equals(organisationalUnit.getId()) && order.getOrderType().equals(type) && order.getStatus().equals(Order.Status.PENDING)){
                marketOrders.add(order);
            }
        }
        return mergeOrders(marketOrders);
    }

    /**
     * Returns the collection of orders from the current organisational unit.
     * @param type The order type
     * @return
     */
    public DataCollection<Order> getOwnOrders(Order.Type type){
        DataCollection<Order> ownOrders = new DataCollection<>();
        for (Order order : orders){
            if (order.getUnitId().equals(organisationalUnit.getId()) && order.getOrderType().equals(type) && order.getStatus().equals(Order.Status.PENDING)){
                ownOrders.add(order);
            }
        }
        return ownOrders;
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

    /**
     * Returns a LinkedHashMap containing the price history of an asset, with a specific order type.
     * @param asset The asset to look up
     * @param type The type of order
     * @return A LinkedHashMap containing the price history.
     */
    public LinkedHashMap<LocalDate, Float> getPriceHistory(Asset asset, Order.Type type){
        LinkedHashMap<LocalDate, Float> priceHistory = new LinkedHashMap();

        LinkedList<LocalDate> timestamps = new LinkedList<>();
        LinkedList<Float> prices = new LinkedList<>();
        for (Order order : orders){
            if (order.getOrderType().equals(type) && order.getAssetId().equals(asset.getId())){
                timestamps.add(LocalDate.from(order.getOrderDate()));
                prices.add(order.getPrice());
            }
        }

        for (int i = 0; i < timestamps.size() - 1; i++){
            float sameDayOrderCount = 1;
            // If the date in the current order is the same as that in the next order,
            // removes the time from the next order and adds the price together, increases the orders count in that day
            // and do not proceed the loop
            if (timestamps.get(i).equals(timestamps.get(i+1))){
                timestamps.remove(i + 1);
                prices.set(i, prices.get(i) + prices.get(i + 1));
                prices.remove(i + 1);
                sameDayOrderCount++;
                i--;
            }
            // Otherwise conclude the day, calculating the average price and reset the daily orders count.
            else{
                prices.set(i, prices.get(i) / sameDayOrderCount);
                sameDayOrderCount = 1;
            }
        }

        // Interpolation of missing data, only perform if
        // there are at least 2 data points
        if (timestamps.size() > 1){
            float[] ratesOfChange = new float[timestamps.size() - 1];
            for (int i = 0; i < timestamps.size() - 1; i++){
                ratesOfChange[i] = (prices.get(i + 1) - prices.get(i)) / (float) ChronoUnit.DAYS.between(timestamps.get(i), timestamps.get(i + 1));
                System.out.println("Rate of change");
                System.out.println(prices.get(i + 1) - prices.get(i));
                System.out.println(ChronoUnit.DAYS.between(timestamps.get(i), timestamps.get(i + 1)));
            }

            LocalDate currentDate = timestamps.get(0);
            int currentDateIndex = 0;
            for (int i = 0; i < ratesOfChange.length; i++){
                whileLoop:
                while (currentDate.isBefore(timestamps.getLast())){
                    currentDate = currentDate.plusDays(1);
                    currentDateIndex++;
                    if (!timestamps.contains(currentDate)) {
                        timestamps.add(currentDateIndex, currentDate);
                        prices.add(currentDateIndex, prices.get(currentDateIndex - 1) + ratesOfChange[i]);
                    }
                    else{
                        break whileLoop;
                    }
                }
            }
        }

        for (int i = 0; i < timestamps.size(); i++){
            priceHistory.put(timestamps.get(i), prices.get(i));
        }
        return priceHistory;
    }
}
