package server.DataSourceClasses;

import common.dataClasses.User;
import server.DBconnection;

import java.sql.*;

public class UserDataSource {
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `cab302_eTrade`.`users` (\n" +
            "  `username` VARCHAR(16) NOT NULL,\n" +
            "  `password` VARCHAR(32) NOT NULL,\n" +
            "  `user_type` ENUM('user', 'admin') NOT NULL DEFAULT 'user',\n" +
            "  `organisation_id` INT NULL DEFAULT NULL,\n" +
            "  PRIMARY KEY (`username`),\n" +
            "  CONSTRAINT `user_organisaion`\n" +
            "    FOREIGN KEY (`organisation_id`)\n" +
            "    REFERENCES `cab302_eTrade`.`organisations` (`organisation_id`)\n" +
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

    private Connection connection;
    private PreparedStatement addUser;
    private PreparedStatement deleteUser;
    private PreparedStatement getUser;

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

    public void addUser(User newuser){
        try{
            addUser.setString(1, newuser.getUsername());
            addUser.setString(2, newuser.getPassword());
            addUser.setString(3, newuser.getAccountType());
            addUser.setInt(4, newuser.getOrganisationId());
            addUser.executeQuery();
        } catch (SQLException e) {e.printStackTrace();}
    }

    public void deleteUser(String UserName){
        try {
            deleteUser.setString(1, UserName);
            deleteUser.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUser(String UserName) {
        User dummy = new User(null,null,null,-1);
        ResultSet rs = null;
        try {
            getUser.setString(1, UserName);
            rs = getUser.executeQuery();
            dummy.setUsername(rs.getString("username"));
            dummy.setPassword(rs.getString("password"));
            dummy.setAccountType(rs.getString("user_type"));
            dummy.setOrganisation(rs.getInt("organisation_id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dummy;
    }

    public void close() {
        /* BEGIN MISSING CODE */
        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        /* END MISSING CODE */
    }


}
