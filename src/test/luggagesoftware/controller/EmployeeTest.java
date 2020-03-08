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
    @DisplayName("Employees toevoegen onder juiste omstandigheden")
    void addEmployeeToList() {

        Employee testEmployeeOne = new Employee("0001", "Ahmet", "ahmetcan",
                "ahmet@gmail.com", "1", "1", "5");
        Employee testEmployeeTwo = new Employee("0002", "Emin", "Torun",
                "emin@gmail.com", "1", "10", "4");
        Employee testEmployeeThree = new Employee("0003", "Furkan", "Turkmen",
                "furkan@gmail.com", "1", "1", "3");
        Employee testEmployeeFour = new Employee("0004", "John", "Doe",
                "john@gmail.com", "1", "1", "3");

            testEmp.addEmployeeToList(testEmployeeOne);
            testEmp.addEmployeeToList(testEmployeeTwo);
            testEmp.addEmployeeToList(testEmployeeThree);
            /*testEmp.addEmployeeToList(testEmployeeFour);
            testEmp.addEmployeeToList(testEmployeeFour);*/

        int i = 0;
        if(!testEmp.employees.isEmpty() && testEmp.employees.size() <= 3){
            System.out.println("De volgende employees zijn toegevoegd: \n");
            for(Employee emp : testEmp.employees) {
                assertEquals(emp.getName(), testEmp.employees.get(i).getName());
                System.out.println(testEmp.employees.get(i).getName());
                i++;
            }

        }else if(testEmp.employees.isEmpty()){
            throw new ArithmeticException("Employees zijn nooit toegevoegd!");
        } else{
            throw new ArithmeticException("Limiet is overschreden met " + (testEmp.employees.size() - 3) + " employee(s)!");

        }
    }

    @Test
    @DisplayName("Naam vergelijken met onjuist invoer")
    void getName() {
        String name = testEmp.getName();
        assertSame(name, wrongName, "Your name is not " + wrongName + ", but should be " + name + "!\n");
    }


    @Test
    @DisplayName("Id controleren of begin klopt")
    void getId() {
        String id = testEmp.getId();
        assertThat(id, startsWith("0001"));
    }

    @Test
    @DisplayName("Username controleren of einde klopt")
    void getUsername() {
        String username = testEmp.getUsername();
        assertThat(username, Matchers.endsWith("ahmetcan"));
    }

    @Test
    @DisplayName("Email controleren")
    void getMail() {
        String email = testEmp.getMail();
        assertEquals(email, "ahmet@gmail.com");
    }

    @Test
    @DisplayName("Level controleren")
    void getLevel() {
        String level = testEmp.getLevel();
        assertEquals(level, "1");
    }

    @Test
    @DisplayName("AirportId controleren")
    void getAirport_id() {
        String airport_id = testEmp.getAirport_id();
        assertEquals(airport_id, "1");
    }

    @Test
    @DisplayName("State controleren")
    void getState() {
        String state = testEmp.getState();
        assertEquals(state, "5");

    }

    @Test
    @DisplayName("Strings controleren")
    void getString() {
        String stringWhat = testEmp.getString("name");
        assertEquals(testEmp.getName(), stringWhat);
    }
}