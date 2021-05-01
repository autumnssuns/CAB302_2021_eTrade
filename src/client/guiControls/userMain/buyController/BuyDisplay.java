package client.guiControls.userMain.buyController;

import client.guiControls.adminMain.organisationsController.OrganisationsController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BuyDisplay extends HBox {
    private int stockID;
    private String name;
    private int quantity;
    // buttons
    private Label idLabel;
    private Label nameLabel;
    private Label creditLabel;
    private Label assetQuantityLabel;
    private VBox organisationalAssetsBox = new VBox();

    private Button editButton;
    private Button removeButton;

    private BuyController controller;
    /**
     * Initiates the box with asset information.
     * @param stockID The asset's id.
     * @param name The name of the asset.
     * @param quantity The quantity.
     */
    public BuyDisplay(int stockID, String name, int quantity){
        super();
        this.setAlignment(Pos.CENTER);
        this.setPrefHeight(80);
        this.setPrefWidth(700);
        //this.setLayoutX(41);
        this.setLayoutY(260);
        this.setSpacing(20);

        this.stockID = stockID;
        this.name = name;
        this.quantity = quantity;


        initiateNodes();

        this.getChildren().addAll(idLabel, nameLabel, creditLabel, assetQuantityLabel, editButton, removeButton);
    }

    /**
     * Draw the nodes displaying the asset's info.
     */
    private void initiateNodes(){
        createIdLabel();
        createNameLabel();
        createCreditLabel();
        createAssetQuantityLabel();
        createRemoveButton();
    }

    /**
     * Creates a label to display the asset's id.
     */
    private void createIdLabel(){
        idLabel = new Label(String.valueOf(stockID));
        idLabel.getStyleClass().add("blackLabel");
        idLabel.setAlignment(Pos.CENTER);
        idLabel.setPrefWidth(50);
        idLabel.setPrefHeight(80);
    }

    /**
     * Creates a text field to display the asset's name.
     */
    private void createNameLabel(){
        nameLabel = new Label(name);
        nameLabel.getStyleClass().add("blackLabel");
        nameLabel.setPrefWidth(200);
        nameLabel.setPrefHeight(30);
    }

    /**
     * Reload the box using the stored asset's info.
     */
    public void reloadEntries(){
        nameLabel.setText(name);
        creditLabel.setText(String.valueOf(quantity));
    }

    /**
     * Creates a text field to display the asset's description.
     */
    private void createCreditLabel(){
        creditLabel = new Label(String.valueOf(quantity));
        creditLabel.getStyleClass().add("blackLabel");
        creditLabel.setPrefWidth(100);
        creditLabel.setPrefHeight(30);
    }

    /**
     * Creates a text field to display the asset's description.
     */
    private void createAssetQuantityLabel(){
        assetQuantityLabel = new Label(String.valueOf(quantity));
        assetQuantityLabel.getStyleClass().add("blackLabel");
        assetQuantityLabel.setPrefWidth(100);
        assetQuantityLabel.setPrefHeight(30);
    }

    /**
     * Creates a button that allows the users to remove the stock display
     */
   /* private void createRemoveButton(){
        removeButton = new Button("Remove");
        removeButton.setPrefWidth(100);
        removeButton.setPrefHeight(30);
        removeButton.setOnAction(e -> );
    }*/

    /**
     * Creates a button that allows the admin to remove an asset.
     */
    private void createRemoveButton(){
        removeButton = new Button("Remove");
        removeButton.setPrefWidth(100);
        removeButton.setPrefHeight(30);
        removeButton.setOnAction(e -> removeEntry());
    }


    /**
     * Set the controller for this box.
     * @param controller The controller for this box.
     */
    public void setController(BuyController controller){
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
     * Removes the current cart item.
     */
    private void removeEntry() {
        ((VBox) this.getParent()).getChildren().remove(this);
    }

    /**
     * Sets the quantity of the stock item to the new value
     * @param quantity The new quantity.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
