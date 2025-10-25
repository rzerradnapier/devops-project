package com.napier.devops;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Country class.
 */
public class CountryTest {

    private Country country;

    @BeforeEach
    public void setUp() {
        country = new Country();
    }

    @Test
    public void testSetAndGetCode() {
        String code = "USA";
        country.setCode(code);
        assertEquals(code, country.getCode());
    }

    @Test
    public void testSetAndGetName() {
        String name = "United States";
        country.setName(name);
        assertEquals(name, country.getName());
    }

    @Test
    public void testSetAndGetContinent() {
        String continent = "North America";
        country.setContinent(continent);
        assertEquals(continent, country.getContinent());
    }

    @Test
    public void testSetAndGetRegion() {
        String region = "North America";
        country.setRegion(region);
        assertEquals(region, country.getRegion());
    }

    @Test
    public void testSetAndGetPopulation() {
        Integer population = 331000000;
        country.setPopulation(population);
        assertEquals(population, country.getPopulation());
    }

    @Test
    public void testSetAndGetCapital() {
        Integer capital = 3813;
        country.setCapital(capital);
        assertEquals(capital, country.getCapital());
    }

    @Test
    public void testSetAll() {
        String code = "USA";
        String name = "United States";
        String continent = "North America";
        String region = "North America";
        Integer population = 331000000;
        Integer capital = 3813;

        Country result = country.setAll(code, name, continent, region, population, capital);

        // Test method chaining
        assertSame(country, result);

        // Test all properties are set correctly
        assertEquals(code, country.getCode());
        assertEquals(name, country.getName());
        assertEquals(continent, country.getContinent());
        assertEquals(region, country.getRegion());
        assertEquals(population, country.getPopulation());
        assertEquals(capital, country.getCapital());
    }

    @Test
    public void testToString() {
        country.setAll("USA", "United States", "North America", "North America", 331000000, 3813);
        String expected = "Country {\n" +
                "  code='USA',\n" +
                "  name='United States',\n" +
                "  continent='North America',\n" +
                "  region='North America',\n" +
                "  population=331000000,\n" +
                "  capital='3813'\n" +
                '}';
        assertEquals(expected, country.toString());
    }

    @Test
    public void testToStringWithNullValues() {
        String expected = "Country {\n" +
                "  code='null',\n" +
                "  name='null',\n" +
                "  continent='null',\n" +
                "  region='null',\n" +
                "  population=null,\n" +
                "  capital='null'\n" +
                '}';
        assertEquals(expected, country.toString());
    }

    @Test
    public void testSetAllWithNullValues() {
        Country result = country.setAll(null, null, null, null, null, null);

        assertSame(country, result);
        assertNull(country.getCode());
        assertNull(country.getName());
        assertNull(country.getContinent());
        assertNull(country.getRegion());
        assertNull(country.getPopulation());
        assertNull(country.getCapital());
    }
}