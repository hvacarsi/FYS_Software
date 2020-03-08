package luggagesoftware.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MatchTest {
    Match testMatch;
    @BeforeAll
    public void testMatch(){
        testMatch = new Match("147", "Found", "500755301",
                    "1", "Emin", "3/6/2020", "red", "Wheels", "Schiphol Airport");
        }

    @AfterAll
    void tearDown() {
        testMatch = null;
    }

    @Test
    @DisplayName("Type controleren")
    void getType() {
        String type = testMatch.getType();
        assertTrue(type == "Wheels");
    }

    @Test
    @DisplayName("Airport controleren")
    void getAirport() {
        String airport = testMatch.getAirport();
        assertEquals(airport, "Rotterdam The Hague Airport", "You're at the wrong airport, your luggage is at Schiphol Airport!");
    }

    @Test
    @DisplayName("Color controleren")
    void getColor() {
        String color = testMatch.getColor();
        assertSame("red", color);
    }

    @Test
    @DisplayName("Label nummer controleren of begin klopt")
    void getLblnr() {
        String lblNr = testMatch.getLblnr();
        assertThat(lblNr, startsWith("14"));
    }

    @Test
    @DisplayName("State controleren")
    void getState() {
        String state = testMatch.getState();
        assertEquals(state, "Found");
    }

    @Test
    @DisplayName("Traveler Id controleren of einde klopt")
    void getTravelerId() {
        String travelerId = testMatch.getTravelerId();
        assertThat(travelerId, Matchers.endsWith("301"));
    }

    @Test
    @DisplayName("Match Id controleren")
    void getMatchId() {
        String matchId = testMatch.getMatchId();
        assertEquals(matchId, "1");
    }

    @Test
    @DisplayName("Naam controleren of begin klopt")
    void getTravelerName() {
        String travelerName = testMatch.getTravelerName();
        assertThat(travelerName, Matchers.startsWith("Em"));
    }

    @Test
    @DisplayName("Jaar van datum controleren")
    void getDate() {
        String date = testMatch.getDate();
        assertThat(date, Matchers.endsWith("2020"));
    }

    @Test
    @DisplayName("Strings controleren")
    void getString() {
        String stringLblnr = testMatch.getString("lblnr");
        assertEquals(testMatch.getLblnr(), stringLblnr);
    }
}