package server.DataSourceClasses;

import common.Exceptions.InvalidArgumentValueException;
import common.dataClasses.DataCollection;
import common.dataClasses.Notification;
import org.junit.jupiter.api.*;
import server.DBConnection;

import static org.junit.jupiter.api.Assertions.*;

class NotificationDataSourceTest {
    private static NotificationDataSource notificationDataSource;

    @BeforeAll
    static void startTestMode(){
        DBConnection.setTestMode(true);
        notificationDataSource = new NotificationDataSource();
    }

    @AfterAll
    static void stopTestMode(){
        DBConnection.setTestMode(false);
    }

    @BeforeEach
    void setUp() {
        CasesToResponse.cleanDatabase();
    }

    @AfterEach
    void tearDown(){
        CasesToResponse.cleanDatabase();
    }

    @Test
    void delete() {
        for (int i = 0; i < 5; i++){
            Notification notification = new Notification().setMessage("i");
            notificationDataSource.add(notification);
        }
        notificationDataSource.delete(0);
        assertNull(notificationDataSource.get(0));
    }

    @Test
    void deleteAll() {
        for (int i = 0; i < 5; i++){
            Notification notification = new Notification().setMessage("i");
            notificationDataSource.add(notification);
        }
        notificationDataSource.deleteAll();
        assertTrue(notificationDataSource.getAll().isEmpty());
    }

    @Test
    void setAndGet() throws InvalidArgumentValueException {
        Notification notification = new Notification()
                                    .addReceiverUnit(0)
                                    .setMessage("Test");
        notificationDataSource.add(notification);
        notification.setNotificationId(0);
        Notification result = notificationDataSource.get();
        assertEquals(notification, result);
    }

    @Test
    void getFromUnitIdTest() throws InvalidArgumentValueException {
        Notification first = new Notification()
                                    .addReceiverUnit(2)
                                    .setMessage("Test1");
        Notification second = new Notification()
                            .addReceiverUnit(2)
                            .setMessage("Test2");
        notificationDataSource.add(first);
        notificationDataSource.add(second);
        first.setNotificationId(0);
        second.setNotificationId(1);
        DataCollection<Notification> expected = new DataCollection();
        expected.add(first);
        expected.add(second);
        DataCollection<Notification> result = notificationDataSource.getFromUnitId(2);
        assertEquals(expected, result);

    }

    @Test
    void getAll() throws InvalidArgumentValueException {
        DataCollection<Notification> notifications = new DataCollection<>();
        for (int i = 0; i < 5; i++){
            Notification notification = new Notification().setMessage("Notification " + i);
            notificationDataSource.add(notification);
            notifications.add(notification.setNotificationId(i));
        }
        DataCollection<Notification> result = notificationDataSource.getAll();
        assertEquals(notifications, result);
    }

    @Test
    void edit() throws InvalidArgumentValueException {
        // Set up by adding a notification
        Notification notification = new Notification()
                .addReceiverUnit(0)
                .setMessage("Test");
        notificationDataSource.add(notification);
        // Make changes and update
        notification.addReceiverUnit(1).addReceiverUnit(2).addReceiverUnit(3).setMessage("New message");
        notification.setNotificationId(0);
        notificationDataSource.edit(notification);
        Notification result = notificationDataSource.get(notification.getNotificationId());
        assertEquals(notification, result);
    }
}