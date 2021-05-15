package server.DataSourceClasses;

import common.dataClasses.OrganisationalUnit;
import server.DBconnection;

import java.sql.*;

/**
 * Provides needed functions to interact with "organisationalUnits" database for data
 */
public class OrganisationsDataSource {
    //Create environment
    //SQL queries
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `cab302_eTrade`.`organisationalUnits` (\n" +
            "  `organisation_id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `organisation_name` VARCHAR(16) NOT NULL,\n" +
            "  `credits` DECIMAL(2) NOT NULL DEFAULT 0,\n" +
            "  PRIMARY KEY (`organisation_id`))\n" +
            "ENGINE = InnoDB;";
    private static final String ADD_ORGANISATION = "INSERT INTO organisationalUnits(organisation_id, organisation_name, credits) VALUES (?, ?, ?);";
    private static final String DELETE_ORGANISATION = "DELETE FROM organisationalUnits WHERE organisation_name=?";
    private static final String GET_ORGANISATION = "SELECT FROM organisationalUnits WHERE organisation_name=?";

    //Prepared statements
    private Connection connection;
    private PreparedStatement addOrganisation;
    private PreparedStatement deleteOrganisation;
    private PreparedStatement getOrganisation;

    /**
     * Connect to the database then create table if not exists
     */
    public OrganisationsDataSource() {
        connection = DBconnection.getInstance();
        try {
            Statement st = connection.createStatement();
            st.execute(CREATE_TABLE);
            addOrganisation = connection.prepareStatement(ADD_ORGANISATION);
            deleteOrganisation = connection.prepareStatement(DELETE_ORGANISATION);
            getOrganisation = connection.prepareStatement(GET_ORGANISATION);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add new organisational unit if not exists
     * @param newOrganisationalUnit OrganisationalUnit object to input
     */
    public void addOrganisation(OrganisationalUnit newOrganisationalUnit){
        try {
            //set values into the above query
            addOrganisation.setInt(1, newOrganisationalUnit.getId());
            addOrganisation.setString(2, newOrganisationalUnit.getName());
            addOrganisation.setFloat(3, newOrganisationalUnit.getBalance());
            addOrganisation.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete an OrganisationalUnit if exists
     * @param Name
     */
    public void deleteOrganisation(String Name){
        try {
            //set values into the above query
            deleteOrganisation.setString(1, Name);
            deleteOrganisation.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return already existed organisational unit.
     * @param Name OrganisationalUnit Object input to get values from
     * @return Object-OrganisationalUnit
     */
    public OrganisationalUnit getOrganisation(String Name){
        //Create dummy object to store data
        OrganisationalUnit dummy = new OrganisationalUnit(-1,null, -1);
        ResultSet rs = null;
        try {
            getOrganisation.setString(1, Name);
            rs = getOrganisation.executeQuery();
            //Store data into the dummy
            dummy.setId(rs.getInt("organisation_id"));
            dummy.setName(rs.getString("organisation_name"));
            dummy.setBalance(rs.getFloat("credits"));
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
