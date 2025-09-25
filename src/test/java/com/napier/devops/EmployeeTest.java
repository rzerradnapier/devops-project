package com.napier.devops;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for Employee class.
 */
public class EmployeeTest {

    // We will use this Employee instance for performing and testing operations on an Employee object.
    // It is initialized in each test via the setUp method.
    private Employee employee;

    /**
     * Set up the test data.
     */
    @BeforeEach
    public void setUp() {
        // Initializing a new Employee object before each test
        employee = new Employee();
    }

    /**
     * Test the assignment and retrieval of the emp_no field.
     */
    @Test
    public void testEmpNo() {
        int empNo = 12345;
        employee.emp_no = empNo;
        // Making sure that the emp_no field correctly stores the assigned value
        assertEquals(empNo, employee.emp_no);
    }

    /**
     * Test the assignment and retrieval of the first_name field.
     */
    @Test
    public void testFirstName() {
        String firstName = "John";
        employee.first_name = firstName;
        // Making sure that the first_name field correctly stores the assigned value
        assertEquals(firstName, employee.first_name);
    }

    /**
     * Test the assignment and retrieval of the last_name field.
     */
    @Test
    public void testLastName() {
        String lastName = "Doe";
        employee.last_name = lastName;
        // Making sure that the last_name field correctly stores the assigned value
        assertEquals(lastName, employee.last_name);
    }

    /**
     * Test the assignment and retrieval of the title field.
     */
    @Test
    public void testTitle() {
        String title = "Engineer";
        employee.title = title;
        // Making sure that the title field correctly stores the assigned value
        assertEquals(title, employee.title);
    }

    /**
     * Test the assignment and retrieval of the salary field.
     */
    @Test
    public void testSalary() {
        int salary = 100000;
        employee.salary = salary;
        // Making sure that the salary field correctly stores the assigned value
        assertEquals(salary, employee.salary);
    }

    /**
     * Test the assignment and retrieval of the dept_name field.
     */
    @Test
    public void testDeptName() {
        String deptName = "Engineering";
        employee.dept_name = deptName;
        // Making sure that the dept_name field correctly stores the assigned value
        assertEquals(deptName, employee.dept_name);
    }

    /**
     * Test the assignment and retrieval of the manager field.
     */
    @Test
    public void testManager() {
        String manager = "Manager1";
        employee.manager = manager;
        // Making sure that the manager field correctly stores the assigned value
        assertEquals(manager, employee.manager);
    }
}