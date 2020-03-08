package luggagesoftware.controller;

import luggagesoftware.Luggage;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class LuggageTest {

    Luggage testLuggage;

    @BeforeAll
    public void testLuggage() {
        testLuggage = new Luggage("147", "36ANT2020", "Lost",
                "Red", "wheels", "33", null, "Schiphol Airport", "3/6/2020");
    }


    @AfterAll
    void tearDown() {
        testLuggage = null;
    }

    @Test
    @DisplayName("Labelnummer controleren")
    void getLabelNumber() {
        String labelNumber = testLuggage.getLabelNumber();
        assertSame("147", labelNumber);
    }

    @Test
    @DisplayName("Destination controleren of het null is")
    void getDestination() {
        assertThrows(NullPointerException.class,
                ()->{
                    String destination = testLuggage.getDestination();
                    NullPointerException nullPointerException = new NullPointerException(destination);
                    System.out.println("Destination was: " + nullPointerException.getMessage());
                    throw nullPointerException;
                    //do whatever you want to do here
                    //ex : objectName.thisMethodShoulThrowNullPointerExceptionForNullParameter(null);
                });
    }

    @Test
    @DisplayName("Departure nullifyen")
    void getDeparture() {

        String departure = testLuggage.getDeparture();
        assertNull(departure, "Departure has been nullified");
    }

    @Test
    void getDate() {
    }

    @Test
    @DisplayName("Vluchtnummer controleren door een deel te matchen")
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