package server.Excluded_PUT_ALL_EXCLUSIONS_HERE.Features;

import common.dataClasses.*;
import server.Excluded_PUT_ALL_EXCLUSIONS_HERE.DataSourceClasses.UserDataSource;

public class TestDatabase {
    public static void main(String[] args){
        UserDataSource users = new UserDataSource();
        User testuser = new User(0, "Rodo", "DUYANH", "123", "user", 1);
        users.addUser(testuser);
    }
}
