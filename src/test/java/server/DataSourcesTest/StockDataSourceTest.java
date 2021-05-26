package server.DataSourcesTest;
import static org.junit.jupiter.api.Assertions.*;
import common.Exceptions.InvalidArgumentValueException;
import common.dataClasses.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Excluded_PUT_ALL_EXCLUSIONS_HERE.DataSourceClasses.StockDataSource;

class StockDataSourceTest {
    private static StockDataSource stockDataSource;
    @BeforeEach
    void setUp() {
        stockDataSource = new StockDataSource();
        stockDataSource.DeleteAll();
    }

    @AfterAll
    static void tearDown()
    {
        stockDataSource.Close();
    }


    @Test
    void EditItemQuantity() {
        User testuser = new User(1, "DuyPham",
                "new", "123", "user", 1);
        Stock stock1 = new Stock(testuser.getUnitId());
        //admin choice on GUI
        int assetIdOption = 1;
        int newQuantity = 100;
        try {
            Asset asset1 = new Asset(1,"Test asset 1", "Testing");
            stock1.add(new Item(asset1, 10));
            //input new value (but still keep the same asset id)
            stock1.setAssetId(assetIdOption);
            stock1.setAssetQuantity(newQuantity);
            //update stock table with unit's stock
            stockDataSource.UpdateUnitStock(stock1);
            stockDataSource.EditItemQuantity(stock1);
            //return stock from database to check value
            stock1 = stockDataSource.GetStock(testuser);
            //checking
            assertEquals(1, stock1.get(0).getId());
            assertEquals(100 ,stock1.get(0).getQuantity());
        } catch (InvalidArgumentValueException e) {
            e.printStackTrace();
        }

    }

    @Test
    void updateStock_and_queryUnitStock() {
        User testuser = new User(1, "DuyPham",
                "new", "123", "user", 1);
        Stock stock1 = new Stock(testuser.getUnitId());
        try {
            Asset asset1 = new Asset(1,"Test asset 1", "Testing");
            Asset asset2 = new Asset(2, "Test Asset 2", "Testing");
            stock1.add(new Item(asset1, 10));
            stock1.add(new Item(asset1, 10));
            stock1.add(new Item(asset2, 10));
            stock1.add(new Item(asset2, 10));
            stockDataSource.UpdateUnitStock(stock1);
            Stock userStock = stockDataSource.GetStock(testuser);
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

//
//    @Test
//    void getStock() {
//    }
//
//    @Test
//    void getStockList() {
//    }
//
//    @Test
//    void deleteAsset() {
//    }
//
//    @Test
//    void close() {
//    }
}