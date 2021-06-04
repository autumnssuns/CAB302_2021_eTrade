package common.dataClasses;

import common.Exceptions.InvalidArgumentValueException;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a notification to inform an event occurring for an organisational unit
 */
public class Notification implements IData {
    private Integer notificationId;
    // A notification can be linked to many organisational unit
    private ArrayList<Integer> unitIds;
    private String message;

    /**
     * Default constructor that can be built upon
     */
    public Notification(){
        unitIds = new ArrayList<Integer>();
    }

    /**
     * Set the ID of the notification
     * @param notificationId The ID of the notification
     * @return The current instance for building
     */
    public Notification setNotificationId(Integer notificationId) throws InvalidArgumentValueException {
        if (notificationId != null && notificationId < 0){
            throw new InvalidArgumentValueException();
        }
        this.notificationId = notificationId;
        return this;
    }

    /**
     * Add a receiving organisational unit
     * @param unitId The ID of the receiving organisational unit
     * @return The current instance for building
     */
    public Notification addReceiverUnit(Integer unitId) throws InvalidArgumentValueException {
        if (unitId == null || unitId < 0){
            throw new InvalidArgumentValueException();
        }
        this.unitIds.add(unitId);
        return this;
    }

    /**
     * Set the message enclosed in the notification
     * @param message The message enclosed in the notification
     * @return The current instance for building
     */
    public Notification setMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * Get the ID of the current notification
     * @return The ID of the current notification
     */
    public Integer getNotificationId() {
        return notificationId;
    }

    /**
     * Checks if an organisational unit is in the notification's receiver list
     * @param unitId The ID of the organisational unit to check
     * @return true of the organisational unit is in the receiver list, false otherwise
     */
    public boolean containsUnit(Integer unitId) {
        return unitIds.contains(unitId);
    }

    /**
     * Gets a list of receivers for the notification as a string.
     * @return The string containing a list of receiver, separated by commas (",")
     */
    public String getReceivers(){
        String receivers = "";
        for (Integer unitId : unitIds){
            receivers += unitId + ",";
        }
        return receivers;
    }

    /**
     * Get the message enclosed in the notification
     * @return The message enclosed in the notification
     */
    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(notificationId, that.notificationId) && Objects.equals(unitIds, that.unitIds) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notificationId, unitIds, message);
    }
}
