package server.DataSourcesTest;

import common.Exceptions.InvalidArgumentValueException;
import common.dataClasses.Asset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.AssetsDataSource;

import static org.junit.jupiter.api.Assertions.*;

class AssetsDataSourceTest {

    private static AssetsDataSource assetsDataSource;
    private static AssetsDataSource emptyAssetsDataSource;

    @BeforeEach
    void setUp() {
        assetsDataSource = new AssetsDataSource();
        assetsDataSource.deleteAllAsset();
    }

    @Test
    void addAndGetAsset() throws InvalidArgumentValueException {
        assetsDataSource.addAsset(new Asset(0, "Bitcoin", "Crypto currency"));
        assertEquals(assetsDataSource.getAsset(0).getId(), 0);
        assertEquals(assetsDataSource.getAsset(0).getName(), "Bitcoin") ;
        assertEquals(assetsDataSource.getAsset(0).getDescription(), "Crypto currency");
    }

    @Test
    void deleteAssetAndGetAssetList() throws InvalidArgumentValueException {
        emptyAssetsDataSource = new AssetsDataSource();
        assetsDataSource.addAsset(new Asset(0, "Bitcoin", "Crypto currency"));
        assetsDataSource.deleteAsset(0);
        assertEquals(assetsDataSource.getAssetList(), emptyAssetsDataSource.getAssetList());
    }

    @Test
    void deleteAllAssetAndGetAssetList() throws InvalidArgumentValueException {
        emptyAssetsDataSource = new AssetsDataSource();
        assetsDataSource.addAsset(new Asset(0, "Bitcoin", "Crypto currency"));
        assetsDataSource.addAsset(new Asset(3, "Ethereum", "Crypto currency"));
        assetsDataSource.addAsset(new Asset(1, "GPU 100x", "GPU cal power"));
        assetsDataSource.addAsset(new Asset(2, "GPU 500x", "GPU cal power"));
        assetsDataSource.deleteAllAsset();
        assertEquals(assetsDataSource.getAssetList(), emptyAssetsDataSource.getAssetList()) ;
    }

    @Test
    void editAsset() throws InvalidArgumentValueException {
        assetsDataSource.addAsset(new Asset(0, "Bitcoin", "Crypto currency"));
        assertEquals(assetsDataSource.getAsset(0).getName(), "Bitcoin");
        assetsDataSource.editAsset(new Asset(0, "Ethereum", "Crypto"));
        assertEquals(assetsDataSource.getAsset(0).getName(), "Ethereum");
        assertEquals(assetsDataSource.getAsset(0).getDescription(), "Crypto");
    }
}