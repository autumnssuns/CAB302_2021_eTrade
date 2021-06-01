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
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `cab302_eTrade`.`assets` (\n" +
            "  `asset_id` INT NOT NULL,\n" +
            "  `asset_name` VARCHAR(16) NOT NULL,\n" +
            "  `asset_description` VARCHAR(256) NULL DEFAULT NULL,\n" +
            "  PRIMARY KEY (`asset_id`))\n" +
            "ENGINE = InnoDB;\n";
    private static final String ADD_ASSET = "INSERT INTO assets(asset_id, asset_name, asset_description) VALUES (?, ?, ?);";
    private static final String DELETE_ASSET = "DELETE FROM assets WHERE asset_id=?";
    private static final String GET_ASSET = "SELECT * FROM assets WHERE asset_id=?";
    private static final String GET_ALL_ASSET = "SELECT * FROM assets";
    private static final String EDIT_ASSET =
            "UPDATE assets" +
                    "SET asset_name=?, asset_description=?" +
                    "WHERE asset_id=?";

    //Prepare statements.
    private PreparedStatement addAsset;
    private PreparedStatement deleteAsset;
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
            addAsset.executeQuery();
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
            deleteAsset.executeQuery();
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
        try {
            dummy = new Asset(-1,null, null);
        } catch (InvalidArgumentValueException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        try {
            //Insert value into query string above
            getAsset.setInt(1, id);
            rs = getAsset.executeQuery();
            //Store values into the dummy
            dummy.setId(rs.getInt("asset_id"));
            dummy.setName(rs.getString("asset_name"));
            dummy.setDescription(rs.getString("asset_description"));
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
            editAsset.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
