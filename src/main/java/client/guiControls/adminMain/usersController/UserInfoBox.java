package client.guiControls.adminMain.usersController;

import client.IViewUnit;
import client.guiControls.DisplayController;
import client.guiControls.adminMain.AdminLocalDatabase;
import client.guiControls.adminMain.AdminMainController;
import common.Exceptions.InvalidArgumentValueException;
import common.Response;
import common.dataClasses.Asset;
import common.dataClasses.DataCollection;
import common.dataClasses.OrganisationalUnit;
import common.dataClasses.User;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * A box to display user information and can be interacted with.
 */
public class UserInfoBox extends HBox implements IViewUnit {
    private AdminMainController controller;

    private int userId;
    private String name;
    private String username;
    private String password;
    private String organisationalUnit;
    private String role;

    private Label idLabel;
    private TextField nameTextField;
    private TextField usernameTextField;
    private PasswordField passwordField;
    private ComboBox organisationUnitSelectionBox;
    private ComboBox roleSelectionBox;

    private Button editButton;
    private Button removeButton;

    /**
     * Initiates the box with user information.
     * @param userId The user's id.
     * @param name The name of the user.
     * @param username The unique username of the user.
     * @param password The user's password.
     * @param organisationalUnit The name of the user's organisationalUnit.
     * @param role The user's role.
     */
    public UserInfoBox(int userId, String name, String username, String password, String organisationalUnit, String role){
        super();

        this.userId = userId;
        this.name = name;
        this.username = username;
        this.password = password;
        this.organisationalUnit = organisationalUnit;
        this.role = role;

        initialize();
        load();
    }

    @Override
    public void initialize() {
        this.setAlignment(Pos.CENTER);
        this.setPrefHeight(80);
        this.setPrefWidth(1363);
        this.setLayoutX(41);
        this.setLayoutY(260);
        this.setSpacing(20);

        idLabel = new Label();
        idLabel.getStyleClass().add("blackLabel");
        idLabel.setAlignment(Pos.CENTER);
        idLabel.setPrefWidth(100);
        idLabel.setPrefHeight(80);

        nameTextField = new TextField();
        nameTextField.setPrefWidth(190);
        nameTextField.setPrefHeight(30);
        nameTextField.setId("userFullname" + userId);

        usernameTextField = new TextField();
        usernameTextField.setPrefWidth(190);
        usernameTextField.setPrefHeight(30);
        usernameTextField.setId("username" + userId);

        passwordField = new PasswordField();
        passwordField.setPrefWidth(190);
        passwordField.setPrefHeight(30);
        passwordField.setId("password" + userId);

        organisationUnitSelectionBox = new ComboBox();
        organisationUnitSelectionBox.setPromptText("Organisational Unit");
        organisationUnitSelectionBox.setPrefWidth(300);
        organisationUnitSelectionBox.setPrefHeight(30);
        organisationUnitSelectionBox.setEditable(true);
        organisationUnitSelectionBox.setId("userOrganisation" + userId);

        roleSelectionBox = new ComboBox();
        roleSelectionBox.setPromptText("Role");
        roleSelectionBox.setPrefWidth(190);
        roleSelectionBox.setPrefHeight(30);
        roleSelectionBox.setEditable(false);
        roleSelectionBox.getItems().addAll("user","admin");
        roleSelectionBox.setId("userRole" + userId);

        editButton = new Button("Edit");
        editButton.setPrefWidth(100);
        editButton.setPrefHeight(30);
        editButton.setOnAction(e -> startEdit());
        editButton.setId("userEditButton" + userId);

        removeButton = new Button("Remove");
        removeButton.setPrefWidth(100);
        removeButton.setPrefHeight(30);
        removeButton.setOnAction(e -> {
            try {
                removeEntry();
            } catch (InvalidArgumentValueException invalidArgumentValueException) {
                invalidArgumentValueException.printStackTrace();
            }
        });
        removeButton.setId("userRemoveButton" + userId);

        this.getChildren().addAll(idLabel, nameTextField, usernameTextField, passwordField, organisationUnitSelectionBox, roleSelectionBox, editButton, removeButton);
        disable();
    }

    @Override
    public void load() {
        loadIdLabel();
        loadFullNameTextField();
        loadUsernameTextField();
        loadPasswordField();
        loadRoleSelectionBox();
        loadOrganisationUnitSelectionBox();
    }

    /**
     * Sets the controller for this component.
     * @param controller The controller.
     */
    public void setController(AdminMainController controller){
        this.controller = controller;
    }

