package common.dataClasses;

/**
 * Represents an item in cart - a version of an asset that is considered to be placed into an order, at a certain quantity and price.
 */
public class CartItem extends common.dataClasses.Item{
    private float price;

    /**
     * Initialises the item in cart.
     * @param item The item from an organisation or market.
     * @param cartQuantity The amount of item to be placed in the cart.
     * @param price The unit price of the item.
     */
    public CartItem(Item item, int cartQuantity, float price) {
        super(item, cartQuantity);
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
}
