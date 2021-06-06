package server.DataSourceClasses;

import common.Exceptions.InvalidArgumentValueException;
import common.dataClasses.Asset;
import common.dataClasses.DataCollection;
import server.DBConnection;

import java.sql.*;
/**
 * Provides needed functions to interact with "assets" database for data
 */
public class AssetsDataSource extends DataSource {

    // SQL query strings
    private static final String CREATE_TABLE =
            """
                    CREATE TABLE IF NOT EXISTS assets (
                        asset_id          INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                        asset_name        VARCHAR (16)  NOT NULL,
                        asset_description VARCHAR (256) DEFAULT NULL
                    );""";
    private static final String ADD_ASSET = "INSERT INTO assets (asset_id, asset_name, asset_description) \nVALUES (?, ?, ?);";
    private static final String DELETE_ASSET = "DELETE FROM assets WHERE asset_id=?";
    private static final String DELETE_ALL_ASSET = "DELETE FROM assets";
    private static final String GET_ASSET = "SELECT * FROM assets WHERE asset_id=?";
    private static final String GET_ALL_ASSET = "SELECT * FROM assets";
    protected static final String GET_MAX_ID = "SELECT asset_id FROM assets";
    private static final String EDIT_ASSET =
            """
                    UPDATE assets
                    SET asset_name=?, asset_description=?
                    WHERE asset_id=?""";

    // Prepare statements.
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
        connection = DBConnection.getInstance();
        try {
            Statement st = connection.createStatement();
            st.execute(CREATE_TABLE);
            addAsset = connection.prepareStatement(ADD_ASSET);
            deleteAsset = connection.prepareStatement(DELETE_ASSET);
            deleteAllAsset = connection.prepareStatement(DELETE_ALL_ASSET);
            getAsset = connection.prepareStatement(GET_ASSET);
            editAsset = connection.prepareStatement(EDIT_ASSET);
            getAllAsset = connection.prepareStatement(GET_ALL_ASSET);
            getMaxId = connection.prepareStatement(GET_MAX_ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add new asset into the database.
     * @param newAsset Asset Object wanted to add
     */
    public void addAsset(Asset newAsset){
        try {
            //Insert values into above query string
            int newAssetId = newAsset.getId() == null ? getNextId() : newAsset.getId();
            addAsset.setInt(1, newAssetId);
            addAsset.setString(2, newAsset.getName());
            addAsset.setString(3, newAsset.getDescription());
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

    /**
     * Delete all assets from the database (mostly utilized for testing)
     */
    public void deleteAll() {
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

    /**
     * Query all the assets in the database
      * @return A DataCollection<Asset> of all assets
     */
    public DataCollection<Asset> getAssetList(){
        DataCollection<Asset> assets = new DataCollection<>();
        try {
            ResultSet rs = getAllAsset.executeQuery();
            while (rs.next()){
                Integer nextId = rs.getInt(1);
                assets.add(getAsset(nextId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assets;
    }

    /**
     * Update an asset on the database
     * @param assetNewInfo an Asset class object containing new data
     */
    public void editAsset(Asset assetNewInfo) throws SQLException {
        editAsset.setString(1, assetNewInfo.getName());
        editAsset.setString(2, assetNewInfo.getDescription());
        editAsset.setInt(3, assetNewInfo.getId());
        editAsset.executeUpdate();
    }
}