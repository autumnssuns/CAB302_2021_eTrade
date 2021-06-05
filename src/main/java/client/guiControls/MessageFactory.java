package client.guiControls;

/**
 * A factory to generate a message GUI based on its type
 */
public class MessageFactory {
    /**
     * The different types of message the user can receive
     */
    public enum MessageType{
        SUCCESS,
        ERROR,
        DEFAULT;
    }

    /**
     * Creates a message display (a text-containing box)
     * @param message The message linked with the display
     * @param messageType The type of the message
     * @return A new GUI component displaying a message
     */
    public static MessageViewUnit createMessage(String message, MessageType messageType){
        MessageViewUnit messageViewUnit = new MessageViewUnit(message);
        String styleClass = switch (messageType) {
            case SUCCESS -> "success-message";
            case ERROR -> "error-message";
            default -> "highlight-message";
        };
        messageViewUnit.getStyleClass().add(styleClass);
        return messageViewUnit;
    }
}