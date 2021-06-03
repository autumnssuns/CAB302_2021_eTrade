package common.dataClasses;

import common.Exceptions.InvalidArgumentValueException;
import common.dataClasses.Asset;
import common.dataClasses.Cart;
import common.dataClasses.CartItem;
import common.dataClasses.Order;
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
    }

    @Test
    void addMultipleItems() throws InvalidArgumentValueException {
        cart1 = new Cart(Order.Type.BUY);
        cart1.add(new CartItem(cpuHours, 10, 1.01f));
        cart1.add(new CartItem(databaseServer, 20, 2.02f));
        assertEquals(2, cart1.size());
    }

    @Test
    void addZeroItem() {
        cart2 = new Cart(Order.Type.SELL);
        assertEquals(0,cart2.size());
    }

    @Test
    void addTheSameItem() throws InvalidArgumentValueException {
        cart1 = new Cart(Order.Type.BUY);
        cart1.add(new CartItem(cpuHours, 10, 1.01f));
        cart1.add(new CartItem(databaseServer, 20, 2.02f));
        cart1.add(new CartItem(cpuHours, 15, 1.01f));
        assertEquals(2, cart1.size());
    }


    @Test
    void getTotalPrice() throws InvalidArgumentValueException {
        cart1 = new Cart(Order.Type.BUY);
        cart1.add(new CartItem(cpuHours, 10, 1.01f));
        cart1.add(new CartItem(databaseServer, 20, 2.02f));
        assertEquals(50.5f, cart1.getTotalPrice());
    }

}