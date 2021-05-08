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
        String username = "username";
        String password = "password";
        User tempUser = new User(username, password);
        loginRequest = new Request(tempUser, "login");

        User user = new User(0, username, username, password, "user", 0);
        queryRequest = new Request(user, "query stock");

        User userAsAttachment = new User(1, "Do ", "something", "with this", "user", 0);
        updateRequest = new Request(user, "update", userAsAttachment);

        User userAsAttachment2 = new User(2, "Delete","Delete", "this", "user", 0);
        deleteRequest = new Request(user, "delete", userAsAttachment2);
    }

    @Test
    void getSender() {
        assertNull(loginRequest.getUser());
        assertEquals("username", queryRequest.getUser());
        assertEquals("username", updateRequest.getUser());
        assertEquals("username", deleteRequest.getUser());
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
        assertEquals(new User("username", "password"), loginRequest.getAttachment());
        assertNull(queryRequest.getAttachment());
        assertEquals(new User(1, "Do ", "something", "with this", "user", 0), updateRequest.getAttachment());
        assertEquals(new User(2, "Delete","Delete", "this", "user", 0), deleteRequest.getAttachment());
    }
}