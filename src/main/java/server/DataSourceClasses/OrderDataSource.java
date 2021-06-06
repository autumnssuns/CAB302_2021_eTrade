package server.DataSourceClasses;

import common.dataClasses.DataCollection;
import common.dataClasses.Order;
import common.dataClasses.Order.Type;
import server.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Provides needed functions to interact with "orders" database for data
 */
public class OrderDataSource extends DataSource {
    //Setting up the environment.
    //SQL queries
    private static final String CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS orders (
                order_id          INTEGER           NOT NULL
                                                    PRIMARY KEY AUTOINCREMENT,
                order_type        VARCHAR(4)        NOT NULL,
                organisation_id   INT               NOT NULL,
                asset_id          INT               NOT NULL,
                placed_quantity   INT               NOT NULL
                                                    DEFAULT 0,
                resolved_quantity INT               NOT NULL
                                                    DEFAULT 0,
                price             DECIMAL(10,2)     NOT NULL,
                order_date        VARCHAR(50)       NOT NULL
                                                    DEFAULT CURRENT_TIMESTAMP,
                finished_date     VARCHAR(50)       DEFAULT NULL,
                status            VARCHAR(10)       NOT NULL
                                                    DEFAULT ('placed')\s
            );""";

    private static final String ADD_ORDER = "INSERT INTO orders(order_id, order_type, organisation_id, " +
            "asset_id, placed_quantity, resolved_quantity, price, order_date, finished_date, status) \n" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String DELETE_ORDER = "DELETE FROM orders WHERE order_id=?";
    private static final String DELETE_ALL_ORDERS = "DELETE FROM orders";
    private static final String GET_ORDER = "SELECT * FROM orders WHERE order_id=?";
    private static final String GET_ALL_ORDER = "SELECT * FROM orders";
    private static final String EDIT_ORDER =
            """
                    UPDATE orders
                    SET order_type=?, organisation_id=?, asset_id=?, placed_quantity=?, resolved_quantity=?, price=?, order_date=?, finished_date=?, status=?
                    WHERE order_id=?""";
    protected static final String GET_MAX_ID = "SELECT order_id FROM orders";

    // Prepare statements.
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
        super();
        connection = DBConnection.getInstance();
        try {
            Statement st = connection.createStatement();
            st.execute(CREATE_TABLE);
            addOrder = connection.prepareStatement(ADD_ORDER);
            deleteOrder = connection.prepareStatement(DELETE_ORDER);
            getOrder = connection.prepareStatement(GET_ORDER);
            editOrder = connection.prepareStatement(EDIT_ORDER);
            getAllOrder = connection.prepareStatement(GET_ALL_ORDER);
            deleteAllOrders = connection.prepareStatement(DELETE_ALL_ORDERS);
            getMaxId = connection.prepareStatement(GET_MAX_ID);
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
            int newOrderId = order.getOrderId() == null ? getNextId() : order.getOrderId();
            addOrder.setInt(1, newOrderId);
            addOrder.setString(2, order.getOrderType().name());
            addOrder.setFloat(3, order.getUnitId());
            addOrder.setInt(4, order.getAssetId());
            addOrder.setInt(5, order.getPlacedQuantity());
            addOrder.setInt(6, order.getResolvedQuantity());
            addOrder.setFloat(7, order.getPrice());
            addOrder.setString(8, order.getOrderDate().format(formatter));
            if (order.getFinishDate() == null){
                addOrder.setNull(9, java.sql.Types.VARCHAR);
            }
            else{
                addOrder.setString(9, order.getFinishDate().format(formatter));
            }

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
     * Delete all orders from the database
     */
    public void deleteAll(){
        try {
            deleteAllOrders.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return existed order
     * @param orderId ID of the order wanted to return (Int value)
     * @return Order Object
     */
    public Order getOrder(int orderId){
        //create a dummy Order Object to store values
        Order dummy = null;
        try {
            //set value
            getOrder.setInt(1, orderId);
            ResultSet rs = getOrder.executeQuery();
            while( rs.next() ) {
                LocalDateTime finishedDate;
                try{
                    finishedDate = LocalDateTime.parse(rs.getString("finished_date"), formatter);
                }
                catch (NullPointerException e){
                    finishedDate = null;
                }
                dummy = new Order(
                        rs.getInt("order_id"),
                        Type.valueOf(rs.getString("order_type")),
                        rs.getInt("organisation_id"),
                        rs.getInt("asset_id"),
                        rs.getInt("placed_quantity"),
                        rs.getInt("resolved_quantity"),
                        rs.getFloat("price"),
                        finishedDate,
                        LocalDateTime.parse(rs.getString("order_date"), formatter),
                        Order.Status.valueOf(rs.getString("status")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dummy;
    }

    /**
     * Get all orders from the database
     * @return an Order DataCollection
     */
    public DataCollection<Order> getOrderList(){
        DataCollection<Order> orders = new DataCollection<>();
        try {
                ResultSet rs = getAllOrder.executeQuery();
                while (rs.next()){
                    Integer nextId = rs.getInt(1);
                    orders.add(getOrder(nextId));
                }
            } catch (Exception e) {
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
            if (orderNewInfo.getOrderDate() == null){
                editOrder.setNull(7, java.sql.Types.VARCHAR);
            }
            else{
                editOrder.setString(7, orderNewInfo.getOrderDate().format(formatter));
            }
            if (orderNewInfo.getFinishDate() == null){
                editOrder.setNull(8, java.sql.Types.VARCHAR);
            }
            else{
                editOrder.setString(8, orderNewInfo.getFinishDate().format(formatter));
            }
            editOrder.setString(9, orderNewInfo.getStatus().name());
            editOrder.setInt(10, orderNewInfo.getOrderId());
            editOrder.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}