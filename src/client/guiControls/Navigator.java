package client.guiControls;

// A class to store the directory of panes
public enum Navigator {
    HOME_PANE("/mainUser/saleController/sellPage.fxml");

    private String path;

    Navigator(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
