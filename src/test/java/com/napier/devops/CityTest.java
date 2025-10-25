package com.napier.devops;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the City class.
 */
public class CityTest {

    private City city;

    @BeforeEach
    void setUp() {
        city = new City();
    }

    /**
     * Test the setAll method and verify all fields are set correctly.
     */
    @Test
    void testSetAll() {
        // Test data
        Integer id = 1;
        String name = "Tokyo";
        String countryCode = "JPN";
        String district = "Tokyo";
        Integer population = 7980230;

        // Call setAll method
        City result = city.setAll(id, name, countryCode, district, population);

        // Verify that setAll returns the same instance
        assertSame(city, result);

        // Verify all fields are set correctly
        assertEquals(id, city.getId());
        assertEquals(name, city.getName());
        assertEquals(countryCode, city.getCountryCode());
        assertEquals(district, city.getDistrict());
        assertEquals(population, city.getPopulation());
    }

    /**
     * Test individual getters and setters.
     */
    @Test
    void testGettersAndSetters() {
        // Test ID
        Integer id = 100;
        city.setId(id);
        assertEquals(id, city.getId());

        // Test Name
        String name = "London";
        city.setName(name);
        assertEquals(name, city.getName());

        // Test Country Code
        String countryCode = "GBR";
        city.setCountryCode(countryCode);
        assertEquals(countryCode, city.getCountryCode());

        // Test District
        String district = "England";
        city.setDistrict(district);
        assertEquals(district, city.getDistrict());

        // Test Population
        Integer population = 7556900;
        city.setPopulation(population);
        assertEquals(population, city.getPopulation());
    }

    /**
     * Test the toString method.
     */
    @Test
    void testToString() {
        // Set up test data
        city.setAll(1, "New York", "USA", "New York", 8175133);

        // Get the string representation
        String result = city.toString();

        // Verify that the string contains all the expected information
        assertTrue(result.contains("id=1"));
        assertTrue(result.contains("name='New York'"));
        assertTrue(result.contains("countryCode='USA'"));
        assertTrue(result.contains("district='New York'"));
        assertTrue(result.contains("population=8175133"));
    }

    /**
     * Test with null values.
     */
    @Test
    void testWithNullValues() {
        // Test setting null values
        city.setId(null);
        city.setName(null);
        city.setCountryCode(null);
        city.setDistrict(null);
        city.setPopulation(null);

        // Verify null values are handled correctly
        assertNull(city.getId());
        assertNull(city.getName());
        assertNull(city.getCountryCode());
        assertNull(city.getDistrict());
        assertNull(city.getPopulation());
    }
}