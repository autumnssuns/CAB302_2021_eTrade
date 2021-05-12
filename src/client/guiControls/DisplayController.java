package client.guiControls;

/**
 * A view-only controller that can interact with a MainController to update the view it controls.
 */
public class DisplayController {
    protected MainController controller;

    /**
     * Links the display controller with a main controller.
     * @param controller The main controller to link.
     */
    public void setController(MainController controller) {
        this.controller = controller;
    }

    public void update(){ }
}
