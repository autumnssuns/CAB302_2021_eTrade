package server.DataSourceClasses;
import common.Request;
import common.Response;
import common.dataClasses.*;
import common.dataClasses.Order;
import org.junit.jupiter.api.*;
import server.DBConnection;

import static org.junit.jupiter.api.Assertions.*;
import static server.DataSourceClasses.CasesToResponse.findItem;

import java.time.LocalDateTime;

public class CasesToResponseTest {
    static Boolean createTable = true; //change this to create a table

    @BeforeAll
    static void startTestMode(){
        DBConnection.setTestMode(true);
    }

    @AfterAll
    static void stopTestMode(){
        DBConnection.setTestMode(false);
    }

    @BeforeAll
    public static void SetUp() throws Exception {
        CasesToResponse.cleanDatabase();
        if(createTable == true) {
            CasesToResponse.initiate();
        }
    }
//
//    @AfterAll
//    public static void tearDownn() throws IOException {
//        DBConnection.dropDatabase();
//    }

    @Test
    public void LoginTest() {
        // Register for user???
        User test = new User("duy","abcd").hashPassword();
        Request loginRequest = new Request(test,Request.ActionType.PING);
        Response response = CasesToResponse.login(loginRequest);
        test = (User) response.getAttachment();
        assertEquals(2, test.getId());
        assertEquals(1, test.getUnitId());
        assertEquals("Daniel Pham", test.getFullName());
    }
    @Test
    public void addOrder() throws Exception {
        CasesToResponse.add(new Order(16, Order.Type.SELL, 3, 3, 50, 0, 15.5f, LocalDateTime.of(0000, 1, 1, 00, 00), LocalDateTime.of(2021, 5, 9, 3, 42), Order.Status.PENDING));
        CasesToResponse.add(new Order(17, Order.Type.BUY, 2, 3, 50, 0, 15.5f, LocalDateTime.of(0000, 1, 1, 00, 00), LocalDateTime.of(2021, 5, 9, 3, 42), Order.Status.PENDING));
        CasesToResponse.add(new Order(18, Order.Type.BUY, 0, 3, 59, 0, 15.5f, LocalDateTime.of(0000, 1, 1, 00, 00), LocalDateTime.of(2021, 5, 9, 3, 42), Order.Status.PENDING));


        OrderDataSource orderDataSource = new OrderDataSource();
        assertEquals(2, orderDataSource.getOrder(10).getAssetId());

        Item item = findItem(1,0);
        assertEquals(44, item.getQuantity());
    }
}
