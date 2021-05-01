package client.guiControls.adminMain.organisationsController;

import client.guiControls.adminMain.assetsController.AssetInfoBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * A controller to control the ORGANISATIONS Page (which allows the admin to add / remove or edit organisations' information).
 */
public class OrganisationsController {
    @FXML
    VBox organisationsDisplayBox;
    @FXML
    AnchorPane displayPane;
    @FXML
    AnchorPane editPane;
    @FXML
    TextField nameTextField;
    @FXML
    TextField creditTextField;
    @FXML
    VBox organisationalAssetsBox;
    @FXML
    TextField newAssetNameTextField;
    @FXML
    TextField newAssetQuantityTextField;
    @FXML
    Button confirmButton;

    /**
     * Adds a new entry to the current display.
     * @param organisationId The ID of the asset.
     * @param name The name of the asset.
     * @param credit The username of the asset.
     */
    public void addOrganisationInfoBox(int organisationId, String name, float credit, int assetQuantity){
        OrganisationInfoBox organisationInfoBox = new OrganisationInfoBox(organisationId, name, credit, assetQuantity);
        organisationInfoBox.setOrganisationalAssetsBox(organisationalAssetsBox);
        organisationInfoBox.setController(this);

        organisationsDisplayBox.getChildren().add(organisationInfoBox);
    }

    /**
     * Adds a new entry, representing a new organisational unit.
     */
    public void startEditor(){
        editPane.setVisible(true);
        confirmButton.setOnAction(e -> confirmEditor());
    }

    /**
     * Edit an existing organisational unit.
     */
    public void startEditor(OrganisationInfoBox caller){
        editPane.setVisible(true);
        nameTextField.setText(caller.getName());
        creditTextField.setText(String.valueOf(caller.getCredit()));
        organisationalAssetsBox.getChildren().addAll(caller.getOrganisationalAssetsBox().getChildren());
        confirmButton.setOnAction(e -> confirmEditor(caller));
    }

    /**
     * Confirms the addition of a new organisation.
     */
    public void confirmEditor(){
        int organisationId = organisationsDisplayBox.getChildren().size();
        String name = nameTextField.getText();
        float credit = Float.parseFloat(creditTextField.getText());

        addOrganisationInfoBox(organisationId, name, credit, 0);

        closeEditor();
    }

    /**
     * Confirms the edit of an existing organisation.
     * @param caller The instance that calls for an editor.
     */
    public void confirmEditor(OrganisationInfoBox caller){
        String name = nameTextField.getText();
        float credit = Float.parseFloat(creditTextField.getText());

        caller.setName(name);
        caller.setCredit(credit);
        caller.reloadEntries();

        closeEditor();
    }

    /**
     * Close the editor.
     */
    public void closeEditor(){
        editPane.setVisible(false);
        clearEditor();
    }

    /**
     * Clears the text fields and display of the editor
     */
    public void clearEditor(){
        nameTextField.clear();
        creditTextField.clear();
        organisationalAssetsBox.getChildren().clear();
    }

    /**
     * Adds a new asset to the organisation in the editor.
     */
    public void addOrganisationalAssetInfoBox(){
        String assetName = newAssetNameTextField.getText();
        int quantity = Integer.parseInt(newAssetQuantityTextField.getText());

        OrganisationalAssetInfoBox organisationalAssetInfoBox = new OrganisationalAssetInfoBox(assetName, quantity);
        organisationalAssetsBox.getChildren().add(organisationalAssetInfoBox);
    }

    //TODO: Gets data from database
    public void update(){

    }

    //TODO: Method to check if input is valid
    public boolean validateInfo(String name, String description){
        return true;
    }
}
