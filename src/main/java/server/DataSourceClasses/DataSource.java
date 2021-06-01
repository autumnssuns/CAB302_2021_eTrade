package server.DataSourceClasses;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    protected Connection connection;

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
}
