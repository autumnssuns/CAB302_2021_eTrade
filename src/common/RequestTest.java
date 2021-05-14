package common;

import common.dataClasses.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestTest {

    private Request loginRequest;
    private Request queryRequest;
    private Request updateRequest;
    private Request deleteRequest;

    @BeforeEach
    void setUp(){
        String username = "dan";
        String password = "123";
        User tempUser = new User(username, password);
        loginRequest = new Request(tempUser, "login");

        User user = new User(0, username, username, password, "user", 0);
        queryRequest = new Request(user, "query stock");

        User userAsAttachment = new User(1, "Dan Tran ", "dan", "123", "user", 0);
        updateRequest = new Request(user, "update", userAsAttachment);
        updateRequest.setAttachmentType(User.class);

        User userAsAttachment2 = new User(2, "Rodo Nguyen","rodo", "rodo", "user", 0);
        deleteRequest = new Request(user, "delete", userAsAttachment2);
        deleteRequest.setAttachmentType(User.class);
    }

    @Test
    void getSender() {
        assertEquals(new User("dan", "123"), loginRequest.getUser());
        assertEquals(new User(0, "dan", "dan", "123", "user", 0), queryRequest.getUser());
    }

    @Test
    void getAction() {
        assertEquals("login", loginRequest.getAction());
        assertEquals("query stock", queryRequest.getAction());
        assertEquals("update", updateRequest.getAction());
        assertEquals("delete", deleteRequest.getAction());
    }

    @Test
    void getAttachment() {
        assertNull(loginRequest.getAttachment());
        assertNull(queryRequest.getAttachment());
        assertEquals(new User(1, "Dan Tran ", "dan", "123", "user", 0), updateRequest.getAttachment());
        assertEquals(new User(2, "Rodo Nguyen","rodo", "rodo", "user", 0), deleteRequest.getAttachment());
    }

    @Test
    void getAttachmentType(){
        assertNull(loginRequest.getAttachmentType());
        assertNull(queryRequest.getAttachmentType());
        assertEquals(User.class, updateRequest.getAttachmentType());
        assertEquals(User.class, deleteRequest.getAttachmentType());
    }
}