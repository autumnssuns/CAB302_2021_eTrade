package client.guiControls.adminMain.assetsController;

import client.IViewUnit;
import client.Styler;
import client.guiControls.adminMain.AdminMainController;
import common.Exceptions.InvalidArgumentValueException;
import common.Request;
import common.Response;
import common.dataClasses.Asset;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * A box to display asset information and can be interacted with.
 */
public class AssetInfoBox extends HBox implements IViewUnit {
    private AdminMainController controller;
    private Asset asset;

    private Label idLabel;
    private TextField nameTextField;
    private TextField descriptionTextField;

    private Button editButton;
    private Button removeButton;


    /**
     * Initiate the view with a linked asset.
     * @param asset The linked asset.
     */
    public AssetInfoBox(Asset asset){
        super();
        this.asset = asset;

        initialize();
        load();
    }

    /**
     * Initiates all the GUi components
     */
    @Override
    public void initialize() {
        this.setAlignment(Pos.CENTER);
        this.setPrefHeight(80);
        this.setPrefWidth(1238);
        this.setLayoutX(20);
        this.setLayoutY(200);
        this.setSpacing(3);

        idLabel = new Label();
        idLabel.getStyleClass().add(Styler.STANDARD_ASSET_NAME_BOX.styleClass());
        idLabel.setAlignment(Pos.CENTER);
        idLabel.setPrefWidth(100);
        idLabel.setPrefHeight(80);

        nameTextField = new TextField();
        nameTextField.setPrefWidth(Styler.STANDARD_ASSET_NAME_BOX.width());
        nameTextField.setPrefHeight(Styler.STANDARD_ASSET_BOX.height());
        nameTextField.setId("assetName" + asset.getId());

        descriptionTextField = new TextField();
        descriptionTextField.setPrefWidth(450);
        descriptionTextField.setPrefHeight(30);
        descriptionTextField.setId("assetDescription" + asset.getId());

        editButton = new Button("Edit");
        editButton.setPrefWidth(100);
        editButton.setPrefHeight(30);
        editButton.setOnAction(e -> startEdit());
        editButton.setId("assetEditButton" + asset.getId());

        removeButton = new Button("Remove");
        removeButton.setPrefWidth(100);
        removeButton.setPrefHeight(30);
        removeButton.setOnAction(e -> {
            try {
                removeEntry();
            } catch (InvalidArgumentValueException invalidArgumentValueException) {
                invalidArgumentValueException.printStackTrace();
            }
        });
        removeButton.setId("assetRemoveButton" + asset.getId());

        this.getChildren().addAll(idLabel, nameTextField, descriptionTextField, editButton, removeButton);
        disable();
    }

    /**
     * Loads the data from the linked object to the GUI components
     */
    @Override
    public void load() {
        loadIdLabel();
        loadNameTextField();
        loadDescriptionTextField();
    }

    /**
     * Sets the controller for this component.
     * @param controller The controller.
     */
    public void setController(AdminMainController controller){
        this.controller = controller;
    }

    /**
     * Update the asset's info by taking data from the text fields.
     */
    private void updateValues(){
        try {
            asset.setName(nameTextField.getText());
        } catch (InvalidArgumentValueException e) {
            e.printStackTrace();
        }
        asset.setDescription(descriptionTextField.getText());
    }

    /**
     * Loads a label to display the asset's id.
     */
    private void loadIdLabel(){
        idLabel.setText(String.valueOf(asset.getId()));
    }

    /**
     * Loads a text field to display the asset's name.
     */
    private void loadNameTextField(){
        nameTextField.setText(asset.getName());
    }

    /**
     * Loads a text field to display the asset's description.
     */
    private void loadDescriptionTextField(){
        descriptionTextField.setText(asset.getDescription());
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
        editButton.setOnAction(e -> {
            try {
                confirmEdit();
            } catch (InvalidArgumentValueException invalidArgumentValueException) {
                invalidArgumentValueException.printStackTrace();
            }
        });
        removeButton.setText("Cancel");
        removeButton.setOnAction(e -> cancelEdit());
    }

    /**
     * Confirms the changes to the current entry.
     */
    private void confirmEdit() throws InvalidArgumentValueException {
        disable();
        updateValues();
        Response response = controller.sendRequest(Request.ActionType.UPDATE, asset, Request.ObjectType.ASSET);
        editButton.setText("Edit");
        editButton.setOnAction(e -> startEdit());
        removeButton.setText("Remove");
        removeButton.setOnAction(e -> {
            try {
                removeEntry();
            } catch (InvalidArgumentValueException invalidArgumentValueException) {
                invalidArgumentValueException.printStackTrace();
            }
        });
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
        removeButton.setOnAction(e -> {
            try {
                removeEntry();
            } catch (InvalidArgumentValueException invalidArgumentValueException) {
                invalidArgumentValueException.printStackTrace();
            }
        });
    }

    /**
     * Removes the current entry.
     */
    private void removeEntry() throws InvalidArgumentValueException {
        Response response = controller.sendRequest(Request.ActionType.DELETE, asset, Request.ObjectType.ASSET);
        if (response.isAccepted()){
            ((VBox) this.getParent()).getChildren().remove(this);
        }
    }
}
