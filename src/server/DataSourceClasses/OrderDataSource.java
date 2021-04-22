package server.DataSourceClasses;

import common.dataClasses.Organisation;
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
    private static final String ADD_ORGANISATION = "INSERT INTO organisations(organisation_id, organisation_name, credits) VALUES (?, ?, ?);";
    private static final String DELETE_ORGANISATION = "DELETE FROM organisations WHERE organisation_name=?";
    private static final String GET_ORGANISATION = "SELECT FROM organisations WHERE organisation_name=?";

    private Connection connection;
    private PreparedStatement addOrganisation;
    private PreparedStatement deleteOrganisation;
    private PreparedStatement getOrganisation;

    public OrderDataSource() {
        connection = DBconnection.getInstance();
        try {
            Statement st = connection.createStatement();
            st.execute(CREATE_TABLE);
            addOrganisation = connection.prepareStatement(ADD_ORGANISATION);
            deleteOrganisation = connection.prepareStatement(DELETE_ORGANISATION);
            getOrganisation = connection.prepareStatement(GET_ORGANISATION);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addOrganisation(Organisation newOrganisation){
        try {
            addOrganisation.setInt(1, newOrganisation.getId());
            addOrganisation.setString(2, newOrganisation.getName());
            addOrganisation.setFloat(3, newOrganisation.getBalance());
            addOrganisation.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrganisation(String Name){
        try {
            deleteOrganisation.setString(1, Name);
            deleteOrganisation.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Organisation getOrganisation(String Name){
        Organisation dummy = new Organisation(-1,null, -1);
        ResultSet rs = null;
        try {
            getOrganisation.setString(1, Name);
            rs = getOrganisation.executeQuery();
            dummy.setId(rs.getInt("organisation_id"));
            dummy.setName(rs.getString("organisation_name"));
            dummy.setBalance(rs.getFloat("credits"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dummy;
    }

    public void close() {
        /* BEGIN MISSING CODE */
        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        /* END MISSING CODE */
    }
}
