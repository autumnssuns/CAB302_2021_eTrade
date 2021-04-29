package server.DataSourceClasses;

import common.dataClasses.Asset;
import server.DBconnection;

import java.sql.*;
/**
 * Provides needed functions to interact with "assets" database for data
 */
public class AssetsDataSource {
    //Setting up the environment.
    //SQL queries.
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `cab302_eTrade`.`assets` (\n" +
            "  `asset_id` INT NOT NULL,\n" +
            "  `asset_name` VARCHAR(16) NOT NULL,\n" +
            "  `asset_description` VARCHAR(256) NULL DEFAULT NULL,\n" +
            "  PRIMARY KEY (`asset_id`))\n" +
            "ENGINE = InnoDB;\n";
    private static final String ADD_ASSET = "INSERT INTO assets(asset_id, asset_name, asset_description) VALUES (?, ?, ?);";
    private static final String DELETE_ASSET = "DELETE FROM assets WHERE asset_name=?";
    private static final String GET_ASSET = "SELECT FROM assets WHERE asset_name=?";
    //Prepare statements.
    private Connection connection;
    private PreparedStatement addAsset;
    private PreparedStatement deleteAsset;
    private PreparedStatement getAsset;

    /**
     * Connect to the database then create one if not exists
     */
    public AssetsDataSource() {
        connection = DBconnection.getInstance();
        try {
            Statement st = connection.createStatement();
            st.execute(CREATE_TABLE);
            addAsset = connection.prepareStatement(ADD_ASSET);
            deleteAsset = connection.prepareStatement(DELETE_ASSET);
            getAsset = connection.prepareStatement(GET_ASSET);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add new asset into the database.
     * @param newasset Asset Object wanted to add
     */
    public void addAsset(Asset newasset){
        try {
            //Insert values into above query string
            addAsset.setInt(1, newasset.getId());
            addAsset.setString(2, newasset.getName());
            addAsset.setString(3, newasset.getDescription());
            //execute the query
            addAsset.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete an asset from the database
     * @param Name a String Name of the asset wanted to delete
     */
    public void deleteAsset(String Name){
        try {
            deleteAsset.setString(1, Name);
            deleteAsset.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return an existed asset from the database
     * @param Name String Name of the asset wanted to return.
     * @return Asset Object
     */
    public Asset getAsset(String Name){
        //create a dummy object to store values
        Asset dummy = new Asset(-1,null, null);
        ResultSet rs = null;
        try {
            //Insert value into query string above
            getAsset.setString(1, Name);
            rs = getAsset.executeQuery();
            //Store values into the dummy
            dummy.setId(rs.getInt("asset_id"));
            dummy.setName(rs.getString("asset_name"));
            dummy.setDescription(rs.getString("asset_description"));
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
