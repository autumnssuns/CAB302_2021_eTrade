package common.dataClasses;

import common.Exceptions.InvalidArgumentValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CartTest {

    Cart cart1;
    Cart cart2;
    Asset cpuHours;
    Asset databaseServer;

    @BeforeEach
    void setUp() throws Exception {
        // set up Assets
        cpuHours = new Asset(0, "CPU Hours", "CPU for rent");
        databaseServer = new Asset(1, "10 GB Database Server", "Remove SQL Server");
        // carts
        cart1 = new Cart(Order.Type.BUY);
        cart2 = new Cart(Order.Type.SELL);

        cart1.add(new CartItem(cpuHours, 10, 1.01f));
        cart1.add(new CartItem(databaseServer, 20, 2.02f));
    }

    @Test
    void addMultipleItems() {
        assertEquals(cart1.size(), 2);
    }

    @Test
    void addZeroItem() {
        assertEquals(cart2.size(), 0);
    }

    @Test
    void addTheSameItem() throws InvalidArgumentValueException {
        cart1.add(new CartItem(cpuHours, 15, 1.01f));
        assertEquals(cart1.size(), 2);
    }


    @Test
    void getTotalPrice() {
        assertEquals(cart1.getTotalPrice(), 50.5f);
    }

    @Test
    void checkOut() {
        cart1.checkOut();
        assertEquals(cart1.size(), 0);
    }

    @Test
    void getCartType() {
        assertEquals(cart2.getCartType(), Order.Type.SELL);
    }
}