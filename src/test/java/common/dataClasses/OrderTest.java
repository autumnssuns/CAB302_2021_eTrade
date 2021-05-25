package common.dataClasses;

import common.Exceptions.InvalidArgumentValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    Order buy;

    Asset sampleAsset;


    @BeforeEach
    void setUp() throws InvalidArgumentValueException {
        buy = new Order(0, Order.Type.BUY,
                101, 0, 5, 0, 10,
                null, null, Order.Status.PENDING);
        sampleAsset = new Asset(0, "Asset 0", "N/A");
    }

    @Test
    void negativeId() {
        assertThrows(Exception.class, () -> {
            Order sell = new Order(-2, Order.Type.BUY, 101, 0, 5, 0, 10, null, null, Order.Status.PENDING);
        });
    }

    @Test
    void setAsset() {
        buy.setAsset(sampleAsset);
    }

    @Test
    void getAsset() {
        buy.setAsset(sampleAsset);
        assertEquals(buy.getAsset(), sampleAsset);
    }

    @Test
    void excessiveResolvedQuantity() {
        assertThrows(Exception.class, () -> {
            buy.ResolvedQuantity(6);
        });
    }

    @Test
    void getOrderId() {
        assertEquals(buy.getOrderId(), 0);
    }

    @Test
    void getOrderType() {
        assertEquals(buy.getOrderType(), Order.Type.BUY);
    }

    @Test
    void getUnitId() {
        assertEquals(buy.getUnitId(), 101);
    }

    @Test
    void getAssetId() {
        assertEquals(buy.getAssetId(), 0);
    }

    @Test
    void getPlacedQuantity() {
        assertEquals(buy.getPlacedQuantity(), 5);
    }

    @Test
    void getResolvedQuantity() {
        assertEquals(buy.getResolvedQuantity(), 0);
    }

    @Test
    void getPrice() {
        assertEquals(buy.getPrice(), 10);
    }

    @Test
    void getFinishDate() {
        assertEquals(buy.getFinishDate(), null);
    }

    @Test
    void getOrderDate() {
        assertEquals(buy.getOrderDate(), null);
    }

    @Test
    void getStatus() {
        assertEquals(buy.getStatus(), Order.Status.PENDING);
    }

    @Test
    void setOrderId() {
        buy.setOrderId(9);
        assertEquals(buy.getOrderId(), 9);

    }

    @Test
    void setOrderType() {
        buy.setOrderType(Order.Type.SELL);
        assertEquals(buy.getOrderType(), Order.Type.SELL);
    }

    @Test
    void setUnitId() {
        buy.setUnitId(11);
        assertEquals(buy.getUnitId(), 11);
    }

    @Test
    void setAssetId() {
        buy.setAssetID(1);
        assertEquals(buy.getAssetId(), 1);
    }

    @Test
    void setPlacedQuantity() {
        buy.setPlacedQuantity(100);
        assertEquals(buy.getPlacedQuantity(), 100);
    }

    @Test
    void setResolvedQuantity() {
        buy.setResolvedQuantity(2);
        buy.setResolvedQuantity(2);
        assertEquals(buy.getResolvedQuantity(), 1);
    }

    @Test
    void setPrice() {
        buy.setPrice(999.9f);
        assertEquals(buy.getPrice(), 999.9f);
    }

    @Test
    void setOrderDate() {
    }

    @Test
    void setFinishDate() {
    }

    @Test
    void setStatus() {
        buy.setStatus(Order.Status.COMPLETED);
        assertEquals(buy.getStatus(), Order.Status.COMPLETED);
        buy.setStatus(Order.Status.CANCELLED);
        assertEquals(buy.getStatus(), Order.Status.CANCELLED);
    }

    @Test
    void testEquals() {
        Object o = new Order(0, Order.Type.BUY,
                101, 0, 5, 0, 10,
                null, null, Order.Status.PENDING);
        Object oo = new Order(0, Order.Type.BUY,
                101, 0, 5, 0, 10,
                null, null, Order.Status.CANCELLED);

        assertEquals(buy.equals(o), true);
        assertEquals(buy.equals(oo), false);
    }

    @Test
    void testHashCodeWorking() {
        buy.hashCode();
    }

    @Test
    void isSimilarTo() {
        Order buy01 = new Order(0, Order.Type.BUY,
                101, 0, 77, 0, 10,
                null, null, Order.Status.PENDING);
        assertEquals(buy.isSimilarTo(buy01), true);
    }
}