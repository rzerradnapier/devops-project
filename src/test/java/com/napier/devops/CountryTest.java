package com.napier.devops;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for Country class.
 * Tests the Country model class functionality.
 */
public class CountryTest {

    // We will use this country instance for performing and testing operations on an country object.
    // It is initialized in each test via the setUp method.
    private Country country;

    /**
     * Set up the test data.
     */
    @BeforeEach
    public void setUp() {
        // Initializing a new country object before each test
        country = new Country();
    }

    /**
     * Test code field (getter & setter).
     */
    @Test
    public void testCode() {
        String code = "USA";
        country.setCode(code);
        assertEquals(code, country.getCode());
    }

    /**
     * Test name field (getter & setter).
     */
    @Test
    public void testName() {
        String name = "United States";
        country.setName(name);
        assertEquals(name, country.getName());
    }

    /**
     * Test continent field (getter & setter).
     */
    @Test
    public void testContinent() {
        String continent = "North America";
        country.setContinent(continent);
        assertEquals(continent, country.getContinent());
    }

    /**
     * Test region field (getter & setter).
     */
    @Test
    public void testRegion() {
        String region = "North America";
        country.setRegion(region);
        assertEquals(region, country.getRegion());
    }

    /**
     * Test population field (getter & setter).
     */
    @Test
    public void testPopulation() {
        int population = 331000000;
        country.setPopulation(population);
        assertEquals(population, country.getPopulation());
    }

    /**
     * Test capital field (getter & setter).
     */
    @Test
    public void testCapital() {
        int capitalId = 123; // assuming capital is stored as int (FK to city)
        country.setCapital(capitalId);
        assertEquals(capitalId, country.getCapital());
    }
    /**
     * Test for setAll method.
     */
    @Test
    public void testSetAll() {
        String code = "CAN";
        String name = "Canada";
        String continent = "North America";
        String region = "Américas";
        Integer population = 37590000;
        Integer capital = 456; // Assuming capital is stored as int (FK to city)

        country.setAll(code, name, continent, region, population, capital);

        assertEquals(code, country.getCode());
        assertEquals(name, country.getName());
        assertEquals(continent, country.getContinent());
        assertEquals(region, country.getRegion());
        assertEquals(population, country.getPopulation());
        assertEquals(capital, country.getCapital());
    }
}
