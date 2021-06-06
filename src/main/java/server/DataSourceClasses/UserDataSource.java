package server.DataSourceClasses;

import common.dataClasses.DataCollection;
import common.dataClasses.User;
import org.sonatype.guice.asm.Type;
import server.DBConnection;

import java.sql.*;

/**
 * Provides needed functions to interact with "users" database for data
 */
public class UserDataSource extends DataSource {

    // SQL query strings
    private static final String CREATE_TABLE =
            """
                    CREATE TABLE IF NOT EXISTS users (
                        user_id         INT          NOT NULL,
                        fullname        VARCHAR (50) NOT NULL,
                        username        VARCHAR (20) NOT NULL,
                        password        VARCHAR (32) NOT NULL,
                        user_type       VARCHAR (5)  NOT NULL
                                                     DEFAULT 'user',
                        organisation_id INT          DEFAULT NULL,
                        PRIMARY KEY (
                            username
                        ));""";
    private static final String ADD_USER = "INSERT INTO users(user_id, fullname, username, password, user_type, organisation_id) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String DELETE_USER = "DELETE FROM users WHERE user_id = ?";
    private static final String GET_USER = "SELECT * FROM users WHERE username = ?";
    private static final String GET_ALL_USER = "SELECT * FROM users";
    private static final String EDIT_USER =
            """
                    UPDATE users\n
                    SET fullname = ?, username = ?, password = ?, user_type = ?, organisation_id = ?\n
                    WHERE user_id = ?""";
    private static final  String DELETE_ALL = "DELETE FROM users";
    protected static final String GET_MAX_ID = "SELECT user_id FROM users";

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
    public UserDataSource() throws SQLException {
        connection = DBConnection.getInstance();

        Statement st = connection.createStatement();
        st.execute(CREATE_TABLE);
        addUser = connection.prepareStatement(ADD_USER);
        deleteUser = connection.prepareStatement(DELETE_USER);
        getUser = connection.prepareStatement(GET_USER);
        editUser = connection.prepareStatement(EDIT_USER);
        getAllUser = connection.prepareStatement(GET_ALL_USER);
        deleteAll = connection.prepareStatement(DELETE_ALL);
        getMaxId = connection.prepareStatement(GET_MAX_ID);

        // Check and add an admin account if does not have any
        boolean hasAdmin = false;
        for (User user : getUserList()){
            hasAdmin = user.getAccountType().equals("admin");
            if (hasAdmin) {
                break;
            }
        }
        if(!hasAdmin){
            addUser(new User(null, "Admin",
                    "admin", "root", "admin", null).hashPassword());
        }
    }



    /**
     * Delete all users from the database
     */
    public void deleteAll() throws SQLException {
        deleteAll.executeUpdate();
    }

    /**
     * Add a new user to the table if not exists
     * @param newUser user object to add
     */
    public void addUser(User newUser) throws Exception {
        if (newUser.getUsername().length() > 20) {
            throw new Exception("Username exceeds the permitted length");
        }
        try{
            //Set values for the above SQL query
            int newUserId = newUser.getId() == null ? getNextId() : newUser.getId();
            addUser.setInt(1,newUserId);
            addUser.setString(2, newUser.getFullName());
            addUser.setString(3, newUser.getUsername());
            addUser.setString(4, newUser.getPassword());
            addUser.setString(5, newUser.getAccountType());
            if (newUser.getUnitId() == null){
                addUser.setNull(6, Type.INT);
            }
            else{
                addUser.setInt(6, newUser.getUnitId());
            }
            addUser.executeUpdate();
        } catch (SQLException e) {e.printStackTrace();}
    }

    /**
     * Delete a user from the table if exists
     * @param userId id of user
     */
    public void deleteUser(int userId) throws Exception {
        if (userId < 0) {
            throw new Exception("Negative user id is not allowed!");
        }
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
     * @return all details of an UserGUI object
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

    /**
     * Get all users from the database
     * @return
     */
    public DataCollection<User> getUserList(){
        DataCollection<User> users = new DataCollection<>();
        try {
            ResultSet rs = getAllUser.executeQuery();
            while (rs.next()){
                users.add(getUser(rs.getString("username")));
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
            editUser.setInt(6, userNewInfo.getId());
            editUser.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}