package common;

import common.Exceptions.InvalidArgumentValueException;
import common.dataClasses.Asset;
import common.dataClasses.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseTest {
    private Asset asset0;
    private User user0;
    private Response assetResponse;
    private Response userResponse;


    @BeforeEach
    void setUp() throws InvalidArgumentValueException {
        asset0 = new Asset(0, "CPU Hours", "CPU for rent");
        user0 = new User(4, "Rodo Nguyen",
                "rodo", "rodo", "user", 3);
        assetResponse = new Response(true, asset0);
        userResponse = new Response(false, user0);
    }

    @Test
    void isAccepted() {
        assertTrue(assetResponse.isAccepted());
        assertFalse(userResponse.isAccepted());
    }

    @Test
    void getAttachment() {
        assertEquals(user0, userResponse.getAttachment());
        assertEquals(asset0, assetResponse.getAttachment());
    }

    @Test
    void setAndGetMessage() {
        userResponse.setMessage("This is a new message for userResponse.");
        assertEquals("This is a new message for userResponse.",userResponse.getMessage());
    }

    @Test
    void trueEquals() {
        Response same = new Response(false, user0);
        assertTrue(userResponse.equals(same));
    }

    @Test
    void falseEquals() {
        // Different in accepted value
        Response diffAccepted = new Response(true, user0);
        assertFalse(userResponse.equals(diffAccepted));
        // Different in attachment
        Response diffAttachment = new Response(false, asset0);
        assertFalse(userResponse.equals(diffAttachment));
        // Different in message
        Response diffMessage = new Response(true, user0);
        diffMessage.setMessage("Not default");
        assertFalse(userResponse.equals(diffMessage));
    }

    @Test
    void testHashCodeWorking() {
        userResponse.hashCode();
    }
}