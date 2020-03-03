package luggagesoftware.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmployeeTest {
    Employee testEmp;

    @BeforeAll
    public void testEmployee(){
        testEmp = new Employee("0001", "Ahmet", "ahmetcan",
                "ahmet@gmail.com", "1", "1", "5");
    }


    @Test
    void getName() {
        String name = testEmp.getName();
        assertEquals(name, "Ahmet");
    }


    @Test
    void getId() {
        String id = testEmp.getId();
        assertEquals(id, "0001");
    }

    @Test
    void getUsername() {
        String username = testEmp.getUsername();
        assertEquals(username, "ahmetcan");
    }

    @Test
    void getMail() {
        String email = testEmp.getMail();
        assertEquals(email, "ahmet@gmail.com");
    }

    @Test
    void getLevel() {
        String level = testEmp.getLevel();
        assertEquals(level, "1");
    }

    @Test
    void getAirport_id() {
        String airport_id = testEmp.getAirport_id();
        assertEquals(airport_id, "1");
    }

    @Test
    void getState() {
        String state = testEmp.getState();
        assertEquals(state, "5");
    }

}