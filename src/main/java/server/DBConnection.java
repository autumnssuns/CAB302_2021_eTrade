package server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    /**
     * The singleton instance of the database connection.
     */
    private static Connection instance = null;

    private static boolean isTestDatabase = false;

    /**
     * Determines whether or not the connection is used for testing (which redirects it to the test database).
     * @param testMode true if the database is in test mode, false otherwise.
     */
    public static void setTestMode(boolean testMode){
        isTestDatabase = testMode;
        if (instance != null) {
            try {
                instance.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        new DBConnection();
    }

    /**
     * Provides global access to the singleton instance of the UrlSet.
     * @return a handle to the singleton instance of the UrlSet.
     */
    public static Connection getInstance() {
        if (instance == null) {
            new DBConnection();
        }
        return instance;
    }

    /**
     * Constructor initializes the connection.
     */
    private DBConnection(){
        try{
            //String url = "jdbc:sqlite:/D:\\SQLite\\sqlite-tools-win32-x86-3350400\\Accounts.db";
            String url;
            // Test database
            if (isTestDatabase){
                url = "jdbc:sqlite:cab302_eTrade_test.db";
            }
            // Deploy database
            else{
                url = "jdbc:sqlite:cab302_eTrade.db";
            }
            instance = DriverManager.getConnection(url);
            System.out.println("Connection established!");
        } catch (SQLException e){System.out.println(e.getMessage());}
    }
}