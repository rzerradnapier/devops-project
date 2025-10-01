package com.napier.devops;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.napier.constant.Constant.DEFAULT_COUNTRY_CODE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit test for Country class.
 */
public class CountryTest extends AppTest{

    // We will use this country instance for performing and testing operations on an country object.
    // It is initialized in each test via the setUp method.
    private Country country;

    /**
     * Set up the test data.
     */
    @BeforeEach
    public void setUp() {
        // Call the parent setup to initialize the app and connection
        super.setUp();
        // Initializing a new country object before each test
        country = new Country();
    }

    /**
     * Tests the {@link App#getCountryByCode(String)} method to ensure it retrieves
     * a country object correctly based on the provided country code.
     * Verifies that the returned country is not null and that the code matches
     * the expected value.
     */
    @Test
    public void testGetCountry() {
        Country country = app.getCountryByCode(DEFAULT_COUNTRY_CODE);
        assertNotNull(country);
        assertEquals(DEFAULT_COUNTRY_CODE, country.getCode());
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
}
