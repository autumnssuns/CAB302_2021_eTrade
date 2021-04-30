package common.dataClasses;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssetTest {

    private static Object[][] assets = new Object[][]{
            {0, "CPU Hours", "CPU for rent"},
            {1, "10 GB Database Server", "Remove SQL Server"},
            {2, "A Generic Video Game", "Nothing is more generic than this."},
            {3, "Coffin Dance Video", "You know what this is"}
    };

    private Asset[] testAssets = new Asset[assets.length];

    @BeforeEach
    void setUp() {
        for (int i = 0; i < assets.length; i++){
            Object[] assetData = assets[i];
            testAssets[i] = new Asset((int) assetData[0], (String) assetData[1], (String) assetData[2]);
        }
    }

    @Test
    void getId() {
        for (int i = 0; i < assets.length; i++){
            int expectedResult = (int) assets[i][0];
            int actualResult = testAssets[i].getId();
            assertEquals(expectedResult, actualResult);
        }
    }

    @Test
    void getName() {
        for (int i = 0; i < assets.length; i++){
            String expectedResult = (String) assets[i][1];
            String actualResult = testAssets[i].getName();
            assertEquals(expectedResult, actualResult);
        }
    }

    @Test
    void getDescription() {
        for (int i = 0; i < assets.length; i++){
            String expectedResult = (String) assets[i][2];
            String actualResult = testAssets[i].getDescription();
            assertEquals(expectedResult, actualResult);
        }
    }

    @Test
    void setName() {
        for (int i = 0; i < assets.length; i++){
            testAssets[i].setName(testAssets[i].getName() + " Clearance");
            String actualResult = testAssets[i].getName();
            String expectedResult = assets[i][1] + " Clearance";
            assertEquals(expectedResult, actualResult);
        }
    }

    @Test
    void setDescription() {
        for (int i = 0; i < assets.length; i++){
            testAssets[i].setDescription(testAssets[i].getDescription() + ". Now sold at a lower price.");
            String actualResult = testAssets[i].getDescription();
            String expectedResult = assets[i][2] + ". Now sold at a lower price.";
            assertEquals(expectedResult, actualResult);
        }
    }
}