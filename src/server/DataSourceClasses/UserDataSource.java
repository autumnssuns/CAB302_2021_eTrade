package server.DataSourceClasses;

import common.dataClasses.User;
import server.DBconnection;

import java.sql.*;

/**
 * Provides needed functions to interact with "users" database for data
 */
public class UserDataSource {
    //Create the environment
    //SQL queries
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `cab302_eTrade`.`users` (\n" +
            "  `username` VARCHAR(16) NOT NULL,\n" +
            "  `password` VARCHAR(32) NOT NULL,\n" +
            "  `user_type` ENUM('user', 'admin') NOT NULL DEFAULT 'user',\n" +
            "  `organisation_id` INT NULL DEFAULT NULL,\n" +
            "  PRIMARY KEY (`username`),\n" +
            "  CONSTRAINT `user_organisaion`\n" +
            "    FOREIGN KEY (`organisation_id`)\n" +
            "    REFERENCES `cab302_eTrade`.`organisationalUnits` (`organisation_id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION)\n" +
            "ENGINE = InnoDB;\n" +
            "\n" +
            "CREATE INDEX `organisaion_idx` ON `cab302_eTrade`.`users` (`organisation_id` ASC) VISIBLE;\n" +
            "\n" +
            "\n" +
            "SET SQL_MODE=@OLD_SQL_MODE;\n" +
            "SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;\n" +
            "SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;";
    private static final String ADD_USER = "INSERT INTO users(username, password, user_type, organisation_id) VALUES (?, ?, ?, ?);";
    private static final String DELETE_USER = "DELETE FROM users WHERE username=?";
    private static final String GET_USER = "SELECT FROM users WHERE username=?";

    //Prepared statements
    private Connection connection;
    private PreparedStatement addUser;
    private PreparedStatement deleteUser;
    private PreparedStatement getUser;

    /**
     * Connect to database then create the table if not exist.
     */
    public UserDataSource() {
        connection = DBconnection.getInstance();
        try {
            Statement st = connection.createStatement();
            st.execute(CREATE_TABLE);
            addUser = connection.prepareStatement(ADD_USER);
            deleteUser = connection.prepareStatement(DELETE_USER);
            getUser = connection.prepareStatement(GET_USER);
        } catch (SQLException e)
        {e.printStackTrace();}
    }

    /**
     * Add a new user to the table if not exists
     * @param newuser user object to add
     */
    public void addUser(User newuser){
        try{
            //Set values for the above SQL query
            addUser.setString(1, newuser.getUsername());
            addUser.setString(2, newuser.getPassword());
            addUser.setString(3, newuser.getAccountType());
            addUser.setInt(4, newuser.getUnitId());
            addUser.executeQuery();
        } catch (SQLException e) {e.printStackTrace();}
    }

    /**
     * Delete a user from the table if exists
     * @param UserName
     */
    public void deleteUser(String UserName){
        try {
            //Set values for the above SQL query
            deleteUser.setString(1, UserName);
            deleteUser.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return a user in the database
     * @param UserName Name of user wanted to return
     * @return all details of an User object
     */
    public User getUser(String UserName) {
        //Create a dummy to store all information then return the dummy later
        User dummy = new User(-1,null,null,null,null,-1);
        ResultSet rs = null;
        try {
            //Set values for the above SQL query
            getUser.setString(1, UserName);
            rs = getUser.executeQuery();
            //Stores values into dummy object
            dummy.setUsername(rs.getString("username"));
            dummy.setPassword(rs.getString("password"));
            dummy.setAccountType(rs.getString("user_type"));
            dummy.setOrganisation(rs.getInt("organisation_id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dummy;
    }

    /**
     * Close the connection to database
     */
    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
