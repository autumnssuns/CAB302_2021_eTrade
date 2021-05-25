package client.guiControls.adminMain.organisationalUnitsController;

import client.IViewUnit;
import common.Exceptions.InvalidArgumentValueException;
import common.dataClasses.Item;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * A box to display the assets that belong to an organisational unit and can be interacted with.
 */
public class UnitAssetInfoBox extends HBox implements IViewUnit {
    private static int counter = 0; // TODO: Replace with identification using both asset ID and organisational unit ID
    private Item item;

    private Label nameLabel;
    private TextField quantityTextField;

    private Button editButton;
    private Button removeButton;

    /**
     * Initiates the box with asset information.
     * @param item The linked asset with quantity.
     */
    public UnitAssetInfoBox(Item item){
        super();

        this.item = item;

        initialize();
        load();
        counter++;
    }

    /**
     * Initiates the GUI components
     */
    @Override
    public void initialize() {
        this.setAlignment(Pos.CENTER);
        this.setPrefHeight(80);
        this.setPrefWidth(600);
        this.setLayoutX(41);
        this.setLayoutY(80);
        this.setLayoutY(80);
        this.setSpacing(20);

        nameLabel = new Label();
        nameLabel.setPrefWidth(250);
        nameLabel.setPrefHeight(30);
        nameLabel.setId("organisationalUnitAssetName" + counter);

        quantityTextField = new TextField();
        quantityTextField.setPrefWidth(100);
        quantityTextField.setPrefHeight(30);
        quantityTextField.setId("organisationalUnitAssetQuantity" + counter);

        editButton = new Button("Edit");
        editButton.setStyle("-fx-font-size:10");
        editButton.setPrefWidth(50);
        editButton.setPrefHeight(30);
        editButton.setOnAction(e -> startEdit());
        editButton.setId("organisationalUnitAssetEditButton" + counter);

        removeButton = new Button("Remove");
        removeButton.setPrefWidth(50);
        removeButton.setPrefHeight(30);
        removeButton.setStyle("-fx-font-size:10");
        removeButton.setOnAction(e -> removeEntry());
        removeButton.setId("organisationalUnitAssetRemoveButton" + counter);

        this.getChildren().addAll(nameLabel, quantityTextField, editButton, removeButton);
        disable();
    }

    /**
     * Loads the GUI component from the linked data
     */
    @Override
    public void load() {
        loadQuantityTextField();
        loadNameLabel();
    }

    /**
     * Update the asset's info by taking data from the text fields.
     */
    private void updateValues(){
        try {
            item.setQuantity(Integer.parseInt(quantityTextField.getText()));
        } catch (InvalidArgumentValueException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a label to display the asset's name
     */
    private void loadNameLabel(){
        nameLabel.setText(item.getName());
    }

    /**
     * Loads a text field to display the asset's quantity
     */
    private void loadQuantityTextField(){
        quantityTextField.setText(String.valueOf(item.getQuantity()));
    }

    /**
     * Disables the current entry from being edited.
     */
    private void disable(){
        nameLabel.setDisable(true);
        quantityTextField.setDisable(true);
    }

    /**
     * Enables the current entry to be edited.
     */
    private void enable(){
        nameLabel.setDisable(false);
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
        load();
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
