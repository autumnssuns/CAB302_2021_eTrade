package client.guiControls.adminMain;

/**
 * A class containing the data related to the session operated by an admin. The data contained can be updated with the server's version.
 */
public class AdminData {
    protected AdminMainController controller;

    /**
     * Links the data storage to a controller.
     * @param controller
     */
    public void setController(AdminMainController controller){
        this.controller = controller;
    }
}
