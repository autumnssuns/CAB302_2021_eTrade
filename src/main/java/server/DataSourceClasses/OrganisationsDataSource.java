package server.DataSourceClasses;

import common.dataClasses.DataCollection;
import common.dataClasses.OrganisationalUnit;

import java.sql.*;

/**
 * Provides needed functions to interact with "organisationalUnits" database for data
 */
public class OrganisationsDataSource {
    //Create environment
    //SQL queries
    private static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS     organisationalUnits (\n" +
            "    organisation_id            INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    organisation_name          VARCHAR(16) NOT NULL,\n" +
            "    credits  DECIMAL(2)        NOT NULL DEFAULT 0\n" +
            "    );";
    private static final String ADD_ORGANISATION = "INSERT INTO organisationalUnits(organisation_id, organisation_name, credits) VALUES (?, ?, ?);";
    private static final String DELETE_ORGANISATION = "DELETE FROM organisationalUnits WHERE organisation_id=?";
    private static final String GET_ORGANISATION = "SELECT * FROM organisationalUnits WHERE organisation_id=?";
    private static final String GET_ALL_ORGANISATION = "SELECT * FROM organisationalUnits";
    private static final String EDIT_ORGANISATION =
            "UPDATE organisationalUnits\n" +
                    "SET organisation_name=?, credits=?" +
                    "WHERE organisation_id=?";
    private static final String DELETE_ALL = "DELETE FROM organisationalUnits";
    //Prepared statements
    private Connection connection;
    private PreparedStatement addOrganisation;
    private PreparedStatement deleteOrganisation;
    private PreparedStatement getOrganisation;
    private PreparedStatement editOrganisation;
    private PreparedStatement getAllOrganisation;
    private PreparedStatement deleteAll;

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
            editOrganisation = connection.prepareStatement(EDIT_ORGANISATION);
            getAllOrganisation = connection.prepareStatement(GET_ALL_ORGANISATION);
            deleteAll = connection.prepareStatement(DELETE_ALL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll()
    {
        try {
            deleteAll.executeUpdate();
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
            addOrganisation.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete an OrganisationalUnit if exists
     * @param id
     */
    public void deleteOrganisation(int id){
        try {
            //set values into the above query
            deleteOrganisation.setInt(1, id);
            deleteOrganisation.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return already existed organisational unit.
     * @param id OrganisationalUnit Object input to get values from
     * @return Object-OrganisationalUnit
     */
    public OrganisationalUnit getOrganisation(int id) {
        //Create dummy object to store data
        OrganisationalUnit dummy = null;
        ResultSet rs = null;
        try {
            getOrganisation.setInt(1, id);
            rs = getOrganisation.executeQuery();
            //Store data into the dummy
            dummy = new OrganisationalUnit(rs.getInt("organisation_id"),
                        rs.getString("organisation_name"),
                    rs.getFloat("credits"));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dummy;
    }

    //Todo: Get Organisation list method
    public DataCollection<OrganisationalUnit> getOrganisationList(){
        DataCollection<OrganisationalUnit> Organisations = new DataCollection<>();
        try {
            ResultSet rs = getAllOrganisation.executeQuery();
            while (rs.next()){

                Organisations.add(new OrganisationalUnit(
                        rs.getInt("organisation_id"),
                        rs.getString("organisation_name"),
                        rs.getFloat("credits")
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Organisations;
    }
    /**
     * A method to update an Organisational Unit information on  database
     * @param organisationNewInfo an Organisational Unit class object containing new data
     */
    public void editOrganisation(OrganisationalUnit organisationNewInfo)  {
        try {
            editOrganisation.setString(1, organisationNewInfo.getName());
            editOrganisation.setFloat(2, organisationNewInfo.getBalance());
            editOrganisation.setInt(3, organisationNewInfo.getId());
            editOrganisation.executeUpdate();
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
