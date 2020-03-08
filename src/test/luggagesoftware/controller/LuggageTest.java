package luggagesoftware.controller;

import luggagesoftware.Luggage;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class LuggageTest {

    Luggage testLuggage;

    @BeforeAll
    public void testLuggage() {
        testLuggage = new Luggage("147", "36ANT2020", "Lost",
                "Red", "wheels", "33", "Antalya", "Schiphol Airport", "3/6/2020");
    }


    @AfterAll
    void tearDown() {
        testLuggage = null;
    }

    @Test
    void getLabelNumber() {
        String labelNumber = testLuggage.getLabelNumber();
        assertSame("147", labelNumber);
    }

    @Test
    void getDestination() {
        String destination = testLuggage.getDestination();
        assertNull(destination, "Your destination is Antalya.");
    }

    @Test
    void getDeparture() {
        String departure = testLuggage.getDeparture();
        assertThat(departure, startsWith("Schiphol"));
    }

    @Test
    void getDate() {
        String date = testLuggage.getDate();
        assertThat(date, Matchers.startsWith("3/6"));
    }

    @Test
    void getFlightNumber() {
        String flightNumber = testLuggage.getFlightNumber();
        assertThat(flightNumber, Matchers.containsString("6ANT2"));
    }

    @Test
    void getLuggageId() {
        String luggageId = testLuggage.getLuggageId();
        assertThat(luggageId, Matchers.endsWith("3"));
    }

    @Test
    void getString() {
        String stringLblnr = testLuggage.getString("labelNumber");
        assertEquals(testLuggage.getLabelNumber(), stringLblnr);
    }

    @Test
    void getState() {
        String state = testLuggage.getState();
        assertEquals(state, "Found", "Your luggage is still lost. Let's hope we find it as soon as possible");
    }

    @Test
    void getColor() {
        String color = testLuggage.getColor();
        assertSame("Red", color);
    }

    @Test
    void getType() {
        String type = testLuggage.getType();
        assertTrue(type == "wheels");
    }
}