package common;

import common.dataClasses.IData;
import common.dataClasses.Order;
import common.dataClasses.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestTest {

    private Request<common.dataClasses.IData> loginRequest;
    private Request<common.dataClasses.IData> queryRequest;
    private Request<User> updateRequest;
    private Request<User> deleteRequest;

    private User user;
    private User userAsAttachment;

    @BeforeEach
    void setUp(){
        String username = "dan";
        String password = "123";
        User tempUser = new User(username, password);
        loginRequest = new Request<>(tempUser, Request.ActionType.LOGIN);

        user = new User(0, username, username, password, "user", 0);
        queryRequest = new Request<>(user, Request.ActionType.READ).setObjectType(Request.ObjectType.STOCK);

        userAsAttachment = new User(1, "Dan Tran", "dan", "123", "user", 0);
        updateRequest = new Request<>(user, Request.ActionType.UPDATE, userAsAttachment);
        updateRequest.setObjectType(Request.ObjectType.USER);

        User userAsAttachment2 = new User(2, "Rodo Nguyen","rodo", "rodo", "user", 0);
        deleteRequest = new Request<>(user, Request.ActionType.DELETE, userAsAttachment2);
        deleteRequest.setObjectType(Request.ObjectType.USER);
    }

    @Test
    void getSender() {
        assertEquals(new User("dan", "123"), loginRequest.getUser());
        assertEquals(new User(0, "dan", "dan", "123", "user", 0), queryRequest.getUser());
    }

    @Test
    void getAction() {
        assertEquals(Request.ActionType.LOGIN, loginRequest.getActionType());
        assertEquals(Request.ActionType.READ, queryRequest.getActionType());
        assertEquals(Request.ActionType.UPDATE, updateRequest.getActionType());
        assertEquals(Request.ActionType.DELETE, deleteRequest.getActionType());
    }

    @Test
    void getAttachment() {
        assertNull(loginRequest.getAttachment());
        assertNull(queryRequest.getAttachment());
        assertEquals(new User(1, "Dan Tran", "dan", "123", "user", 0), updateRequest.getAttachment());
        assertEquals(new User(2, "Rodo Nguyen","rodo", "rodo", "user", 0), deleteRequest.getAttachment());
    }

    @Test
    void getAttachmentType(){
        assertNull(loginRequest.getObjectType());
        assertEquals(Request.ObjectType.STOCK,queryRequest.getObjectType());
        assertEquals(Request.ObjectType.USER, updateRequest.getObjectType());
        assertEquals(Request.ObjectType.USER, deleteRequest.getObjectType());
    }

    @Test
    void modifyAndCheckPreviousObjectState() {
        updateRequest.setPreviousObjectState(user);
        assertEquals(user,updateRequest.getPreviousObjectState());
    }

    @Test
    void trueEquals() {
        user = new User(0, "dan", "dan", "123", "user", 0);
        userAsAttachment = new User(1, "Dan Tran", "dan", "123", "user", 0);
        updateRequest = new Request<>(user, Request.ActionType.UPDATE, userAsAttachment);

        Request sameRequest =  new Request<>(user,
                Request.ActionType.UPDATE, userAsAttachment);

        assertTrue(updateRequest.equals(sameRequest));
    }

    @Test
    void falseEquals() {
        user = new User(0, "dan", "dan", "123", "user", 0);
        userAsAttachment = new User(1, "Dan Tran", "dan", "123", "user", 0);

        Request differentInType =  new Request<>(user,
                Request.ActionType.DELETE, userAsAttachment);
        assertFalse(updateRequest.equals(differentInType));

        User user1 = new User(99, "Dan Tran", "dan", "123", "user", 0);
        Request differentInSender = new Request<>(user1,
                Request.ActionType.UPDATE, userAsAttachment);
        assertFalse(updateRequest.equals(differentInSender));
    }

    @Test
    void hashCodeWorking() {
        deleteRequest.hashCode();
    }
}