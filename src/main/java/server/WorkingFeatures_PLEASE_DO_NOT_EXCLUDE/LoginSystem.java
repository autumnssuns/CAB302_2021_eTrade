package server.WorkingFeatures_PLEASE_DO_NOT_EXCLUDE;

import server.DBconnection;
import server.WorkingFeatures_PLEASE_DO_NOT_EXCLUDE.HashPassword;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.*;


public class LoginSystem {

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException
    {
        register("Duy", "123456");
        login("Duy", "123456");
    }

/*register method*/

    /**
     *
     * @param UserName get the AccountName of user
     * @param PassWord get the Password of user
     * @return True if login successfully and False if fail
     */
    public static boolean register(String UserName, String PassWord) {
        // "status" available for demonstrating the behaviour of the method.
        Boolean status = false;
        Connection conn;
        //Hash the password
        String HashedPassword = HashPassword.HashPassword(PassWord);
        try {
            // create a connection to the database
            conn = DBconnection.getInstance();

            //create statement for sql queries
            Statement statement = conn.createStatement();


            //input username (and password) to check for existed account in the database
            try {
                String Insertquery = String.format("INSERT INTO users (AccountName, Password) VALUES ('%s', '%s');", UserName, HashedPassword);
                statement.executeUpdate(Insertquery);
                status = true;
            } catch (SQLException e) {
                System.out.println("AccountName already Exists! \n false" );
            }

            //temporary feature: tracking the status for unit testing and print out notification
            if (status) {
                System.out.println("Register Successfully!");
                System.out.println(status);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        // Return boolean result for checking the behaviour
        return status;
    }

/*Login method*/
    /**
     *
     * @param UserName get UserName input
     * @param PassWord get Password input
     * @return True if successfully register for an account and False if fail
     */
    public static boolean login(String UserName, String PassWord) {
        // "status" available for demonstrating the behaviour of the method.
        Boolean status = false;
        ResultSet result;
        Connection conn;
        //Hash password
        String HashedPassword = HashPassword.HashPassword(PassWord);
        try {
            //connect to database
            conn = DBconnection.getInstance();
            //create statement for sql queries
            Statement statement = conn.createStatement();

            //create queries, input the username and password keywords for authentication.
            String sql = String.format("SELECT * FROM users WHERE AccountName = '%s'  AND Password = '%s';",UserName,HashedPassword);
            result = statement.executeQuery(sql);
            //checking each values to be the same (Password and Account)
            while (result.next()) {
                String databaseUserName = result.getString("AccountName");
                String databasePassWord = result.getString("Password");
                if (UserName.equals(databaseUserName) && HashedPassword.equals(databasePassWord)) {
                    status = true;
                }
            }
            //temporary feature: tracking the status for unit testing and print out notification
            if (status) {
                System.out.println("Login Successfully!");
                System.out.println(status);
            } else {
                System.out.println("Incorrect Password OR Invalid AccountName!");
                System.out.println(status);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // return boolean value for future method
        return status;
    }


// Some methods to find an existed element inside any database for unit testing (in this situation just need to go for any -
// i chose first element in the database).


    //This method is looking for the existed Account Name
    /**
     *
     * @return String of existed AccountName
     */
    public static String FindExistAccountName() {
        //prepare statements
        Statement statement;
        //query to limit the searching result into one for matching
        String Query = "SELECT * FROM users LIMIT 1;";
        String existName = null;


            try {
                //connect to database
                Connection conn = DBconnection.getInstance();
                statement = conn.createStatement();
                ResultSet name = statement.executeQuery(Query);
                while (name.next()){
                existName = name.getString("AccountName");}

            } catch (SQLException e) {
                e.printStackTrace();
            }
        //return the existed name
        return existName;
    }

    //This method is looking for existed Password
    /**
     *
     * @return String of existed Password
     */
    public static String FindExistPassword() {

        Statement statement;
        //query to limit the searching result into one
        String Query = "SELECT * FROM users LIMIT 1;";

        String existPass = null;
            try {
                //connect to database
                Connection conn = DBconnection.getInstance();
                statement = conn.createStatement();
                ResultSet name = statement.executeQuery(Query);
                while (name.next()) {
                    existPass = name.getString("Password");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        //return the existed password
        return existPass;
    }
}



