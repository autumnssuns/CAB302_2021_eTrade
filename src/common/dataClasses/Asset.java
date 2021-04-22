package common.dataClasses;

import java.io.Serializable;

/**
 * Represents an asset that can be traded in the system.
 */
public class Asset implements Serializable, IData {
    protected int assetId;
    protected String assetName;
    protected String description;

    /**
     * Initialise the asset. This constructor should be called by the server and packed into the server's response.
     * @param assetName
     * @param description
     */
    public Asset(int assetId, String assetName, String description){
        this.assetId = assetId;
        setName(assetName);
        setDescription(description);
    }

    /**
     * Retrieves the ID of the asset.
     * @return The ID of the asset.
     */
    public int getId() {
        return assetId;
    }
    public void setId(int Id) {this.assetId = Id;}

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
    public void setName(String name){
        this.assetName = name;
    }

    /**
     * Set the description of the asset to a new value.
     * @param description The new description for the asset.
     */
    public void setDescription(String description){
        this.description = description;
    }
}
