package client.guiControls.adminMain.assetsController;

import client.guiControls.DisplayController;
import client.guiControls.adminMain.AdminLocalDatabase;
import client.guiControls.adminMain.AdminMainController;
import common.Response;
import common.dataClasses.Asset;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

/**
 * A controller to control the ASSETS Page (which allows the admin to add / remove or edit asset types).
 */
public class AssetsController extends DisplayController {

    @FXML
    VBox assetsDisplayBox;
    @FXML
    TextField newNameTextField;
    @FXML
    TextField newDescriptionTextField;
    @FXML
    Button refreshAssetsButton;

    /**
     * Adds a new entry to the current display.
     * @param assetId The ID of the asset.
     * @param name The name of the asset.
     * @param description The username of the asset.
     */
    private void addAssetInfoBox(int assetId, String name, String description){
        AssetInfoBox assetInfoBox = new AssetInfoBox(assetId, name, description);
        assetInfoBox.setController((AdminMainController) controller);
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

        Asset newAsset = new Asset(assetId, name, description);
        Response response = controller.sendRequest("add", newAsset, Asset.class);
        update();

        if (response.isFulfilled()){
            addAssetInfoBox(assetId, name, description);
            clearAddEntry();
        }
    }

    /**
     * Updates the view by checking with server
     */
    @Override
    public void update(){
        assetsDisplayBox.getChildren().clear();
        AdminLocalDatabase localDatabase = (AdminLocalDatabase) controller.getDatabase();
        for (Asset asset : localDatabase.getAssets()){
            addAssetInfoBox(asset.getId(), asset.getName(), asset.getDescription());
        }
    }

    //TODO: Method to check if input is valid
    public boolean validateInfo(String name, String description){
        return true;
    }
}
