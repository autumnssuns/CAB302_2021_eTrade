package common.dataClasses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User user;

    @BeforeEach
    void setUp() {
        user = new User(0, "Test UserGUI", "testuser", "p@ssw0rd", "user", 0).hashPassword();
    }

    @Test
    void setUserId() {
        user.setUserId(1);
        assertEquals(1, user.getId());
    }

    @Test
    void getUsername() {
        assertEquals("testuser", user.getUsername());
    }

    @Test
    void setUsername() {
        user.setUsername("newusername");
        assertEquals("newusername", user.getUsername());
    }

    @Test
    void getPassword() {
        assertEquals("f7cccde243ef3e12a6d7ff84d350395fd1d27517", user.getPassword());
    }

    @Test
    void getAccountType() {
        assertEquals("user", user.getAccountType());
    }

    @Test
    void getUnitId() {
        assertEquals(0, user.getUnitId());
    }

    @Test
    void setPassword() {
        user.setPassword("newPassword");
        user.hashPassword();
        assertEquals("7c1621e64158a2ef923810443c41d2956b917842", user.getPassword());
    }

    @Test
    void setAccountType() {
        user.setAccountType("admin");
        assertEquals("admin", user.getAccountType());
    }

    @Test
    void setOrganisation() {
        user.setOrganisation(1);
        assertEquals(1, user.getUnitId());
    }

    @Test
    void getUserId() {
        assertEquals(0, user.getId());
    }

    @Test
    void getFullName() {
        assertEquals("Test UserGUI", user.getFullName());
    }

    @Test
    void setFullName() {
        user.setFullName("New Fullname");
        assertEquals("New Fullname", user.getFullName());
    }

    @Test
    void setUnitId() {
        user.setUnitId(1);
        assertEquals(1, user.getUnitId());
    }

    @Test
    void testEquals() {
        User otherUser = new User(0, "Test UserGUI", "testuser", "p@ssw0rd", "user", 0).hashPassword();
        assertTrue(user.equals(otherUser) && otherUser.equals(user));
        assertTrue(user.hashCode() == otherUser.hashCode());
    }

    @Test
    void testHashCodeWorking() {
        user.hashCode();
    }
}