package server.DataSourcesTest;

import static org.junit.jupiter.api.Assertions.*;

import common.dataClasses.DataCollection;
import common.dataClasses.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Excluded_PUT_ALL_EXCLUSIONS_HERE.DataSourceClasses.UserDataSource;
import server.WorkingFeatures_PLEASE_DO_NOT_EXCLUDE.HashPassword;

import java.sql.SQLException;

public class UserDataSourceTest {

    private static UserDataSource userDataSource;

    @BeforeEach
    void setUP()
    {
        userDataSource = new UserDataSource();
        userDataSource.DeleteAll();
    }


    @Test
    void  addNewUser_getUser()
    {
        User testuser = new User(1, "DuyPham", "new", "123", "user", 1);
        userDataSource.addUser(testuser);
        User userData = userDataSource.getUser(testuser.getUsername());
        assertEquals(testuser.getUserId(),userData.getUserId());
        assertEquals(testuser.getUsername(), userData.getUsername());
        assertEquals(HashPassword.HashPassword(HashPassword.HashPassword(testuser.getPassword())),
                HashPassword.HashPassword(userData.getPassword()));
    }

    @Test
    void deleteUser() {
        User testuser = new User(1, "DuyPham", "new", "123", "user", 1);
        userDataSource.addUser(testuser);
        userDataSource.deleteUser(testuser.getUserId());
        User userData = userDataSource.getUser(testuser.getUsername());
        assertEquals(userData, null);

    }



    @Test
    void getUserList() {
        User testuser1 = new User(0, "DuyPham", "new1", "123", "user", 1);
        User testuser2 = new User(1, "DuyPham", "new2", "123", "user", 1);
        User testuser3 = new User(2, "DuyPham", "new3", "123", "user", 1);
        userDataSource.addUser(testuser1);
        userDataSource.addUser(testuser2);
        userDataSource.addUser(testuser3);
        DataCollection<User> users = userDataSource.getUserList();
        assertEquals(users.get(0).getUserId(), testuser1.getUserId());
        assertEquals(users.get(1).getUserId(), testuser2.getUserId());
        assertEquals(users.get(2).getUserId(), testuser3.getUserId());
    }

    @Test
    void editUser() {
        User testuser1 = new User(0, "DuyPham", "new1", "123", "user", 1);
        userDataSource.addUser(testuser1);
        User testuser1_New = new User(0, "NEW NAME", "NEW USER NAME", "NEW PASS", "NEW", 0);
        userDataSource.editUser(testuser1_New);
        User newData = userDataSource.getUser(testuser1_New.getUsername());
        assertEquals(newData.getUsername(),testuser1_New.getUsername());
        assertEquals(newData.getUserId(),testuser1_New.getUserId());
        assertEquals(newData.getFullName(),testuser1_New.getFullName());
        assertEquals(newData.getAccountType(), testuser1_New.getAccountType());
        assertEquals(newData.getUnitId(), testuser1_New.getUnitId());
        assertEquals(HashPassword.HashPassword(HashPassword.HashPassword(testuser1_New.getPassword())),
                HashPassword.HashPassword(newData.getPassword()));
    }

}