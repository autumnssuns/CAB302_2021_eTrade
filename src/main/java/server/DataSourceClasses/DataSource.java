package server.DataSourceClasses;

import server.DBConnection;

import java.sql.*;

public class DataSource {
    protected Connection connection;
    protected PreparedStatement getMaxId;

    /**
     * Closes the connection
     * @throws SQLException
     */
    public void close() throws SQLException {
        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets the next ID of the item to be added to this table.
     * @return The next ID of the item to be added to this table.
     */
    protected Integer getNextId(){
        try {
            return getMaxId() + 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the highest ID contained in this table.
     * @return The highest ID contained in this table.
     */
    protected Integer getMaxId() throws SQLException {
        ResultSet rs = getMaxId.executeQuery();
        int max = -1;
        while (rs.next()){
            if (rs.getInt(1) > max){
                max = rs.getInt(1);
            }
        }
        return max;
    }
}
