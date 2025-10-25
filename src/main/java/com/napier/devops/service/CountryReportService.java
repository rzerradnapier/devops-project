package com.napier.devops.service;

import com.napier.devops.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for country-related reporting functionality.
 * Handles all country report use cases.
 */
public class CountryReportService {
    
    private final Connection connection;
    
    public CountryReportService(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Get a country by its code.
     *
     * @param countryCode The code of the country to retrieve.
     * @return A Country object containing details of the country, or null if not found.
     */
    public Country getCountryByCode(String countryCode) {
        Country country = null;
        String sql = "SELECT code, name, continent, region, population, capital FROM country WHERE code = ?";

        // Use PreparedStatement to prevent SQL injection and try-with-resources for automatic closing of resources
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, countryCode);  // bind variable safely
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                country = new Country();
                country.setCode(resultSet.getString("code"));
                country.setName(resultSet.getString("name"));
                country.setContinent(resultSet.getString("continent"));
                country.setRegion(resultSet.getString("region"));
                country.setPopulation(resultSet.getInt("population"));
                country.setCapital(resultSet.getInt("capital"));
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return country;
    }

    /**
     * Get all countries organized by population descending.
     *
     * @return A List of Country objects containing details of all the countries, ordered by population descending.
     */
    public List<Country> getAllCountriesByPopulationLargestToSmallest() {
        List<Country> countries = new ArrayList<>();
        String sql = "SELECT code, name, continent, region, population, capital FROM country ORDER BY population DESC";

        // Use PreparedStatement to prevent SQL injection and try-with-resources for automatic closing of resources
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                Country country = new Country();
                country.setCode(resultSet.getString("code"));
                country.setName(resultSet.getString("name"));
                country.setContinent(resultSet.getString("continent"));
                country.setRegion(resultSet.getString("region"));
                country.setPopulation(resultSet.getInt("population"));
                country.setCapital(resultSet.getInt("capital"));

                // Add the country to the list
                countries.add(country);
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }

        return countries;
    }

    /**
     * Print all countries sorted by population in descending order.
     */
    public void printAllCountriesByPopulationLargestToSmallest() {
        // Get list of all countries sorted by population
        List<Country> countryList = getAllCountriesByPopulationLargestToSmallest();

        if (countryList == null || countryList.isEmpty()) {
            System.err.println("Error: No country data found.");
        } else {
            // Print the details of all the countries
            for (Country country : countryList) {
                System.out.println(country.toString());
            }
        }
    }
}