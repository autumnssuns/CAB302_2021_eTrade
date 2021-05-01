package common.dataClasses;

import java.io.Serializable;

/**
 * Represents an Order
 */
public class Order implements Serializable {
    protected int OrderId;
    protected String OrderType; //buy/sell
    protected int organisationid;
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
     * @param organisation Link with "Organisation class" to retrieve relative information (such as OrganisationID)
     * @param item Link with "CartItem class" to retrieve relative information (Price, Placed Quantity and Asset's ID )
     * @param OrderDate Date ordered
     * @param Status Current status of the Order (transaction Finished/ Remaining)
     * @param OrderType
     */
    public Order(int OrderId, Organisation organisation, CartItem item, String OrderDate, String Status, String OrderType){
        this.organisationid = organisation.getId();
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
     * @return Organisation ID
     */
    public int getOrganisationid(){return organisationid;}

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
     * Set new organisation ID
     * @param organisation_id
     */
    public void setOrganisationID(int organisation_id) { this.organisationid = organisation_id;
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
}
