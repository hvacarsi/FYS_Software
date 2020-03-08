package luggagesoftware.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

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
    void hasMail() {
        assertTrue(testTraveler.hasMail());
    }

    @Test
    void getNaam() {
        String name = testTraveler.getNaam();
        assertEquals(name, "Emin");
    }

    @Test
    void getAchternaam() {
        String lastname = testTraveler.getAchternaam();
        assertEquals(lastname, "Torun");

    }

    @Test
    void getGeb_datum() {
        String dateOfBirth = testTraveler.getGeb_datum();
        assertTrue(dateOfBirth == "13-11-1997");
    }

    @Test
    void getPostcode() {
        String postcode = testTraveler.getPostcode();
        assertSame("1082RT", postcode);
    }

    @Test
    void getStad() {
        String stad = testTraveler.getStad();
        assertTrue(stad == "Amsterdam");
    }

    @Test
    void getLand() {
        //Forced Error!
        String land = testTraveler.getLand();
        assertTrue(land == "Belgie");
    }

    @Test
    void getAdres() {
        String adres = testTraveler.getAdres();
        assertTrue(adres == "Wibautstraat");
    }

    @Test
    void getMobiel() {
        String phonenumber = testTraveler.getMobiel();
        assertTrue(phonenumber.startsWith("06"));
    }

    @Test
    void getMail() {
        String mail = testTraveler.getMail();
        assertTrue(mail == "emintorun58@gmail.com");
    }

    @Test
    void getId() {

    }

    @Test
    void getString() {
    }
}