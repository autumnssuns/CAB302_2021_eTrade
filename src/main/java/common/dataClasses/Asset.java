package common.dataClasses;

import common.Exceptions.*;

import java.util.Objects;

/**
 * Represents an asset that can be traded in the system.
 */
public class Asset implements IData {
    protected Integer assetId;
    protected String assetName;
    protected String description;

    /**
     * Initialise the asset. This constructor should be called by the server and packed into the server's response.
     * @param assetName The name of the asset.
     * @param description The description of the asset.
     */
    public Asset(Integer assetId, String assetName, String description) throws InvalidArgumentValueException {
        this.assetId = assetId;
        setName(assetName);
        setDescription(description);
    }

    /**
     * Retrieves the ID of the asset.
     * @return The ID of the asset.
     */
    public Integer getId() {
        return assetId;
    }

    /**
     * Sets the ID of the asset.
     * @param Id the ID of the asset.
     */
    public void setId(Integer Id) {this.assetId = Id;}

    /**
     * Retrieves the name of the asset.
     * @return The asset's name.
     */
    public String getName(){
        return assetName;
    }

    /**
     * Retrieves the description of the asset.
     * @return The asset's description.
     */
    public String getDescription(){
        return description;
    }

    /**
     * Set the name of the asset to a new value.
     * @param name The new name for the asset.
     */
    public void setName(String name) throws InvalidArgumentValueException {
        try{
            if (name.isBlank() || name.length() == 0){
                throw new InvalidArgumentValueException();
            }
            this.assetName = name;
        }
        catch (NullPointerException e){
            throw new NullArgumentException();
        }
    }

    /**
     * Set the description of the asset to a new value.
     * @param description The new description for the asset.
     */
    public void setDescription(String description){
        this.description = description;
    }


    /**
     * Indicates if some object is equal to this instance.
     * @param o The object to compare.
     * @return true if the object is equal to the instance, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asset asset = (Asset) o;
        return assetId == asset.assetId && Objects.equals(assetName, asset.assetName) && Objects.equals(description, asset.description);
    }

    /**
     * Returns the hashCode of this instance.
     * @return The hashCode of this instance.
     */
    @Override
    public int hashCode() {
        return Objects.hash(assetId, assetName, description);
    }
}
