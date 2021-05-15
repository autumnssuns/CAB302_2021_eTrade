package common;

import common.Exceptions.InvalidArgumentValueException;
import common.dataClasses.Asset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssetTest {

    Asset asset;

    @BeforeEach
    void setUp() throws Exception{
        asset = new Asset(0, "Asset 0", "N/A");
    }

    @Test
    void getId() {
        assertEquals(asset.getId(), 0);
    }

    @Test
    void setId() {
        asset.setId(1);
        assertEquals(asset.getId(), 1);
    }

    @Test
    void getName() {
        assertEquals(asset.getName(), "Asset 0");
    }

    @Test
    void getDescription() {
        assertEquals(asset.getDescription(), "N/A");
    }

    @Test
    void setName() throws InvalidArgumentValueException {
        asset.setName("New Name");
        assertEquals(asset.getName(), "New Name");
    }

    @Test
    void setInvalidName() {
        assertThrows(InvalidArgumentValueException.class, () -> asset.setName(""));
        assertThrows(InvalidArgumentValueException.class, () -> asset.setName(null));
    }

    @Test
    void setDescription() {
        asset.setDescription("Description");
        assertEquals(asset.getDescription(), "Description");
    }

    @Test
    void testEquals() throws InvalidArgumentValueException {
        Asset otherAsset = new Asset(0, "Asset 0", "N/A");
        assertTrue(asset.equals(otherAsset) && otherAsset.equals(asset));
        assertTrue(asset.hashCode() == otherAsset.hashCode());
    }
}