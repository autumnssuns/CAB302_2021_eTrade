package server.DataSourcesTest;

import static org.junit.jupiter.api.Assertions.*;
import common.dataClasses.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Excluded_PUT_ALL_EXCLUSIONS_HERE.DataSourceClasses.UserDataSource;
import server.WorkingFeatures_PLEASE_DO_NOT_EXCLUDE.HashPassword;

public class UserDataSourceTest {
    private static UserDataSource userDataSource;
    @BeforeEach
    void setUP()
    {
        userDataSource = new UserDataSource();
        userDataSource.DeleteAll();
    }
    @AfterEach
    void tearDown()
        {
            userDataSource.close();
        }

    @Test
    void  addNewUser()
    {
        User testuser = new User(1, "DuyPham", "new", "123", "user", 1);
        userDataSource.addUser(testuser);
        User userData = userDataSource.getUser(testuser.getUsername());
        assertEquals(testuser.getUserId(),userData.getUserId());
        assertEquals(testuser.getUsername(), userData.getUsername());
        assertEquals(HashPassword.HashPassword(HashPassword.HashPassword(testuser.getPassword())),
                HashPassword.HashPassword(userData.getPassword()));
    }

//    @Test
//    void deleteUser() {
//
//    }
//
//    @Test
//    void getUser() {
//    }
//
//    @Test
//    void getUserList() {
//    }
//
//    @Test
//    void editUser() {
//    }
//
//    @Test
//    void close() {
//    }
}
