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
    void setAndGetAssetId() {
        stock0.setAssetId(99);
        assertEquals(99, stock0.getAssetId());
    }

    @Test
    void setAndGetAssetQuantity() {
        stock0.setAssetQuantity(100);
        assertEquals(100, stock0.getAssetQuantity());
    }

    @Test
    void add() throws InvalidArgumentValueException {
        assertTrue(stock0.add(new Item(asset0, 100)));
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