package client.guiControls.mainUser.buyController;

import client.data.Session;
import client.data.sessionalClasses.Cart;
import client.data.sessionalClasses.CartItem;
import client.data.sessionalClasses.Item;
import client.data.sessionalClasses.Stock;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class BuyController {
    @FXML Button firstButton;

    public void buttonTest(){
        System.out.println("Test");
    }

    public void enableFirstButton(){
        firstButton.setOnAction((event) -> {
            buttonTest();
        });
    }
}
