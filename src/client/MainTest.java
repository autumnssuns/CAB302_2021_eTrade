package client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;

import java.util.concurrent.TimeoutException;

import static org.junit.Assert.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

/**
 * Robot GUI Tester
 */
public class MainTest extends ApplicationTest{

    @Override
    public void start(Stage primaryStage) throws Exception{
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("guiControls/login/Login.fxml"));
        Scene scene = new Scene(root);

        String css = this.getClass().getResource("guiControls/client.css").toExternalForm();
        scene.getStylesheets().add(css);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @AfterEach
    public void tearDown() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
        FxToolkit.cleanupStages();
    }

    @Test
    public void loginFailTest() {
        clickOn("#nameTextField");
        write("random");
        clickOn("#passwordField");
        write("abcdefg");
        clickOn("#loginButton");
        FxAssert.verifyThat("#statusLabel", LabeledMatchers.hasText("Incorrect username or password. Please try again!"));
    }

    @Test
    public void adminLoginSuccessTest() {
        clickOn("#nameTextField");
        write("admin");
        clickOn("#passwordField");
        write("root");
        clickOn("#loginButton");
        FxAssert.verifyThat("#userLabel", LabeledMatchers.hasText("admin"));
    }

    @Test
    public void adminMainControllerTest(){
        adminLoginSuccessTest();
        clickOn("#assetsButton");
        clickOn("#usersButton");
        clickOn("#organisationUnitsButton");
    }

    //TODO: Add more test suite
}