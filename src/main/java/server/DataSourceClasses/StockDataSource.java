package server.DataSourceClasses;

// return add , edit , query, delete , return all, get 1,
// server, casetoresponse

import common.Exceptions.InvalidArgumentValueException;
import common.dataClasses.*;
import server.DBConnection;

import java.sql.*;

public class StockDataSource extends DataSource {

    // SQL query strings
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS stock (\n" +
                    "    organisation_id INT NOT NULL,\n" +
                    "    asset_id        INT NOT NULL,\n" +
                    "    asset_quantity  INT NOT NULL\n" +
                    "                        DEFAULT 0,\n" +
                    "    PRIMARY KEY (\n" +
                    "        organisation_id,\n" +
                    "        asset_id\n" +
                    "    )\n" +
                    ");";
    private static final String GET_STOCK =
            "SELECT * FROM stock WHERE organisation_id = ?";
    private static final String GET_ALL_STOCK = "SELECT * FROM stock";
    private static final String UPDATE_STOCK =
            "INSERT INTO stock(organisation_id, asset_id, asset_quantity) " +
                    "VALUES (?,?,?)";
    private static final String DELETE_UNIT_STOCK =
            "DELETE FROM stock WHERE organisation_id = ?";
    private static final  String DELETE_ALL_DATA = "DELETE FROM stock";



    private PreparedStatement updateStock;
    private PreparedStatement getStock;
    private PreparedStatement deleteUnitStock;
    private PreparedStatement getAllStock;
    private PreparedStatement deleteAll;


    /**
     * Connect to the Stock database and create one if not exists
     */
    public StockDataSource() {
        connection = DBConnection.getInstance();
        try {
            Statement st = connection.createStatement();
            st.execute(CREATE_TABLE);
            getStock = connection.prepareStatement(GET_STOCK);
            updateStock = connection.prepareStatement(UPDATE_STOCK);
            deleteUnitStock = connection.prepareStatement(DELETE_UNIT_STOCK);
            getAllStock = connection.prepareStatement(GET_ALL_STOCK);
            deleteAll = connection.prepareStatement(DELETE_ALL_DATA);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Delete all stocks from the database
     */
    public void deleteAll() {
        try {
            deleteAll.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Update a unit's stock on stock table
     * @param attachment a Stock object
     */
    public void updateUnitStock(Stock attachment) {
        deleteStock(attachment);
        for(Item item: attachment)
        {
            try {
                updateStock.setInt(1, attachment.getUnitId());
                updateStock.setInt(2, item.getId());
                updateStock.setInt(3, item.getQuantity());
                updateStock.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * Get stocks list from an organization
     * @param unitId user wanted to return his/her stock
     * @return an Object array of {asset_id, organization_id, asset_quantity}
     */
    public Stock getStock(int unitId) {
        Stock stock = new Stock(unitId);
        try {
            //Provide value for query
            getStock.setInt(1, unitId);
            //Current assets in database
            AssetsDataSource assetsDataSource = new AssetsDataSource();
            //find all assets belonged to an user's unit (and get the asset id to link with a real asset object)
            ResultSet rs = getStock.executeQuery();
            //Check asset id in "stock table" with assets in database then add items into stock object
            while(rs.next()) {
                int assetId = rs.getInt("asset_id");
                int quantity = rs.getInt("asset_quantity");
                Asset newAsset = assetsDataSource.getAsset(assetId);
                Item newItem = new Item(newAsset, quantity);
                stock.add(newItem);
            }
        } catch (SQLException | InvalidArgumentValueException e) {
            e.printStackTrace();
        }
        return stock;
    }

    public DataCollection<Stock> getStockList(){

        DataCollection<Stock> stocks = new DataCollection<>();

        OrganisationsDataSource organisations = new OrganisationsDataSource();
        DataCollection<OrganisationalUnit> organisationsList = organisations.getOrganisationList();
        AssetsDataSource asset = new AssetsDataSource();
        try {
            ResultSet rs = getAllStock.executeQuery();
            //Create stock of each organisation.
            for (OrganisationalUnit organisation : organisationsList){
                stocks.add(new Stock(organisation.getId()));
            }
            while (rs.next()){
                //add items to stock of right organisation.
                int unitId = rs.getInt("organisation_id");
                int assetId = rs.getInt("asset_id");
                for(Stock stock : stocks)
                {
                    if(stock.getUnitId() == unitId)
                    {
                        Asset newAsset = asset.getAsset(assetId);
                        Item newItem = new Item(newAsset, rs.getInt("asset_quantity"));
                        stock.add(newItem);
                    }
                }
            }
        } catch (SQLException | InvalidArgumentValueException e) {
            e.printStackTrace();
        }
        return stocks;
    }

    /**
     * Delete stock of an organisation
     * @param attachment a Stock object
     */
    void deleteStock(Stock attachment) {
        try {
            deleteUnitStock.setInt(1, attachment.getUnitId());
            deleteUnitStock.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close the connection to database
     */
    public void Close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}