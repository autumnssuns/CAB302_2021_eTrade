package server.DataSourceClasses;

import common.dataClasses.Asset;
import server.DBconnection;

import java.sql.*;

public class AssetsDataSource {
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `cab302_eTrade`.`assets` (\n" +
            "  `asset_id` INT NOT NULL,\n" +
            "  `asset_name` VARCHAR(16) NOT NULL,\n" +
            "  `asset_description` VARCHAR(256) NULL DEFAULT NULL,\n" +
            "  PRIMARY KEY (`asset_id`))\n" +
            "ENGINE = InnoDB;\n";
    private static final String ADD_ASSET = "INSERT INTO assets(asset_id, asset_name, asset_description) VALUES (?, ?, ?);";
    private static final String DELETE_ASSET = "DELETE FROM assets WHERE asset_name=?";
    private static final String GET_ASSET = "SELECT FROM assets WHERE asset_name=?";

    private Connection connection;
    private PreparedStatement addAsset;
    private PreparedStatement deleteAsset;
    private PreparedStatement getAsset;

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

    public void addAsset(Asset newasset){
        try {
            addAsset.setInt(1, newasset.getId());
            addAsset.setString(2, newasset.getName());
            addAsset.setString(3, newasset.getDescription());
            addAsset.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAsset(String Name){
        try {
            deleteAsset.setString(1, Name);
            deleteAsset.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Asset getAsset(String Name){
        Asset dummy = new Asset(-1,null, null);
        ResultSet rs = null;
        try {
            getAsset.setString(1, Name);
            rs = getAsset.executeQuery();
            dummy.setId(rs.getInt("asset_id"));
            dummy.setName(rs.getString("asset_name"));
            dummy.setDescription(rs.getString("asset_description"));
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
