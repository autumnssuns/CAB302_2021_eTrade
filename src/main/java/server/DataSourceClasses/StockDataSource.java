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
                " PRIMARY KEY (`organisation_id`, `asset_id`),\n" +
                " CONSTRAINT `asset`\n" +
                "   FOREIGN KEY (`asset_id`)\n" +
                "   REFERENCES `cab302_eTrade`.`assets` (`asset_id`)\n" +
                "   ON DELETE NO ACTION\n" +
                "   ON UPDATE NO ACTION,\n" +
                " CONSTRAINT `stock_organisation`\n" +
                " FOREIGN KEY (`organisation_id`, `asset_id`)\n" +
                "   REFERENCES `cab302_eTrade`.`organisations` (`organisation_id`)\n" +
                "   ON DELETE NO ACTION\n" +
                "   ON UPDATE NO ACTION)\n" +
                " ENGINE = InnoDB;\n" +
                "\n" +
                " CREATE INDEX `asset_idx` ON `cab302_eTrade`.`stock` (`asset_id` ASC) VISIBLE;";
    private static final String EDIT_QUANTITY=
            "UPDATE stock" +
            "   SET organisation_id=?, asset_id=?, asset_quantity=?" +
            "   WHERE organisation=?, asset_id=? ";
    private static final String GET_STOCK =
            "SELECT * FROM stock WHERE organisation_id = ?";
    private static final String GET_ALL_STOCK = "";
    private static final String INSERT_ASSET =
            "INSERT INTO stock(organisation_id, asset_id, asset_quantity) " +
            "VALUES (?,?,?)";


    private Connection connection;
    private PreparedStatement editQuantity;
    private PreparedStatement insertAsset;
    private PreparedStatement getStock;

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Edit quantity of an asset of an organization
     * @param organisationID ~
     * @param assetID ~
     * @param assetQuantity ~
     */
    public void editQuantity(int organisationID, int assetID, int assetQuantity) {
        try {
            editQuantity.setInt(1, organisationID);
            editQuantity.setInt(2, assetID);
            editQuantity.setInt(3, assetQuantity);
            editQuantity.setInt(4, organisationID);
            editQuantity.setInt(5, assetID);
            editQuantity.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert new asset with its quantity to an organization
     * @param organisationId ~
     * @param assetId ~
     * @param assetQuantity ~
     */
    public void insertAsset(int organisationId, int assetId, int assetQuantity) {
        try {
            insertAsset.setInt(1, organisationId);
            insertAsset.setInt(2, assetId);
            insertAsset.setInt(3, assetQuantity);
            insertAsset.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get stocks list from an organization
     * @param organisationId ~
     * @return an Object array of {asset_id, organization_id, asset_quantity}
     */
    public Object[] getStock(int organisationId) {
        Object[] stocks = new Object[]{};
        ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(stocks));
        try {
            getStock.setInt(1, organisationId);
            ResultSet rs = getStock.executeQuery();
            while(rs.next()) {
                temp.add(new int[]{rs.getInt("asset_id"), rs.getInt("organization_id"), rs.getInt("asset_quantity")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp.toArray();
    }
}


