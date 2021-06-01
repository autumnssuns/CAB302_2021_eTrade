package server.Excluded_PUT_ALL_EXCLUSIONS_HERE.DataSourceClasses;

import common.Exceptions.InvalidArgumentValueException;
import common.dataClasses.Asset;
import common.dataClasses.DataCollection;
import server.DBconnection;

import java.sql.*;
/**
 * Provides needed functions to interact with "assets" database for data
 */
public class AssetsDataSource extends DataSource {
    //Setting up the environment.
    //SQL queries.
    private static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS assets (\n" +
                    "    asset_id          INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                    "    asset_name        VARCHAR (16)  NOT NULL,\n" +
                    "    asset_description VARCHAR (256) DEFAULT NULL\n" +
                    ");";
    private static final String ADD_ASSET = "INSERT INTO assets (asset_id, asset_name, asset_description) \nVALUES (?, ?, ?);";
    private static final String DELETE_ASSET = "DELETE FROM assets WHERE asset_id=?";
    private static final String DELETE_ALL_ASSET = "DELETE FROM assets";
    private static final String GET_ASSET = "SELECT * FROM assets WHERE asset_id=?";
    private static final String GET_ALL_ASSET = "SELECT * FROM assets";
    private static final String EDIT_ASSET =
            "UPDATE assets\n" +
                    "SET asset_name=?, asset_description=?\n" +
                    "WHERE asset_id=?";

    //Prepare statements.
    private PreparedStatement addAsset;
    private PreparedStatement deleteAsset;
    private PreparedStatement deleteAllAsset;
    private PreparedStatement getAsset;
    private PreparedStatement getAllAsset;
    private PreparedStatement editAsset;

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
            deleteAllAsset = connection.prepareStatement(DELETE_ALL_ASSET);
            getAsset = connection.prepareStatement(GET_ASSET);
            editAsset = connection.prepareStatement(EDIT_ASSET);
            getAllAsset = connection.prepareStatement(GET_ALL_ASSET);
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
            addAsset.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete an asset from the database
     * @param id a String Name of the asset wanted to delete
     */
    public void deleteAsset(int id){
        try {
            deleteAsset.setInt(1, id);
            deleteAsset.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllAsset() {
        try {
            deleteAllAsset.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return an existed asset from the database
     * @param id String Name of the asset wanted to return.
     * @return Asset Object
     */
    public Asset getAsset(int id){
        //create a dummy object to store values
        Asset dummy = null;
        ResultSet rs;
        try {
            //Insert value into query string above
            getAsset.setInt(1, id);
            rs = getAsset.executeQuery();
            while( rs.next() ) {
                dummy = new Asset(
                        rs.getInt("asset_id"),
                        rs.getString("asset_name"),
                        rs.getString("asset_description")
                );
            }
        } catch (SQLException | InvalidArgumentValueException e) {
            e.printStackTrace();
        }
        return dummy;

    }

    //Todo: Get Asset list method
    public DataCollection<Asset> getAssetList(){
        DataCollection<Asset> assets = new DataCollection<>();
        try {
            ResultSet rs = getAllAsset.executeQuery();
            while (rs.next()){
                assets.add(new Asset(rs.getInt("asset_id"),
                        rs.getString("asset_name"),
                        rs.getString("asset_description")));
            }

        } catch (SQLException | InvalidArgumentValueException e) {
            e.printStackTrace();
        }
        return assets;
    }

    /**
     * A method to update an asset information on  database
     * @param assetNewInfo an Asset class object containing new data
     */
    public void editAsset(Asset assetNewInfo)  {
        try {
            editAsset.setString(1, assetNewInfo.getName());
            editAsset.setString(2, assetNewInfo.getDescription());
            editAsset.setInt(3, assetNewInfo.getId());
            editAsset.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
