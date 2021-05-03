package client.guiControls.adminMain;

import common.dataClasses.Asset;
import common.dataClasses.Organisation;
import common.dataClasses.Stock;
import common.dataClasses.User;

import java.util.ArrayList;

/**
 * A class containing the data related to the session operated by an admin.
 */
public class AdminData {
    ArrayList<Organisation> organisations;
    ArrayList<Stock> stocks;
    ArrayList<User> users;
    ArrayList<Asset> assets;


}
