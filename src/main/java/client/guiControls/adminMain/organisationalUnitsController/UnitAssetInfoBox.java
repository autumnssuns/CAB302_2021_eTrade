package client.guiControls.adminMain.organisationalUnitsController;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * A box to display the assets that belong to an organisational unit and can be interacted with.
 */
public class UnitAssetInfoBox extends HBox {
    private static int counter = 0; // TODO: Replace with identification using both asset ID and organisational unit ID
    private String name;
    private int quantity;

    private TextField nameTextField;
    private TextField quantityTextField;

    private Button editButton;
    private Button removeButton;

    /**
     * Initiates the box with asset information.
     * @param name The name of the asset.
     * @param quantity The description of the asset.
     */
    public UnitAssetInfoBox(String name, int quantity){
        super();
        this.setAlignment(Pos.CENTER);
        this.setPrefHeight(80);
        this.setPrefWidth(600);
        this.setLayoutX(41);
        this.setLayoutY(80);
        this.setLayoutY(80);
        this.setSpacing(20);

        this.name = name;
        this.quantity = quantity;

        initiateNodes();

        this.getChildren().addAll(nameTextField, quantityTextField, editButton, removeButton);
        disable();
        counter++;
    }

    /**
     * Draw the nodes displaying the asset's info.
     */
    private void initiateNodes(){
        createNameTextField();
        createQuantityTextField();
        createEditButton();
        createRemoveButton();
    }

    /**
     * Update the asset's info by taking data from the text fields.
     */
    private void updateValues(){
        name = nameTextField.getText();
        quantity = Integer.parseInt(quantityTextField.getText());
    }

    /**
     * Reload the box using the stored asset's info.
     */
    private void reloadEntries(){
        nameTextField.setText(name);
        quantityTextField.setText(String.valueOf(quantity));
    }

    /**
     * Creates a text field to display the asset's name.
     */
    private void createNameTextField(){
        nameTextField = new TextField(name);
        nameTextField.setPrefWidth(250);
        nameTextField.setPrefHeight(30);
        nameTextField.setId("organisationalUnitAssetName" + counter);
    }

    /**
     * Creates a text field to display the asset's description.
     */
    private void createQuantityTextField(){
        quantityTextField = new TextField(String.valueOf(quantity));
        quantityTextField.setPrefWidth(100);
        quantityTextField.setPrefHeight(30);
        quantityTextField.setId("organisationalUnitAssetQuantity" + counter);
    }

    /**
     * Creates a button that allows the admin to edit a asset's info.
     */
    private void createEditButton(){
        editButton = new Button("Edit");
        editButton.setStyle("-fx-font-size:10");
        editButton.setPrefWidth(50);
        editButton.setPrefHeight(30);
        editButton.setOnAction(e -> startEdit());
        editButton.setId("organisationalUnitAssetEditButton" + counter);
    }

    /**
     * Creates a button that allows the admin to remove an asset.
     */
    private void createRemoveButton(){
        removeButton = new Button("Remove");
        removeButton.setPrefWidth(50);
        removeButton.setPrefHeight(30);
        removeButton.setStyle("-fx-font-size:10");
        removeButton.setOnAction(e -> removeEntry());
        removeButton.setId("organisationalUnitAssetRemoveButton" + counter);
    }

    /**
     * Disables the current entry from being edited.
     */
    private void disable(){
        nameTextField.setDisable(true);
        quantityTextField.setDisable(true);
    }

    /**
     * Enables the current entry to be edited.
     */
    private void enable(){
        nameTextField.setDisable(false);
        quantityTextField.setDisable(false);
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
