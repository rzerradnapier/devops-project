package com.napier.devops;

import com.napier.devops.config.ApplicationFactory;
import com.napier.devops.database.DatabaseConnection;
import com.napier.devops.service.CountryService;
import com.napier.devops.repository.CountryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the application using real database connections.
 * These tests verify that the layered architecture works correctly together.
 */
public class AppIntegrationTest {
    
    static DatabaseConnection dbConnection;
    static CountryService countryService;

    @BeforeAll
    static void init() {
        // Initialize database connection
        dbConnection = new DatabaseConnection();
        boolean connected = dbConnection.connect("localhost:3307", 30000);
        
        // Skip tests if database is not available
        if (!connected) {
            System.out.println("Database not available, skipping integration tests");
            return;
        }
        
        // Initialize service layer for testing
        CountryRepository countryRepository = new CountryRepository(dbConnection);
        countryService = new CountryService(countryRepository);
    }

    /**
     * Integration test for getCountryByCode() through the service layer.
     * This test checks if the method correctly fetches country details by code.
     */
    @Test
    void testGetCountryByCode() {
        // Skip if database not connected
        if (countryService == null) {
            System.out.println("Skipping test - database not available");
            return;
        }
        
        // Call the method with "USA" as the argument to get country details by code
        Country country = countryService.getCountryByCode("USA");

        // Check if the returned country is not null
        assertNotNull(country, "Country should not be null for valid country code");

        // Check if the country's code is "USA", this verifies that the correct country details were fetched
        assertEquals("USA", country.getCode(), "Country code should match the requested code");
        
        // Additional assertions to verify data integrity
        assertNotNull(country.getName(), "Country name should not be null");
        assertNotNull(country.getContinent(), "Country continent should not be null");
        assertTrue(country.getPopulation() > 0, "Country population should be greater than 0");
    }

    /**
     * Integration test for getAllCountriesByPopulation() through the service layer.
     * This test checks if the method correctly fetches all countries and verifies they are sorted by population,
     * from largest to smallest, by checking that the first country has a higher population than the second one.
     */
    @Test
    void testGetAllCountriesByPopulation() {
        // Skip if database not connected
        if (countryService == null) {
            System.out.println("Skipping test - database not available");
            return;
        }
        
        // Call the method to get all countries by population in descending order
        List<Country> countries = countryService.getAllCountriesByPopulation();

        // Verify that we got some countries
        assertNotNull(countries, "Countries list should not be null");
        assertFalse(countries.isEmpty(), "Countries list should not be empty");
        
        // Verify that countries are sorted by population (descending)
        if (countries.size() >= 2) {
            assertTrue(countries.get(0).getPopulation() >= countries.get(1).getPopulation(),
                    "Countries should be sorted by population in descending order");
        }
        
        // Verify data integrity for first few countries
        for (int i = 0; i < Math.min(5, countries.size()); i++) {
            Country country = countries.get(i);
            assertNotNull(country.getCode(), "Country code should not be null");
            assertNotNull(country.getName(), "Country name should not be null");
            assertTrue(country.getPopulation() > 0, "Country population should be greater than 0");
        }
    }

    /**
     * Integration test for invalid country code.
     */
    @Test
    void testGetCountryByCodeInvalid() {
        // Skip if database not connected
        if (countryService == null) {
            System.out.println("Skipping test - database not available");
            return;
        }
        
        // Test with invalid country code
        Country country = countryService.getCountryByCode("XXX");
        
        // Should return null for invalid country code
        assertNull(country, "Should return null for invalid country code");
    }

    /**
     * Integration test for null/empty country code validation.
     */
    @Test
    void testGetCountryByCodeValidation() {
        // Skip if database not connected
        if (countryService == null) {
            System.out.println("Skipping test - database not available");
            return;
        }
        
        // Test with null country code
        Country country1 = countryService.getCountryByCode(null);
        assertNull(country1, "Should return null for null country code");
        
        // Test with empty country code
        Country country2 = countryService.getCountryByCode("");
        assertNull(country2, "Should return null for empty country code");
        
        // Test with whitespace country code
        Country country3 = countryService.getCountryByCode("   ");
        assertNull(country3, "Should return null for whitespace country code");
    }
}