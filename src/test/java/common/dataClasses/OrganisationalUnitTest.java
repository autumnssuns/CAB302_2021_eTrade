package common.dataClasses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrganisationalUnitTest {

    OrganisationalUnit qut;

    @BeforeEach
    void setUp() throws Exception {
        qut = new OrganisationalUnit(0, "QUT", 100);
    }

    @Test
    void getId() {
        assertEquals(qut.getId(), 0);
    }

    @Test
    void setId() throws Exception {
        qut.setId(1);
        assertEquals(qut.getId(), 1);
    }

    @Test
    void setNegativeId() {
        assertThrows(Exception.class, () -> {
            qut.setId(-10);
        });
    }

    @Test
    void getName() {
        assertEquals(qut.getName(), "QUT");
    }

    @Test
    void getBalance() {
        assertEquals(qut.getBalance(), 100);
    }

    @Test
    void setName() {
        qut.setName("UQ");
        assertEquals(qut.getName(), "UQ");
    }

    @Test
    void setBalance() throws Exception {
        qut.setBalance(200);
        assertEquals(qut.getBalance(), 200);
    }

    @Test
    void setNegativeBalance() {
        assertThrows(Exception.class, () -> {
            qut.setBalance(-9);
        });
    }

    @Test
    void testEquals() throws Exception {
        Object o = new OrganisationalUnit(1, "UQ", 999);
        assertEquals(qut.equals(o), false);
        Object oo = new OrganisationalUnit(0, "QUT", 100);
        assertEquals(qut.equals(oo), true);
    }

    @Test
    void testHashCode() {
        int hashCode = qut.hashCode();
    }
}