package client;

import client.data.IServerConnection;
import client.data.MockServerConnection;
import client.guiControls.MainController;
import common.Exceptions.InvalidArgumentValueException;
import common.Request;
import common.Response;
import common.dataClasses.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainControllerTest {
    MainController controller;

    @BeforeEach
    void setUp() {
        controller = new MainController();
    }

    @Test
    void setUser() {
        User tempUser = new User(0, "Admin","username", "password", "admin", 0);
        controller.setUser(tempUser);
        assertEquals(tempUser, controller.getUser());
    }

    @Test
    void setServerConnection() throws InvalidArgumentValueException {
        IServerConnection serverConnection = new MockServerConnection();
        controller.setServerConnection(serverConnection);
        assertEquals(serverConnection, controller.getServerConnection());
    }

    @Test
    void sendRequest() throws InvalidArgumentValueException {
        User tempUser = new User("username", "password");
        controller.setUser(tempUser);
        IServerConnection serverConnection = new MockServerConnection();
        controller.setServerConnection(serverConnection);
        Request request = new Request(tempUser, "login");
        Response response = controller.sendRequest("login");
        assertNotNull(response);
    }
}