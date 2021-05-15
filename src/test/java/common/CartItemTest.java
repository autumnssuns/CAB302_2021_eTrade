package common;

import common.Exceptions.InvalidArgumentValueException;
import common.dataClasses.Asset;
import common.dataClasses.CartItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartItemTest {

    Asset asset;
    CartItem cartItem;

    @BeforeEach
    void setUp() throws InvalidArgumentValueException {
        asset = new Asset(0, "Asset 0", "N/A");
        cartItem = new CartItem(asset, 10, 2f);
    }

    @Test
    void setPrice() throws InvalidArgumentValueException {
        cartItem.setPrice(10);
        assertEquals(10f, cartItem.getPrice());
    }

    @Test
    void setInvalidPrice() throws InvalidArgumentValueException {
        assertThrows(InvalidArgumentValueException.class, () -> cartItem.setPrice(-1f));
    }

    @Test
    void getPrice() {
        assertEquals(2f, cartItem.getPrice());
    }

    @Test
    void getTotalPrice() {
        assertEquals(20f, cartItem.getTotalPrice());
    }

    @Test
    void testEquals() throws InvalidArgumentValueException {
        CartItem otherCartItem = new CartItem(asset, 10, 2f);
        assertTrue(cartItem.equals(otherCartItem) && otherCartItem.equals(cartItem));
        assertTrue(cartItem.hashCode() == otherCartItem.hashCode());
    }
}