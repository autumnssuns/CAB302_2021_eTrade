package client.guiControls.adminMain;

import client.MainGUITest;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.ComboBoxMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;

import java.util.concurrent.TimeoutException;


/**
 * Robot GUI Tester.
 */
public class AdminGUITest extends ApplicationTest {
    MainGUITest mainGUITest = new MainGUITest();

    @Start
    public void start(Stage primaryStage) throws Exception{
        mainGUITest.start(primaryStage);
    }

    @BeforeEach
    public void setUp(){
        mainGUITest.adminLoginSuccessTest();
    }

    @AfterEach
    public void tearDown() throws TimeoutException {
        mainGUITest.tearDown();
    }

    @Test
    public void adminMainControllerTest(){
        clickOn("#assetsButton");
        clickOn("#usersButton");
        clickOn("#organisationUnitsButton");
    }

    @Test
    public void adminLogoutTest(){
        clickOn("#logoutButton");
        FxAssert.verifyThat("#loginButton", NodeMatchers.isNotNull());
    }

    @Test
    public void adminAssetsControlTest(){
        clickOn("#assetsButton");
        // Add
        for (int i = 0; i < 2; i++){
            clickOn("#newNameTextField");
            write("Asset " + i);
            clickOn("#newDescriptionTextField");
            write("This is asset " + i);
            clickOn("#newAssetButton");
        }

        // Edit & Remove
        clickOn("#assetEditButton1");
        doubleClickOn("#assetName1");
        doubleClickOn("#assetName1");
        write("Still asset 1");
        doubleClickOn("#assetDescription1");
        doubleClickOn("#assetDescription1");
        write("This is still asset 1");
        clickOn("#assetEditButton1");
        FxAssert.verifyThat("#assetName1", TextInputControlMatchers.hasText("Still asset 1"));
        FxAssert.verifyThat("#assetDescription1", TextInputControlMatchers.hasText("This is still asset 1"));
        clickOn("#assetRemoveButton1");

        // Edit & Cancel
        clickOn("#assetEditButton0");
        doubleClickOn("#assetName0");
        doubleClickOn("#assetName0");
        write("Still asset 0");
        doubleClickOn("#assetDescription0");
        doubleClickOn("#assetDescription0");
        write("This is still asset 0");
        clickOn("#assetRemoveButton0");
        FxAssert.verifyThat("#assetName0", TextInputControlMatchers.hasText("Asset 0"));
    }

    @Test
    public void adminUsersControlTest(){
        clickOn("#usersButton");
        for (int i = 0; i < 2; i++){
            clickOn("#newUserNameTextField");
            write("User " + i);
            clickOn("#newUsernameTextField");
            write("user" + i);
            clickOn("#newPasswordField");
            write("password " + i);
            clickOn("#newOrganisationSelectionBox");
            write("tempOrganisation");
            clickOn("#newRoleSelectionBox");
            write("user");
            clickOn("#addNewUserButton");
        }

        // Edit & Remove
        clickOn("#userEditButton1");
        doubleClickOn("#userName1");
        doubleClickOn("#userName1");
        write("New Name");
        doubleClickOn("#username1");
        doubleClickOn("#username1");
        write("newUsername");
        doubleClickOn("#password1");
        doubleClickOn("#password1");
        write("newPassword");
        doubleClickOn("#userOrganisation1");
        doubleClickOn("#userOrganisation1");
        write("newOrganisation");
        doubleClickOn("#userRole1");
        doubleClickOn("#userRole1");
        write("admin");
        clickOn("#userEditButton1");
        FxAssert.verifyThat("#userName1", TextInputControlMatchers.hasText("New Name"));
        FxAssert.verifyThat("#username1", TextInputControlMatchers.hasText("newUsername"));
        FxAssert.verifyThat("#password1", TextInputControlMatchers.hasText("newPassword"));
        FxAssert.verifyThat("#userOrganisation1", ComboBoxMatchers.hasSelectedItem("newOrganisation"));
        FxAssert.verifyThat("#userRole1", ComboBoxMatchers.hasSelectedItem("admin"));
        clickOn("#userRemoveButton1");

        // Edit & Cancel
        clickOn("#userEditButton0");
        doubleClickOn("#userName0");
        doubleClickOn("#userName0");
        write("Still Person 0");
        clickOn("#userRemoveButton0");
        FxAssert.verifyThat("#userName0", TextInputControlMatchers.hasText("User 0"));
    }


