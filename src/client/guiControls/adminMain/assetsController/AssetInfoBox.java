package client.guiControls.adminMain.assetsController;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * A box to display asset information and can be interacted with.
 */
public class AssetInfoBox extends HBox {
    private int assetId;
    private String name;
    private String description;

    private Label idLabel;
    private TextField nameTextField;
    private TextField descriptionTextField;

    private Button editButton;
    private Button removeButton;

    /**
     * Initiates the box with asset information.
     * @param assetId The asset's id.
     * @param name The name of the asset.
     * @param description The description of the asset.
     */
    public AssetInfoBox(int assetId, String name, String description){
        super();
        this.setAlignment(Pos.CENTER);
        this.setPrefHeight(80);
        this.setPrefWidth(1363);
        this.setLayoutX(41);
        this.setLayoutY(260);
        this.setSpacing(20);

        this.assetId = assetId;
        this.name = name;
        this.description = description;

        initiateNodes();

        this.getChildren().addAll(idLabel, nameTextField, descriptionTextField, editButton, removeButton);
        disable();
    }

    /**
     * Draw the nodes displaying the asset's info.
     */
    private void initiateNodes(){
        createIdLabel();
        createNameTextField();
        createDescriptionTextField();
        createEditButton();
        createRemoveButton();
    }

    /**
     * Update the asset's info by taking data from the text fields.
     */
    private void updateValues(){
        name = nameTextField.getText();
        description = descriptionTextField.getText();
    }

    /**
     * Reload the box using the stored asset's info.
     */
    private void reloadEntries(){
        nameTextField.setText(name);
        descriptionTextField.setText(description);
    }

    /**
     * Creates a label to display the asset's id.
     */
    private void createIdLabel(){
        idLabel = new Label(String.valueOf(assetId));
        idLabel.getStyleClass().add("blackLabel");
        idLabel.setAlignment(Pos.CENTER);
        idLabel.setPrefWidth(100);
        idLabel.setPrefHeight(80);
    }

    /**
     * Creates a text field to display the asset's name.
     */
    private void createNameTextField(){
        nameTextField = new TextField(name);
        nameTextField.setPrefWidth(300);
        nameTextField.setPrefHeight(30);
        nameTextField.setId("assetName" + assetId);
    }

    /**
     * Creates a text field to display the asset's description.
     */
    private void createDescriptionTextField(){
        descriptionTextField = new TextField(description);
        descriptionTextField.setPrefWidth(700);
        descriptionTextField.setPrefHeight(30);
        descriptionTextField.setId("assetDescription" + assetId);
    }

    /**
     * Creates a button that allows the admin to edit a asset's info.
     */
    private void createEditButton(){
        editButton = new Button("Edit");
        editButton.setPrefWidth(100);
        editButton.setPrefHeight(30);
        editButton.setOnAction(e -> startEdit());
        editButton.setId("assetEditButton" + assetId);
    }

    /**
     * Creates a button that allows the admin to remove an asset.
     */
    private void createRemoveButton(){
        removeButton = new Button("Remove");
        removeButton.setPrefWidth(100);
        removeButton.setPrefHeight(30);
        removeButton.setOnAction(e -> removeEntry());
        removeButton.setId("assetRemoveButton" + assetId);
    }

    /**
     * Disables the current entry from being edited.
     */
    private void disable(){
        nameTextField.setDisable(true);
        descriptionTextField.setDisable(true);
    }

    /**
     * Enables the current entry to be edited.
     */
    private void enable(){
        nameTextField.setDisable(false);
        descriptionTextField.setDisable(false);
        editButton.setText("Confirm");
        editButton.setOnAction(e -> startEdit());
    }

    /**
     * Begins editing the current entry.
     */
    private void startEdit(){
        enable();
        editButton.setText("Confirm");
        editButton.setOnAction(e -> confirmEdit());
        removeButton.setText("Cancel");
        removeButton.setOnAction(e -> cancelEdit());
    }

    /**
     * Confirms the changes to the current entry.
     */
    private void confirmEdit() {
        disable();
        updateValues();
        editButton.setText("Edit");
        editButton.setOnAction(e -> startEdit());
        removeButton.setText("Remove");
        removeButton.setOnAction(e -> removeEntry());
    }

    /**
     * Cancels the changes to the current entry.
     */
    private void cancelEdit(){
        disable();
        reloadEntries();
        editButton.setText("Edit");
        editButton.setOnAction(e -> startEdit());
        removeButton.setText("Remove");
        removeButton.setOnAction(e -> removeEntry());
    }

    /**
     * Removes the current entry.
     */
    private void removeEntry() {
        ((VBox) this.getParent()).getChildren().remove(this);
    }
}
