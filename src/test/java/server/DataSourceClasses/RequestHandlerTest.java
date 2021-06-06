package server.DataSourceClasses;
import common.Request;
import common.Response;
import common.dataClasses.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import server.DBConnection;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import static server.DataSourceClasses.RequestHandler.findItem;

public class RequestHandlerTest {
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
        RequestHandler.cleanDatabase();
        if(createTable == true) {
            RequestHandler.initiate();
        }
    }

    @Test
    public void LoginTest() throws Exception {
        User test = new User("duy","abcd").hashPassword();
        Request loginRequest = new Request(test,Request.ActionType.PING);
        Response response = RequestHandler.login(loginRequest);
        test = (User) response.getAttachment();
        assertEquals(2, test.getId());
        assertEquals(1, test.getUnitId());
        assertEquals("Daniel Pham", test.getFullName());
    }

    @Test
    public void FailLogin() throws Exception {
        User wrongPassLogin = new User("duy","wrongpass").hashPassword();
        User wrongAccountName = new User("WrongName", "abcd").hashPassword();
        Request loginRequest = new Request(wrongPassLogin,Request.ActionType.PING);
        Request loginRequest2 = new Request(wrongAccountName,Request.ActionType.PING);
        Response response = RequestHandler.login(loginRequest);
        Response response2 = RequestHandler.login(loginRequest2);
        wrongPassLogin = (User) response.getAttachment();
        wrongAccountName = (User) response2.getAttachment();
        assertEquals(null,wrongPassLogin);
        assertEquals(null, wrongAccountName);
    }

    @Test
    public void CleanDatabase() throws Exception {
        RequestHandler.cleanDatabase();
        DataCollection<User> users = (DataCollection<User>) RequestHandler.queryUsers().getAttachment();
        DataCollection<Asset> assets = (DataCollection<Asset>) RequestHandler.queryAssets().getAttachment();
        DataCollection<OrganisationalUnit> organisationalUnits = (DataCollection<OrganisationalUnit>) RequestHandler.queryOrganisations().getAttachment();
        DataCollection<Stock> stocks = (DataCollection<Stock>) RequestHandler.queryStocks().getAttachment();
        DataCollection<Order> orders = (DataCollection<Order>) RequestHandler.queryOrders().getAttachment();
        NotificationDataSource notificationDataSource = new NotificationDataSource();
        DataCollection<Notification> notifications = notificationDataSource.getAll();
        assertEquals(1,
                users.size() + assets.size() + organisationalUnits.size() + stocks.size() + orders.size() + notifications.size());
    }

    @Test
    public void addOrder() throws Exception {
        RequestHandler.add(new Order(16, Order.Type.SELL, 3, 3, 50, 0, 15.5f, LocalDateTime.of(0000, 1, 1, 00, 00), LocalDateTime.of(2021, 5, 9, 3, 42), Order.Status.PENDING));
        RequestHandler.add(new Order(17, Order.Type.BUY, 2, 3, 50, 0, 15.5f, LocalDateTime.of(0000, 1, 1, 00, 00), LocalDateTime.of(2021, 5, 9, 3, 42), Order.Status.PENDING));
        RequestHandler.add(new Order(18, Order.Type.BUY, 0, 3, 59, 0, 15.5f, LocalDateTime.of(0000, 1, 1, 00, 00), LocalDateTime.of(2021, 5, 9, 3, 42), Order.Status.PENDING));

        OrderDataSource orderDataSource = new OrderDataSource();
        assertEquals(2, orderDataSource.getOrder(10).getAssetId());

        Item item = findItem(1,0);
        assertEquals(44, item.getQuantity());
    }

    @Test
    public void addUser() throws Exception{

        RequestHandler.add(new User (1245,"ABC","ABC","123456789", "Admin",1 ));

        UserDataSource userDataSource = new UserDataSource();
        User user = userDataSource.getUser("ABC");
        assertEquals(user.getAccountType(), "Admin");
        assertEquals(user.getUnitId(), 1);

    }

    @Test
    public void addOrganisationalUnit() throws Exception{
        RequestHandler.add(new OrganisationalUnit(99, "SuperTeam", 1586));
        OrganisationsDataSource orgDataSource = new OrganisationsDataSource();
        OrganisationalUnit org = orgDataSource.getOrganisation(99);

        assertEquals("SuperTeam",org.getName());

    }
    @Test
    public void addStock() throws Exception{
        RequestHandler.add(new Stock(100));
        StockDataSource stockDataSource = new StockDataSource();
        Stock stock = stockDataSource.getStock(100);
        assertNotNull(stock);
    }

    @Test
    public void editOrder() throws Exception{
        Order edited = new Order(0, Order.Type.BUY, 0, 0, 99, 0, 10f, null, LocalDateTime.of(2021, 5, 6, 16, 52), Order.Status.PENDING);
        Response r = RequestHandler.edit(edited);
        OrderDataSource order = new OrderDataSource();;
        Order result = order.getOrder(0);
        assertEquals(result, r.getAttachment());
    }

    @Test
    public void queryUser() throws Exception{
        UserDataSource userDataSource = new UserDataSource();
        User expected = new User(4, "Rodo Nguyen", "rodo", "rodo", "user", 3);
        Response r = RequestHandler.query(expected);
        assertEquals(userDataSource.getUser("rodo"),r.getAttachment());

    }


}