    @Test
    public void adminOrganisationsControlTest(){
        clickOn("#organisationUnitsButton");
        clickOn("#addNewOrganisationButton");
        clickOn("#organisationNameTextField");
        write("tempOrganisation");
        clickOn("#creditTextField");
        write("500");
        FxAssert.verifyThat("#organisationEditPane", NodeMatchers.isVisible());

        // Add asset to organisation
        for (int i = 0 ; i < 2; i++){
            clickOn("#newOrganisationAssetNameTextField");
            write("Asset " + i);
            clickOn("#newOrganisationAssetQuantityTextField");
            write("99");
            clickOn("#addNewOrganisationAssetButton");
        }

        // Edit & Remove asset from organisation
        clickOn("#organisationAssetEditButton1");
        clickOn("#organisationAssetName1");
        doubleClickOn("#organisationAssetName1");
        write("New Asset");
        clickOn("#organisationAssetQuantity1");
        doubleClickOn("#organisationAssetQuantity1");
        write("80");
        clickOn("#organisationAssetEditButton1");
        FxAssert.verifyThat("#organisationAssetName1", TextInputControlMatchers.hasText("New Asset"));
        FxAssert.verifyThat("#organisationAssetQuantity1", TextInputControlMatchers.hasText("80"));
        clickOn("#organisationAssetRemoveButton1");

        // Edit & Cancel asset from organisation
        clickOn("#organisationAssetEditButton0");
        clickOn("#organisationAssetName0");
        doubleClickOn("#organisationAssetName0");
        write("New Asset");
        clickOn("#organisationAssetQuantity0");
        doubleClickOn("#organisationAssetQuantity0");
        write("80");
        clickOn("#organisationAssetRemoveButton0");
        FxAssert.verifyThat("#organisationAssetName0", TextInputControlMatchers.hasText("Asset 0"));
        FxAssert.verifyThat("#organisationAssetQuantity0", TextInputControlMatchers.hasText("99"));

        clickOn("#confirmOrganisationButton");
        FxAssert.verifyThat("#organisationName0", LabeledMatchers.hasText("tempOrganisation"));
        FxAssert.verifyThat("#organisationEditPane", NodeMatchers.isInvisible());

        // Add blank organisation with no asset
        clickOn("#addNewOrganisationButton");
        clickOn("#organisationNameTextField");
        write("blankOrganisation");
        clickOn("#creditTextField");
        write("500");
        FxAssert.verifyThat("#organisationEditPane", NodeMatchers.isVisible());
        clickOn("#confirmOrganisationButton");
        FxAssert.verifyThat("#organisationName1", LabeledMatchers.hasText("blankOrganisation"));
        FxAssert.verifyThat("#organisationEditPane", NodeMatchers.isInvisible());

        // Edit the blank organisation
        clickOn("#organisationEditButton1");
        FxAssert.verifyThat("#organisationNameTextField", TextInputControlMatchers.hasText("blankOrganisation"));
        FxAssert.verifyThat("#creditTextField", TextInputControlMatchers.hasText("500.0"));
        tripleClickOn("#organisationNameTextField");
        write("stillBlankOrganisation");
        tripleClickOn("#creditTextField");
        write("300");
        clickOn("#confirmOrganisationButton");
        FxAssert.verifyThat("#organisationName1", LabeledMatchers.hasText("stillBlankOrganisation"));
        FxAssert.verifyThat("#organisationCredit1", LabeledMatchers.hasText("300.0"));

        // Edit the blank organisation & cancel
        clickOn("#organisationEditButton1");
        tripleClickOn("#organisationNameTextField");
        write("newBlankOrganisation");
        tripleClickOn("#creditTextField");
        write("300");
        clickOn("#cancelOrganisationButton");
        FxAssert.verifyThat("#organisationName1", LabeledMatchers.hasText("stillBlankOrganisation"));
        FxAssert.verifyThat("#organisationCredit1", LabeledMatchers.hasText("300.0"));
    }

    private void tripleClickOn(String query, MouseButton... buttons){
        clickOn(query, buttons);
        doubleClickOn(query, buttons);
    }
}