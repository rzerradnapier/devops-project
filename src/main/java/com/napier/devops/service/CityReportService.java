package com.napier.devops.service;

import com.napier.devops.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for city-related reporting functionality.
 * Handles all city report use cases.
 */
public class CityReportService {
    
    private final Connection connection;
    
    public CityReportService(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * USE CASE 7: Get all cities organized by population descending.
     * Produce a Report on All Cities in the World by Population
     *
     * @return A List of City objects containing details of all the cities, ordered by population descending.
     */
    public List<City> getAllCitiesByPopulationLargestToSmallest() {
        List<City> cities = new ArrayList<>();
        String sql = "SELECT ID, Name, CountryCode, District, Population FROM city ORDER BY Population DESC";

        // Use PreparedStatement to prevent SQL injection and try-with-resources for automatic closing of resources
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                City city = new City();
                city.setId(resultSet.getInt("ID"));
                city.setName(resultSet.getString("Name"));
                city.setCountryCode(resultSet.getString("CountryCode"));
                city.setDistrict(resultSet.getString("District"));
                city.setPopulation(resultSet.getInt("Population"));

                // Add the city to the list
                cities.add(city);
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }

        return cities;
    }

    /**
     * USE CASE 8: Get all cities in a continent organized by population descending.
     * Produce a Report on Cities in a Continent by Population
     *
     * @param continent The continent name to filter cities by
     * @return A List of City objects containing details of all the cities in the continent, ordered by population descending.
     */
    public List<City> getAllCitiesInContinentByPopulationLargestToSmallest(String continent) {
        List<City> cities = new ArrayList<>();
        
        if (continent == null || continent.trim().isEmpty()) {
            System.err.println("Error: Continent parameter cannot be null or empty.");
            return cities;
        }
        
        String sql = "SELECT c.ID, c.Name, c.CountryCode, c.District, c.Population " +
                     "FROM city c " +
                     "JOIN country co ON c.CountryCode = co.Code " +
                     "WHERE co.Continent = ? " +
                     "ORDER BY c.Population DESC";

        // Use PreparedStatement to prevent SQL injection and try-with-resources for automatic closing of resources
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, continent);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                City city = new City();
                city.setId(resultSet.getInt("ID"));
                city.setName(resultSet.getString("Name"));
                city.setCountryCode(resultSet.getString("CountryCode"));
                city.setDistrict(resultSet.getString("District"));
                city.setPopulation(resultSet.getInt("Population"));

                // Add the city to the list
                cities.add(city);
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }

        return cities;
    }

    /**
     * Print all cities sorted by population in descending order.
     * USE CASE 7: Produce a Report on All Cities in the World by Population
     */
    public void printAllCitiesByPopulationLargestToSmallest() {
        // Get list of all cities sorted by population
        List<City> cityList = getAllCitiesByPopulationLargestToSmallest();

        if (cityList == null || cityList.isEmpty()) {
            System.err.println("Error: No city data found.");
        } else {
            System.out.println("Report: All Cities in the World by Population (Largest to Smallest)");
            System.out.println("Total cities found: " + cityList.size());
            System.out.println("=".repeat(80));
            
            // Print the details of all the cities
            for (City city : cityList) {
                System.out.println(city.toString());
            }
        }
    }

    /**
     * Print all cities in a continent sorted by population in descending order.
     * USE CASE 8: Produce a Report on Cities in a Continent by Population
     *
     * @param continent The continent name to filter cities by
     */
    public void printAllCitiesInContinentByPopulationLargestToSmallest(String continent) {
        if (continent == null || continent.trim().isEmpty()) {
            System.err.println("Error: Continent parameter cannot be null or empty.");
            return;
        }
        
        // Get list of all cities in the continent sorted by population
        List<City> cityList = getAllCitiesInContinentByPopulationLargestToSmallest(continent);

        if (cityList == null || cityList.isEmpty()) {
            System.err.println("Error: No city data found for continent: " + continent);
        } else {
            System.out.println("Report: All Cities in " + continent + " by Population (Largest to Smallest)");
            System.out.println("Total cities found: " + cityList.size());
            System.out.println("=".repeat(80));
            
            // Print the details of all the cities
            for (City city : cityList) {
                System.out.println(city.toString());
            }
        }
    }
}