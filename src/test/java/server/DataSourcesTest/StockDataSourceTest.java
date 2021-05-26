package server.DataSourcesTest;

import common.Exceptions.InvalidArgumentValueException;
import common.dataClasses.Asset;
import common.dataClasses.Item;
import common.dataClasses.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Excluded_PUT_ALL_EXCLUSIONS_HERE.DataSourceClasses.StockDataSource;

class StockDataSourceTest {
    private static StockDataSource stockDataSource;
    @BeforeEach
    void setUp() {
        stockDataSource = new StockDataSource();
//        stockDataSource.DeleteAll();
    }

//    @AfterAll
//    static void tearDown()
//    {
//        stockDataSource.close();
//    }



    @Test
    void insertAsset() {
        Stock stock1 = new Stock(0);
        Stock stock2 = new Stock(1);
        try {
            Asset asset1 = new Asset(1,"Test asset 1", "Testing");
            Asset asset2 = new Asset(2, "Test Asset 2", "Testing");
            stock1.add(new Item(asset1,100));
            stock1.add(new Item(asset1, 10));
            stockDataSource.UpdateStock(stock1);
            stock1.add(new Item(asset2, 100));
            stockDataSource.UpdateStock(stock1);
            stock1.remove(0);
            stockDataSource.UpdateStock(stock1);
            stock2.add(new Item(asset1, 10));
            stock2.add(new Item(asset2,100));
            stockDataSource.UpdateStock(stock2);
            stock1.remove(0);
            stockDataSource.UpdateStock(stock1);
            stockDataSource.getStockList();




        } catch (InvalidArgumentValueException e) {
            e.printStackTrace();
        }

    }
//    @Test
//    void editQuantity() {
//
//    }
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