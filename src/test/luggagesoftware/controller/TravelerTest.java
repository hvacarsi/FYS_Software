package luggagesoftware.controller;

import javafx.beans.property.StringProperty;
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
        assertTrue(postcode == "1082RT");
    }

    @Test
    void getStad() {
    }

    @Test
    void getLand() {
    }

    @Test
    void getAdres() {
    }

    @Test
    void getMobiel() {
        String phonenumber = testTraveler.getMobiel();
        assertTrue(phonenumber.startsWith("06"));
    }

    @Test
    void getMail() {
    }

    @Test
    void getId() {

    }

    @Test
    void getString() {
    }
}