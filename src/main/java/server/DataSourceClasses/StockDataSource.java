package server.DataSourceClasses;

// return add , edit , query, delete , return all, get 1,
// server, casetoresponse

import common.dataClasses.DataCollection;
import common.dataClasses.Stock;
import server.DBconnection;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class StockDataSource {
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `cab302_eTrade`.`stock` (\n" +
            "   `organisation_id` INT NOT NULL,\n" +
            "   `asset_id` INT NOT NULL,\n" +
            "   `asset_quantity` INT NOT NULL DEFAULT 0,\n" +
            " PRIMARY KEY (`organisation_id`, `asset_id`))";
    private static final String EDIT_QUANTITY=
            "UPDATE stock" +
                    "   SET organisation_id=?, asset_id=?, asset_quantity=?" +
                    "   WHERE organisation=?, asset_id=? ";
    private static final String GET_STOCK =
            "SELECT * FROM stock WHERE organisation_id = ?";
    private static final String INSERT_ASSET =
            "INSERT INTO stock(organisation_id, asset_id, asset_quantity) " +
                    "VALUES (?,?,?)";
    private static final String DELETE_ASSET =
            "DELETE FROM stock WHERE asset_id = ?";
    private Connection connection;
    private PreparedStatement editQuantity;
    private PreparedStatement insertAsset;
    private PreparedStatement getStock;
    private PreparedStatement deleteAsset;

    /**
     * Connect to the Stock database and create one if not exists
     */
    public StockDataSource() {
        connection = DBconnection.getInstance();
        try {
            Statement st = connection.createStatement();
            st.execute(CREATE_TABLE);
            editQuantity = connection.prepareStatement(EDIT_QUANTITY);
            getStock = connection.prepareStatement(GET_STOCK);
            insertAsset = connection.prepareStatement(INSERT_ASSET);
            deleteAsset = connection.prepareStatement(DELETE_ASSET);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Edit quantity of an asset of an organization
     * @param attachment a Stock object
     */
    public void editQuantity(Stock attachment) {
        try {
            editQuantity.setInt(1, attachment.getUnitId());
            editQuantity.setInt(2, attachment.getAssetId());
            editQuantity.setInt(3, attachment.getAssetQuantity());
            editQuantity.setInt(4, attachment.getUnitId());
            editQuantity.setInt(5, attachment.getAssetId());
            editQuantity.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert new asset with its quantity to an organization
     * @param attachment a Stock object
     */
    public void insertAsset(Stock attachment) {
        try {
            insertAsset.setInt(1, attachment.getUnitId());
            insertAsset.setInt(2, attachment.getAssetId());
            insertAsset.setInt(3, attachment.getAssetQuantity());
            insertAsset.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get stocks list from an organization
     * @param attachment a Stock object
     * @return an Object array of {asset_id, organization_id, asset_quantity}
     */
    public DataCollection<Stock> getStock(Stock attachment) {
        DataCollection<Stock> stocks = new DataCollection<>();
        try {
            getStock.setInt(1, attachment.getUnitId());
            ResultSet rs = getStock.executeQuery();
            while(rs.next()) {
                stocks.add(new Stock(rs.getInt("asset_id"),
                        rs.getInt("organization_id"),
                        rs.getInt("asset_quantity")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stocks;
    }

    /**
     * Delete an asset from an organization unit
     * @param attachment a Stock object
     */
    public void deleteAsset(Stock attachment) {
        try {
            deleteAsset.setInt(1, attachment.getAssetId());
            insertAsset.executeQuery();
        } catch (SQLException e) {
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


