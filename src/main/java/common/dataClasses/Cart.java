package common.dataClasses;

import common.Exceptions.InvalidArgumentValueException;
import common.dataClasses.CartItem;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a cart of items.
 */
public class Cart extends ArrayList<CartItem> implements IData {
    private final Order.Type cartType;

    /**
     * Sets the type of the cart ('buy' or 'sell')
     * @param cartType The type of the cart ('buy' or 'sell')
     */
    public Cart(Order.Type cartType){
        this.cartType = cartType;
    }

    /**
     * Adds a new item into the cart. If the item has already existed in the cart
     * with the same price, they are stacked up.
     * @param item The new item.
     * @return true if the item is added successfully, false otherwise.
     */
    @Override
    public boolean add(CartItem item){
        boolean result = false;

        // Checks if the item has already exist. If yes, add the new item on top.
        for(int i = 0; i < this.size(); i++){
            CartItem currentItem = this.get(i);
            //Todo: change the condition the itemID
            if (Objects.equals(currentItem.getName(), item.getName()) && currentItem.getPrice() == item.getPrice()){
                try {
                    this.get(i).add(item.getQuantity());
                } catch (InvalidArgumentValueException e) {
                    e.printStackTrace();
                }
                result = true;
                break;
            }
        }

        // If the item is new, add it to the cart.
        result = result ? result : super.add(item);
        return result;
    }

    /**
     * Retrieves the total price of all the items in this cart.
     * @return The total price of all the items in this cart.
     */
    public float getTotalPrice() {
        float total = 0;
        for (CartItem item : this){
            total += item.getTotalPrice();
        }
        return total;
    }
}