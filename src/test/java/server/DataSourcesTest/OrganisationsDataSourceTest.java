package server.DataSourcesTest;

import common.dataClasses.OrganisationalUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Excluded_PUT_ALL_EXCLUSIONS_HERE.DataSourceClasses.OrganisationsDataSource;

import static org.junit.jupiter.api.Assertions.*;

class OrganisationsDataSourceTest {

    OrganisationsDataSource organisationsDataSource;
    OrganisationalUnit org00 = new OrganisationalUnit(0, "QUT", 99);
    OrganisationalUnit org01 = new OrganisationalUnit(1, "UQ", 100);

    OrganisationsDataSourceTest() throws Exception {
    }

    @BeforeEach
    void setUp() {
        organisationsDataSource = new OrganisationsDataSource();
        organisationsDataSource.deleteAll();
        organisationsDataSource.addOrganisation(org00);
    }

    @Test
    void deleteAll() {
        organisationsDataSource.deleteAll();
        assertEquals(organisationsDataSource.getOrganisationList().size(), 0);
    }

    @Test
    void addOrganisation() {
        organisationsDataSource.addOrganisation(org01);
        assertEquals(organisationsDataSource.getOrganisationList().size(), 2);
    }

    @Test
    void deleteOrganisation() {
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