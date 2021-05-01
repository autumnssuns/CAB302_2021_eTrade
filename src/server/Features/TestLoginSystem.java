package server.Features;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class TestLoginSystem {
    //setup the materials
    private String UniqueAccountName;
    private String ExistName;
    private String ExistPass;

    //Method to regen a random string for testing
    /**
     *
     * @return String of random chars
     */
    public static String RandomUserName() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
    //Creating the testing structure.
    //Open
    @BeforeEach
    public void SetUp(){
        //setup the environment.
        UniqueAccountName = RandomUserName();
        ExistName = "Duy";
        ExistPass ="123456";
    }

    //End
    @AfterEach
    public void TearDown(){
        UniqueAccountName = null;
        ExistName = null;
        ExistPass = null;
    }

// Test Cases

    //#1: Successfully Login case
    @Test
    public void CorrectLogin(){
        //enter valid account to test
        Boolean testcase = LoginSystem.login(ExistName, ExistPass);
        Assertions.assertEquals(true, testcase);
    }

    //#2: Use wrong Account Name
    @Test
    public void WrongLoginName(){
        //Enter invalid account to test
        Boolean testcase = LoginSystem.login(UniqueAccountName, ExistPass);
        Assertions.assertEquals(false, testcase);
    }

    //#3: Use Wrong Password
    @Test
    public void WrongPassword(){
        //Enter invalid account to test
        Boolean testcase = LoginSystem.login(ExistName, ExistPass+"#");
        Assertions.assertEquals(false, testcase);
    }

    //#4: Double check the successful login - To make sure the ResultSet works
    @Test
    public void DoubleCorrectLogin(){
        //enter valid account to test
        Boolean testcase = LoginSystem.login(ExistName,ExistPass);
        Boolean testcase2 = LoginSystem.login(ExistName, ExistPass);
        Assertions.assertEquals(true, testcase);
        Assertions.assertEquals(true, testcase2);
    }

    //#5: Double check the fail login - To make sure the ResultSet works
    @Test
    public void DoubleWrongLogin(){
        //enter invalid account to test
        Boolean testcase = LoginSystem.login(UniqueAccountName, ExistPass);
        Boolean testcase2 = LoginSystem.login(ExistName, ExistPass+"#");
        Assertions.assertEquals(false, testcase);
        Assertions.assertEquals(false, testcase2);
    }

    //#6: Try one wrong Login and one correct login - Another test to make sure the ResultSet working fine
    @Test
    public void wrong1correct1(){
        //enter 1 invalid account and 1 valid account to test
        Boolean testcase = LoginSystem.login(ExistName, ExistPass); //valid account
        Boolean testcase1 = LoginSystem.login(UniqueAccountName, "123"); //invalid account
        Assertions.assertEquals(true, testcase);
        Assertions.assertEquals(false, testcase1);
    }

    //#7: Test register method - success case
    @Test
    public void SuccessRegister() {
        // Enter a non-exist account name
        Boolean account = LoginSystem.register(UniqueAccountName,"12346");
        Assertions.assertEquals(true, account);
    }

    //#8: Test register method - fail case
    @Test
    public  void FailRegister(){
        //enter an existed account name
        boolean testcase = LoginSystem.register(ExistName, "123");
        Assertions.assertFalse(testcase);
    }


    //#9: Successfully register 2 times - another test for ResultSet
    @Test
    public void Register2correct(){
        Boolean testcase = LoginSystem.register(UniqueAccountName, "123");
        Boolean testcase1 = LoginSystem.register(UniqueAccountName+"#", "123");
        Assertions.assertEquals(true, testcase);
        Assertions.assertEquals(true, testcase1);
    }

    //#10: Fail to register 2 times - ResultSet test and double check the method
    @Test
    public  void DoubleFailRegister(){
        //enter an existed account name
        boolean testcase = LoginSystem.register(ExistName, "123");
        boolean testcase1 = LoginSystem.register(ExistName, "123");
        Assertions.assertFalse(testcase);
        Assertions.assertFalse(testcase1);
    }

    //#11: Register success one and fail one - ResultSet test.
    @Test
    public void Register1correct1wrong(){
        Boolean testcase = LoginSystem.register(UniqueAccountName, "123");
        Boolean testcase1 = LoginSystem.register(ExistName, "123");
        Assertions.assertEquals(true, testcase);
        Assertions.assertEquals(false, testcase1);
    }

}


