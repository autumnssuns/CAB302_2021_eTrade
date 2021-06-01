package server.DataSourceClasses;

import common.Response;

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
            String url = "jdbc:sqlite:cab302_eTrade.db";
            instance = DriverManager.getConnection(url);
            System.out.println("Connection established!");
        } catch (SQLException e){System.out.println(e.getMessage());}
    }

    /**
     * Drops the current database.
     * @throws IOException Thrown if the database cannot be dropped.
     */
    public static void dropDatabase() throws IOException {
        try {
            instance.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Dropping database...");
        Path databaseFilePath = Paths.get("cab302_eTrade.db");
        Files.deleteIfExists(databaseFilePath);
    }
}