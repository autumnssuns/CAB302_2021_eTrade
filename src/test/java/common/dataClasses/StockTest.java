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
    void setUnitId() {
    }

    @Test
    void getUnitId() {
    }

    @Test
    void add() throws InvalidArgumentValueException {
        assertTrue(stock0.add(new Item(asset0, 100)));
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }
}