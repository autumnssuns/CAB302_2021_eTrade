package server.DataSourcesTest;
import common.Request;
import common.Response;
import common.dataClasses.*;
import common.dataClasses.Order;
import org.junit.jupiter.api.*;
import server.Excluded_PUT_ALL_EXCLUSIONS_HERE.DataSourceClasses.*;
import common.Exceptions.InvalidArgumentValueException;
import static org.junit.jupiter.api.Assertions.*;

import common.dataClasses.Asset;
import common.dataClasses.DataCollection;
import server.Excluded_PUT_ALL_EXCLUSIONS_HERE.DataSourceClasses.AssetsDataSource;

import java.time.LocalDateTime;

public class CasesToResponseTest {
    static Boolean createTable = false; //change this to create a table
    @BeforeAll
    public static void SetUp() throws InvalidArgumentValueException {
        if(createTable == true) {
            CasesToResponse.initiate();
        }
    }

    @Test
    public void LoginTest() {
        User test = new User("duy","abcd");
        Request loginRequest = new Request(test,"");
        Response response = CasesToResponse.Login(loginRequest);
        test = (User) response.getAttachment();
        assertEquals(2, test.getUserId());
        assertEquals(1, test.getUnitId());
        assertEquals("Daniel Pham", test.getFullName());
    }

    @Test
    public void addOrderBuyType(){
        CasesToResponse.add(new Order(17, Order.Type.BUY, 1, 0, 64, 0, 10f, LocalDateTime.of(0000,1,1,00,00), LocalDateTime.of(2021, 5, 6, 16, 52), Order.Status.PENDING));
        CasesToResponse.add(new Order(18, Order.Type.SELL, 0, 0, 20, 0, 10f, LocalDateTime.of(0000,1,1,00,00), LocalDateTime.of(2021, 5, 6, 16, 52), Order.Status.PENDING));
        CasesToResponse.add(new Order(19, Order.Type.SELL, 0, 0, 20, 0, 10f, LocalDateTime.of(0000,1,1,00,00), LocalDateTime.of(2021, 5, 6, 16, 52), Order.Status.PENDING));
    }

}
