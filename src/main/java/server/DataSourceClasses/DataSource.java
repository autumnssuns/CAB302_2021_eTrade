package server.DataSourceClasses;

import common.dataClasses.IData;
import server.DBConnection;

import java.sql.*;

public class DataSource {
    protected Connection connection;
    protected PreparedStatement getMaxId;

    /**
     * Gets the next ID of the item to be added to this table.
     * @return The next ID of the item to be added to this table.
     */
    protected Integer getNextId(){
        return getMaxId() + 1;
    }

    /**
     * Gets the highest ID contained in this table.
     * @return The highest ID contained in this table.
     */
    protected Integer getMaxId() {
        ResultSet rs;
        int max = -1;
        try {
            rs = getMaxId.executeQuery();
            while (rs.next()){
                if (rs.getInt(1) > max){
                    max = rs.getInt(1);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return max;
    }
}
