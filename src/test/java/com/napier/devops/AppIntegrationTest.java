package com.napier.devops;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("localhost:3306", 30000);

    }

    /**
     * Test method for getCountryByCode() in com.napier.devops.App.
     * This test checks if the method correctly fetches country details by code.
     */
    @Test
    void testGetCountryByCode() {
        // Call the method with "USA" as the argument to get country details by code
        Country country = app.getCountryByCode("USA");

        // Check if the returned country is not null
        assertNotNull(country);

        // Check if the country's code is "USA", this verifies that the correct country details were fetched
        assertEquals("USA", country.getCode());
    }

    /**
     * Test method for getAllCountriesByPopulationLargestToSmallest() in com.napier.devops.App.
     * This test checks if the method correctly fetches all countries and verifies they are sorted by population,
     * from largest to smallest, by checking that the first country has a higher population than the second one.
     */
    @Test
    void testGetAllCountriesByPopulationLargestToSmallest() {
        // Call the method to get all countries by population in descending order
        List<Country> countries = app.getAllCountriesByPopulationLargestToSmallest();

        // Here we do a spot check on the first two countries in the returned list
        // The countries are expected to be sorted by population, hence the first country
        // should have a higher population than the second one
        assertTrue(countries.get(0).getPopulation() > countries.get(1).getPopulation());
    }
}
