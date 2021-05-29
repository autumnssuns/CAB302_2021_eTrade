package common.dataClasses;

import common.Exceptions.InvalidArgumentValueException;
import common.Exceptions.NullArgumentException;

import java.util.Objects;

/**
 * Represents an item - an quantifiable version of an asset possessed by an organisational unit.
 */
public class Item extends Asset {
    protected int quantity;

    /**
     * Initialises an item by linking it to an asset and includes the quantity to be stored.
     * @param asset The asset type of the item.
     * @param quantity The quantity to be stored.
     */
    public Item(Asset asset, int quantity) throws InvalidArgumentValueException {
        super(asset.getId(), asset.getName(), asset.getDescription());
        setQuantity(quantity);
    }

    /**
     * Retrieves the quantity of the asset that is owned by the organisational unit.
     * @return The quantity of the asset.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the asset to a new value.
     * @param quantity The new quantity.
     */
    public void setQuantity(int quantity) throws InvalidArgumentValueException {
        try{
            if (quantity < 0){
                throw new InvalidArgumentValueException();
            }
            this.quantity = quantity;
        }
        catch (NullPointerException e){
            throw new NullArgumentException();
        }
    }

    /**
     * Increases the quantity of the related asset in the organisational unit's stock.
     * @param amount The amount to be added.
     */
    public void add(int amount) throws InvalidArgumentValueException {
        setQuantity(quantity + amount);
    }

    /**
     * Checks if the asset is out of stock.
     * @return true if there are no asset left in the stock, false otherwise.
     */
    public boolean isOutOfStock(){
        return quantity == 0;
    }

    /**
     * Moves a certain amount of the current asset to a cart.
     * @param amount The amount of asset to be moved to cart.
     * @param price The price of the asset.
     * @return An instance of the current asset, but in cart.
     */
    public CartItem moveToCart(int amount, float price) throws InvalidArgumentValueException {
        setQuantity(this.getQuantity() - amount);
        return new CartItem(this, amount, price);
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
        if (!super.equals(o)) return false;
        Item item = (Item) o;
        return quantity == item.quantity;
    }

    /**
     * Returns the hashCode of this instance.
     * @return The hashCode of this instance.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), quantity);
    }
}