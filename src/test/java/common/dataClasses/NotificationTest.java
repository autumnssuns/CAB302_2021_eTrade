package common.dataClasses;

import common.Exceptions.InvalidArgumentValueException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {

    @Test
    void setNullNotificationId() throws InvalidArgumentValueException {
        Notification notification = new Notification().setNotificationId(null);
        assertNull(notification.getNotificationId());
    }

    @Test
    void setValidNotificationId() throws InvalidArgumentValueException {
        Notification notification = new Notification().setNotificationId(1);
        assertEquals(1, notification.getNotificationId());
    }

    @Test
    void setInvalidNotificationId() throws InvalidArgumentValueException {
        assertThrows(InvalidArgumentValueException.class, () -> {
            Notification notification = new Notification().setNotificationId(-1);
        });
    }

    @Test
    void addValidReceiverUnit() throws InvalidArgumentValueException {
        Notification notification = new Notification().addReceiverUnit(0).addReceiverUnit(1)
                .addReceiverUnit(2);
        for (int i = 0; i < 3; i++){
            assertTrue(notification.containsReceiver(i));
        }
    }

    @Test
    void getReceiversTest() throws InvalidArgumentValueException {
        Notification notification = new Notification().addReceiverUnit(0)
                .addReceiverUnit(1)
                .addReceiverUnit(2);
        assertEquals("0,1,2,", notification.getReceivers());
    }

    @Test
    void addNullReceiverUnit() {
        assertThrows(InvalidArgumentValueException.class, () -> {
            Notification notification = new Notification().addReceiverUnit(null);
        });
    }

    @Test
    void addInvalidReceiverUnit() {
        assertThrows(InvalidArgumentValueException.class, () -> {
            Notification notification = new Notification().addReceiverUnit(-1);
        });
    }

    @Test
    void addValidReaderUnit() throws InvalidArgumentValueException {
        Notification notification = new Notification().addReaderUnit(0).addReaderUnit(1)
                .addReaderUnit(2);
        for (int i = 0; i < 3; i++){
            assertTrue(notification.containsReader(i));
        }
    }

    @Test
    void getReadersTest() throws InvalidArgumentValueException {
        Notification notification = new Notification().addReaderUnit(0)
                .addReaderUnit(1)
                .addReaderUnit(2);
        assertEquals("0,1,2,", notification.getReaders());
    }

    @Test
    void addNullReaderUnit() {
        assertThrows(InvalidArgumentValueException.class, () -> {
            Notification notification = new Notification().addReaderUnit(null);
        });
    }

    @Test
    void addInvalidReaderUnit() {
        assertThrows(InvalidArgumentValueException.class, () -> {
            Notification notification = new Notification().addReaderUnit(-1);
        });
    }

    @Test
    void setMessage() {
        Notification notification = new Notification().setMessage("Test message");
        assertEquals("Test message", notification.getMessage());
    }

    @Test
    void testEquals() throws InvalidArgumentValueException {
        Notification notification = new Notification().setNotificationId(0).setMessage("Test").addReceiverUnit(0);
        Notification other = new Notification().setNotificationId(0).setMessage("Test").addReceiverUnit(0);
        assertTrue(notification.equals(other) && other.equals(notification));
        assertTrue(notification.hashCode() == other.hashCode());
    }

    @Test
    void testNotEquals() throws InvalidArgumentValueException {
        Notification notification = new Notification().setNotificationId(0).setMessage("Test1");
        Notification other = new Notification().setNotificationId(1).setMessage("Test2");
        assertFalse(notification.equals(other) && other.equals(notification));
        assertFalse(notification.hashCode() == other.hashCode());
    }
}