    /**
     * Update the user's info by taking data from the text fields.
     */
    private void updateValues(){
        name = nameTextField.getText();
        username = usernameTextField.getText();
        password = passwordField.getText();
        organisationalUnit = (String) organisationUnitSelectionBox.getValue();
        role = (String) roleSelectionBox.getValue();
    }

    /**
     * Loads a label to display the user's id.
     */
    private void loadIdLabel(){
        idLabel.setText(String.valueOf(userId));
    }

    /**
     * Loads a text field to display the user's name.
     */
    private void loadFullNameTextField(){
        nameTextField.setText(name);
    }

    /**
     * Loads a text field to display the user's unique username.
     */
    private void loadUsernameTextField(){
        usernameTextField.setText(username);
    }

    /**
     * Loads a password field to edit the user's password.
     */
    private void loadPasswordField(){
        passwordField.setText(password);
    }

    /**
     * Loads a ComboBox to display the user's organisationalUnit.
     */
    // TODO: Sync with organisations
    private void loadOrganisationUnitSelectionBox(){
        organisationUnitSelectionBox.setValue(organisationalUnit);
        organisationUnitSelectionBox.getItems().addAll("TestOrg", "The Justice League", "The supervillains", "The random civilians");

    }

    /**
     * Loads a ComboBox to display the user's role.
     */
    private void loadRoleSelectionBox(){
        roleSelectionBox.setValue(role);
    }

    /**
     * Disables the current entry from being edited.
     */
    private void disable(){
        nameTextField.setDisable(true);
        usernameTextField.setDisable(true);
        passwordField.setDisable(true);
        organisationUnitSelectionBox.setDisable(true);
        roleSelectionBox.setDisable(true);
    }

    /**
     * Enables the current entry to be edited.
     */
    private void enable(){
        nameTextField.setDisable(false);
        usernameTextField.setDisable(false);
        passwordField.setDisable(false);
        organisationUnitSelectionBox.setDisable(false);
        roleSelectionBox.setDisable(false);
        editButton.setText("Confirm");
        editButton.setOnAction(e -> startEdit());
    }

    /**
     * Begins editing the current entry.
     */
    private void startEdit(){
        enable();
        editButton.setText("Confirm");
        editButton.setOnAction(e -> {
            try {
                confirmEdit();
            } catch (InvalidArgumentValueException invalidArgumentValueException) {
                invalidArgumentValueException.printStackTrace();
            }
        });
        removeButton.setText("Cancel");
        removeButton.setOnAction(e -> cancelEdit());
    }

    /**
     * Confirms the changes to the current entry.
     */
    private void confirmEdit() throws InvalidArgumentValueException {
        disable();
        updateValues();
        int unitId = 0;
        for (OrganisationalUnit unit : ((AdminLocalDatabase)controller.getDatabase()).getOrganisationalUnits()){
            if (unit.getName().equals(organisationalUnit)){
                unitId = unit.getId();
                break;
            }
        }
        Response response = controller.sendRequest("edit", new User(userId, name, username, password, role, unitId), User.class);
        if (response.isFulfilled()){
            controller.updateLocalDatabase(User.class);
        }
        editButton.setText("Edit");
        editButton.setOnAction(e -> startEdit());
        removeButton.setText("Remove");
        removeButton.setOnAction(e -> {
            try {
                removeEntry();
            } catch (InvalidArgumentValueException invalidArgumentValueException) {
                invalidArgumentValueException.printStackTrace();
            }
        });
    }

    /**
     * Cancels the changes to the current entry.
     */
    private void cancelEdit(){
        disable();
        load();
        editButton.setText("Edit");
        editButton.setOnAction(e -> startEdit());
        removeButton.setText("Remove");
        removeButton.setOnAction(e -> {
            try {
                removeEntry();
            } catch (InvalidArgumentValueException invalidArgumentValueException) {
                invalidArgumentValueException.printStackTrace();
            }
        });
    }

    /**
     * Removes the current entry.
     */
    private void removeEntry() throws InvalidArgumentValueException {
        int unitId = 0;
        for (OrganisationalUnit unit : ((AdminLocalDatabase)controller.getDatabase()).getOrganisationalUnits()){
            if (unit.getName().equals(organisationalUnit)){
                unitId = unit.getId();
                break;
            }
        }
        Response response = controller.sendRequest("delete", new User(userId, name, username, password, role, unitId), User.class);
        if (response.isFulfilled()){
            controller.updateLocalDatabase(User.class);
            ((VBox) this.getParent()).getChildren().remove(this);
        }
    }
}
