package server.DataSourceClasses;

import common.Exceptions.InvalidArgumentValueException;
import common.dataClasses.DataCollection;
import common.dataClasses.Notification;
import server.DBConnection;
import java.sql.*;

public class NotificationDataSource extends DataSource{
    //Setting up the environment.
    //SQL queries.
    private static final String CREATE_TABLE =
            """
                    CREATE TABLE IF NOT EXISTS notifications (
                        notification_id        INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                        unit_ids               VARCHAR (256) DEFAULT '',
                        message                VARCHAR (256) DEFAULT ''
                    );""";
    private static final String ADD_NOTIFICATION = "INSERT INTO notifications (notification_id, unit_ids, message) \nVALUES (?, ?, ?);";
    private static final String DELETE_NOTIFICATION = "DELETE FROM notifications WHERE notification_id=?";
    private static final String DELETE_ALL_NOTIFICATION = "DELETE FROM notifications";
    private static final String GET_NOTIFICATION = "SELECT * FROM notifications WHERE notification_id=?";
    private static final String GET_ALL_NOTIFICATION = "SELECT * FROM notifications";
    private static final String UPDATE_NOTIFICATION = """
            UPDATE notifications\s
            SET unit_ids=?, message=?
            WHERE notification_id=?""";
    private static final String GET_MAX_ID = "SELECT notification_id FROM notifications";

    //Prepare statements.
    private PreparedStatement addNotification;
    private PreparedStatement deleteNotification;
    private PreparedStatement deleteAllNotification;
    private PreparedStatement getNotification;
    private PreparedStatement getAllNotification;
    private PreparedStatement updateNotification;
    private PreparedStatement getMaxId;

    /**
     * Connect to the database then create a table if not exists
     */
    public NotificationDataSource() {
        connection = DBConnection.getInstance();
        try {
            Statement st = connection.createStatement();
            st.execute(CREATE_TABLE);
            addNotification = connection.prepareStatement(ADD_NOTIFICATION);
            deleteNotification = connection.prepareStatement(DELETE_NOTIFICATION);
            deleteAllNotification = connection.prepareStatement(DELETE_ALL_NOTIFICATION);
            getNotification = connection.prepareStatement(GET_NOTIFICATION);
            getAllNotification = connection.prepareStatement(GET_ALL_NOTIFICATION);
            updateNotification = connection.prepareStatement(UPDATE_NOTIFICATION);
            getMaxId = connection.prepareStatement(GET_MAX_ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new notification to the data table.
     * @param notification The new notification for the data table.
     */
    public void add(Notification notification){
        try{
            addNotification.setInt(1, getNextId());
            addNotification.setString(2, notification.getReceivers());
            addNotification.setString(3, notification.getMessage());
            addNotification.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Delete a notification from a table.
     * @param notificationId The ID of the notification to be deleted.
     */
    public void delete(Integer notificationId){
        try {
            deleteNotification.setInt(1, notificationId);
            deleteNotification.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Delete all notifications stored in the database.
     */
    public void deleteAll() {
        try {
            deleteAllNotification.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return a notification queried from the database.
     * @param notificationId The ID of the notification to be retrieved.
     * @return The notification with the given ID.
     */
    public Notification get(Integer notificationId){
        Notification notification = null;
        ResultSet rs;
        try{
            if (notificationId == null){
                notificationId = getMaxId();
            }
            getNotification.setInt(1, notificationId);
            rs = getNotification.executeQuery();
            while (rs.next()){
                String message = rs.getString("message");
                String[] receiverIds = rs.getString("unit_ids").split(",");
                notification = new Notification()
                        .setNotificationId(notificationId)
                        .setMessage(message);
                // Append receiver IDs
                for (String receiverId : receiverIds){
                    try{
                        notification.addReceiverUnit(Integer.parseInt(receiverId));
                    }
                    catch (NumberFormatException ignored){
                        //Ignore when parse fails
                    }
                }
            }
        }
        catch (SQLException | InvalidArgumentValueException e) {
            e.printStackTrace();
        }
        return notification;
    }

    /**
     * Return the latest notification in the database.
     * @return The latest notification in the database.
     */
    public Notification get(){
        try {
            return get(getMaxId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    /**
     * Return all notifications stored within the database.
     * @return A collection of all notifications from the database.
     */
    public DataCollection<Notification> getAll(){
        DataCollection<Notification> notifications = new DataCollection<>();
        ResultSet rs;
        try{
            rs = getAllNotification.executeQuery();
            while (rs.next()){
                notifications.add(get(rs.getInt("notification_id")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return notifications;
    }

    /**
     * Edit a notification in the database
     * @param notification The overriding notification
     */
    public void edit(Notification notification)  {
        try {
            updateNotification.setString(1, notification.getReceivers());
            updateNotification.setString(2, notification.getMessage());
            updateNotification.setString(3, String.valueOf(notification.getNotificationId()));
            updateNotification.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Gets the next ID of the item to be added to this table.
     * @return The next ID of the item to be added to this table.
     */
    private Integer getNextId(){
        try {
            return getMaxId() + 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the highest ID contained in this table.
     * @return The highest ID contained in this table.
     */
    private Integer getMaxId() throws SQLException {
        ResultSet rs = getMaxId.executeQuery();
        int max = -1;
        while (rs.next()){
            if (rs.getInt(1) > max){
                max = rs.getInt(1);
            }
        }
        return max;
    }
}
