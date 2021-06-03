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
        assertEquals(0, qut.getId());
    }

    @Test
    void setId() throws Exception {
        qut.setId(1);
        assertEquals(1, qut.getId());
    }

    @Test
    void setNegativeId() {
        assertThrows(Exception.class, () -> {
            qut.setId(-10);
        });
    }

    @Test
    void getName() {
        assertEquals( "QUT", qut.getName() );
    }

    @Test
    void getBalance() {
        assertEquals(100, qut.getBalance());
    }

    @Test
    void setName() {
        qut.setName("UQ");
        assertEquals("UQ", qut.getName());
    }

    @Test
    void setBalance() throws Exception {
        qut.setBalance(200);
        assertEquals(200, qut.getBalance());
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
        assertEquals(false, qut.equals(o) );
        Object oo = new OrganisationalUnit(0, "QUT", 100);
        assertEquals(true, qut.equals(oo));
    }

    @Test
    void testHashCodeWorking() {
        int hashCode = qut.hashCode();
    }
}