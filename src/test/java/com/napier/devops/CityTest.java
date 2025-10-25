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
    public void setUp() {
        city = new City();
    }

    @Test
    public void testSetAndGetId() {
        Integer id = 1;
        city.setId(id);
        assertEquals(id, city.getId());
    }

    @Test
    public void testSetAndGetName() {
        String name = "New York";
        city.setName(name);
        assertEquals(name, city.getName());
    }

    @Test
    public void testSetAndGetCountryCode() {
        String countryCode = "USA";
        city.setCountryCode(countryCode);
        assertEquals(countryCode, city.getCountryCode());
    }

    @Test
    public void testSetAndGetDistrict() {
        String district = "New York";
        city.setDistrict(district);
        assertEquals(district, city.getDistrict());
    }

    @Test
    public void testSetAndGetPopulation() {
        Integer population = 8000000;
        city.setPopulation(population);
        assertEquals(population, city.getPopulation());
    }

    @Test
    public void testSetAll() {
        Integer id = 1;
        String name = "New York";
        String countryCode = "USA";
        String district = "New York";
        Integer population = 8000000;

        City result = city.setAll(id, name, countryCode, district, population);

        // Test method chaining
        assertSame(city, result);

        // Test all properties are set correctly
        assertEquals(id, city.getId());
        assertEquals(name, city.getName());
        assertEquals(countryCode, city.getCountryCode());
        assertEquals(district, city.getDistrict());
        assertEquals(population, city.getPopulation());
    }

    @Test
    public void testToString() {
        city.setAll(1, "New York", "USA", "New York", 8000000);
        String expected = "City {\n" +
                "  id=1,\n" +
                "  name='New York',\n" +
                "  countryCode='USA',\n" +
                "  district='New York',\n" +
                "  population=8000000\n" +
                '}';
        assertEquals(expected, city.toString());
    }

    @Test
    public void testToStringWithNullValues() {
        String expected = "City {\n" +
                "  id=null,\n" +
                "  name='null',\n" +
                "  countryCode='null',\n" +
                "  district='null',\n" +
                "  population=null\n" +
                '}';
        assertEquals(expected, city.toString());
    }
}