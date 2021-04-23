package common.dataClasses;

import java.io.Serializable;

public class Order implements Serializable {
    protected int OrderId; //
    protected String OrderType; //buy/sell
    protected int organisationid;
    protected int AssetId;
    protected int PlacedQuantity;//cartitem
    protected int ResolvedQuantity = 0;
    protected float price;
    protected String FinishedDate = null; //Cartitem
    protected String OrderDate; //
    protected String Status; //

    public Order(int OrderId/**/, Organisation organisation, CartItem item, String OrderDate, String Status, String OrderType){
        this.organisationid = organisation.getId();
        this.OrderId = OrderId;
        this.AssetId = item.getId();
        this.PlacedQuantity = item.getQuantity();
        this.price = item.getPrice();
        this.Status = Status;
        this.OrderDate = OrderDate;
        this.OrderType = OrderType;

    }
    public  void ResolvedQuantity(int assetnumber) {
        ResolvedQuantity += assetnumber;
    }
    public int getOrderId(){return OrderId;}

    public String getOrderType() {return OrderType;}

    public int getOrganisationid(){return organisationid;}

    public int getAssetId(){return AssetId;}

    public int getPlacedQuantity() {return PlacedQuantity;}

    public int getResolvedQuantity() {return ResolvedQuantity;}

    public float getPrice() {return price;}

    public String getFinishedDate(){return FinishedDate;}

    public String getOrderDate() {return OrderType;}

    public String getStatus() {return Status;}


    public void setOrderID(int order_id) { this.OrderId = order_id;
    }

    public void setOrderType(String order_type) { this.OrderType = order_type;
    }

    public void setOrganisationID(int organisation_id) { this.organisationid = organisation_id;
    }

    public void setAssetID(int asset_id) { this.AssetId = asset_id;
    }

    public void setPlacedQuantity(int placed_quantity) { this.PlacedQuantity = placed_quantity;
    }

    public void setResolvedQuantity(int resolved_quantity) { this.ResolvedQuantity = resolved_quantity;
    }

    public void setPrice(float price) { this.price = price;
    }

    public void setOrderDate(String order_date) { this.OrderDate = order_date;
    }

    public void setFinishedDate(String finished_date) { this.FinishedDate = finished_date;
    }

    public void setStatus(String status) { this.Status = status;
    }
}
