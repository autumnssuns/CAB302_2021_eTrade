package client.guiControls.adminMain.assetsController;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

/**
 * A controller to control the ASSETS Page (which allows the admin to add / remove or edit asset types).
 */
public class AssetsController {

    @FXML
    VBox assetsDisplayBox;
    @FXML
    TextField newNameTextField;
    @FXML
    TextField newDescriptionTextField;

    /**
     * Adds a new entry to the current display.
     * @param assetId The ID of the asset.
     * @param name The name of the asset.
     * @param description The username of the asset.
     */
    private void addAssetInfoBox(int assetId, String name, String description){
        AssetInfoBox assetInfoBox = new AssetInfoBox(assetId, name, description);
        assetsDisplayBox.getChildren().add(assetInfoBox);
    }

    /**
     * Resets the fields in the new asset row.
     */
    private void clearAddEntry(){
        newNameTextField.clear();
        newDescriptionTextField.clear();
    }

    /**
     * Adds a new entry, representing a new asset type.
     */
    public void addEntry(){
        //TODO: Perform data check for text fields
        int assetId = assetsDisplayBox.getChildren().size();
        String name = newNameTextField.getText();
        String description = newDescriptionTextField.getText();

        addAssetInfoBox(assetId, name, description);
        clearAddEntry();
    }

    //TODO: Gets data from database
    public void update(){

    }

    //TODO: Method to check if input is valid
    public boolean validateInfo(String name, String description){
        return true;
    }
}
