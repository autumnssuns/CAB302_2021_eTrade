package server.Excluded_PUT_ALL_EXCLUSIONS_HERE.DataSourceClasses;

import common.dataClasses.DataCollection;
import common.dataClasses.Order;
import common.dataClasses.Order.Type;
import server.DBconnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Provides needed functions to interact with "orders" database for data
 */
public class OrderDataSource {
    //Setting up the environment.
    //SQL queries
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS orders (\n" +
            "    order_id          INTEGER      NOT NULL\n" +
            "                                   PRIMARY KEY AUTOINCREMENT,\n" +
            "    order_type        VARCHAR (4)  NOT NULL,\n" +
            "    organisation_id   INT          NOT NULL,\n" +
            "    asset_id          INT          NOT NULL,\n" +
            "    placed_quantity   INT          NOT NULL\n" +
            "                                   DEFAULT 0,\n" +
            "    resolved_quantity INT          NOT NULL\n" +
            "                                   DEFAULT 0,\n" +
            "    price             DECIMAL (2)  NOT NULL,\n" +
            "    order_date        VARCHAR(50)  NOT NULL\n" +
            "                                   DEFAULT CURRENT_TIMESTAMP,\n" +
            "    finished_date     VARCHAR(50)  DEFAULT NULL,\n" +
            "    status            VARCHAR(10)  NOT NULL\n" +
            "                                   DEFAULT ('placed') \n" +
            ");";

    private static final String ADD_ORDER = "INSERT INTO orders(order_id, order_type, organisation_id, " +
            "asset_id, placed_quantity, resolved_quantity, price, order_date, finished_date, status) \n" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String DELETE_ORDER = "DELETE FROM orders WHERE order_id=?";
    private static final String DELETE_ALL_ORDERS = "DELETE FROM orders";
    private static final String GET_ORDER = "SELECT * FROM orders WHERE order_id=?";
    private static final String GET_ALL_ORDER = "SELECT * FROM orders";
    private static final String EDIT_ORDER =
            "UPDATE orders\n" +
            "SET order_type=?, organisation_id=?, asset_id=?, placed_quantity=?, resolved_quantity=?, price=?, " +
            "order_date=?, finished_date=?, status=?\n" +
            "WHERE order_id=?";

    //Prepare statements.
    private Connection connection;
    private PreparedStatement addOrder;
    private PreparedStatement deleteOrder;
    private PreparedStatement deleteAllOrders;
    private PreparedStatement getOrder;
    private PreparedStatement editOrder;
    private PreparedStatement getAllOrder;
    static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;


    /**
     * Connect to the database and create one if not exists
     */
    public OrderDataSource() {
        connection = DBconnection.getInstance();
        try {
            Statement st = connection.createStatement();
            st.execute(CREATE_TABLE);
            addOrder = connection.prepareStatement(ADD_ORDER);
            deleteOrder = connection.prepareStatement(DELETE_ORDER);
            getOrder = connection.prepareStatement(GET_ORDER);
            editOrder = connection.prepareStatement(EDIT_ORDER);
            getAllOrder = connection.prepareStatement(GET_ALL_ORDER);
            deleteAllOrders = connection.prepareStatement(DELETE_ALL_ORDERS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add an order object into the database
     * @param order Order Object wanted to add
     */
    public void addOrder(Order order){
        try {
            //input values into the query string above
            addOrder.setInt(1, order.getOrderId());
            addOrder.setString(2, order.getOrderType().name());
            addOrder.setFloat(3, order.getUnitId());
            addOrder.setInt(4, order.getAssetId());
            addOrder.setInt(5, order.getPlacedQuantity());
            addOrder.setInt(6, order.getResolvedQuantity());
            addOrder.setFloat(7, order.getPrice());
            addOrder.setString(8, order.getOrderDate().format(formatter));
            addOrder.setString(9, order.getFinishDate().format(formatter));
            addOrder.setString(10, order.getStatus().name());
            //execute the query
            addOrder.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete an order based on its' ID
     * @param OrderId ID of the order (int value)
     */
    public void deleteOrder(int OrderId){
        try {
            deleteOrder.setInt(1, OrderId);
            deleteOrder.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete all orders
     */
    public void deleteAllOrders(){
        try {
            deleteAllOrders.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return existed order
     * @param OrderId ID of the order wanted to return (Int value)
     * @return Order Object
     */
    public Order getOrder(int OrderId){
        //create a dummy Order Object to store values
        Order dummy = null;
        try {
            //set value
            getOrder.setInt(1, OrderId);
            ResultSet rs = getOrder.executeQuery();
            while( rs.next() ) {
                dummy = new Order(
                        rs.getInt("order_id"),
                        Type.valueOf(rs.getString("order_type")),
                        rs.getInt("organisation_id"),
                        rs.getInt("asset_id"),
                        rs.getInt("places_quantity"),
                        rs.getInt("resolved _quantity"),
                        rs.getFloat("price"),
                        LocalDateTime.parse(rs.getString("order_date"), formatter),
                        LocalDateTime.parse(rs.getString("finish_date"), formatter),
                        Order.Status.valueOf(rs.getString("status")));
            };
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dummy;

//        Order dummy = new Order( -1, null, -1, -1, -1, -1,
//                -1,null,null, null);
//        try {
//            //set value
//            getOrder.setInt(1, OrderId);
//            ResultSet rs = getOrder.executeQuery();
//            //Stores values into the dummy object
//            dummy.setOrderId(rs.getInt("order_id"));
//            dummy.setOrderType(Order.Type.valueOf(rs.getString("order_type")));
//            dummy.setUnitId(rs.getInt("organisation_id"));
//            dummy.setAssetID(rs.getInt("asset_id"));
//            dummy.setPlacedQuantity(rs.getInt("placed_quantity"));
//            dummy.setResolvedQuantity(rs.getInt("resolved_quantity"));
//            dummy.setPrice(rs.getFloat("price"));
//            dummy.setOrderDate((rs.getDate("order_date")).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
//            dummy.setFinishDate((rs.getDate("order_date")).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
//            dummy.setStatus(Order.Status.valueOf(rs.getString("status")));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return dummy;
    }

    /**
     * Method to return all orders in the database
     * @return an Order data collection of Buy Order
     */
    public DataCollection<Order> getOrderList(){
        DataCollection<Order> orders = new DataCollection<>();
        try {
            ResultSet rs = getAllOrder.executeQuery();
            while (rs.next()){
                orders.add(new Order(
                        rs.getInt("order_id"),
                        Type.valueOf(rs.getString("order_type")),
                        rs.getInt("organisation_id"),
                        rs.getInt("asset_id"),
                        rs.getInt("placed_quantity"),
                        rs.getInt("resolved_quantity"),
                        rs.getFloat("price"),
                        LocalDateTime.parse(rs.getString("order_date"), formatter),
                        LocalDateTime.parse(rs.getString("finished_date"), formatter),
                        Order.Status.valueOf(rs.getString("status")))
                        );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }


    /**
     * A method to update an Order information on  database
     * @param orderNewInfo an Order class object containing new data
     */
    public void editOrder(Order orderNewInfo)  {
        try {
            editOrder.setString(1, orderNewInfo.getOrderType().name());
            editOrder.setInt(2, orderNewInfo.getUnitId());
            editOrder.setInt(3, orderNewInfo.getAssetId());
            editOrder.setInt(4, orderNewInfo.getPlacedQuantity());
            editOrder.setInt(5, orderNewInfo.getResolvedQuantity());
            editOrder.setFloat(6, orderNewInfo.getPrice());
            editOrder.setString(7, orderNewInfo.getOrderDate().format(formatter));
            editOrder.setString(8, orderNewInfo.getFinishDate().format(formatter));
            editOrder.setString(9, orderNewInfo.getStatus().name());
            editOrder.setInt(10, orderNewInfo.getOrderId());
            editOrder.executeUpdate();
        }catch (SQLException e){
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
