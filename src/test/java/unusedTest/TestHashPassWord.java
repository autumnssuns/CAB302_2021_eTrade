package unusedTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import server.WorkingFeatures_PLEASE_DO_NOT_EXCLUDE.HashPassword;

public class TestHashPassWord {
    //private HashPassword hashPassword;


    @Test
    public void passwordIsHashed(){
        String answer = HashPassword.HashPassword("Test");
        //String expected = "Test";
        Assertions.assertNotNull(answer);
        Assertions.assertNotEquals("Test", answer);
    }
}
