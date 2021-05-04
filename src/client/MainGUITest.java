package client;

import client.guiControls.adminMain.AdminGUITest;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;
import java.util.concurrent.TimeoutException;

/**
 * The main GUI test that runs through all expected GUI activities as a whole.
 */
public class MainGUITest extends ApplicationTest{

    /**
     * Starts the test by initialising a scene in a window.
     * @param primaryStage The stage where the scene is contained.
     * @throws Exception Required by javafx start() method.
     */
    @Start
    public void start(Stage primaryStage) throws Exception{
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("/client/guiControls/login/Login.fxml"));
        Scene scene = new Scene(root);

        String css = this.getClass().getResource("/client/guiControls/client.css").toExternalForm();
        scene.getStylesheets().add(css);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Clean up after each test.
     * @throws TimeoutException Required by FxToolKit methods.
     */
    @AfterEach
    public void tearDown() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
        FxToolkit.cleanupStages();
    }

    /**
     * Bulk testing all features in the admin GUI in one session.
     */
    @Test
    public void adminBulkTest(){
        AdminGUITest adminGUITest = new AdminGUITest();
        adminLoginSuccessTest();
        adminGUITest.adminMainControllerTest();
        adminGUITest.adminUsersControlTest();
        adminGUITest.adminAssetsControlTest();
        adminGUITest.adminOrganisationsControlTest();
    }

    /**
     * Test for failed login attempts.
     */
    @Test
    public void loginFailTest() {
        clickOn("#nameTextField");
        write("random");
        clickOn("#passwordField");
        write("abcdefg");
        clickOn("#loginButton");
        FxAssert.verifyThat("#statusLabel", LabeledMatchers.hasText("Incorrect username or password. Please try again!"));
    }

    /**
     * Test for successful admin login.
     */
    @Test
    public void adminLoginSuccessTest() {
        clickOn("#nameTextField");
        write("admin");
        clickOn("#passwordField");
        write("root");
        clickOn("#loginButton");
        FxAssert.verifyThat("#userLabel", LabeledMatchers.hasText("admin"));
    }

    /**
     * Test for successful user login.
     */
    @Test
    public void userLoginSuccessTest(){
        clickOn("#nameTextField");
        write("dan");
        clickOn("#passwordField");
        write("123");
        clickOn("#loginButton");
    }

    //TODO: Add more test suite
}