package common.dataClasses;

import common.Exceptions.InvalidArgumentValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockTest {

    Stock stock0;
    Asset asset0 = new Asset(1, "CPU Hours", "CPU for rent");
    OrganisationalUnit org0 = new OrganisationalUnit(0, "The Justice League", 9999.0f);
    StockTest() throws Exception {};


    @BeforeEach
    void setUp() {
        stock0 = new Stock(0);
    }

    @Test
    void setAndGetUnitId() {
        stock0.setUnitId(99);
        assertEquals(99, stock0.getUnitId());
    }

    @Test
    void add() throws InvalidArgumentValueException {
        assertTrue(stock0.add(new Item(asset0, 100)));
    }

    @Test
    void addMultipleOfTheSame() throws InvalidArgumentValueException {
        Item first = new Item(asset0, 100);
        Item second = new Item(asset0, 50);
        stock0.add(first);
        stock0.add(second);
        assertEquals(stock0.get(0).getQuantity(), 150);
    }

    @Test
    void testEquals() {
        Object stock1 = (Object) new Stock(1);
        Object stock00 = (Object) new Stock(0);
        assertFalse(stock0.equals(stock1));
        assertTrue(stock0.equals(stock00));
    }

    @Test
    void testHashCodeWorking() {
        stock0.hashCode();
    }
}