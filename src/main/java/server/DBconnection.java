package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
    /**
     * create statement for database connection.
     */
    private static Connection instance = null;


    /**
     * Provides global access to the singleton instance of the UrlSet.
     * @return a handle to the singleton instance of the UrlSet.
     */
    public static Connection getInstance() {
        if (instance == null) {
             DBConnection();
        }
        return instance;
    }

    /**
     * Constructor initializes the connection.
     */
    private static void DBConnection(){
        try{
            //String url = "jdbc:sqlite:/D:\\SQLite\\sqlite-tools-win32-x86-3350400\\Accounts.db";
            String url = "jdbc:sqlite:cab302_eTrade.db";
            instance = DriverManager.getConnection(url);
            System.out.println("Connection established!");
        } catch (SQLException e){System.out.println(e.getMessage());}
    }
}
