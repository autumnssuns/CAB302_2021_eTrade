package client.guiControls.userMain.buyController;

import client.guiControls.adminMain.usersController.UserInfoBox;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class BuyController {
    @FXML
    VBox stockDisplayBox;

    @FXML Button firstButton;

    public void buttonTest(){
        System.out.println("Test");
    }

    public void enableFirstButton(){
        firstButton.setOnAction((event) -> {
            buttonTest();
        });
    }

    /**
     * Display the available stocks in the market
     * @param stockID the asset id
     * @param name name of the asset
     * @param quantity quantity of the asset
     */
    private void addStock(int stockID, String name, int quantity){
        BuyDisplay buyDisplay = new BuyDisplay(stockID, name , quantity);
        stockDisplayBox.getChildren().add(buyDisplay);
    }

    /**
     * Remove the display of the cart items
     */
    private void clearCart(){

    }



    //TODO: Gets data from database
    public void update(){

    }
}
