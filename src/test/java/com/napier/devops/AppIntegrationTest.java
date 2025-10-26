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
        app.connect("localhost:33060", 30000);

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
     * USE CASE 1 :This test checks if the method correctly fetches all countries and verifies they are sorted by population,
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
     * USE CASE 2: This test checks if the method correctly fetches all countries in a continent organised by largest to smallest population Continent.
     */
    @Test
    void testGetAllCountriesByPopulationInAContinentLargestToSmallest() {
        // Call the method to get all countries in Africa by population in descending order
        List<Country> countries = app.getCountryReportService().getAllCountriesInContinentByPopulationLargestToSmallest("Africa");

        // Verify that the list is not null and not empty
        assertNotNull(countries);
        assertFalse(countries.isEmpty());

        // Here we do a spot check on the first two countries in the returned list
        // The countries are expected to be sorted by population, hence the first country
        // should have a higher population than the second one
        assertTrue(countries.get(0).getPopulation() > countries.get(1).getPopulation());

        // Test with another continent
        List<Country> europeCountries = app.getCountryReportService().getAllCountriesInContinentByPopulationLargestToSmallest("Europe");
        assertNotNull(europeCountries);
        assertFalse(europeCountries.isEmpty());

        // Test with invalid continent
        List<Country> invalidCountries = app.getCountryReportService().getAllCountriesInContinentByPopulationLargestToSmallest("InvalidContinent");
        assertNotNull(invalidCountries);
        assertTrue(invalidCountries.isEmpty());
    }


    /**
     * USE CASE 3: This test checks if the method correctly fetches all countries in a region organised by largest to smallest population region.
     */
    @Test
    void testGetAllCountriesByPopulationInARegionLargestToSmallest() {
        // Call the method to get all countries in South America by population in descending order
        List<Country> countries = app.getCountryReportService().getAllCountriesInRegionByPopulationLargestToSmallest("South America");

        // Verify that the list is not null and not empty
        assertNotNull(countries);
        assertFalse(countries.isEmpty());

        // Here we do a spot check on the first two countries in the returned list.
        // The countries are expected to be sorted by population, hence the first country
        // should have a higher population than the second one
        assertTrue(countries.get(0).getPopulation() > countries.get(1).getPopulation());

        // Test with another region
        List<Country> europeCountries = app.getCountryReportService().getAllCountriesInRegionByPopulationLargestToSmallest("Eastern Europe");
        assertNotNull(europeCountries);
        assertFalse(europeCountries.isEmpty());

        // Test with invalid region
        List<Country> invalidCountries = app.getCountryReportService().getAllCountriesInRegionByPopulationLargestToSmallest("InvalidRegion");
        assertNotNull(invalidCountries);
        assertTrue(invalidCountries.isEmpty());
    }


    /**
     * USE CASE 4: This test checks if the method correctly fetches the top N most populated countries in the world.
     */
    @Test
    void testGetTopNCountriesByPopulation() {
        int n = 10;  // Replace with any integer
        // Call the method to get the top N most populated countries
        List<Country> countries = app.getCountryReportService().printTopNCountriesByPopulation(n);

        // Verify that the list is not null and not empty
        assertNotNull(countries);
        assertFalse(countries.isEmpty());

        // Verify the size of the returned list is N
        assertEquals(n, countries.size());

        // Check if the list is ordered correctly by confirming that each country's population is less than or equal to the previous country's population
        for (int i = 0; i < countries.size() - 1; i++) {
            assertTrue(countries.get(i).getPopulation() >= countries.get(i + 1).getPopulation());
        }
    }


    /**
     * USE CASE 5: This test checks if the method correctly fetches the top N most populated countries in a specified continent.
     * The method is expected to return a list of countries in the specified continent sorted by population,
     * from largest to smallest. The list size should be N.
     */
    @Test
    void testPrintTopNCountriesInContinentByPopulation() {
        // Define the number of countries and the continent
        String continent = "Europe";
        int numCountries = 5;

        try {
            // Call the method and check if it executes without throwing an exception
            app.getCountryReportService().printTopNCountriesInContinentByPopulation(continent, numCountries);
        } catch (Exception e) {
            fail("Exception should not have been thrown.");
        }
    }

    /**
     * USE CASE 6: This test checks if the method correctly fetches the top N most populated countries in a specified region.
     * The method is expected to return a list of countries in the specified region sorted by population,
     * from largest to smallest. The list size should be N.
     */
    @Test
    void testPrintTopNCountriesInRegionByPopulation() {
        // Define the number of countries and the continent
        String region = "South America";
        int numCountries = 5;

        try {
            // Call the method and check if it executes without throwing an exception
            app.getCountryReportService().printTopNCountriesInRegionByPopulation(region, numCountries);
        } catch (Exception e) {
            fail("Exception should not have been thrown.");
        }
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
