package client.guiControls;

import common.Response;
import common.dataClasses.IData;

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

    /**
     * Pass the request to the mainController and in turns, be sent to the database.
     * @param action The request action "query", "add", "edit", "delete"
     * @param attachment The attachment related to the request.
     * @param attachmentType The type of the attachment that can be read.
     * @param <T> The type of the attachment.
     * @return
     */
    public <T extends IData> Response sendRequest(String action, T attachment, Class<T> attachmentType) {
        return controller.sendRequest(action, attachment, attachmentType);
    }
}
