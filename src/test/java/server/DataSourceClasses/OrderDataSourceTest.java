package server.DataSourceClasses;

import common.dataClasses.Order;
import org.junit.jupiter.api.*;
import server.DBConnection;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderDataSourceTest {

    private static OrderDataSource orders;
    Order order00 = new Order(0, Order.Type.SELL, 0, 0,
            99, 0, 10f,
            LocalDateTime.of(0,1,1,0,0),
            LocalDateTime.of(2021, 5, 6, 16, 52),
            Order.Status.PENDING);
    Order order02 = new Order(2, Order.Type.BUY, 0, 9,
            99, 0, 10f,
            LocalDateTime.of(0, 1, 1, 0, 0),
            LocalDateTime.of(2021, 5, 6, 16, 52),
            Order.Status.PENDING);

    OrderDataSourceTest() throws Exception {
    }

    @BeforeAll
    static void startTestMode(){
        DBConnection.setTestMode(true);
    }

    @AfterAll
    static void stopTestMode(){
        DBConnection.setTestMode(false);
    }

    @BeforeEach
    void setUpWithOneOrderSuccessfully() throws Exception {
        RequestHandler.cleanDatabase();
        orders = new OrderDataSource();
        orders.addOrder(new Order(1, Order.Type.BUY, 0, 9,
                99, 0, 10f,
                LocalDateTime.of(2021, 5, 6, 16, 52),
                LocalDateTime.of(2021, 5, 6, 16, 52),
                Order.Status.PENDING));
    }

    @AfterEach
    void tearDown() throws Exception {
        RequestHandler.cleanDatabase();
    }

    @Test
    void addOrder() {
        orders.addOrder(order00);
        assertEquals(orders.getOrder(0).getOrderId(), 0);
    }

    @Test
    void deleteOrder() {
        orders.deleteOrder(1);
        assertEquals(orders.getOrder(1), null);
    }

    @Test
    void deleteAllOrders() {
        orders.addOrder(order02);
        orders.deleteAll();
        assertEquals(orders.getOrder(1), null);
        assertEquals(orders.getOrder(2), null);
    }

    @Test
    void getOrder() {
        assertEquals(orders.getOrder(1).getOrderId(), 1);
    }

    @Test
    void getOrderList() {
        orders.addOrder(order00);
        orders.addOrder(order02);
        assertEquals(orders.getOrderList().size(), 3);
    }

    @Test
    void editOrder() throws Exception {
        orders.editOrder(new Order(1, Order.Type.BUY, 0, 9,
                99, 0, 1000000,
                LocalDateTime.of(2021, 5, 6, 16, 52),
                LocalDateTime.of(2021, 5, 6, 16, 52),
                Order.Status.PENDING));
        assertEquals(orders.getOrder(1).getPrice(), 1000000);
    }
}