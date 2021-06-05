package client.guiControls.adminMain.usersController;

import client.IViewUnit;
import client.guiControls.DisplayController;
import client.guiControls.adminMain.AdminLocalDatabase;
import client.guiControls.adminMain.AdminMainController;
import common.Exceptions.InvalidArgumentValueException;
import common.Request;
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
    private UsersController controller;
    private User user;
    private OrganisationalUnit unit;

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
     * @param user The linked user.
     * @param controller The controller for this field
     */
    public UserInfoBox(User user, UsersController controller) throws InvalidArgumentValueException {
        super();
        this.controller = controller;
        this.user = user;
        this.unit = controller.getOrganisation(user) == null ?
                    new OrganisationalUnit(null,"N/A",0) :
                    controller.getOrganisation(user);
        initialize();
        load();
    }

    @Override
    public void initialize() {
        this.setAlignment(Pos.CENTER);
        this.setPrefHeight(80);
        this.setPrefWidth(1300);
        this.setLayoutX(41);
        this.setLayoutY(260);
        this.setSpacing(5);

        idLabel = new Label();
        idLabel.getStyleClass().add("blackLabel");
        idLabel.setAlignment(Pos.CENTER);
        idLabel.setPrefWidth(80);
        idLabel.setPrefHeight(80);

        nameTextField = new TextField();
        nameTextField.setPrefWidth(190);
        nameTextField.setPrefHeight(30);
        nameTextField.setId("userFullname" + user.getUserId());

        usernameTextField = new TextField();
        usernameTextField.setPrefWidth(190);
        usernameTextField.setPrefHeight(30);
        usernameTextField.setId("username" + user.getUserId());

        passwordField = new PasswordField();
        passwordField.setPrefWidth(190);
        passwordField.setPrefHeight(30);
        passwordField.setId("password" + user.getUserId());

        organisationUnitSelectionBox = new ComboBox();
        organisationUnitSelectionBox.setPromptText("Organisational Unit");
        organisationUnitSelectionBox.setPrefWidth(300);
        organisationUnitSelectionBox.setPrefHeight(30);
        organisationUnitSelectionBox.setEditable(true);
        organisationUnitSelectionBox.setId("userOrganisation" + user.getUserId());

        roleSelectionBox = new ComboBox();
        roleSelectionBox.setPromptText("Role");
        roleSelectionBox.setPrefWidth(190);
        roleSelectionBox.setPrefHeight(30);
        roleSelectionBox.setEditable(false);
        roleSelectionBox.getItems().addAll("user","admin");
        roleSelectionBox.setId("userRole" + user.getUserId());

        editButton = new Button("Edit");
        editButton.setPrefWidth(80);
        editButton.setPrefHeight(30);
        editButton.setStyle("-fx-font-size:10");
        editButton.setOnAction(e -> startEdit());
        editButton.setId("userEditButton" + user.getUserId());

        removeButton = new Button("Remove");
        removeButton.setPrefWidth(80);
        removeButton.setPrefHeight(30);
        removeButton    .setStyle("-fx-font-size:10");
        removeButton.setOnAction(e -> {
            try {
                removeEntry();
            } catch (InvalidArgumentValueException invalidArgumentValueException) {
                invalidArgumentValueException.printStackTrace();
            }
        });
        removeButton.setId("userRemoveButton" + user.getUserId());

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
     * Update the user's info by taking data from the text fields.
     */
    private void updateValues(){
        user.setFullName(nameTextField.getText());
        user.setUsername(usernameTextField.getText());
        if (!user.getPassword().equals(passwordField.getText())){
            user.setPassword(passwordField.getText());
            user.hashPassword();
        }
        user.setOrganisation(controller.getOrganisation((String) organisationUnitSelectionBox.getValue()).getId());
        user.setAccountType((String) roleSelectionBox.getValue());
    }

    /**
     * Loads a label to display the user's id.
     */
    private void loadIdLabel(){
        idLabel.setText(String.valueOf(user.getUserId()));
    }

    /**
     * Loads a text field to display the user's name.
     */
    private void loadFullNameTextField(){
        nameTextField.setText(user.getFullName());
    }

    /**
     * Loads a text field to display the user's unique username.
     */
    private void loadUsernameTextField(){
        usernameTextField.setText(user.getUsername());
    }

    /**
     * Loads a password field to edit the user's password.
     */
    private void loadPasswordField(){
        passwordField.setText(user.getPassword());
    }

    /**
     * Loads a ComboBox to display the user's organisationalUnit.
     */
    // TODO: Sync with organisations
    private void loadOrganisationUnitSelectionBox(){
        organisationUnitSelectionBox.setValue(unit.getName());
        organisationUnitSelectionBox.getItems().clear();
        for (OrganisationalUnit unit : controller.getOrganisationalUnits()){
            organisationUnitSelectionBox.getItems().add(unit.getName());
        }
    }

    /**
     * Loads a ComboBox to display the user's role.
     */
    private void loadRoleSelectionBox(){
        roleSelectionBox.setValue(user.getAccountType());
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
        controller.sendRequest(Request.ActionType.UPDATE, user, Request.ObjectType.USER);
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
        controller.sendRequest(Request.ActionType.DELETE, user, Request.ObjectType.USER);
    }
}
