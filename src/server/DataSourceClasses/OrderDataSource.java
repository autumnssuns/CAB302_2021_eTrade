package server.DataSourceClasses;

import common.dataClasses.Order;
import server.DBconnection;

import java.sql.*;

public class OrderDataSource {
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `cab302_eTrade`.`orders` (\n" +
            "  `order_id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `order_type` ENUM('buy', 'sell') NOT NULL,\n" +
            "  `organisation_id` INT NOT NULL,\n" +
            "  `asset_id` INT NOT NULL,\n" +
            "  `placed_quantity` INT NOT NULL DEFAULT 0,\n" +
            "  `resolved_quantity` INT NOT NULL DEFAULT 0,\n" +
            "  `price` DECIMAL(2) NOT NULL,\n" +
            "  `order_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
            "  `finished_date` DATETIME NULL DEFAULT NULL,\n" +
            "  `status` ENUM('placed', 'finished', 'cancelled') NOT NULL DEFAULT 'placed',\n" +
            "  PRIMARY KEY (`order_id`),\n" +
            "  CONSTRAINT `buy_organisation`\n" +
            "    FOREIGN KEY (`organisation_id` , `asset_id`)\n" +
            "    REFERENCES `cab302_eTrade`.`stock` (`organisation_id` , `asset_id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION)\n" +
            "ENGINE = InnoDB;\n" +
            "\n" +
            "CREATE INDEX `organisation_idx` ON `cab302_eTrade`.`orders` (`organisation_id` ASC, `asset_id` ASC) VISIBLE;";

    private static final String ADD_ORDER = "INSERT INTO orders(order_id, order_type, organisation_id, " +
            "asset_id, placed_quantity, resolved_quantity, price, order_date, finished_date, status) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String DELETE_ORDER = "DELETE FROM orders WHERE order_id=?";
    private static final String GET_ORDER = "SELECT FROM orders WHERE order_id=?";

    private Connection connection;
    private PreparedStatement addOrder;
    private PreparedStatement deleteOrder;
    private PreparedStatement getOrder;

    public OrderDataSource() {
        connection = DBconnection.getInstance();
        try {
            Statement st = connection.createStatement();
            st.execute(CREATE_TABLE);
            addOrder = connection.prepareStatement(ADD_ORDER);
            deleteOrder = connection.prepareStatement(DELETE_ORDER);
            getOrder = connection.prepareStatement(GET_ORDER);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addOrder(Order order){
        try {
            addOrder.setInt(1, order.getOrderId());
            addOrder.setString(2, order.getOrderType());
            addOrder.setFloat(3, order.getOrganisationid());
            addOrder.setInt(4, order.getAssetId());
            addOrder.setInt(5, order.getPlacedQuantity());
            addOrder.setFloat(6, order.getResolvedQuantity());
            addOrder.setFloat(7, order.getPrice());
            addOrder.setString(8, order.getOrderDate());
            addOrder.setString(9, order.getFinishedDate());
            addOrder.setString(10, order.getStatus());

            addOrder.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrder(int OrderId){
        try {
            deleteOrder.setInt(1, OrderId);
            deleteOrder.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Order getOrder(int OrderId){
        Order dummy = new Order( -1, null, null, null, null, null);
        ResultSet rs = null;
        try {
            getOrder.setInt(1, OrderId);
            rs = getOrder.executeQuery();
            dummy.setOrderID(rs.getInt("order_id"));
            dummy.setOrderType(rs.getString("order_type"));
            dummy.setOrganisationID(rs.getInt("organisation_id"));
            dummy.setAssetID(rs.getInt("asset_id"));
            dummy.setPlacedQuantity(rs.getInt("placed_quantity"));
            dummy.setResolvedQuantity(rs.getInt("resolved_quantity"));
            dummy.setPrice(rs.getFloat("price"));
            dummy.setOrderDate(rs.getString("order_date"));
            dummy.setFinishedDate(rs.getString("finished_date"));
            dummy.setStatus(rs.getString("status"));
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
