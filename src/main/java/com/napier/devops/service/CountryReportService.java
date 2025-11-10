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
     * This function retrieves all countries from a specific continent and sorts them by population in
     * descending order.
     *
     * @param continent A String containing the name of the continent from which to retrieve countries.
     * @return A List of Country objects containing details of all the countries from the specified continent,
     * sorted by population in descending order.
     */
    public List<Country> getAllCountriesInContinentByPopulationLargestToSmallest(String continent) {
        List<Country> countries = new ArrayList<>();
        String sql = "SELECT code, name, continent, region, population, capital FROM country WHERE continent = ? ORDER BY population DESC";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, continent);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                Country country = new Country();
                country.setCode(resultSet.getString("code"));
                country.setName(resultSet.getString("name"));
                country.setContinent(resultSet.getString("continent"));
                country.setRegion(resultSet.getString("region"));
                country.setPopulation(resultSet.getInt("population"));
                country.setCapital(resultSet.getInt("capital"));
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

    /**
     * This function fetches all the countries that belong to a specified continent and then prints them in descending order
     * of population size.
     *
     * @param continent The name of the continent for which to retrieve and print all countries.
     */
    public void printAllCountriesByPopulationInAContinentLargestToSmallest(String continent) {
        // Get list of all countries in the provided continent sorted by population
        List<Country> countryList = getAllCountriesInContinentByPopulationLargestToSmallest(continent);

        if (countryList == null || countryList.isEmpty()) {
            System.err.println("Error: No country data found for continent: " + continent);
        } else {
            // Print the details of all the countries
            for (Country country : countryList) {
                System.out.println(country.toString());
            }
        }
    }


    /**
     * This function retrieves all country data in a specific region, sorts them by population in descending order,
     * and returns them as a list of 'Country' objects.
     *
     * @param region A String containing the name of the region from which to retrieve countries.
     * @return A List of Country objects containing details of all the countries from the specified region,
     * sorted by population in descending order.
     */
    public List<Country> getAllCountriesInRegionByPopulationLargestToSmallest(String region) {
        List<Country> countries = new ArrayList<>();
        String sql = "SELECT code, name, continent, region, population, capital FROM country WHERE region = ? ORDER BY population DESC";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, region);

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                Country country = new Country();
                country.setCode(resultSet.getString("code"));
                country.setName(resultSet.getString("name"));
                country.setContinent(resultSet.getString("continent"));
                country.setRegion(resultSet.getString("region"));
                country.setPopulation(resultSet.getInt("population"));
                country.setCapital(resultSet.getInt("capital"));

                countries.add(country);
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }

        return countries;
    }


    /**
     * Prints all the countries in a specified region, sorted by population in descending order.
     *
     * @param region The name of the region for which to retrieve and print all countries.
     */
    public void printAllCountriesByPopulationInARegionLargestToSmallest(String region) {
        // Get list of all countries in the provided region sorted by population
        List<Country> countryList = getAllCountriesInRegionByPopulationLargestToSmallest(region);

        if (countryList == null || countryList.isEmpty()) {
            System.err.println("Error: No country data found for region: " + region);
        } else {
            // Print the details of all the countries
            for (Country country : countryList) {
                System.out.println(country.toString());
            }
        }
    }


    /**
     * Prints details of top N countries sorted by population in descending order.
     *
     * @param n The number of top countries to print. If the actual number of countries is less than N, it prints details for all countries.
     * @return A List of Country objects containing details of top N countries
     * sorted by population in descending order.
     */
    public List<Country> printTopNCountriesByPopulation(int n) {

        // Get list of all countries sorted by population
        List<Country> countryList = getAllCountriesByPopulationLargestToSmallest();
        List<Country> topNCountryList = new ArrayList<>();


        if (countryList == null || countryList.isEmpty()) {
            System.err.println("Error: No country data found.");
        } else {
            // Ensure we don't go out of bounds if the list is less than defaultN
            int count = Math.min(n, countryList.size());

            // Print the details of top N countries
            for (int i = 0; i < count; i++) {
                System.out.println(countryList.get(i).toString());
                topNCountryList.add(countryList.get(i));
            }
        }
        return topNCountryList;
    }


    /**
     * Prints details of top N countries by population in a specific continent.
     *
     * @param continent The name of the continent for which to retrieve and print top N countries.
     * @param n         The number of top countries in the continent to print. If the actual number of countries in the continent is less than N, it prints details for all countries.
     * @return A List of Country objects containing details of top N countries from the specified continent,
     * sorted by population in descending order.
     */
    public List<Country> printTopNCountriesInContinentByPopulation(String continent, int n) {
        // Get list of all countries in the provided continent sorted by population
        List<Country> countryList = getAllCountriesInContinentByPopulationLargestToSmallest(continent);
        List<Country> topNCountryList = new ArrayList<>();

        if (countryList == null || countryList.isEmpty()) {
            System.err.println("Error: No country data found for continent: " + continent);
        } else {
            // Ensure we don't go out of bounds if the list is less than n
            int count = Math.min(n, countryList.size());

            // Print the details of top N countries
            for (int i = 0; i < count; i++) {
                System.out.println(countryList.get(i).toString());
                topNCountryList.add(countryList.get(i));
            }
        }
        return topNCountryList;
    }


    /**
     * Prints details of top N countries by population in a specific region.
     *
     * @param defaultRegion The name of the region for which to retrieve and print top N countries.
     * @param defaultN      The number of top countries in the region to print. If the actual number of countries in the region is less than defaultN, it prints details for all countries.
     * @return A List of Country objects containing details of top N countries from the specified region,
     * * sorted by population in descending order.
     */
    public List<Country> printTopNCountriesInRegionByPopulation(String defaultRegion, int defaultN) {

        List<Country> countryList = getAllCountriesInRegionByPopulationLargestToSmallest(defaultRegion);
        List<Country> topNCountryList = new ArrayList<>();

        if (countryList == null || countryList.isEmpty()) {
            System.err.println("Error: No country data found for region: " + defaultRegion);
        } else {
            // Ensure we don't go out of bounds if the list is less than defaultN
            int count = Math.min(defaultN, countryList.size());

            // Print the details of top N countries
            for (int i = 0; i < count; i++) {
                System.out.println(countryList.get(i).toString());
                topNCountryList.add(countryList.get(i));
            }
        }
        return topNCountryList;
    }


}