package luggagesoftware.controller;

import luggagesoftware.Luggage;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.hamcrest.MatcherAssert.assertThat;
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
        assertNull(destination);
    }

    @Test
    void getDeparture() {
    }

    @Test
    void getDate() {
    }

    @Test
    void getFlightNumber() {
        String flightNumber = testLuggage.getFlightNumber();
        assertThat(flightNumber, Matchers.containsString("6ANT2"));
    }

    @Test
    void getLuggageId() {
    }

    @Test
    void getString() {
    }

    @Test
    void getState() {
    }

    @Test
    void getColor() {
    }

    @Test
    void getType() {
    }
}