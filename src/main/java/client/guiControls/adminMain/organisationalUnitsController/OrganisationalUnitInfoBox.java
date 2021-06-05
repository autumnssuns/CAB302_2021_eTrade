package client.guiControls.adminMain.organisationalUnitsController;

import client.IViewUnit;
import common.Exceptions.InvalidArgumentValueException;
import common.Request;
import common.Response;
import common.dataClasses.OrganisationalUnit;
import common.dataClasses.Stock;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * A box to display organisational unit information and can be interacted with.
 * // TODO: Fix a bug where organisational unit info is not stored properly when added.
 */
public class OrganisationalUnitInfoBox extends HBox implements IViewUnit {
    private OrganisationalUnit unit;
    private Stock stock;

    private Label idLabel;
    private Label nameLabel;
    private Label creditLabel;
    private Label assetQuantityLabel;
    private final VBox organisationalUnitAssetsBox = new VBox();

    private OrganisationalUnitsController controller;

    /**
     * Initiates a view with a linked organisational unit and stock
     * @param unit The linked unit
     * @param stock The linked stock
     */
    public OrganisationalUnitInfoBox(OrganisationalUnit unit, Stock stock){
        super();

        this.unit = unit;
        this.stock = stock;

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
        this.setPrefWidth(700);
        //this.setLayoutX(41);
        this.setLayoutY(200);
        this.setSpacing(5);

        idLabel = new Label();
        idLabel.getStyleClass().add("blackLabel");
        idLabel.setAlignment(Pos.CENTER);
        idLabel.setPrefWidth(30);
        idLabel.setPrefHeight(80);

        nameLabel = new Label();
        nameLabel.getStyleClass().add("blackLabel");
        nameLabel.setPrefWidth(150);
        nameLabel.setPrefHeight(30);
        nameLabel.setId("organisationalUnitName" + unit.getId());

        creditLabel = new Label();
        creditLabel.getStyleClass().add("blackLabel");
        creditLabel.setPrefWidth(100);
        creditLabel.setPrefHeight(30);
        creditLabel.setId("organisationalUnitCredit" + unit.getId());

        assetQuantityLabel = new Label();
        assetQuantityLabel.getStyleClass().add("blackLabel");
        assetQuantityLabel.setPrefWidth(100);
        assetQuantityLabel.setPrefHeight(30);

        Button editButton = new Button("Edit");
        editButton.setStyle("-fx-font-size:10");
        editButton.setPrefWidth(50);
        editButton.setPrefHeight(30);
        editButton.setOnAction(e -> startEdit());
        editButton.setId("organisationalUnitEditButton" + unit.getId());

        Button removeButton = new Button("Remove");
        removeButton.setStyle("-fx-font-size:10");
        removeButton.setPrefWidth(50);
        removeButton.setPrefHeight(30);
        removeButton.setOnAction(e -> {
            try {
                removeEntry();
            } catch (InvalidArgumentValueException invalidArgumentValueException) {
                invalidArgumentValueException.printStackTrace();
            }
        });
        removeButton.setId("organisationalUnitRemoveButton" + unit.getId());

        this.getChildren().addAll(idLabel, nameLabel, creditLabel, assetQuantityLabel, editButton, removeButton);
    }

    /**
     * Loads the linked data to the GUI components
     */
    @Override
    public void load() {
        loadIdLabel();
        loadNameLabel();
        loadAssetQuantityLabel();
        loadCreditLabel();
    }

    /**
     * Loads a label to display the organisational unit's id.
     */
    private void loadIdLabel(){
        idLabel.setText(String.valueOf(unit.getId()));
    }

    /**
     * Loads a label to display the organisational unit's name.
     */
    private void loadNameLabel(){
        nameLabel.setText(unit.getName());

    }

    /**
     * Loads a text field to display the organisational unit's description.
     */
    private void loadCreditLabel(){
        creditLabel.setText(String.valueOf(unit.getBalance()));
    }

    /**
     * Loads a text field to display the organisational unit's description.
     */
    private void loadAssetQuantityLabel(){
        assetQuantityLabel.setText(String.valueOf(stock.size()));
    }

    /**
     * Reload the box using the stored organisational unit's info.
     */
    public void reloadEntries(){
        nameLabel.setText(unit.getName());
        creditLabel.setText(String.valueOf(unit.getBalance()));
    }

    //NOTE: Get info from display
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
    public void setController(OrganisationalUnitsController controller){
        this.controller = controller;
    }

    /**
     * Returns the id of the organisational unit.
     * @return The id of the organisational unit.
     */
    public int getUnitId(){
        return unit.getId();
    }

    /**
     * Returns the name of the organisational unit.
     * @return The name of the organisational unit.
     */
    public String getName(){
        return unit.getName();
    }

    /**
     * Returns the credit of the organisational unit.
     * @return The credit of the organisational unit.
     */
    public float getCredit(){
        return unit.getBalance();
    }

    /**
     * Returns the stock linked to the current organisational unit.
     * @return The stock linked to the current organisational unit.
     */
    public Stock getStock(){
        return stock;
    }

    /**
     * Sets a new assets box for the organisational unit.
     * @param newBox The new assets box.
     */
    public void setOrganisationalUnitAssetsBox(VBox newBox){
        organisationalUnitAssetsBox.getChildren().addAll(newBox.getChildren());
        System.out.println(organisationalUnitAssetsBox);
        System.out.println(organisationalUnitAssetsBox.getChildren().size());
    }

    /**
     * Removes the current entry.
     */
    private void removeEntry() throws InvalidArgumentValueException {
        Response response = controller.sendRequest(Request.ActionType.DELETE, unit, Request.ObjectType.ORGANISATIONAL_UNIT);
        if (response.isFulfilled()){
            ((VBox) this.getParent()).getChildren().remove(this);
        }
    }

    /**
     * Sets the name of the current organisational unit to a new value.
     * @param name The new name.
     */
    public void setName(String name) {
        unit.setName(name);
    }

    /**
     * Sets the credit of the current organisational unit to a new value.
     * @param credit The new credit.
     */
    public void setCredit(float credit) throws InvalidArgumentValueException {
        unit.setBalance(credit);
    }

    /**
     * Sets the stock of the organisational unit
     * @param stock The stock of the linked organisational unit
     */
    public void setStock(Stock stock) {
        this.stock = stock;
    }
}