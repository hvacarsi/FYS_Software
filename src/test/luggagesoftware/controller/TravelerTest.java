package luggagesoftware.controller;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TravelerTest {
    Traveler testTraveler;

    @BeforeAll
    public void testEmployee(){
        testTraveler = new Traveler("Emin", "Torun", "13-11-1997", "1082RT",
                "Amsterdam", "Nederland", "Wibautstraat", "0654948586",
                "emintorun58@gmail.com", "500755301");
    }

    @AfterAll
    void tearDown() {
        testTraveler = null;
    }

    @Test
    @DisplayName("Traveler email controleren of het bestaat")
    void hasMail() {
        assertTrue(testTraveler.hasMail());
    }

    @Test
    @DisplayName("Traveler naam controleren")
    void getNaam() {
        String name = testTraveler.getNaam();
        assertEquals(name, "Emin");
    }

    @Test
    @DisplayName("Traveler achternaam controleren")
    void getAchternaam() {
        String lastname = testTraveler.getAchternaam();
        assertEquals(lastname, "Torun");

    }

    @Test
    @DisplayName("Traveler geboorte datum controleren")
    void getGeb_datum() {
        String dateOfBirth = testTraveler.getGeb_datum();
        assertTrue(dateOfBirth == "13-11-1997");
    }

    @Test
    @DisplayName("Traveler postcode controleren")
    void getPostcode() {
        String postcode = testTraveler.getPostcode();
        assertSame("1082RT", postcode);
    }

    @Test
    @DisplayName("Traveler geboorteplaats controleren")
    void getStad() {
        String stad = testTraveler.getStad();
        assertTrue(stad == "Amsterdam");
    }

    @Test
    @DisplayName("Traveler geboorteland controleren")
    void getLand() {
        //Forced Error!
        String land = testTraveler.getLand();
        assertTrue(land == "Belgie");
    }

    @Test
    @DisplayName("Traveler adres controleren")
    void getAdres() {
        String adres = testTraveler.getAdres();
        assertTrue(adres == "Wibautstraat");
    }

    @Test
    @DisplayName("Traveler mobielenummer controleren of begin klopt")
    void getMobiel() {
        String phonenumber = testTraveler.getMobiel();
        assertTrue(phonenumber.startsWith("06"));
    }

    @Test
    @DisplayName("Traveler email controleren")
    void getMail() {
        String mail = testTraveler.getMail();
        assertTrue(mail == "emintorun58@gmail.com");
    }

    @Test
    @DisplayName("Traveler id controleren")
    void getId() {
        String id = testTraveler.getId();
        assertSame(id, null, "ID is null!");

    }


    @Test
    @DisplayName("Strings controleren")
    void getString() {
        String stringWhat = testTraveler.getString("postcode");
        assertEquals(testTraveler.getPostcode(), stringWhat);
    }
}