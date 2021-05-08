package common.dataClasses;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents an Order
 */
public class Order implements Serializable {
    protected int OrderId;
    protected String OrderType; //buy/sell
    protected int unitId;
    protected int AssetId;
    protected int PlacedQuantity;//cartitem
    protected int ResolvedQuantity = 0;
    protected float price;
    protected String FinishedDate = null; //Cartitem
    protected String OrderDate;
    protected String Status;

    /**
     * Initialise the order
     * @param OrderId Order's ID
     * @param organisationalUnit Link with "OrganisationalUnit class" to retrieve relative information (such as unitId)
     * @param item Link with "CartItem class" to retrieve relative information (Price, Placed Quantity and Asset's ID )
     * @param OrderDate Date ordered
     * @param Status Current status of the Order (transaction Finished/ Remaining)
     * @param OrderType
     */
    public Order(int OrderId, OrganisationalUnit organisationalUnit, CartItem item, String OrderDate, String Status, String OrderType){
        this.unitId = organisationalUnit.getId();
        this.OrderId = OrderId;
        this.AssetId = item.getId();
        this.PlacedQuantity = item.getQuantity();
        this.price = item.getPrice();
        this.Status = Status;
        this.OrderDate = OrderDate;
        this.OrderType = OrderType;
    }

    /**
     * A method to record how many assets in an order have been successfully purchased
     * @param assetnumber number of the assets in the transaction
     */
    public  void ResolvedQuantity(int assetnumber) {
        ResolvedQuantity += assetnumber;
    }

    /**
     *
     * @return Order ID
     */
    public int getOrderId(){return OrderId;}

    /**
     *
     * @return the Order's type (Buy/Sell)
     */
    public String getOrderType() {return OrderType;}

    /**
     *
     * @return OrganisationalUnit ID
     */
    public int getUnitId(){return unitId;}

    /**
     *
     * @return Asset ID
     */
    public int getAssetId(){return AssetId;}


    /**
     *
     * @return Order's Placed Quantity
     */
    public int getPlacedQuantity() {return PlacedQuantity;}

    /**
     *
     * @return Order's Resolved Quantity
     */
    public int getResolvedQuantity() {return ResolvedQuantity;}

    /**
     *
     * @return Order total price
     */
    public float getPrice() {return price;}

    /**
     *
     * @return Transaction finished date
     */
    public String getFinishedDate(){return FinishedDate;}

    /**
     *
     * @return Transaction placed date
     */
    public String getOrderDate() {return OrderType;}

    /**
     *
     * @return return Transaction's status (Finished/Remaining)
     */
    public String getStatus() {return Status;}

    /**
     * set the order id to given Int
     * @param order_id
     */
    public void setOrderID(int order_id) { this.OrderId = order_id;
    }

    /**
     * set order type to new type
     * @param order_type
     */
    public void setOrderType(String order_type) { this.OrderType = order_type;
    }

    /**
     * Set new organisational unit ID
     * @param organisation_id
     */
    public void setunitId(int organisation_id) { this.unitId = organisation_id;
    }

    /**
     * Set new asset ID
     * @param asset_id
     */
    public void setAssetID(int asset_id) { this.AssetId = asset_id;
    }

    /**
     * Set new quantity
     * @param placed_quantity
     */
    public void setPlacedQuantity(int placed_quantity) { this.PlacedQuantity = placed_quantity;
    }

    /**
     * Set new quantity
     * @param resolved_quantity
     */
    public void setResolvedQuantity(int resolved_quantity) { this.ResolvedQuantity = resolved_quantity;
    }

    /**
     * Set new price
     * @param price
     */
    public void setPrice(float price) { this.price = price;
    }

    /**
     * Set new order date
     * @param order_date
     */
    public void setOrderDate(String order_date) { this.OrderDate = order_date;
    }

    /**
     * Set new Finished date
     * @param finished_date
     */
    public void setFinishedDate(String finished_date) { this.FinishedDate = finished_date;
    }

    /**
     * Set new status
     * @param status
     */
    public void setStatus(String status) { this.Status = status;
    }

    /**
     * Indicates if some object is equal to this instance.
     * @param o The object to compare.
     * @return true if the object is equal to the instance, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return OrderId == order.OrderId && unitId == order.unitId && AssetId == order.AssetId && PlacedQuantity == order.PlacedQuantity && ResolvedQuantity == order.ResolvedQuantity && Float.compare(order.price, price) == 0 && Objects.equals(OrderType, order.OrderType) && Objects.equals(FinishedDate, order.FinishedDate) && Objects.equals(OrderDate, order.OrderDate) && Objects.equals(Status, order.Status);
    }

    /**
     * Returns the hashCode of this instance.
     * @return The hashCode of this instance.
     */
    @Override
    public int hashCode() {
        return Objects.hash(OrderId, OrderType, unitId, AssetId, PlacedQuantity, ResolvedQuantity, price, FinishedDate, OrderDate, Status);
    }
}
