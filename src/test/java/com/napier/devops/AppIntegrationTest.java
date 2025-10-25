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
     * Test method for getCountryByCode() via CountryReportService.
     * This test checks if the method correctly fetches country details by code.
     */
    @Test
    void testGetCountryByCode() {
        // Call the method with "USA" as the argument to get country details by code
        Country country = app.getCountryReportService().getCountryByCode("USA");

        // Check if the returned country is not null
        assertNotNull(country);

        // Check if the country's code is "USA", this verifies that the correct country details were fetched
        assertEquals("USA", country.getCode());
    }

    /**
     * Test method for getAllCountriesByPopulationLargestToSmallest() via CountryReportService.
     * This test checks if the method correctly fetches all countries and verifies they are sorted by population,
     * from largest to smallest, by checking that the first country has a higher population than the second one.
     */
    @Test
    void testGetAllCountriesByPopulationLargestToSmallest() {
        // Call the method to get all countries by population in descending order
        List<Country> countries = app.getCountryReportService().getAllCountriesByPopulationLargestToSmallest();

        // Here we do a spot check on the first two countries in the returned list
        // The countries are expected to be sorted by population, hence the first country
        // should have a higher population than the second one
        assertTrue(countries.get(0).getPopulation() > countries.get(1).getPopulation());
    }

    /**
     * Test method for getAllCitiesByPopulationLargestToSmallest() via CityReportService.
     * USE CASE 7: This test checks if the method correctly fetches all cities and verifies they are sorted by population,
     * from largest to smallest, by checking that the first city has a higher population than the second one.
     */
    @Test
    void testGetAllCitiesByPopulationLargestToSmallest() {
        // Call the method to get all cities by population in descending order
        List<City> cities = app.getCityReportService().getAllCitiesByPopulationLargestToSmallest();

        // Verify that the list is not null and not empty
        assertNotNull(cities);
        assertFalse(cities.isEmpty());

        // Here we do a spot check on the first two cities in the returned list
        // The cities are expected to be sorted by population, hence the first city
        // should have a higher population than the second one
        assertTrue(cities.get(0).getPopulation() >= cities.get(1).getPopulation());

        // Verify that each city has required fields populated
        City firstCity = cities.get(0);
        assertNotNull(firstCity.getId());
        assertNotNull(firstCity.getName());
        assertNotNull(firstCity.getCountryCode());
        assertNotNull(firstCity.getPopulation());
    }

    /**
     * Test method for getAllCitiesInContinentByPopulationLargestToSmallest() via CityReportService.
     * USE CASE 8: This test checks if the method correctly fetches all cities in a continent and verifies they are sorted by population,
     * from largest to smallest, by checking that the first city has a higher population than the second one.
     */
    @Test
    void testGetAllCitiesInContinentByPopulationLargestToSmallest() {
        // Call the method to get all cities in Asia by population in descending order
        List<City> cities = app.getCityReportService().getAllCitiesInContinentByPopulationLargestToSmallest("Asia");

        // Verify that the list is not null and not empty
        assertNotNull(cities);
        assertFalse(cities.isEmpty());

        // Here we do a spot check on the first two cities in the returned list
        // The cities are expected to be sorted by population, hence the first city
        // should have a higher population than the second one
        assertTrue(cities.get(0).getPopulation() >= cities.get(1).getPopulation());

        // Verify that each city has required fields populated
        City firstCity = cities.get(0);
        assertNotNull(firstCity.getId());
        assertNotNull(firstCity.getName());
        assertNotNull(firstCity.getCountryCode());
        assertNotNull(firstCity.getPopulation());

        // Test with another continent
        List<City> europeCities = app.getCityReportService().getAllCitiesInContinentByPopulationLargestToSmallest("Europe");
        assertNotNull(europeCities);
        assertFalse(europeCities.isEmpty());

        // Test with invalid continent
        List<City> invalidCities = app.getCityReportService().getAllCitiesInContinentByPopulationLargestToSmallest("InvalidContinent");
        assertNotNull(invalidCities);
        assertTrue(invalidCities.isEmpty());
    }
}
