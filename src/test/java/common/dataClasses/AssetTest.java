package common.dataClasses;

import common.Exceptions.InvalidArgumentValueException;
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
        assertEquals(0, asset.getId());
    }

    @Test
    void setId() {
        asset.setId(1);
        assertEquals(1, asset.getId());
    }

    @Test
    void getName() {
        assertEquals("Asset 0", asset.getName());
    }

    @Test
    void getDescription() {
        assertEquals("N/A", asset.getDescription());
    }

    @Test
    void setName() throws InvalidArgumentValueException {
        asset.setName("New Name");
        assertEquals("New Name", asset.getName());
    }

    @Test
    void setInvalidName() {
        assertThrows(InvalidArgumentValueException.class, () -> asset.setName(""));
        assertThrows(InvalidArgumentValueException.class, () -> asset.setName(null));
    }

    @Test
    void setDescription() {
        asset.setDescription("Description");
        assertEquals("Description", asset.getDescription());
    }

    @Test
    void testEquals() throws InvalidArgumentValueException {
        Asset otherAsset = new Asset(0, "Asset 0", "N/A");
        assertTrue(asset.equals(otherAsset) && otherAsset.equals(asset));
        assertEquals(otherAsset.hashCode(), asset.hashCode());
    }

    @Test
    void testHashCodeWorking() {
        asset.hashCode();
    }
}