package client.guiControls.userMain.notificationController;

import client.IViewUnit;
import common.dataClasses.Notification;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * A GUI components to view the notifications
 */
public class NotificationViewUnit extends HBox implements IViewUnit {
    private Notification notification;

    private Label messageLabel;

    /**
     * Initialises the unit view for a notification.
     * @param notification The linked notification
     */
    public NotificationViewUnit(Notification notification){
        this.notification = notification;

        initialize();
        load();
    }

    /**
     * Initialize the GUI elements and their styling.
     */
    @Override
    public void initialize() {
        this.setId("notificationViewUnit" + notification.getNotificationId());
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(0);

        messageLabel = new Label();
        messageLabel.getStyleClass().add("blackLabel");
        messageLabel.setId("notificationMessage"+notification.getNotificationId());
        messageLabel.setPrefWidth(236);
        messageLabel.setPrefHeight(60);
        messageLabel.setWrapText(true);

        this.getChildren().addAll(messageLabel);
    }

    /**
     * Loads the GUI components, by calling all other load methods.
     */
    @Override
    public void load() {
        loadMessage();
    }

    /**
     * Loads and display the message in the notification
     */
    private void loadMessage(){
        messageLabel.setText(notification.getMessage());
    }
}
