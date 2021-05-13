package client.guiControls.adminMain.organisationalUnitsController;

import client.guiControls.DisplayController;
import client.guiControls.adminMain.AdminLocalDatabase;
import common.Response;
import common.dataClasses.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * A controller to control the ORGANISATIONAL UNITS Page (which allows the admin to add / remove or edit organisationalUnits' information).
 */
public class OrganisationalUnitsController extends DisplayController {
    private Stock tempStock;

    @FXML
    VBox organisationalUnitsDisplayBox;
    @FXML
    AnchorPane displayPane;
    @FXML
    AnchorPane organisationalUnitEditPane;
    @FXML
    TextField organisationalUnitNameTextField;
    @FXML
    TextField creditTextField;
    @FXML
    VBox organisationalUnitAssetsBox;
    @FXML
    ComboBox newOrganisationalUnitAssetNameComboBox;
    @FXML
    TextField newOrganisationalUnitAssetQuantityTextField;
    @FXML
    Button confirmOrganisationalUnitButton;
    private AdminLocalDatabase localDatabase;

    /**
     * Adds a new entry to the current display.
     * @param unitId The ID of the asset.
     * @param name The name of the asset.
     * @param credit The username of the asset.
     */
    public void addOrganisationalUnitInfoBox(int unitId, String name, float credit, int assetQuantity, Stock stock){
        OrganisationalUnitInfoBox organisationalUnitInfoBox = new OrganisationalUnitInfoBox(unitId, name, credit, assetQuantity, stock);
        organisationalUnitInfoBox.setOrganisationalUnitAssetsBox(organisationalUnitAssetsBox);
        organisationalUnitInfoBox.setController(this);

        organisationalUnitsDisplayBox.getChildren().add(organisationalUnitInfoBox);
    }

    /**
     * Adds a new entry, representing a new organisational unit.
     */
    public void startEditor(){
        organisationalUnitEditPane.setVisible(true);
        tempStock = new Stock(-1);  // Unassigned
        confirmOrganisationalUnitButton.setOnAction(e -> confirmEditor());
    }

    /**
     * Edit an existing organisational unit.
     */
    public void startEditor(OrganisationalUnitInfoBox caller){
        tempStock = caller.getStock();
        organisationalUnitEditPane.setVisible(true);
        organisationalUnitNameTextField.setText(caller.getName());
        creditTextField.setText(String.valueOf(caller.getCredit()));

        organisationalUnitAssetsBox.getChildren().clear();
        for (Item item : caller.getStock()){
            organisationalUnitAssetsBox.getChildren().add(new UnitAssetInfoBox(item.getName(), item.getQuantity()));
        }

        confirmOrganisationalUnitButton.setOnAction(e -> confirmEditor(caller));
    }

    /**
     * Confirms the addition of a new organisational unit.
     */
    public void confirmEditor(){
        int unitId = organisationalUnitsDisplayBox.getChildren().size();
        String name = organisationalUnitNameTextField.getText();
        float credit = Float.parseFloat(creditTextField.getText());

        OrganisationalUnit organisationalUnit = new OrganisationalUnit(unitId, name, credit);
        Response response = controller.sendRequest("add", organisationalUnit, OrganisationalUnit.class);
        tempStock.setUnitId(unitId);
        controller.sendRequest("edit", tempStock, Stock.class);
        update();
        if (response.isFulfilled()){
            addOrganisationalUnitInfoBox(unitId, name, credit, 0, tempStock);
            closeEditor();
        }
    }

    /**
     * Confirms the edit of an existing organisational unit.
     * @param caller The instance that calls for an editor.
     */
    public void confirmEditor(OrganisationalUnitInfoBox caller){
        String name = organisationalUnitNameTextField.getText();
        float credit = Float.parseFloat(creditTextField.getText());

        OrganisationalUnit organisationalUnit = new OrganisationalUnit(caller.getUnitId(), name, credit);
        Response response = controller.sendRequest("edit", organisationalUnit, OrganisationalUnit.class);
        tempStock.setUnitId(caller.getUnitId());
        controller.sendRequest("edit", tempStock, Stock.class);
        caller.setStock(tempStock);
        if (response.isFulfilled()){
            caller.setName(name);
            caller.setCredit(credit);
            caller.reloadEntries();
            closeEditor();
        }
    }

    /**
     * Close the editor.
     */
    public void closeEditor(){
        update();
        organisationalUnitEditPane.setVisible(false);
        clearEditor();
    }

    /**
     * Clears the text fields and display of the editor
     */
    public void clearEditor(){
        organisationalUnitNameTextField.clear();
        creditTextField.clear();
        organisationalUnitAssetsBox.getChildren().clear();
    }

    /**
     * Adds a new asset to the organisational unit in the editor.
     */
    public void addOrganisationalUnitAssetInfoBox(){
        String assetName = (String) newOrganisationalUnitAssetNameComboBox.getValue();
        int quantity = Integer.parseInt(newOrganisationalUnitAssetQuantityTextField.getText());

        DataCollection<Asset> assets = localDatabase.getAssets();
        
        Asset linkedAsset = null;
        for (Asset asset : assets){
            if (asset.getName().equals(assetName)){
                linkedAsset = asset;
                break;
            }
        }

        UnitAssetInfoBox unitAssetInfoBox = new UnitAssetInfoBox(assetName, quantity);
        tempStock.add(new Item(new Asset(linkedAsset.getId(), assetName, linkedAsset.getDescription()), quantity));
        organisationalUnitAssetsBox.getChildren().add(unitAssetInfoBox);
        newOrganisationalUnitAssetNameComboBox.valueProperty().set(null);
        newOrganisationalUnitAssetQuantityTextField.clear();
    }

    //TODO: Gets data from database
    @Override
    public void update(){
        organisationalUnitsDisplayBox.getChildren().clear();
        localDatabase = (AdminLocalDatabase) controller.getDatabase();
        DataCollection<OrganisationalUnit> organisationalUnits = localDatabase.getOrganisationalUnits();
        DataCollection<Stock> stocks = localDatabase.getStocks();
        DataCollection<Asset> assets = localDatabase.getAssets();

        newOrganisationalUnitAssetNameComboBox.getItems().clear();
        for (Asset asset : assets){
            newOrganisationalUnitAssetNameComboBox.getItems().add(asset.getName());
        }

        String[] organisationNames = new String[organisationalUnits.size()];
        for (int i = 0; i < organisationalUnits.size(); i++){
            organisationNames[i] = organisationalUnits.get(i).getName();
        }

        for (OrganisationalUnit organisationalUnit : organisationalUnits){
            Stock stock = stocks.get(organisationalUnit.getId());
            int assetQuantity = stock.size();
            addOrganisationalUnitInfoBox(organisationalUnit.getId(), organisationalUnit.getName(), organisationalUnit.getBalance(), assetQuantity, stock);
        }
    }

    //TODO: Method to check if input is valid
    public boolean validateInfo(String name, String description){
        return true;
    }

    public <T extends IData> Response sendRequest(String action, T attachment, Class<T> attachmentType){
        return controller.sendRequest(action, attachment, attachmentType);
    }
}
