package server.DataSourceClasses;

import common.Exceptions.InvalidArgumentValueException;
import common.dataClasses.Asset;
import org.junit.jupiter.api.*;
import server.DBConnection;


import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AssetsDataSourceTest {

    private static AssetsDataSource assetsDataSource;
    private static AssetsDataSource emptyAssetsDataSource;

    @BeforeAll
    static void startTestMode(){
        DBConnection.setTestMode(true);
    }

    @AfterAll
    static void stopTestMode(){
        DBConnection.setTestMode(false);
    }

    @BeforeEach
    void setUp() throws Exception {
        CasesToResponse.cleanDatabase();
        assetsDataSource = new AssetsDataSource();
    }

    @AfterEach
    void tearDown() throws Exception {
        CasesToResponse.cleanDatabase();
    }

    @Test
    void addAndGetAsset() throws InvalidArgumentValueException {
        Asset expected = new Asset(0, "Bitcoin", "Crypto currency");
        assetsDataSource.addAsset(expected);
        Asset result = assetsDataSource.getAsset(0);
        assertEquals(expected, result);
    }

    @Test
    void getNonExistentAsset() throws InvalidArgumentValueException {
        Asset asset = new Asset(0, "Bitcoin", "Crypto currency");
        assetsDataSource.addAsset(asset);
        Asset result = assetsDataSource.getAsset(1);
        assertNull(result);
    }

    @Test
    void getAssetList() throws InvalidArgumentValueException {
        emptyAssetsDataSource = new AssetsDataSource();
        assetsDataSource.addAsset(new Asset(0, "Bitcoin", "Crypto currency"));
        assetsDataSource.addAsset(new Asset(3, "Ethereum", "Crypto currency"));
        assetsDataSource.addAsset(new Asset(1, "GPU 100x", "GPU cal power"));
        assetsDataSource.addAsset(new Asset(2, "GPU 500x", "GPU cal power"));
        assertEquals(4, assetsDataSource.getAssetList().size());
    }

    @Test
    void deleteAssetAndGetAssetList() throws InvalidArgumentValueException {
        assetsDataSource.addAsset(new Asset(0, "Bitcoin", "Crypto currency"));
        assetsDataSource.deleteAsset(0);
        assertEquals(0, assetsDataSource.getAssetList().size());
    }

    @Test
    void deleteAllAssetAndGetAssetList() throws InvalidArgumentValueException {
        emptyAssetsDataSource = new AssetsDataSource();
        assetsDataSource.addAsset(new Asset(0, "Bitcoin", "Crypto currency"));
        assetsDataSource.addAsset(new Asset(3, "Ethereum", "Crypto currency"));
        assetsDataSource.addAsset(new Asset(1, "GPU 100x", "GPU cal power"));
        assetsDataSource.addAsset(new Asset(2, "GPU 500x", "GPU cal power"));
        assetsDataSource.deleteAll();
        assertEquals(0, assetsDataSource.getAssetList().size()) ;
    }

    @Test
    void editAsset() throws InvalidArgumentValueException, SQLException {
        assetsDataSource.addAsset(new Asset(0, "Bitcoin", "Crypto currency"));
        assertEquals(assetsDataSource.getAsset(0).getName(), "Bitcoin");
        assetsDataSource.editAsset(new Asset(0, "Ethereum", "Crypto"));
        assertEquals("Ethereum", assetsDataSource.getAsset(0).getName());
        assertEquals( "Crypto", assetsDataSource.getAsset(0).getDescription());
    }

    @Test
    void editWrongAsset() throws InvalidArgumentValueException, SQLException {
        assetsDataSource.addAsset(new Asset(0, "Bitcoin", "Crypto currency"));
        assertEquals(assetsDataSource.getAsset(0).getName(), "Bitcoin");
        // Asset ID 1 does not exist, so no edit takes place
        assetsDataSource.editAsset(new Asset(1, "Ethereum", "Crypto"));
        assertEquals("Bitcoin", assetsDataSource.getAsset(0).getName());
        assertEquals( "Crypto currency", assetsDataSource.getAsset(0).getDescription());
    }
}