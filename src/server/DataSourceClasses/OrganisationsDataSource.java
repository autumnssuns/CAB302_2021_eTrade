package server.DataSourceClasses;

import common.dataClasses.Organisation;
import server.DBconnection;

import java.sql.*;

/*GONNA DO THIS DATASOURCE LATER*/
public class OrganisationsDataSource {
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `cab302_eTrade`.`organisations` (\n" +
            "  `organisation_id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `organisation_name` VARCHAR(16) NOT NULL,\n" +
            "  `credits` DECIMAL(2) NOT NULL DEFAULT 0,\n" +
            "  PRIMARY KEY (`organisation_id`))\n" +
            "ENGINE = InnoDB;";
    private static final String ADD_ORGANISATION = "INSERT INTO organisations(organisation_id, organisation_name, credits) VALUES (?, ?, ?);";
    private static final String DELETE_ORGANISATION = "DELETE FROM organisations WHERE organisation_name=?";
    private static final String GET_ORGANISATION = "SELECT FROM organisations WHERE organisation_name=?";

    private Connection connection;
    private PreparedStatement addOrganisation;
    private PreparedStatement deleteOrganisation;
    private PreparedStatement getOrganisation;

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

    public void addOrganisation(Organisation newOrganisation){
        try {
            addOrganisation.setInt(1, newOrganisation.getId());
            addOrganisation.setString(2, newOrganisation.getName());
            addOrganisation.setFloat(3, newOrganisation.getBalance());
            addOrganisation.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrganisation(String Name){
        try {
            deleteOrganisation.setString(1, Name);
            deleteOrganisation.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Organisation getOrganisation(String Name){
        Organisation dummy = new Organisation(-1,null, -1);
        ResultSet rs = null;
        try {
            getOrganisation.setString(1, Name);
            rs = getOrganisation.executeQuery();
            dummy.setId(rs.getInt("organisation_id"));
            dummy.setName(rs.getString("organisation_name"));
            dummy.setBalance(rs.getFloat("credits"));
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
