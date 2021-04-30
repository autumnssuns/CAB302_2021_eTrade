package client.guiControls.adminMain.organisationsController;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * A box to display organisation information and can be interacted with.
 * // TODO: Fix a bug where organisation info is not stored properly when added.
 */
public class OrganisationInfoBox extends HBox {
    private int organisationId;
    private String name;
    private float credit;
    private int assetQuantity;

    private Label idLabel;
    private Label nameLabel;
    private Label creditLabel;
    private Label assetQuantityLabel;
    private VBox organisationalAssetsBox = new VBox();

    private Button editButton;
    private Button removeButton;

    private OrganisationsController controller;

    /**
     * Initiates the box with organisation information.
     * @param organisationId The organisation's id.
     * @param name The name of the organisation.
     * @param credit The credit of the organisation.
     */
    public OrganisationInfoBox(int organisationId, String name, float credit, int assetQuantity){
        super();
        this.setAlignment(Pos.CENTER);
        this.setPrefHeight(80);
        this.setPrefWidth(700);
        //this.setLayoutX(41);
        this.setLayoutY(260);
        this.setSpacing(20);

        this.organisationId = organisationId;
        this.name = name;
        this.credit = credit;
        this.assetQuantity = assetQuantity;

        initiateNodes();

        this.getChildren().addAll(idLabel, nameLabel, creditLabel, assetQuantityLabel, editButton, removeButton);
    }

    /**
     * Draw the nodes displaying the organisation's info.
     */
    private void initiateNodes(){
        createIdLabel();
        createNameLabel();
        createCreditLabel();
        createAssetQuantityLabel();
        createEditButton();
        createRemoveButton();
    }

    /**
     * Creates a label to display the organisation's id.
     */
    private void createIdLabel(){
        idLabel = new Label(String.valueOf(organisationId));
        idLabel.getStyleClass().add("blackLabel");
        idLabel.setAlignment(Pos.CENTER);
        idLabel.setPrefWidth(50);
        idLabel.setPrefHeight(80);
    }

    /**
     * Creates a text field to display the organisation's name.
     */
    private void createNameLabel(){
        nameLabel = new Label(name);
        nameLabel.getStyleClass().add("blackLabel");
        nameLabel.setPrefWidth(200);
        nameLabel.setPrefHeight(30);
    }

    /**
     * Reload the box using the stored organisation's info.
     */
    public void reloadEntries(){
        nameLabel.setText(name);
        creditLabel.setText(String.valueOf(credit));
    }

    /**
     * Creates a text field to display the organisation's description.
     */
    private void createCreditLabel(){
        creditLabel = new Label(String.valueOf(credit));
        creditLabel.getStyleClass().add("blackLabel");
        creditLabel.setPrefWidth(100);
        creditLabel.setPrefHeight(30);
    }

    /**
     * Creates a text field to display the organisation's description.
     */
    private void createAssetQuantityLabel(){
        assetQuantityLabel = new Label(String.valueOf(assetQuantity));
        assetQuantityLabel.getStyleClass().add("blackLabel");
        assetQuantityLabel.setPrefWidth(100);
        assetQuantityLabel.setPrefHeight(30);
    }

    /**
     * Creates a button that allows the admin to edit an organisation's info.
     */
    private void createEditButton(){
        editButton = new Button("Edit");
        editButton.setPrefWidth(100);
        editButton.setPrefHeight(30);
        editButton.setOnAction(e -> startEdit());
    }

    /**
     * Creates a button that allows the admin to remove an organisation.
     */
    private void createRemoveButton(){
        removeButton = new Button("Remove");
        removeButton.setPrefWidth(100);
        removeButton.setPrefHeight(30);
        removeButton.setOnAction(e -> removeEntry());
    }

    /**
     * Begins editing the current entry.
     */
    private void startEdit(){
        controller.startEditor(this);
    }

    /**
     * Set the controller for this box.
     * @param controller The controller for this box.
     */
    public void setController(OrganisationsController controller){
        this.controller = controller;
    }

    /**
     * Returns the name of the organisation.
     * @return The name of the organisation.
     */
    public String getName(){
        return name;
    }

    /**
     * Returns the credit of the organisation.
     * @return The credit of the organisation.
     */
    public float getCredit(){
        return credit;
    }

    /**
     * Returns the Assets Box of the current organisation.
     * @return The Assets Box of the current organisation.
     */
    public VBox getOrganisationalAssetsBox(){
        System.out.println(organisationalAssetsBox);
        System.out.println(organisationalAssetsBox.getChildren().size());
        return this.organisationalAssetsBox;
    }

    /**
     * Sets a new assets box for the organisation.
     * @param newBox The new assets box.
     */
    public void setOrganisationalAssetsBox(VBox newBox){
        organisationalAssetsBox.getChildren().addAll(newBox.getChildren());
        System.out.println(organisationalAssetsBox);
        System.out.println(organisationalAssetsBox.getChildren().size());
    }

    /**
     * Removes the current entry.
     */
    private void removeEntry() {
        ((VBox) this.getParent()).getChildren().remove(this);
    }

    /**
     * Sets the name of the current organisation to a new value.
     * @param name The new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the credit of the current organisation to a new value.
     * @param credit The new credit.
     */
    public void setCredit(float credit){
        this.credit=credit;
    }
}