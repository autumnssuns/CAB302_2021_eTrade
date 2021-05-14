package common.dataClasses;

import common.Exceptions.InvalidArgumentValueException;

import java.util.Objects;

/**
 * Represents an item in cart - a version of an asset that is considered to be placed into an order, at a certain quantity and price.
 */
public class CartItem extends common.dataClasses.Item{
    private float price;

    /**
     * Initialises the asset in cart.
     * @param asset The asset from an organisational unit or market.
     * @param cartQuantity The amount of asset to be placed in the cart.
     * @param price The unit price of the asset.
     */
    public CartItem(Asset asset, int cartQuantity, float price) throws InvalidArgumentValueException {
        super(asset, cartQuantity);
        this.price = price;
    }
//
//    /**
//     * Retrieves the quantity of the item in cart.
//     * @return The quantity of the item in cart.
//     */
//    public int getQuantity(){
//        return quantity;
//    }

    /**
     * Retrieves the unit price of the item in cart.
     * @return The price of the item in cart.
     */
    public float getPrice() {
        return this.price;
    }

    /**
     * Retrieves the total price of the item in cart.
     * @return The total price of the item in cart.
     */
    public float getTotalPrice() {
        return price * quantity;
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
        CartItem cartItem = (CartItem) o;
        return Float.compare(cartItem.price, price) == 0;
    }

    /**
     * Returns the hashCode of this instance.
     * @return The hashCode of this instance.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), price);
    }
}
