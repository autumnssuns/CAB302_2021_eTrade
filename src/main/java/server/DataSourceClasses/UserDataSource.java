package server.DataSourceClasses;

import common.dataClasses.DataCollection;
import common.dataClasses.User;
import org.sonatype.guice.asm.Type;
import server.WorkingFeatures_PLEASE_DO_NOT_EXCLUDE.HashPassword;

import java.sql.*;

/**
 * Provides needed functions to interact with "users" database for data
 */
public class UserDataSource extends DataSource {
    //Create the environment
    //SQL queries
    private static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS users (\n" +
                    "    user_id         INT          NOT NULL,\n" +
                    "    fullname        VARCHAR (50) NOT NULL,\n" +
                    "    username        VARCHAR (20) NOT NULL,\n" +
                    "    password        VARCHAR (32) NOT NULL,\n" +
                    "    user_type       VARCHAR (5)  NOT NULL\n" +
                    "                                 DEFAULT 'user',\n" +
                    "    organisation_id INT          DEFAULT NULL,\n" +
                    "    PRIMARY KEY (\n" +
                    "        username\n" +
                    "    ),\n" +
                    "    CONSTRAINT user_organisaion\n" +
                    ");";
    private static final String ADD_USER = "INSERT INTO users(user_id, fullname, username, password, user_type, organisation_id) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String DELETE_USER = "DELETE FROM users WHERE user_id = ?";
    private static final String GET_USER = "SELECT * FROM users WHERE username = ?";
    private static final String GET_ALL_USER = "SELECT * FROM users";
    private static final String EDIT_USER =
            "UPDATE users \n" +
                    "SET " +
                    "fullname = ?, username = ?, password = ?," +
                    " user_type = ?, organisation_id = ? \n" +
                    "WHERE \n" +
                    "user_id = ?";
    private static final  String DELETE_ALL = "DELETE FROM users";

    //Prepared statements
    private PreparedStatement addUser;
    private PreparedStatement deleteUser;
    private PreparedStatement getUser;
    private PreparedStatement editUser;
    private PreparedStatement getAllUser;
    private PreparedStatement deleteAll;

    /**
     * Connect to database then create the table if not exist.
     */
    public UserDataSource() {
        connection = DBConnection.getInstance();
        try {
            Statement st = connection.createStatement();
            st.execute(CREATE_TABLE);
            addUser = connection.prepareStatement(ADD_USER);
            deleteUser = connection.prepareStatement(DELETE_USER);
            getUser = connection.prepareStatement(GET_USER);
            editUser = connection.prepareStatement(EDIT_USER);
            getAllUser = connection.prepareStatement(GET_ALL_USER);
            deleteAll = connection.prepareStatement(DELETE_ALL);
        } catch (SQLException e)
        {e.printStackTrace();}
    }


    public void deleteAll() {
        try {
            deleteAll.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a new user to the table if not exists
     * @param newuser user object to add
     */
        public void addUser(User newuser){
        try{
            //Set values for the above SQL query
            addUser.setInt(1,newuser.getUserId());
            addUser.setString(2, newuser.getFullName());
            addUser.setString(3, newuser.getUsername());
            addUser.setString(4, newuser.getPassword());
            addUser.setString(5, newuser.getAccountType());
            if (newuser.getUnitId() == null){
                addUser.setNull(6, Type.INT);
            }
            else{
                addUser.setInt(6, newuser.getUnitId());
            }

            addUser.executeUpdate();
        } catch (SQLException e) {e.printStackTrace();}
    }

    /**
     * Delete a user from the table if exists
     * @param userId
     */
    public void deleteUser(int userId){
        try {
            //Set values for the above SQL query
            deleteUser.setInt(1, userId);
            deleteUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return a user in the database
     * @param userName Name of user wanted to return
     * @return all details of an User object
     */
    public User getUser(String userName) {
        //Create a dummy to store all information then return the dummy later
        User dummy = null;
        ResultSet rs;
        try {
            //Set values for the above SQL query
            getUser.setString(1, userName);
            rs = getUser.executeQuery();
            while (rs.next()) {
                Integer unitId = (Integer) rs.getObject("organisation_id");
                dummy = new User(
                //Stores values into dummy object
                rs.getInt("user_id"),
                rs.getString("fullname"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("user_type"),
                unitId
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dummy;
    }

    //Todo: Get User list method
    public DataCollection<User> getUserList(){
        DataCollection<User> users = new DataCollection<>();
        try {
            ResultSet rs = getAllUser.executeQuery();
            while (rs.next()){
                Integer unitId = (Integer) rs.getObject("organisation_id");
                users.add(new User(
                        rs.getInt("user_id"),
                        rs.getString("fullname"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("user_type"),
                        unitId));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * A method to update an asset information on  database
     * @param userNewInfo an Asset class object containing new data
     */
    public void editUser(User userNewInfo)  {
        try {
            editUser.setString(1, userNewInfo.getFullName());
            editUser.setString(2, userNewInfo.getUsername());
            editUser.setString(3, userNewInfo.getPassword());
            editUser.setString(4, userNewInfo.getAccountType());
            editUser.setInt(5, userNewInfo.getUnitId());
            editUser.setInt(6, userNewInfo.getUserId());
            editUser.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
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
