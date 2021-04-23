package common.dataClasses;

import java.io.Serializable;

/**
 * Represents an item - an quantifiable version of an asset possessed by an organisation.
 */
public class Item extends Asset implements Serializable {
    protected int quantity;
    protected int alterquantity;

    /**
     * Initialises an item by linking it to an asset and includes the quantity to be stored.
     * @param asset The asset type of the item.
     * @param quantity The quantity to be stored.
     */
    public Item(Asset asset, int quantity){
        super(asset.getId(), asset.getName(), asset.getDescription());
        setQuantity(quantity);
    }

    /**
     * Retrieves the quantity of the asset that is owned by the organisation.
     * @return The quantity of the asset.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the asset to a new value.
     * @param quantity The new quantity.
     */
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    /**
     * Increases the quantity of the related asset in the organisation's stock.
     * @param amount The amount to be added.
     */
    public void add(int amount){
        setQuantity(quantity + amount);
    }

    /**
     * Checks if the asset is out of stock.
     * @return true if there are no asset left in the stock, false otherwise.
     */
    public boolean outOfStock(){
        return quantity == 0;
    }

    /**
     * Moves a certain amount of the current asset to a cart.
     * @param amount The amount of asset to be moved to cart.
     * @param price The price of the asset.
     * @return An instance of the current asset, but in cart.
     */
    public CartItem moveToCart(int amount, float price){
        this.quantity -= amount;
        return new CartItem(this, amount, price);
    }
}