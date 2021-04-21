package common;

import common.dataClasses.User;
import org.junit.Before;
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
        String[] attachment = new String[]{username, password};
        loginRequest = new Request("login", attachment);

        User user = new User(username, password, "user", 0);
        queryRequest = new Request(user, "query stock");

        User userAsAttachment = new User("Do something", "with this", "user", 0);
        updateRequest = new Request(user, "update", userAsAttachment);

        User userAsAttachment2 = new User("Delete", "this", "user", 0);
        deleteRequest = new Request(user, "delete", userAsAttachment2);
    }

    @Test
    void getSenderName() {
        assertNull(loginRequest.getSenderName());
        assertEquals("username", queryRequest.getSenderName());
        assertEquals("username", updateRequest.getSenderName());
        assertEquals("username", deleteRequest.getSenderName());
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
        assertArrayEquals(new String[]{"username", "password"}, (String[]) loginRequest.getAttachment());
        assertNull(queryRequest.getAttachment());
        assertEquals(new User("Do something", "with this", "user", 0), (User) updateRequest.getAttachment());
        assertEquals(new User("Delete", "this", "user", 0), (User) deleteRequest.getAttachment());
    }
}