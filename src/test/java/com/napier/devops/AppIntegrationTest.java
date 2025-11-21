package com.napier.devops;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.napier.constant.Constant.*;
import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest {
    static App app;

    @BeforeAll
    static void init() {
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
     * USE CASE 1: This test checks if the method correctly fetches all countries and verifies they are sorted by population,
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

    /**
     * Integration test for USE CASE 9: Cities in a Region by Population
     * This test verifies that the method correctly fetches cities in a specific region.
     */
    @Test
    void testGetAllCitiesInRegionByPopulation_UseCase9() {
        // Test with Western Europe region
        List<City> westernEuropeCities = app.getCityReportService().getAllCitiesInRegionByPopulationLargestToSmallest("Western Europe");

        // Verify that the list is not null and not empty
        assertNotNull(westernEuropeCities);
        assertFalse(westernEuropeCities.isEmpty());

        // Verify sorting
        assertTrue(westernEuropeCities.get(0).getPopulation() >= westernEuropeCities.get(1).getPopulation());

        // Verify that each city has required fields populated
        City firstCity = westernEuropeCities.get(0);
        assertNotNull(firstCity.getId());
        assertNotNull(firstCity.getName());
        assertNotNull(firstCity.getCountryCode());
        assertNotNull(firstCity.getPopulation());

        // Test with Eastern Asia region
        List<City> easternAsiaCities = app.getCityReportService().getAllCitiesInRegionByPopulationLargestToSmallest("Eastern Asia");
        assertNotNull(easternAsiaCities);
        assertFalse(easternAsiaCities.isEmpty());

        // Test with invalid region
        List<City> invalidRegionCities = app.getCityReportService().getAllCitiesInRegionByPopulationLargestToSmallest("InvalidRegion");
        assertNotNull(invalidRegionCities);
        assertTrue(invalidRegionCities.isEmpty());

        System.out.println("USE CASE 9 - Western Europe cities found: " + westernEuropeCities.size());
        System.out.println("USE CASE 9 - Eastern Asia cities found: " + easternAsiaCities.size());
        System.out.println("USE CASE 9 - Top Western Europe city: " + firstCity.getName() + " with population: " + firstCity.getPopulation());
    }

    /**
     * Integration test for USE CASE 10: Cities in a Country by Population
     * This test verifies that the method correctly fetches cities in a specific country.
     */
    @Test
    void testGetAllCitiesInCountryByPopulation_UseCase10() {
        // Test with USA
        List<City> usaCities = app.getCityReportService().getAllCitiesInCountryByPopulationLargestToSmallest("USA");

        // Verify that the list is not null and not empty
        assertNotNull(usaCities);
        assertFalse(usaCities.isEmpty());

        // Verify sorting
        assertTrue(usaCities.get(0).getPopulation() >= usaCities.get(1).getPopulation());

        // Verify all cities are from USA
        for (City city : usaCities) {
            assertEquals("USA", city.getCountryCode());
        }

        // Test with China
        List<City> chinaCities = app.getCityReportService().getAllCitiesInCountryByPopulationLargestToSmallest("CHN");
        assertNotNull(chinaCities);
        assertFalse(chinaCities.isEmpty());

        // Verify all cities are from China
        for (City city : chinaCities) {
            assertEquals("CHN", city.getCountryCode());
        }

        // Test with invalid country code
        List<City> invalidCities = app.getCityReportService().getAllCitiesInCountryByPopulationLargestToSmallest("XXX");
        assertNotNull(invalidCities);
        assertTrue(invalidCities.isEmpty());

        System.out.println("USE CASE 10 - USA cities found: " + usaCities.size());
        System.out.println("USE CASE 10 - China cities found: " + chinaCities.size());
    }

    /**
     * Integration test for USE CASE 11: Cities in a District by Population
     * This test verifies that the method correctly fetches cities in a specific district.
     */
    @Test
    void testGetAllCitiesInDistrictByPopulation_UseCase11() {
        // Test with California district
        List<City> californiaCities = app.getCityReportService().getAllCitiesInDistrictByPopulationLargestToSmallest("California");

        // Verify that the list is not null and not empty
        assertNotNull(californiaCities);
        assertFalse(californiaCities.isEmpty());

        // Verify sorting
        assertTrue(californiaCities.get(0).getPopulation() >= californiaCities.get(1).getPopulation());

        // Verify all cities are from California district
        for (City city : californiaCities) {
            assertEquals("California", city.getDistrict());
        }

        // Test with New York district
        List<City> newYorkCities = app.getCityReportService().getAllCitiesInDistrictByPopulationLargestToSmallest("New York");
        assertNotNull(newYorkCities);
        assertFalse(newYorkCities.isEmpty());

        // Verify all cities are from New York district
        for (City city : newYorkCities) {
            assertEquals("New York", city.getDistrict());
        }

        // Test with invalid district
        List<City> invalidCities = app.getCityReportService().getAllCitiesInDistrictByPopulationLargestToSmallest("InvalidDistrict");
        assertNotNull(invalidCities);
        assertTrue(invalidCities.isEmpty());

        System.out.println("USE CASE 11 - California cities found: " + californiaCities.size());
        System.out.println("USE CASE 11 - New York cities found: " + newYorkCities.size());
    }

    /**
     * Integration test for USE CASE 12: Top N Cities in the World by Population
     * This test verifies that the method correctly fetches top N cities.
     */
    @Test
    void testGetTopNCitiesByPopulation_UseCase12() {
        // Test with N = 10
        List<City> top10Cities = app.getCityReportService().getTopNCitiesByPopulationLargestToSmallest(10);

        // Verify that the list is not null and has exactly 10 cities
        assertNotNull(top10Cities);
        assertEquals(10, top10Cities.size());

        // Verify sorting - each city should have population >= next city
        for (int i = 0; i < top10Cities.size() - 1; i++) {
            assertTrue(top10Cities.get(i).getPopulation() >= top10Cities.get(i + 1).getPopulation());
        }

        // Verify that each city has required fields populated
        City firstCity = top10Cities.get(0);
        assertNotNull(firstCity.getId());
        assertNotNull(firstCity.getName());
        assertNotNull(firstCity.getCountryCode());
        assertNotNull(firstCity.getPopulation());

        // Test with N = 5
        List<City> top5Cities = app.getCityReportService().getTopNCitiesByPopulationLargestToSmallest(5);
        assertNotNull(top5Cities);
        assertEquals(5, top5Cities.size());

        // Test with N = 1
        List<City> top1City = app.getCityReportService().getTopNCitiesByPopulationLargestToSmallest(1);
        assertNotNull(top1City);
        assertEquals(1, top1City.size());

        // Test with invalid N
        List<City> invalidCities = app.getCityReportService().getTopNCitiesByPopulationLargestToSmallest(0);
        assertNotNull(invalidCities);
        assertTrue(invalidCities.isEmpty());

        System.out.println("USE CASE 12 - Top 10 cities found: " + top10Cities.size());
        System.out.println("USE CASE 12 - Top city: " + firstCity.getName() + " with population: " + firstCity.getPopulation());
        System.out.println("USE CASE 12 - Top 5 cities found: " + top5Cities.size());
    }

    /**
     * Integration test for USE CASE 13: Produce a Report on Top N Cities in a Continent
     * This test verifies that the method correctly retrieves and sorts the top N most populated cities
     * within a specified continent.
     */
    @Test
    void testGetTopCitiesByContinent_UseCase13() {
        String continent = DEFAULT_CONTINENT;

        // Test with N = 10
        List<City> top10Cities = app.getCityReportService().getTopCitiesByContinent(continent, DEFAULT_N);

        // Verify that the list is not null and has exactly 10 cities
        assertNotNull(top10Cities, "City list should not be null");
        assertEquals(10, top10Cities.size(), "Should return exactly 10 cities");

        // Verify sorting - each city should have population >= next city
        for (int i = 0; i < top10Cities.size() - 1; i++) {
            assertTrue(
                    top10Cities.get(i).getPopulation() >= top10Cities.get(i + 1).getPopulation(),
                    "Cities should be sorted in descending order by population"
            );
        }

        // Verify that each city has required fields populated
        City firstCity = top10Cities.get(0);
        assertNotNull(firstCity.getId());
        assertNotNull(firstCity.getName());
        assertNotNull(firstCity.getCountryCode());
        assertNotNull(firstCity.getPopulation());

        // Test with smaller limits
        List<City> top5Cities = app.getCityReportService().getTopCitiesByContinent(continent, 5);
        assertNotNull(top5Cities);
        assertEquals(5, top5Cities.size(), "Should return exactly 5 cities");

        List<City> top1City = app.getCityReportService().getTopCitiesByContinent(continent, 1);
        assertNotNull(top1City);
        assertEquals(1, top1City.size(), "Should return exactly 1 city");

        // Test with invalid N (zero or negative)
        List<City> invalidCities = app.getCityReportService().getTopCitiesByContinent(continent, 0);
        assertNotNull(invalidCities, "List should not be null even for invalid input");
        assertTrue(invalidCities.isEmpty(), "Invalid limit should return an empty list");

        System.out.println("USE CASE 13 - Top 10 cities in " + continent + ": " + top10Cities.size());
        System.out.println("USE CASE 13 - Largest city: " + firstCity.getName() + " with population: " + firstCity.getPopulation());
        System.out.println("USE CASE 13 - Top 5 cities in " + continent + ": " + top5Cities.size());
    }

    /**
     * Integration test for USE CASE 14: Produce a Report on Top N Cities in a Region
     * This test verifies that the method correctly retrieves and sorts the top N most populated cities
     * within a specified region.
     */
    @Test
    void testGetTopCitiesByRegion_UseCase14() {
        String region = DEFAULT_REGION;

        // Test with N = 10
        List<City> top10Cities = app.getCityReportService().getTopCitiesByRegion(region, DEFAULT_N);

        // Verify that the list is not null and has exactly 10 cities
        assertNotNull(top10Cities, "City list should not be null");
        assertEquals(10, top10Cities.size(), "Should return exactly 10 cities");

        // Verify sorting - each city should have population >= next city
        for (int i = 0; i < top10Cities.size() - 1; i++) {
            assertTrue(
                    top10Cities.get(i).getPopulation() >= top10Cities.get(i + 1).getPopulation(),
                    "Cities should be sorted in descending order by population"
            );
        }

        // Verify that each city has required fields populated
        City firstCity = top10Cities.get(0);
        assertNotNull(firstCity.getId());
        assertNotNull(firstCity.getName());
        assertNotNull(firstCity.getCountryCode());
        assertNotNull(firstCity.getPopulation());

        // Test with smaller limits
        List<City> top5Cities = app.getCityReportService().getTopCitiesByRegion(region, 5);
        assertNotNull(top5Cities);
        assertEquals(5, top5Cities.size(), "Should return exactly 5 cities");

        List<City> top1City = app.getCityReportService().getTopCitiesByRegion(region, 1);
        assertNotNull(top1City);
        assertEquals(1, top1City.size(), "Should return exactly 1 city");

        // Test with invalid N (zero or negative)
        List<City> invalidCities = app.getCityReportService().getTopCitiesByRegion(region, 0);
        assertNotNull(invalidCities, "List should not be null even for invalid input");
        assertTrue(invalidCities.isEmpty(), "Invalid limit should return an empty list");

        System.out.println("USE CASE 14 - Top 10 cities in region '" + region + "': " + top10Cities.size());
        System.out.println("USE CASE 14 - Largest city: " + firstCity.getName() + " with population: " + firstCity.getPopulation());
        System.out.println("USE CASE 14 - Top 5 cities in region '" + region + "': " + top5Cities.size());
    }

    /**
     * Integration test for USE CASE 15: Produce a Report on Top N Cities in a Country
     * This test verifies that the method correctly retrieves and sorts the top N most populated cities
     * within a specified country.
     */
    @Test
    void testGetTopCitiesByCountry_UseCase15() {
        String countryName = DEFAULT_COUNTRY_NAME;

        // Test with N = 10
        List<City> top10Cities = app.getCityReportService().getTopCitiesByCountry(countryName, DEFAULT_N);

        // Verify that the list is not null and has exactly 10 cities
        assertNotNull(top10Cities, "City list should not be null");
        assertEquals(10, top10Cities.size(), "Should return exactly 10 cities");

        // Verify sorting - each city should have population >= next city
        for (int i = 0; i < top10Cities.size() - 1; i++) {
            assertTrue(
                    top10Cities.get(i).getPopulation() >= top10Cities.get(i + 1).getPopulation(),
                    "Cities should be sorted in descending order by population"
            );
        }

        // Verify that each city has required fields populated
        City firstCity = top10Cities.get(0);
        assertNotNull(firstCity.getId());
        assertNotNull(firstCity.getName());
        assertNotNull(firstCity.getCountryCode());
        assertNotNull(firstCity.getPopulation());

        // Test with smaller limits
        List<City> top5Cities = app.getCityReportService().getTopCitiesByCountry(countryName, 5);
        assertNotNull(top5Cities);
        assertEquals(5, top5Cities.size(), "Should return exactly 5 cities");

        List<City> top1City = app.getCityReportService().getTopCitiesByCountry(countryName, 1);
        assertNotNull(top1City);
        assertEquals(1, top1City.size(), "Should return exactly 1 city");

        // Test with invalid N (zero or negative)
        List<City> invalidCities = app.getCityReportService().getTopCitiesByCountry(countryName, 0);
        assertNotNull(invalidCities, "List should not be null even for invalid input");
        assertTrue(invalidCities.isEmpty(), "Invalid limit should return an empty list");

        System.out.println("USE CASE 15 - Top 10 cities in " + countryName + ": " + top10Cities.size());
        System.out.println("USE CASE 15 - Largest city: " + firstCity.getName() + " with population: " + firstCity.getPopulation());
        System.out.println("USE CASE 15 - Top 5 cities in " + countryName + ": " + top5Cities.size());
    }


    /**
     * Integration test for USE CASE 16: Produce a Report on Top N Cities in a District
     * This test verifies that the method correctly retrieves and sorts the top N most populated cities
     * within a specified district.
     */
    @Test
    void testGetTopCitiesByDistrict_UseCase16() {
        String districtName = DEFAULT_DISTRICT;

        // Test with N = 10
        List<City> top10Cities = app.getCityReportService().getTopCitiesByDistrict(districtName, DEFAULT_N);

        // Verify that the list is not null and has at most 10 cities
        assertNotNull(top10Cities, "City list should not be null");
        assertTrue(top10Cities.size() <= 10, "Should return at most 10 cities");

        // Verify sorting — cities should be sorted in descending order by population
        for (int i = 0; i < top10Cities.size() - 1; i++) {
            assertTrue(
                    top10Cities.get(i).getPopulation() >= top10Cities.get(i + 1).getPopulation(),
                    "Cities should be sorted in descending order by population"
            );
        }

        // Verify that each city has required fields populated
        if (!top10Cities.isEmpty()) {
            City firstCity = top10Cities.get(0);
            assertNotNull(firstCity.getId());
            assertNotNull(firstCity.getName());
            assertNotNull(firstCity.getCountryCode());
            assertNotNull(firstCity.getPopulation());

            System.out.println("USE CASE 16 - Top city in district '" + districtName + "': "
                    + firstCity.getName() + " (" + firstCity.getPopulation() + ")");
        }

        // Test with smaller N = 5
        List<City> top5Cities = app.getCityReportService().getTopCitiesByDistrict(districtName, 5);
        assertNotNull(top5Cities);
        assertTrue(top5Cities.size() <= 5, "Should return at most 5 cities");

        // Test with N = 1
        List<City> top1City = app.getCityReportService().getTopCitiesByDistrict(districtName, 1);
        assertNotNull(top1City);
        assertTrue(top1City.size() <= 1, "Should return at most 1 city");

        // Test with invalid N (zero or negative)
        List<City> invalidCities = app.getCityReportService().getTopCitiesByDistrict(districtName, 0);
        assertNotNull(invalidCities, "List should not be null even for invalid input");
        assertTrue(invalidCities.isEmpty(), "Invalid limit should return an empty list");

        // Test with non-existent district
        List<City> noCities = app.getCityReportService().getTopCitiesByDistrict("NonExistentDistrict", 5);
        assertNotNull(noCities, "List should not be null even for a non-existent district");
        assertTrue(noCities.isEmpty(), "Should return empty list for a district that doesn't exist");

        System.out.println("USE CASE 16 - Total cities found in district '" + districtName + "': " + top10Cities.size());
    }

    /**
     * Integration test for USE CASE 17: Produce a Report on All Capital Cities in the World by Population.
     * This test verifies that the method correctly retrieves all capital cities
     * and orders them in descending order by population.
     */
    @Test
    void testGetAllCapitalCitiesByPopulation_UseCase17() {
        // Execute method
        List<City> capitalCities = app.getCityReportService().getAllCapitalCitiesByPopulation();

        // Verify the list is not null or empty
        assertNotNull(capitalCities, "Capital city list should not be null");
        assertFalse(capitalCities.isEmpty(), "Capital city list should not be empty");

        // Verify sorting — descending order by population
        for (int i = 0; i < capitalCities.size() - 1; i++) {
            int currentPop = capitalCities.get(i).getPopulation();
            int nextPop = capitalCities.get(i + 1).getPopulation();
            assertTrue(currentPop >= nextPop, "Capital cities should be sorted in descending order by population");
        }

        // Verify essential fields for first record
        City topCapital = capitalCities.get(0);
        assertNotNull(topCapital.getId(), "City ID should not be null");
        assertNotNull(topCapital.getName(), "City name should not be null");
        assertNotNull(topCapital.getCountryCode(), "City country code should not be null");
        assertTrue(topCapital.getPopulation() > 0, "City population should be greater than zero");

        // Optional: print summary to console
        System.out.printf(
                "USE CASE 17 - Total capital cities found: %d%nTop capital city: %s (%d)%n",
                capitalCities.size(), topCapital.getName(), topCapital.getPopulation()
        );

        //Test that all results are unique capital cities
        Set<Integer> cityIds = capitalCities.stream()
                .map(City::getId)
                .collect(Collectors.toSet());
        assertEquals(capitalCities.size(), cityIds.size(), "Each capital city should appear only once");

        // Test that the first few entries have valid, non-null fields
        capitalCities.stream().limit(5).forEach(city -> {
            assertNotNull(city.getName(), "Capital name should not be null");
            assertNotNull(city.getCountryCode(), "Capital country code should not be null");
            assertTrue(city.getPopulation() >= 0, "Population should be non-negative");
        });
    }


    /**
     * Integration test for USE CASE 20: Produce a Report on Top N Capital Cities in the World
     * This test verifies that the method correctly retrieves and sorts the top N most populated capital cities.
     */
    @Test
    void testGetTopCapitalCitiesByPopulation_UseCase20() {

        // Test parameters
        int n = 5;  // Number of capital cities to retrieve

        // Execute method
        List<City> topCapitalCities = app.getCityReportService().getTopCapitalCitiesByPopulation(n);

        // Verify that the list is not null and has exactly N cities
        assertNotNull(topCapitalCities, "Capital city list should not be null");
        assertEquals(n, topCapitalCities.size(), "Should return exactly " + n + " cities");

        // Verify sorting — cities should be sorted in descending order by population
        for (int i = 0; i < topCapitalCities.size() - 1; i++) {
            assertTrue(
                    topCapitalCities.get(i).getPopulation() >= topCapitalCities.get(i + 1).getPopulation(),
                    "Capital cities should be sorted in descending order by population"
            );
        }

        // Verify that each city has required fields populated
        City firstCity = topCapitalCities.get(0);
        assertNotNull(firstCity.getId(), "City ID should not be null");
        assertNotNull(firstCity.getName(), "City name should not be null");
        assertNotNull(firstCity.getCountryCode(), "City country code should not be null");
        assertTrue(firstCity.getPopulation() > 0, "City population should be greater than zero");

        // Test with different values of N
        List<City> top10Cities = app.getCityReportService().getTopCapitalCitiesByPopulation(10);
        assertEquals(10, top10Cities.size(), "Should return exactly 10 cities");

        List<City> topCity = app.getCityReportService().getTopCapitalCitiesByPopulation(1);
        assertEquals(1, topCity.size(), "Should return exactly 1 city");

        // Test with invalid N (zero or negative)
        List<City> invalidCities = app.getCityReportService().getTopCapitalCitiesByPopulation(0);
        assertTrue(invalidCities.isEmpty(), "Invalid limit should return an empty list");

        System.out.println("USE CASE 20 - Top " + n + " capital cities retrieved successfully");
        System.out.println("USE CASE 20 - Largest capital city: " + firstCity.getName() +
                " with population: " + firstCity.getPopulation());
    }

    /**
     * Integration test for USE CASE 21: Produce a Report on Top N Capital Cities in a Continent
     * This test verifies that the method correctly retrieves and sorts the top N most populated capital cities
     * within a specified continent.
     */
    @Test
    void testGetTopCapitalCitiesInContinentByPopulation_UseCase21() {
        // Test parameters
        String continent = "Europe";
        int n = 5;  // Number of capital cities to retrieve

        // Execute method
        List<City> topCapitalCities = app.getCityReportService().getTopCapitalCitiesByContinent(continent, n);

        // Verify that the list is not null and has exactly N cities
        assertNotNull(topCapitalCities, "Capital city list should not be null");
        assertEquals(n, topCapitalCities.size(), "Should return exactly " + n + " cities");

        // Verify sorting — cities should be sorted in descending order by population
        for (int i = 0; i < topCapitalCities.size() - 1; i++) {
            assertTrue(
                    topCapitalCities.get(i).getPopulation() >= topCapitalCities.get(i + 1).getPopulation(),
                    "Capital cities should be sorted in descending order by population"
            );
        }

        // Verify that each city has required fields populated
        City firstCity = topCapitalCities.get(0);
        assertNotNull(firstCity.getId(), "City ID should not be null");
        assertNotNull(firstCity.getName(), "City name should not be null");
        assertNotNull(firstCity.getCountryCode(), "City country code should not be null");
        assertTrue(firstCity.getPopulation() > 0, "City population should be greater than zero");

        // Test with different values of N
        List<City> top10Cities = app.getCityReportService().getTopCapitalCitiesByContinent(continent, 10);
        assertEquals(10, top10Cities.size(), "Should return exactly 10 cities");

        List<City> topCity = app.getCityReportService().getTopCapitalCitiesByContinent(continent, 1);
        assertEquals(1, topCity.size(), "Should return exactly 1 city");

        // Test with invalid N (zero or negative)
        List<City> invalidCities = app.getCityReportService().getTopCapitalCitiesByContinent(continent, 0);
        assertTrue(invalidCities.isEmpty(), "Invalid limit should return an empty list");

        // Test with invalid continent
        List<City> invalidContinentCities = app.getCityReportService().getTopCapitalCitiesByContinent("InvalidContinent", n);
        assertTrue(invalidContinentCities.isEmpty(), "Invalid continent should return an empty list");

        System.out.println("USE CASE 21 - Top " + n + " capital cities in " + continent + " retrieved successfully");
        System.out.println("USE CASE 21 - Largest capital city in " + continent + ": " + firstCity.getName() +
                " with population: " + firstCity.getPopulation());
    }

    /**
     * Integration test for USE CASE 22: Produce a Report on Top N Capital Cities in a Region
     * This test verifies that the method correctly retrieves and sorts the top N most populated capital cities
     * within a specified region.
     */
    @Test
    void testGetTopCapitalCitiesInRegionByPopulation_UseCase22() {
        // Test parameters
        String region = "South America";
        int n = 5;  // Number of capital cities to retrieve

        // Execute method
        List<City> topCapitalCities = app.getCityReportService().getTopCapitalCitiesByRegion(region, n);

        // Verify that the list is not null and has exactly N cities
        assertNotNull(topCapitalCities, "Capital city list should not be null");
        assertEquals(n, topCapitalCities.size(), "Should return exactly " + n + " cities");

        // Verify sorting — cities should be sorted in descending order by population
        for (int i = 0; i < topCapitalCities.size() - 1; i++) {
            assertTrue(
                    topCapitalCities.get(i).getPopulation() >= topCapitalCities.get(i + 1).getPopulation(),
                    "Capital cities should be sorted in descending order by population"
            );
        }

        // Verify that each city has required fields populated
        City firstCity = topCapitalCities.get(0);
        assertNotNull(firstCity.getId(), "City ID should not be null");
        assertNotNull(firstCity.getName(), "City name should not be null");
        assertNotNull(firstCity.getCountryCode(), "City country code should not be null");
        assertTrue(firstCity.getPopulation() > 0, "City population should be greater than zero");

        // Test with different values of N
        List<City> top10Cities = app.getCityReportService().getTopCapitalCitiesByRegion(region, 10);
        assertEquals(10, top10Cities.size(), "Should return exactly 10 cities");

        List<City> topCity = app.getCityReportService().getTopCapitalCitiesByRegion(region, 1);
        assertEquals(1, topCity.size(), "Should return exactly 1 city");

        // Test with invalid N (zero or negative)
        List<City> invalidCities = app.getCityReportService().getTopCapitalCitiesByRegion(region, 0);
        assertTrue(invalidCities.isEmpty(), "Invalid limit should return an empty list");

        // Test with invalid region
        List<City> invalidRegionCities = app.getCityReportService().getTopCapitalCitiesByRegion("InvalidRegion", n);
        assertTrue(invalidRegionCities.isEmpty(), "Invalid region should return an empty list");

        System.out.println("USE CASE 22 - Top " + n + " capital cities in " + region + " retrieved successfully");
        System.out.println("USE CASE 22 - Largest capital city in " + region + ": " + firstCity.getName() +
                " with population: " + firstCity.getPopulation());
    }
    /**
     * Integration test for Use Case 23: Population Report for Continents.
     * This test verifies that we can get the city/non-city population breakdown for every continent.
     */
    @Test
    void testGetContinentPopulationReport_UseCase23() {
        List<PopulationMetrics> continents = app.getPopulationMetricsReportService().getContinentPopulationReport();

        // Check list is valid
        assertNotNull(continents);
        assertFalse(continents.isEmpty(), "Continent report should not be null");

        // Retrieve Asia
        PopulationMetrics continent = continents.stream()
                .filter(m -> "Asia".equals(m.getNameOfArea()))
                .findFirst()
                .orElse(null);

        assertNotNull(continent, "Asia should exist in the continent report");


        // Verify the required identifying fields
        assertNotNull(continent.getNameOfArea(), "Area name which is the Continent should not be null");
        assertEquals(PopulationMetrics.ReportType.CONTINENT, continent.getReportType(), "Report type should be CONTINENT");

        // Verify all population metrics are non-negative
        assertTrue(continent.getTotalPopulation() >= 0, "Total population should be non-negative");
        assertTrue(continent.getCityPopulation() >= 0, "City population should be non-negative");
        assertTrue(continent.getNonCityPopulation() >= 0, "Non-City population should be non-negative");

        // Verify percentages are within the valid range between 0 and 100
        assertTrue(continent.getCityPopulationPercentage() >= 0.0 && continent.getCityPopulationPercentage() <= 100.0,
                "City population percentage must be between 0 and 100");
        assertTrue(continent.getNonCityPopulationPercentage() >= 0.0 && continent.getNonCityPopulationPercentage() <= 100.0,
                "Non-City population percentage must be between 0 and 100");




        // Total Population must be > than or = to City Population
        assertTrue(continent.getTotalPopulation() >= continent.getCityPopulation(),
                "Total population should be >= city population");

       // Verify Total = City population + NonCity population
        long totalPop = continent.getCityPopulation() + continent.getNonCityPopulation();
        assertEquals(continent.getTotalPopulation(), totalPop, "Total Population = City + NonCity");

        // Verify the percentages sum to approximately 100
        double percentage = continent.getCityPopulationPercentage() + continent.getNonCityPopulationPercentage();
        assertEquals(100.0, percentage, 0.01, "The sum of the Percentage should be 100%");
    }


    /**
     * Integration test for Use Case 24: Population Report for Regions.
     * This test verifies that we can get the city/non-city population breakdown for every region.
     */
    @Test
    void testGetRegionPopulationReport_UseCase24() {
        List<PopulationMetrics> regions = app.getPopulationMetricsReportService().getRegionPopulationReport();

        // Check list is valid
        assertNotNull(regions);
        assertFalse(regions.isEmpty(), "Region report list should not be null or empty");

        // retrieve Western Europe
        PopulationMetrics region = regions.stream()
                .filter(m -> "Western Europe".equals(m.getNameOfArea()))
                .findFirst()
                .orElse(null);

        assertNotNull(region, "Western Europe should be present in the region report");


        // Verify the required identifying fields
        assertNotNull(region.getNameOfArea(), "Area name which is the Region should not be null");
        assertEquals(PopulationMetrics.ReportType.REGION, region.getReportType(), "Report type should be REGION");

        // Verify all population metrics are non-negative
        assertTrue(region.getTotalPopulation() >= 0, "Total population should be non-negative");
        assertTrue(region.getCityPopulation() >= 0, "City population should be non-negative");
        assertTrue(region.getNonCityPopulation() >= 0, "Non-City population should be non-negative");

        // Verify percentages are within the valid range between 0 and 100
        assertTrue(region.getCityPopulationPercentage() >= 0.0 && region.getCityPopulationPercentage() <= 100.0,
                "City population percentage must be between 0 and 100");
        assertTrue(region.getNonCityPopulationPercentage() >= 0.0 && region.getNonCityPopulationPercentage() <= 100.0,
                "Non-City population percentage must be between 0 and 100");



        // Total Population must be > than or = to City Population
        assertTrue(region.getTotalPopulation() >= region.getCityPopulation(),
                "Total population should be >= city population");

        // Verify Total = City population + NonCity population
        long totalPop = region.getCityPopulation() + region.getNonCityPopulation();
        assertEquals(region.getTotalPopulation(), totalPop, "Total Population = City + NonCity");

        // Verify the percentages sum to approximately 100
        double percentage = region.getCityPopulationPercentage() + region.getNonCityPopulationPercentage();
        assertEquals(100.0, percentage, 0.01, "The sum of the Percentage should be 100%");
    }

    /**
     * Integration test for Use Case 25: Population Report for Countries.
     * This test verifies country-level population metrics.
     */
    @Test
    void testGetCountryPopulationReport_UseCase25() {
        List<PopulationMetrics> countries = app.getPopulationMetricsReportService().getCountryPopulationReport();

        assertNotNull(countries);
        assertFalse(countries.isEmpty(), "Country report list should not be null or empty");

        // retrieve France
        PopulationMetrics country = countries.stream()
                .filter(m -> "France".equals(m.getNameOfArea()))
                .findFirst()
                .orElse(null);

        assertNotNull(country, "France should be present in the country report");


        // Verify required identifying fields are populated
        assertNotNull(country.getNameOfArea(), "Area name which is the Country should not be null");
        assertEquals(PopulationMetrics.ReportType.COUNTRY, country.getReportType(), "Report type should be COUNTRY");

        // Verify all population metrics are non-negative
        assertTrue(country.getTotalPopulation() >= 0, "Total population should be non-negative");
        assertTrue(country.getCityPopulation() >= 0, "City population should be non-negative");
        assertTrue(country.getNonCityPopulation() >= 0, "Non-City population should be non-negative");

        // Verify that the percentages are within the valid range between 0 and 100
        assertTrue(country.getCityPopulationPercentage() >= 0.0 && country.getCityPopulationPercentage() <= 100.0,
                "City population percentage must be between 0 and 100");
        assertTrue(country.getNonCityPopulationPercentage() >= 0.0 && country.getNonCityPopulationPercentage() <= 100.0,
                "Non-City population percentage must be between 0 and 100");



        // Total Population must be > than or = to City Population
        assertTrue(country.getTotalPopulation() >= country.getCityPopulation(),
                "Total population should be >= city population");

        // Verify Total = City population + NonCity population
        long totalPop = country.getCityPopulation() + country.getNonCityPopulation();
        assertEquals(country.getTotalPopulation(), totalPop, "Total Population = City + NonCity");

        // Verify the percentages sum to approximately 100
        double percentage = country.getCityPopulationPercentage() + country.getNonCityPopulationPercentage();
        assertEquals(100.0, percentage, 0.01, "The sum of the Percentage should be 100%");
    }

    /**
     * Integration test for Use Case 26: Retrieve the Population of the World
     * This test verifies that the total world population is retrieved, is positive, and falls within the expected range.
     */
    @Test
    void testGetWorldPopulationReportRangeCheck_UseCase26() {
        long pop = app.getPopulationMetricsReportService().getWorldPopulationReport();

        // Ensure that reported population is more than 0.
        assertTrue(pop > 0, "World population should be more than 0");

        // Ensure the reported population is not a default error indicator or zero.
        assertNotEquals(-1, pop, "World population should not return the error code -1");
        assertNotEquals(0, pop, "World population should not return 0 if the database is populated");

        // Check the range
        final long MIN_REALISTIC_POPULATION = 3_500_000_000L;

        assertTrue(pop > MIN_REALISTIC_POPULATION,
                "World population must be greater than 3.5 billion to make sure every country is included.");
    }

    /**
     * Integration test for Use Case 27: Retrieve the Population of a Continent.
     * This test verifies retrieving metrics for a specific continent provided by argument.
     */
    @Test
    void testGetPopulationContinentReport_UseCase27() {
        // Test valid continent
        PopulationMetrics validContinent = app.getPopulationMetricsReportService().getPopulationContinentReport("Asia");

        assertNotNull(validContinent);
        assertEquals("Asia", validContinent.getNameOfArea());
        assertTrue(validContinent.getTotalPopulation() > 0);

        // Test invalid continent
        PopulationMetrics invalidContinent = app.getPopulationMetricsReportService().getPopulationContinentReport("Vinland");
        assertNull(invalidContinent, "Querying a non-existent continent should return null");
    }

}