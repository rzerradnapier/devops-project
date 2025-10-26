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
     * USE CASE 9: Get all cities in a region organized by population descending.
     * Produce a Report on Cities in a Region by Population
     *
     * @param region The region name to filter cities by
     * @return A List of City objects containing details of all the cities in the region, ordered by population descending.
     */
    public List<City> getAllCitiesInRegionByPopulationLargestToSmallest(String region) {
        List<City> cities = new ArrayList<>();

        if (region == null || region.trim().isEmpty()) {
            System.err.println("Error: Region parameter cannot be null or empty.");
            return cities;
        }

        String sql = "SELECT c.ID, c.Name, c.CountryCode, c.District, c.Population " +
                "FROM city c " +
                "JOIN country co ON c.CountryCode = co.Code " +
                "WHERE co.Region = ? " +
                "ORDER BY c.Population DESC";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, region);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                City city = new City();
                city.setId(resultSet.getInt("ID"));
                city.setName(resultSet.getString("Name"));
                city.setCountryCode(resultSet.getString("CountryCode"));
                city.setDistrict(resultSet.getString("District"));
                city.setPopulation(resultSet.getInt("Population"));

                cities.add(city);
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }

        return cities;
    }

    /**
     * USE CASE 10: Get all cities in a country organized by population descending.
     * Produce a Report on Cities in a Country by Population
     *
     * @param countryCode The country code to filter cities by
     * @return A List of City objects containing details of all the cities in the country, ordered by population descending.
     */
    public List<City> getAllCitiesInCountryByPopulationLargestToSmallest(String countryCode) {
        List<City> cities = new ArrayList<>();

        if (countryCode == null || countryCode.trim().isEmpty()) {
            System.err.println("Error: Country code parameter cannot be null or empty.");
            return cities;
        }

        String sql = "SELECT ID, Name, CountryCode, District, Population " +
                "FROM city " +
                "WHERE CountryCode = ? " +
                "ORDER BY Population DESC";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, countryCode);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                City city = new City();
                city.setId(resultSet.getInt("ID"));
                city.setName(resultSet.getString("Name"));
                city.setCountryCode(resultSet.getString("CountryCode"));
                city.setDistrict(resultSet.getString("District"));
                city.setPopulation(resultSet.getInt("Population"));

                cities.add(city);
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }

        return cities;
    }

    /**
     * USE CASE 11: Get all cities in a district organized by population descending.
     * Produce a Report on Cities in a District by Population
     *
     * @param district The district name to filter cities by
     * @return A List of City objects containing details of all the cities in the district, ordered by population descending.
     */
    public List<City> getAllCitiesInDistrictByPopulationLargestToSmallest(String district) {
        List<City> cities = new ArrayList<>();

        if (district == null || district.trim().isEmpty()) {
            System.err.println("Error: District parameter cannot be null or empty.");
            return cities;
        }

        String sql = "SELECT ID, Name, CountryCode, District, Population " +
                "FROM city " +
                "WHERE District = ? " +
                "ORDER BY Population DESC";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, district);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                City city = new City();
                city.setId(resultSet.getInt("ID"));
                city.setName(resultSet.getString("Name"));
                city.setCountryCode(resultSet.getString("CountryCode"));
                city.setDistrict(resultSet.getString("District"));
                city.setPopulation(resultSet.getInt("Population"));

                cities.add(city);
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }

        return cities;
    }

    /**
     * USE CASE 12: Get top N cities in the world organized by population descending.
     * Produce a Report on Top N Cities in the World
     *
     * @param n The number of top cities to retrieve
     * @return A List of City objects containing details of the top N cities, ordered by population descending.
     */
    public List<City> getTopNCitiesByPopulationLargestToSmallest(int n) {
        List<City> cities = new ArrayList<>();

        if (n <= 0) {
            System.err.println("Error: N parameter must be greater than 0.");
            return cities;
        }

        String sql = "SELECT ID, Name, CountryCode, District, Population " +
                "FROM city " +
                "ORDER BY Population DESC " +
                "LIMIT ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, n);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                City city = new City();
                city.setId(resultSet.getInt("ID"));
                city.setName(resultSet.getString("Name"));
                city.setCountryCode(resultSet.getString("CountryCode"));
                city.setDistrict(resultSet.getString("District"));
                city.setPopulation(resultSet.getInt("Population"));

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

    /**
     * Print all cities in a region sorted by population in descending order.
     * USE CASE 9: Produce a Report on Cities in a Region by Population
     *
     * @param region The region name to filter cities by
     */
    public void printAllCitiesInRegionByPopulationLargestToSmallest(String region) {
        if (region == null || region.trim().isEmpty()) {
            System.err.println("Error: Region parameter cannot be null or empty.");
            return;
        }

        List<City> cityList = getAllCitiesInRegionByPopulationLargestToSmallest(region);

        if (cityList == null || cityList.isEmpty()) {
            System.err.println("Error: No city data found for region: " + region);
        } else {
            System.out.println("Report: All Cities in " + region + " by Population (Largest to Smallest)");
            System.out.println("Total cities found: " + cityList.size());
            System.out.println("=".repeat(80));

            for (City city : cityList) {
                System.out.println(city.toString());
            }
        }
    }

    /**
     * Print all cities in a country sorted by population in descending order.
     * USE CASE 10: Produce a Report on Cities in a Country by Population
     *
     * @param countryCode The country code to filter cities by
     */
    public void printAllCitiesInCountryByPopulationLargestToSmallest(String countryCode) {
        if (countryCode == null || countryCode.trim().isEmpty()) {
            System.err.println("Error: Country code parameter cannot be null or empty.");
            return;
        }

        List<City> cityList = getAllCitiesInCountryByPopulationLargestToSmallest(countryCode);

        if (cityList == null || cityList.isEmpty()) {
            System.err.println("Error: No city data found for country: " + countryCode);
        } else {
            System.out.println("Report: All Cities in " + countryCode + " by Population (Largest to Smallest)");
            System.out.println("Total cities found: " + cityList.size());
            System.out.println("=".repeat(80));

            for (City city : cityList) {
                System.out.println(city.toString());
            }
        }
    }

    /**
     * Print all cities in a district sorted by population in descending order.
     * USE CASE 11: Produce a Report on Cities in a District by Population
     *
     * @param district The district name to filter cities by
     */
    public void printAllCitiesInDistrictByPopulationLargestToSmallest(String district) {
        if (district == null || district.trim().isEmpty()) {
            System.err.println("Error: District parameter cannot be null or empty.");
            return;
        }

        List<City> cityList = getAllCitiesInDistrictByPopulationLargestToSmallest(district);

        if (cityList == null || cityList.isEmpty()) {
            System.err.println("Error: No city data found for district: " + district);
        } else {
            System.out.println("Report: All Cities in " + district + " by Population (Largest to Smallest)");
            System.out.println("Total cities found: " + cityList.size());
            System.out.println("=".repeat(80));

            for (City city : cityList) {
                System.out.println(city.toString());
            }
        }
    }

    /**
     * Print top N cities in the world sorted by population in descending order.
     * USE CASE 12: Produce a Report on Top N Cities in the World
     *
     * @param n The number of top cities to print
     */
    public void printTopNCitiesByPopulationLargestToSmallest(int n) {
        if (n <= 0) {
            System.err.println("Error: N parameter must be greater than 0.");
            return;
        }

        List<City> cityList = getTopNCitiesByPopulationLargestToSmallest(n);

        if (cityList == null || cityList.isEmpty()) {
            System.err.println("Error: No city data found.");
        } else {
            System.out.println("Report: Top " + n + " Cities in the World by Population (Largest to Smallest)");
            System.out.println("Total cities found: " + cityList.size());
            System.out.println("=".repeat(80));

            for (City city : cityList) {
                System.out.println(city.toString());
            }
        }
    }
}