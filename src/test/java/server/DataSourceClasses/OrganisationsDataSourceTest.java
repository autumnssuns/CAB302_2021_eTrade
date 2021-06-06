package server.DataSourceClasses;

import common.dataClasses.OrganisationalUnit;
import org.junit.jupiter.api.*;
import server.DBConnection;
import server.DataSourceClasses.CasesToResponse;
import server.DataSourceClasses.OrganisationsDataSource;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class OrganisationsDataSourceTest {

    OrganisationsDataSource organisationsDataSource;
    OrganisationalUnit org00 = new OrganisationalUnit(0, "QUT", 99);
    OrganisationalUnit org01 = new OrganisationalUnit(1, "UQ", 100);

    OrganisationsDataSourceTest() throws Exception {
    }

    @BeforeAll
    static void startTestMode(){
        DBConnection.setTestMode(true);
    }

    @AfterAll
    static void stopTestMode(){
        DBConnection.setTestMode(false);
    }

    @BeforeEach
    void setUp() throws SQLException {
        CasesToResponse.cleanDatabase();
        organisationsDataSource = new OrganisationsDataSource();
        organisationsDataSource.addOrganisation(org00);
    }

    @AfterEach
    void tearDown() throws SQLException {
        CasesToResponse.cleanDatabase();
    }


    @Test
    void deleteAll() throws SQLException {
        organisationsDataSource.deleteAll();
        assertEquals(0,organisationsDataSource.getOrganisationList().size());
    }

    @Test
    void addOrganisation() throws SQLException {
        organisationsDataSource.addOrganisation(org01);
        assertEquals(2,organisationsDataSource.getOrganisationList().size());
    }

    @Test
    void deleteOrganisation() throws Exception {
        organisationsDataSource.deleteOrganisation(0);
        assertEquals(organisationsDataSource.getOrganisationList().size(), 0);
    }

    @Test
    void getOrganisation() {
        assertEquals(organisationsDataSource.getOrganisation(0).getName(), "QUT");
        assertEquals(organisationsDataSource.getOrganisation(0).getBalance(), 99);
    }

    @Test
    void editOrganisation() throws Exception {
        organisationsDataSource.editOrganisation(new OrganisationalUnit(0, "QUTT", 9900));
        assertEquals(organisationsDataSource.getOrganisation(0).getName(), "QUTT");
        assertEquals(organisationsDataSource.getOrganisation(0).getBalance(), 9900);
    }
}