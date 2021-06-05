package server.DataSourceClasses;
import static org.junit.jupiter.api.Assertions.*;
import common.Exceptions.InvalidArgumentValueException;
import common.dataClasses.*;
import org.junit.jupiter.api.*;
import server.DBConnection;


class StockDataSourceTest {
    private static StockDataSource stockDataSource;
    private static AssetsDataSource assetsDataSource;

    @BeforeAll
    static void startTestMode(){
        DBConnection.setTestMode(true);
    }

    @AfterAll
    static void stopTestMode(){
        DBConnection.setTestMode(false);
    }

    @BeforeEach
    void setUp() {
        CasesToResponse.cleanDatabase();
        assetsDataSource = new AssetsDataSource();
        stockDataSource = new StockDataSource();
    }

    @AfterEach
    void tearDown(){
        CasesToResponse.cleanDatabase();
    }



    @Test
    void updateStock_and_queryUnitStock() {
        User testuser = new User(1, "DuyPham",
                "new", "123", "user", 1);
        Stock stock1 = new Stock(testuser.getUnitId());
        AssetsDataSource assetsDataSource = new AssetsDataSource();
        try {
            Asset asset1 = new Asset(1,"Test asset 1", "Testing");
            Asset asset2 = new Asset(2, "Test Asset 2", "Testing");
            assetsDataSource.addAsset(asset1);
            assetsDataSource.addAsset(asset2);
            stock1.add(new Item(asset1, 10));
            stock1.add(new Item(asset1, 10));
            stock1.add(new Item(asset2, 10));
            stock1.add(new Item(asset2, 10));
            stockDataSource.editStock(stock1);
            Stock userStock = stockDataSource.getStock(testuser.getUnitId());
            //check all items id in the stock
            assertEquals(userStock.get(0).getId(),stock1.get(0).getId());
            assertEquals(userStock.get(1).getId(),stock1.get(1).getId());
            //check all items quantity in the stock
            assertEquals(stock1.get(0).getQuantity(),userStock.get(0).getQuantity());
            assertEquals(stock1.get(1).getQuantity(),userStock.get(1).getQuantity());
        } catch (InvalidArgumentValueException e) {
            e.printStackTrace();
        }
    }



    @Test
    void getStockList() throws Exception {
        //create environment
        AssetsDataSource assetsDataSource = new AssetsDataSource();
        OrganisationsDataSource organisationsDataSource = new OrganisationsDataSource();
        assetsDataSource.deleteAll();
        organisationsDataSource.deleteAll();
        Asset asset1 = null;
        Asset asset2 = null;
        OrganisationalUnit org1 = null;
        OrganisationalUnit org0 = null;
        DataCollection<Stock> stocks;
        try {
            //create assets in database.
            asset1 = new Asset(1,"Test asset 1", "Testing");
            asset2 = new Asset(2, "Test Asset 2", "Testing");
            assetsDataSource.addAsset(asset1);
            assetsDataSource.addAsset(asset2);
            //create organisational units in database
             org1 = new OrganisationalUnit(1, "TestUnit1", 999);
             org0 = new OrganisationalUnit(0, "TestUnit0", 1000);
            organisationsDataSource.addOrganisation(org1);
            organisationsDataSource.addOrganisation(org0);
        //create stocks
        User testuser1 = new User(1, "DuyPham",
                "new", "123", "user", org1.getId());
        User testuser0 = new User(1, "tester 2",
                "new", "123", "user", org0.getId());
        Stock stock1 = new Stock(testuser1.getUnitId());
        Stock stock0 = new Stock(testuser0.getUnitId());
        //stock1
        stock1.add(new Item(asset1,100));
        stock1.add(new Item(asset1, 10));
        stock1.add(new Item(asset2, 10));
        stockDataSource.editStock(stock1);
        //stock0
        stock0.add(new Item(asset1, 10));
        stock0.add(new Item(asset2, 10));
        stockDataSource.editStock(stock0);
        //get all stocks in database (put in a list)
        stocks = stockDataSource.getStockList();

        //check return values
        //stock1
        //check unit id
        assertEquals(org0.getId(),stocks.get(0).getUnitId());
        //check first asset
        assertEquals(stock0.get(0).getId(), stocks.get(0).get(0).getId());
        assertEquals(stock0.get(0).getQuantity(), stocks.get(0).get(0).getQuantity());
        //check 2nd asset
        assertEquals(stock0.get(1).getId(), stocks.get(0).get(1).getId());
        assertEquals(stock0.get(1).getQuantity(), stocks.get(0).get(1).getQuantity());

        //stock0
        //check unit id
        assertEquals(org1.getId(), stocks.get(1).getUnitId());
        //check 1st asset
        assertEquals(stock1.get(0).getId(), stocks.get(1).get(0).getId());
        assertEquals(stock1.get(0).getQuantity(), stocks.get(1).get(0).getQuantity());
        //check 2nd asset
        assertEquals(stock1.get(1).getId(), stocks.get(1).get(1).getId());
        assertEquals(stock1.get(1).getQuantity(), stocks.get(1).get(1).getQuantity());
        } catch (InvalidArgumentValueException e) {
            e.printStackTrace();
        }
    }
}