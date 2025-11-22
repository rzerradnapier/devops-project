package com.napier.devops;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for Country class.
 * Tests the Country model class functionality.
 */
public class CountryTest {

    // We will use this country instance for performing and testing operations on a country object.
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

    /**
     * Test toString method to make sure it provides the right format.
     */
    @Test
    void toString_formatsAllFields_asExpected() {
        // Arrange
        Country c = new Country();
        // If you use a constructor, replace these with it.
        c.setCode("US");
        c.setName("United States");
        c.setContinent("North America");
        c.setRegion("Northern America");
        c.setPopulation(331002651);
        c.setCapital(33100);

        String expected =
                "Country {\t" +
                        "  code='US',\t" +
                        "  name='United States',\t" +
                        "  continent='North America',\t" +
                        "  region='Northern America',\t" +
                        "  population=331002651,\t" +
                        "  capital='33100'\t" +
                        "}";

        // Act
        String actual = c.toString();

        // Assert
        assertEquals(expected, actual);
    }

    /**
     * Test toString method to make sure it provides the right format even if everything is null or zero.
     */
    @Test
    void toString_handlesNulls_consistently() {
        Country c = new Country();
        // Leave everything null / zero to ensure consistent rendering
        c.setPopulation(0);

        String expected =
                "Country {\t" +
                        "  code='null',\t" +
                        "  name='null',\t" +
                        "  continent='null',\t" +
                        "  region='null',\t" +
                        "  population=0,\t" +
                        "  capital='null'\t" +
                        "}";

        assertEquals(expected, c.toString());
    }

}
