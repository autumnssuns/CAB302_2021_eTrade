package unitTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Random;

public class TestHashPassWord {
    //private HashPassword hashPassword;


    @Test
    public void passwordIsHashed(){
        String answer = server.Features.HashPassword.HashPassword("Test");
        //String expected = "Test";
        Assertions.assertNotNull(answer);
        Assertions.assertNotEquals("Test", answer);
    }
}
