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
    void addEmployeeToList() {
        Employee testEmployeeOne = new Employee("0001", "Ahmet", "ahmetcan",
                "ahmet@gmail.com", "1", "1", "5");

        Employee testEmployeeTwo = new Employee("0002", "Emin", "Torun",
                "emin@gmail.com", "1", "10", "4");

        Employee testEmployeeThree = new Employee("0003", "Furkan", "Turkmen",
                "furkan@gmail.com", "1", "1", "3");

        testEmp.addEmployeeToList(testEmployeeOne);
        testEmp.addEmployeeToList(testEmployeeTwo);
        testEmp.addEmployeeToList(testEmployeeThree);

        int i = 0;
        for(Employee emp : testEmp.employees){
            assertEquals(emp.getName(), testEmp.employees.get(i).getName());
            i++;
        }
    }

    @Test
    void getName() {
        String name = testEmp.getName();
        assertTrue(name == wrongName);
    }


    @Test
    void getId() {
        String id = testEmp.getId();
        assertThat(id, startsWith("0001"));
    }

    @Test
    void getUsername() {
        String username = testEmp.getUsername();
        assertThat(username, Matchers.endsWith("ahmetcan"));
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

    @Test
    void getString() {
        String stringWhat = testEmp.getString("name");
        assertEquals(testEmp.getName(), stringWhat);
    }
}