package guiTest;

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
        clickOn("#organisationalUnitsButton");
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
        FxAssert.verifyThat("#assetName0", TextInputControlMatchers.hasText("CPU Hours"));
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
            clickOn("#newOrganisationUnitSelectionBox");
            write("tempOrganisation");
            clickOn("#newRoleSelectionBox");
            write("user");
            clickOn("#addNewUserButton");
        }

        // Edit & Remove
        clickOn("#userEditButton1");
        doubleClickOn("#userFullname1");
        doubleClickOn("#userFullname1");
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
        FxAssert.verifyThat("#userFullname1", TextInputControlMatchers.hasText("New Name"));
        FxAssert.verifyThat("#username1", TextInputControlMatchers.hasText("newUsername"));
        FxAssert.verifyThat("#password1", TextInputControlMatchers.hasText("newPassword"));
        FxAssert.verifyThat("#userOrganisation1", ComboBoxMatchers.hasSelectedItem("newOrganisation"));
        FxAssert.verifyThat("#userRole1", ComboBoxMatchers.hasSelectedItem("admin"));
        clickOn("#userRemoveButton1");

        // Edit & Cancel
        clickOn("#userEditButton0");
        doubleClickOn("#userFullname0");
        doubleClickOn("#userFullname0");
        write("Still Person 0");
        clickOn("#userRemoveButton0");
        FxAssert.verifyThat("#userFullname0", TextInputControlMatchers.hasText("Admin"));
    }


    @Test
    public void adminOrganisationUnitsControlTest(){
        clickOn("#organisationalUnitsButton");
        clickOn("#addNewOrganisationalUnitButton");
        clickOn("#organisationalUnitNameTextField");
        write("tempOrganisation");
        clickOn("#creditTextField");
        write("500");
        FxAssert.verifyThat("#organisationalUnitEditPane", NodeMatchers.isVisible());

        // Add asset to organisational unit
        for (int i = 0 ; i < 2; i++){
            clickOn("#newOrganisationalUnitAssetNameTextField");
            write("Asset " + i);
            clickOn("#newOrganisationalUnitAssetQuantityTextField");
            write("99");
            clickOn("#addNewOrganisationalUnitAssetButton");
        }

        // Edit & Remove asset from organisational unit
        clickOn("#organisationalUnitAssetEditButton1");
        clickOn("#organisationalUnitAssetName1");
        doubleClickOn("#organisationalUnitAssetName1");
        write("New Asset");
        clickOn("#organisationalUnitAssetQuantity1");
        doubleClickOn("#organisationalUnitAssetQuantity1");
        write("80");
        clickOn("#organisationalUnitAssetEditButton1");
        FxAssert.verifyThat("#organisationalUnitAssetName1", TextInputControlMatchers.hasText("New Asset"));
        FxAssert.verifyThat("#organisationalUnitAssetQuantity1", TextInputControlMatchers.hasText("80"));
        clickOn("#organisationalUnitAssetRemoveButton1");

        // Edit & Cancel asset from organisational unit
        clickOn("#organisationalUnitAssetEditButton0");
        clickOn("#organisationalUnitAssetName0");
        doubleClickOn("#organisationalUnitAssetName0");
        write("New Asset");
        clickOn("#organisationalUnitAssetQuantity0");
        doubleClickOn("#organisationalUnitAssetQuantity0");
        write("80");
        clickOn("#organisationalUnitAssetRemoveButton0");
        FxAssert.verifyThat("#organisationalUnitAssetName0", TextInputControlMatchers.hasText("Asset 0"));
        FxAssert.verifyThat("#organisationalUnitAssetQuantity0", TextInputControlMatchers.hasText("99"));

        clickOn("#confirmOrganisationalUnitButton");
        FxAssert.verifyThat("#organisationalUnitName3", LabeledMatchers.hasText("tempOrganisation"));
        FxAssert.verifyThat("#organisationalUnitEditPane", NodeMatchers.isInvisible());

        // Add blank organisational unit with no asset
        clickOn("#addNewOrganisationalUnitButton");
        clickOn("#organisationalUnitNameTextField");
        write("blankOrganisation");
        clickOn("#creditTextField");
        write("500");
        FxAssert.verifyThat("#organisationalUnitEditPane", NodeMatchers.isVisible());
        clickOn("#confirmOrganisationalUnitButton");
        FxAssert.verifyThat("#organisationalUnitName4", LabeledMatchers.hasText("blankOrganisation"));
        FxAssert.verifyThat("#organisationalUnitEditPane", NodeMatchers.isInvisible());

        // Edit the blank organisational unit
        clickOn("#organisationalUnitEditButton4");
        FxAssert.verifyThat("#organisationalUnitNameTextField", TextInputControlMatchers.hasText("blankOrganisation"));
        FxAssert.verifyThat("#creditTextField", TextInputControlMatchers.hasText("500.0"));
        tripleClickOn("#organisationalUnitNameTextField");
        write("stillBlankOrganisation");
        tripleClickOn("#creditTextField");
        write("300");
        clickOn("#confirmOrganisationalUnitButton");
        FxAssert.verifyThat("#organisationalUnitName4", LabeledMatchers.hasText("stillBlankOrganisation"));
        FxAssert.verifyThat("#organisationalUnitCredit4", LabeledMatchers.hasText("300.0"));

        // Edit the blank organisational unit & cancel
        clickOn("#organisationalUnitEditButton4");
        tripleClickOn("#organisationalUnitNameTextField");
        write("newBlankOrganisation");
        tripleClickOn("#creditTextField");
        write("300");
        clickOn("#cancelOrganisationalUnitButton");
        FxAssert.verifyThat("#organisationalUnitName4", LabeledMatchers.hasText("stillBlankOrganisation"));
        FxAssert.verifyThat("#organisationalUnitCredit4", LabeledMatchers.hasText("300.0"));
    }

    private void tripleClickOn(String query, MouseButton... buttons){
        clickOn(query, buttons);
        doubleClickOn(query, buttons);
    }
}