package client.guiControls.userMain.notificationController;

import common.dataClasses.DataCollection;
import common.dataClasses.Notification;

/**
 * A class to control the creation of GUI components displaying the notifications
 */
public class NotificationFactory {
    /**
     * Creates a notification display (a text-containing box)
     * @param notification The notification linked with the display
     * @param hasRead The state of whether or not the notification has been read by the current unit
     * @return A new GUI component displaying a notification
     */
    public static NotificationViewUnit createNotification(Notification notification, boolean hasRead){
        NotificationViewUnit notificationViewUnit = new NotificationViewUnit(notification);
        if (!hasRead){
            notificationViewUnit.getStyleClass().add("highlight");
        }
        return notificationViewUnit;
    }
}
