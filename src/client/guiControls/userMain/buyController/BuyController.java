package client.guiControls.userMain.buyController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
