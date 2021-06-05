package client.guiControls;

import client.IViewUnit;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A view unit to display a message to the user. This message must
 * be contained in a VBox, and when new message is added, the VBox
 * is extended upwards.
 */
public class MessageViewUnit extends Button implements IViewUnit {
    /**
     * The message's life time, in seconds.
     */
    private static final int maxLifeTime = 5;
    private int currentLifeTime;
    private AtomicBoolean isAlive;

    /**
     * Constructor to initialise the message
     * @param message
     */
    public MessageViewUnit(String message){
        isAlive = new AtomicBoolean(true);
        this.setText(message);
        initialize();
    }

    /**
     * Initialises the GUI components
     */
    @Override
    public void initialize() {
        this.getStyleClass().remove("button");
        this.getStyleClass().addAll("blackLabel");
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPrefWidth(236);
        this.setPrefHeight(60);
        this.setWrapText(true);
        this.setOnAction(e -> remove());

        currentLifeTime = maxLifeTime;
        startTimer();
    }

    /**
     * Load not used, but is syntactically required.
     */
    @Override
    public void load() {

    }

    /**
     * Starts a background thread to continually updates the GUI
     */
    protected void startTimer(){
        Thread thread = new Thread(() -> {
            while (isAlive.get()) {
                try{
                    // Updates every 1 second
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    // If runs out of time, remove from the containing VBox
                    if (currentLifeTime == 0){
                        remove();
                    }
                    currentLifeTime--;
                });
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Removes the message from display
     */
    private void remove(){
        VBox container = (VBox) this.getParent();
        container.getChildren().remove(this);
        isAlive.set(false);
    }
}
