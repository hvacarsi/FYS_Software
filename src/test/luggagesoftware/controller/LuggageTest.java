package luggagesoftware.controller;

import luggagesoftware.Luggage;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class LuggageTest {

    Luggage testLuggage;
    String color;

    @BeforeAll
    public void testLuggage(){
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
        assertTrue(labelNumber == "147");
    }

    @Test
    void getDestination() {
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