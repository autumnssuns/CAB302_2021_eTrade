package server.DataSourceClasses;

import static org.junit.jupiter.api.Assertions.*;

import common.dataClasses.DataCollection;
import common.dataClasses.User;
import org.junit.jupiter.api.*;
import server.DBConnection;

public class UserDataSourceTest {

    private static UserDataSource userDataSource;

    @BeforeAll
    static void startTestMode(){
        DBConnection.setTestMode(true);
    }

    @AfterAll
    static void stopTestMode(){
        DBConnection.setTestMode(false);
    }

    @BeforeEach
    void setUP() throws Exception {
        userDataSource = new UserDataSource();
        RequestHandler.cleanDatabase();
    }

    @AfterEach
    void tearDown() throws Exception {
        RequestHandler.cleanDatabase();
    }

    @Test
    void addNewUser_getUser() throws Exception {
        User testuser = new User(1, "DuyPham",
                "new", "123", "user", 1).hashPassword();
        userDataSource.addUser(testuser);
        User userData = userDataSource.getUser(testuser.getUsername());
        assertEquals(testuser.getId(),userData.getId());
        assertEquals(testuser.getUsername(), userData.getUsername());
        assertEquals(testuser.getPassword(),
                userData.getPassword());
    }

    @Test
    void tooLongUserName() {
        User longgg = new User(1, "DuyPham",
                "looooooooooooooooooooooooooooo0000000000000000000000000oong",
                "123", "user", 1);
        assertThrows(Exception.class, () -> {
            userDataSource.addUser(longgg);}
        );
    }

    @Test
    void deleteUser() throws Exception {
        User testuser = new User(1, "DuyPham", "new", "123", "user", 1);
        userDataSource.addUser(testuser);
        userDataSource.deleteUser(1);
        User userData = userDataSource.getUser(testuser.getUsername());
        assertEquals(userData, null);
    }

    @Test
    void deleteNegativeId() throws Exception {
        assertThrows(Exception.class, () -> userDataSource.deleteUser(-11));
    }

    @Test
    void getUserList() throws Exception {
        User testuser1 = new User(0, "DuyPham", "new1", "123", "user", 1);
        User testuser2 = new User(1, "DuyPham", "new2", "123", "user", 1);
        User testuser3 = new User(2, "DuyPham", "new3", "123", "user", 1);
        userDataSource.addUser(testuser1);
        userDataSource.addUser(testuser2);
        userDataSource.addUser(testuser3);
        DataCollection<User> users = userDataSource.getUserList();
        assertEquals(users.get(0).getId(), testuser1.getId());
        assertEquals(users.get(1).getId(), testuser2.getId());
        assertEquals(users.get(2).getUsername(), testuser3.getUsername());
    }

    @Test
    void editUser() throws Exception {
        User testuser1 = new User(0, "DuyPham", "new1", "123", "user", 1).hashPassword();
        userDataSource.addUser(testuser1);
        User testuser1_New = new User(0, "NEW NAME", "NEW USER NAME", "NEW PASS", "NEW", 0).hashPassword();
        userDataSource.editUser(testuser1_New);
        User newData = userDataSource.getUser(testuser1_New.getUsername());

        assertEquals(testuser1_New.getUsername(), newData.getUsername());
        assertEquals(testuser1_New.getId(), newData.getId());
        assertEquals(testuser1_New.getFullName(), newData.getFullName());
        assertEquals(testuser1_New.getAccountType(), newData.getAccountType());
        assertEquals(testuser1_New.getUnitId(), newData.getUnitId());
        assertEquals(testuser1_New.getPassword(),newData.getPassword());
    }

}
