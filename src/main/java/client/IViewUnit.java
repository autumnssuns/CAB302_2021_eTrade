package client;

import client.guiControls.DisplayController;

/**
 * The smallest possible group that display the information of a linked data object (IData)
 */
public interface IViewUnit {
    /**
     * Initialize the display elements and their styling.
     */
    void initialize();

    /**
     * Loads the GUI components, by calling all other load methods.
     */
    void load();
}
