package common;

import common.Exceptions.InvalidArgumentValueException;
import common.dataClasses.Asset;
import common.dataClasses.CartItem;
import common.dataClasses.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    Asset asset;
    Item item;

    @BeforeEach
    void setUp() throws InvalidArgumentValueException {
        asset = new Asset(0, "Asset 0", "N/A");
        item = new Item(asset, 0);
    }

    @Test
    void getQuantity() {
        assertEquals(item.getQuantity(), 0);
    }

    @Test
    void setValidQuantity() throws InvalidArgumentValueException {
        item.setQuantity(15);
        assertEquals(item.getQuantity(), 15);
    }

    @Test
    void setInvalidQuantity() {
        assertThrows(InvalidArgumentValueException.class, () -> item.setQuantity(-1));
    }

    @Test
    void add() throws InvalidArgumentValueException {
        item.add(10);
        assertEquals(item.getQuantity(), 10);
        item.add(5);
        assertEquals(item.getQuantity(), 15);
    }

    @Test
    void isOutOfStock() {
        assertTrue(item.isOutOfStock());
    }

    @Test
    void isNotOutOfStock() throws InvalidArgumentValueException {
        item.add(1);
        assertFalse(item.isOutOfStock());
    }

    @Test
    void moveToCart() throws InvalidArgumentValueException {
        item.add(15);
        CartItem actualCartItem = item.moveToCart(10, 2f);
        CartItem expectedCartItem = new CartItem(asset, 10, 2f);
        // Create correct cart item
        assertEquals(actualCartItem, expectedCartItem);
        // And removed correct number of item
        assertEquals(item.getQuantity(), 5);
    }

    @Test
    void testEquals() throws InvalidArgumentValueException {
        Item otherItem = new Item(asset, 0);
        assertTrue(item.equals(otherItem) && otherItem.equals(item));
        assertTrue(item.hashCode() == otherItem.hashCode());
    }

    @Test
    void testNotEquals() throws InvalidArgumentValueException {
        Asset otherAsset = new Asset(1, "Another Asset", "N/A");
        Item otherItem = new Item(asset, 1);
        Item anotherItem = new Item(otherAsset, 0);
        assertFalse(item.equals(otherItem));
        assertFalse(item.equals(anotherItem));
    }
}