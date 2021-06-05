package common.dataClasses;

import common.Exceptions.InvalidArgumentValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataCollectionTest {
    DataCollection<Asset> assets;

    @BeforeEach
    void setUp() {
        assets = new DataCollection();
    }

    @Test
    void addAll() throws InvalidArgumentValueException {
        Asset[] newAsset = new Asset[]{
                new Asset(0, "Test 1", ""),
                new Asset(1, "Test 2", "")
        };
        assets.addAll(newAsset);
        assertEquals(2, assets.size());
    }

    @Test
    void testEquals() throws InvalidArgumentValueException {
        DataCollection<Asset> otherCollection = new DataCollection();
        otherCollection.add(new Asset(0, "Test 1", ""));
        otherCollection.add(new Asset(1, "Test 2", ""));
        Asset[] newAsset = new Asset[]{
                new Asset(0, "Test 1", ""),
                new Asset(1, "Test 2", "")
        };
        assets.addAll(newAsset);
        assertTrue(assets.equals(otherCollection) && otherCollection.equals(assets));
        assertTrue(assets.hashCode() == otherCollection.hashCode());
    }

    @Test
    void testNotEquals() throws InvalidArgumentValueException {
        DataCollection<Asset> otherCollection = new DataCollection();
        otherCollection.add(new Asset(0, "Test 2", ""));
        otherCollection.add(new Asset(1, "Test 1", ""));
        Asset[] newAsset = new Asset[]{
                new Asset(0, "Test 1", ""),
                new Asset(1, "Test 2", "")
        };
        assets.addAll(newAsset);
        assertFalse(assets.equals(otherCollection) && otherCollection.equals(assets));
        assertFalse(assets.hashCode() == otherCollection.hashCode());
    }
}