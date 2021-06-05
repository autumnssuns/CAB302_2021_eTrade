package client;

/**
 * A styling class to define the properties of different UI elements
 */
public enum Styler {
    STANDARD_ASSET_BOX(30, 0, "", 30),
    STANDARD_ASSET_NAME_BOX(30, 200, "blackLabel",0);

    private final int height;
    private final int width;
    private final String styleClass;
    private final int spacing;

    /**
     * Constructor for enum, containing
     * @param height
     * @param width
     * @param styleClass
     * @param spacing
     */
    Styler(int height, int width, String styleClass, int spacing){
        this.height = height;
        this.width = width;
        this.styleClass = styleClass;
        this.spacing = spacing;
    }

    public int height() {return height;}
    public int width() {return width;}
    public String styleClass() {return styleClass;}
    public int spacing() {return spacing;}
}