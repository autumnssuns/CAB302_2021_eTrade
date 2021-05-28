package server.DataSourcesTest;
import common.Request;
import common.Response;
import common.dataClasses.*;
import common.dataClasses.Order;
import org.junit.jupiter.api.*;
import server.Excluded_PUT_ALL_EXCLUSIONS_HERE.DataSourceClasses.*;
import common.Exceptions.InvalidArgumentValueException;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class CasesToResponseTest {
    static Boolean createTable = true; //change this to create a table

    @BeforeAll
    public static void SetUp() throws Exception {
        if(createTable == true) {
            CasesToResponse.initiate();
        }
    }

//    @Test
//    public void LoginTest() {
//        User test = new User("duy","abcd");
//        Request loginRequest = new Request(test,"");
//        Response response = CasesToResponse.Login(loginRequest);
//        test = (User) response.getAttachment();
//        assertEquals(2, test.getUserId());
//        assertEquals(1, test.getUnitId());
//        assertEquals("Daniel Pham", test.getFullName());
//    }

    @Test
    public void LoginTest() {
        // Register for user???
        User test = new User("duy","abcd");
        Request loginRequest = new Request(test,"");
        Response response = CasesToResponse.login(loginRequest);
        test = (User) response.getAttachment();
        assertEquals(2, test.getUserId());
        assertEquals(1, test.getUnitId());
        assertEquals("Daniel Pham", test.getFullName());
    }
    @Test
    public void addOrderBuyType() throws Exception {
//        AssetsDataSource assetsDataSource = new AssetsDataSource();
//        StockDataSource stockDataSource = new StockDataSource();
//        Stock stock = new Stock(2);
//        Stock dataStock = stockDataSource.GetStock(2);
//        Item cloneitem0 = new Item(assetsDataSource.getAsset(0),10);
//        System.out.println(cloneitem0.getName());
//
//        Item item0 = new Item(assetsDataSource.getAsset(0),10);
//        System.out.println(item0.getName());
//        Item item1 = dataStock.get(1);
//        Item item2 = dataStock.get(2);
//        Item item3 = dataStock.get(3);
//        System.out.println(item0.getName() == cloneitem0.getName());
//        stock.add(cloneitem0);
//        stock.add(item0);
////        for (Item item : dataStock)
////        {
////            Item newitem = item;
////            stock.add(item);
////        }
        //  stockDataSource.UpdateUnitStock(stock);
        CasesToResponse.add(new Order(0, Order.Type.SELL, 0, 0, 99, 0, 10f, LocalDateTime.of(0000, 1, 1, 00, 00), LocalDateTime.of(2021, 5, 6, 16, 52), Order.Status.PENDING));
        CasesToResponse.add(new Order(1, Order.Type.SELL, 0, 1, 99, 0, 3f, LocalDateTime.of(0000, 1, 1, 00, 00), LocalDateTime.of(2021, 5, 6, 13, 42), Order.Status.PENDING));
        CasesToResponse.add(new Order(2, Order.Type.SELL, 0, 2, 99, 0, 4f, LocalDateTime.of(0000, 1, 1, 00, 00), LocalDateTime.of(2021, 5, 6, 7, 45), Order.Status.PENDING));
        CasesToResponse.add(new Order(3, Order.Type.SELL, 0, 3, 99, 0, 5f, LocalDateTime.of(0000, 1, 1, 00, 00), LocalDateTime.of(2021, 5, 6, 22, 00), Order.Status.PENDING));
        CasesToResponse.add(new Order(4, Order.Type.SELL, 1, 0, 55, 0, 8f, LocalDateTime.of(0000, 1, 1, 00, 00), LocalDateTime.of(2021, 5, 7, 21, 52), Order.Status.PENDING));
        CasesToResponse.add(new Order(5, Order.Type.SELL, 1, 1, 55, 0, 7f, LocalDateTime.of(0000, 1, 1, 00, 00), LocalDateTime.of(2021, 5, 7, 15, 26), Order.Status.PENDING));
        CasesToResponse.add(new Order(6, Order.Type.SELL, 1, 2, 55, 0, 8f, LocalDateTime.of(0000, 1, 1, 00, 00), LocalDateTime.of(2021, 5, 7, 18, 28), Order.Status.PENDING));
        CasesToResponse.add(new Order(7, Order.Type.SELL, 1, 3, 50, 0, 9f, LocalDateTime.of(0000, 1, 1, 00, 00), LocalDateTime.of(2021, 5, 7, 13, 36), Order.Status.PENDING));
        CasesToResponse.add(new Order(8, Order.Type.BUY, 2, 0, 40, 0, 10f, LocalDateTime.of(0000, 1, 1, 00, 00), LocalDateTime.of(2021, 5, 8, 14, 45), Order.Status.PENDING));
        CasesToResponse.add(new Order(9, Order.Type.BUY, 2, 1, 40, 0, 10.5f, LocalDateTime.of(0000, 1, 1, 00, 00), LocalDateTime.of(2021, 5, 8, 11, 14), Order.Status.PENDING));
        CasesToResponse.add(new Order(10, Order.Type.BUY, 2, 2, 40, 0, 11.5f, LocalDateTime.of(0000, 1, 1, 00, 00), LocalDateTime.of(2021, 5, 8, 7, 15), Order.Status.PENDING));
        CasesToResponse.add(new Order(11, Order.Type.BUY, 2, 3, 40, 0, 12.5f, LocalDateTime.of(0000, 1, 1, 00, 00), LocalDateTime.of(2021, 5, 8, 4, 20), Order.Status.PENDING));
        CasesToResponse.add(new Order(12, Order.Type.BUY, 3, 0, 50, 0, 13.5f, LocalDateTime.of(0000, 1, 1, 00, 00), LocalDateTime.of(2021, 5, 9, 6, 21), Order.Status.PENDING));
        CasesToResponse.add(new Order(13, Order.Type.BUY, 3, 1, 50, 0, 12.5f, LocalDateTime.of(0000, 1, 1, 00, 00), LocalDateTime.of(2021, 5, 9, 8, 30), Order.Status.PENDING));
        CasesToResponse.add(new Order(14, Order.Type.BUY, 3, 2, 50, 0, 14.5f, LocalDateTime.of(0000, 1, 1, 00, 00), LocalDateTime.of(2021, 5, 9, 0, 11), Order.Status.PENDING));
        CasesToResponse.add(new Order(15, Order.Type.BUY, 3, 3, 50, 0, 15.5f, LocalDateTime.of(0000, 1, 1, 00, 00), LocalDateTime.of(2021, 5, 9, 3, 42), Order.Status.PENDING));
        CasesToResponse.add(new Order(16, Order.Type.SELL, 3, 3, 50, 0, 15.5f, LocalDateTime.of(0000, 1, 1, 00, 00), LocalDateTime.of(2021, 5, 9, 3, 42), Order.Status.PENDING));
        CasesToResponse.add(new Order(17, Order.Type.BUY, 2, 3, 50, 0, 15.5f, LocalDateTime.of(0000, 1, 1, 00, 00), LocalDateTime.of(2021, 5, 9, 3, 42), Order.Status.PENDING));
        CasesToResponse.add(new Order(18, Order.Type.BUY, 0, 3, 59, 0, 15.5f, LocalDateTime.of(0000, 1, 1, 00, 00), LocalDateTime.of(2021, 5, 9, 3, 42), Order.Status.PENDING));
    }
}
