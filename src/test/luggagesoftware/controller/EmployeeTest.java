package luggagesoftware.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmployeeTest {
    Employee testEmp;
    String wrongName = "xyz";
    String wrongId = "0010";
    String wrongUsername = "min";
    String wrongEmail = "can@gmail.com";
    String wrongLevel = "100";
    String wrongAirportId = "55";
    String wrongState = "38";

    @BeforeAll
    public void testEmployee(){
        testEmp = new Employee("0001", "Ahmet", "ahmetcan",
                "ahmet@gmail.com", "1", "1", "5");
    }

    @AfterAll
    void tearDown() {
        testEmp = null;
    }

    @Test
    void getName() {
        String name = testEmp.getName();
        assertTrue(name == wrongName);
    }


    @Test
    void getId() {
        String id = testEmp.getId();
        assertThat(id, startsWith(wrongId));
    }

    @Test
    void getUsername() {
        String username = testEmp.getUsername();
        assertThat(username, Matchers.endsWith(wrongUsername));
    }

    @Test
    void getMail() {
        String email = testEmp.getMail();
        assertEquals(email, wrongEmail);
    }

    @Test
    void getLevel() {
        String level = testEmp.getLevel();
        assertEquals(level, wrongLevel);
    }

    @Test
    void getAirport_id() {
        String airport_id = testEmp.getAirport_id();
        assertEquals(airport_id, wrongAirportId);
    }

    @Test
    void getState() {
        String state = testEmp.getState();
        assertEquals(state, wrongState);
    }

    @Test
    void getString() {
        String stringWhat = testEmp.getString("name");
        assertEquals(testEmp.getName(), stringWhat);
    }
}