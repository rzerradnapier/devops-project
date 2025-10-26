package com.napier.devops.service;

import com.napier.devops.City;
import com.napier.devops.pojo.CityPojo;

import java.sql.*;
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


    /**
     * USE CASE: 13 Produce a Report on Top N Cities in a Continent
     *
     * @param continent The continent to filter cities by.
     * @param limit     The maximum number of cities to return.
     * @return A List of CityPojo objects containing details of the top N populated cities in the specified continent.
     */
    public List<CityPojo> getTopCitiesByContinent(String continent, int limit) {
        List<CityPojo> cities = new ArrayList<>();
        String sql = """
                    SELECT city.ID, city.Name AS CityName, country.Name AS CountryName, country.Continent, city.District, city.Population
                    FROM City
                    INNER JOIN country ON city.CountryCode = country.Code
                    WHERE country.Continent = ?
                    ORDER BY city.Population DESC
                    LIMIT ?
                """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, continent);
            pstmt.setInt(2, limit);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                CityPojo city = new CityPojo();
                city.setId(rs.getInt("ID"));
                city.setName(rs.getString("CityName"));
                city.setCountry(rs.getString("CountryName"));
                city.setContinent(rs.getString("Continent"));
                city.setDistrict(rs.getString("District"));
                city.setPopulation(rs.getInt("Population"));
                cities.add(city);
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return cities;
    }

    /**
     * Print the top N most populated cities in a given continent.
     * USE CASE 13: Produce a Report on Top N Cities in a Continent
     */
    public void printTopCitiesByContinent(String continent, int n) {
        if (continent == null || continent.trim().isEmpty()) {
            System.err.println("Error: Continent parameter cannot be null or empty.");
            return;
        }

        List<CityPojo> cityList = getTopCitiesByContinent(continent, n);

        if (cityList == null || cityList.isEmpty()) {
            System.err.println("Error: No city data found for continent: " + continent);
        } else {
            System.out.println("Report: Top " + n + " Cities in " + continent + " by Population");
            System.out.println("=".repeat(100));
            cityList.forEach(city -> System.out.println(city.toString()));
        }
    }


    /**
     * USE CASE: 14 Produce a Report on Top N Cities in a Region
     *
     * @param region The region to filter cities by.
     * @param n      The maximum number of cities to return.
     * @return A List of CityPojo objects containing details of the top N populated cities in the specified region.
     */
    public List<CityPojo> getTopCitiesByRegion(String region, int n) {
        List<CityPojo> cities = new ArrayList<>();
        String sql = """
                    SELECT city.ID, city.Name AS CityName, country.Name AS CountryName, country.Region,
                           city.District, city.Population
                    FROM city
                    INNER JOIN country ON city.CountryCode = country.Code
                    WHERE country.Region = ?
                    ORDER BY city.Population DESC
                    LIMIT ?
                """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, region);
            pstmt.setInt(2, n);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                CityPojo city = new CityPojo();
                city.setId(rs.getInt("ID"));
                city.setName(rs.getString("CityName"));
                city.setCountry(rs.getString("CountryName"));
                city.setRegion(rs.getString("Region")); // from country
                city.setDistrict(rs.getString("District"));
                city.setPopulation(rs.getInt("Population"));
                cities.add(city);
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }

        return cities;
    }

    /**
     * Print the top N most populated cities in a given region.
     * USE CASE 14: Produce a Report on Top N Cities in a Region
     */
    public void printTopCitiesByRegion(String region, int n) {
        if (region == null || region.trim().isEmpty()) {
            System.err.println("Error: Region parameter cannot be null or empty.");
            return;
        }

        List<CityPojo> cityList = getTopCitiesByRegion(region, n);

        if (cityList == null || cityList.isEmpty()) {
            System.err.println("Error: No city data found for region: " + region);
        } else {
            System.out.println("Report: Top " + n + " Cities in " + region + " by Population");
            System.out.println("=".repeat(100));
            cityList.forEach(city -> System.out.println(city.toString()));
        }
    }


    /**
     * USE CASE: 15 Produce a Report on Top N Cities in a Country
     *
     * @param countryName The country to filter cities by.
     * @param n           The maximum number of cities to return.
     * @return A List of CityPojo objects containing details of the top N populated cities in the specified country.
     */
    public List<City> getTopCitiesByCountry(String countryName, int n) {
        List<City> cities = new ArrayList<>();
        String sql = """
                    SELECT city.ID, city.Name AS CityName, country.Name AS CountryName,
                           city.District, city.Population
                    FROM city
                    INNER JOIN country ON city.CountryCode = country.Code
                    WHERE country.Name = ?
                    ORDER BY city.Population DESC
                    LIMIT ?
                """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, countryName);
            pstmt.setInt(2, n);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                City city = new City();
                city.setId(rs.getInt("ID"));
                city.setName(rs.getString("CityName"));
                city.setCountryCode(rs.getString("CountryCode"));
                city.setDistrict(rs.getString("District"));
                city.setPopulation(rs.getInt("Population"));
                cities.add(city);
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }

        return cities;
    }

    /**
     * Print the top N most populated cities in a given country.
     * USE CASE 15: Produce a Report on Top N Cities in a Country
     */
    public void printTopCitiesByCountry(String country, int n) {
        if (country == null || country.trim().isEmpty()) {
            System.err.println("Error: Country parameter cannot be null or empty.");
            return;
        }

        List<City> cityList = getTopCitiesByCountry(country, n);

        if (cityList == null || cityList.isEmpty()) {
            System.err.println("Error: No city data found for country: " + country);
        } else {
            System.out.println("Report: Top " + n + " Cities in " + country + " by Population");
            System.out.println("=".repeat(100));
            cityList.forEach(city -> System.out.println(city.toString()));
        }
    }


    /**
     * USE CASE: 16 Produce a Report on Top N Cities in a District
     *
     * @param districtName The district to filter cities by.
     * @param n            The maximum number of cities to return.
     * @return A List of CityPojo objects containing details of the top N populated cities in the specified district.
     */
    public List<City> getTopCitiesByDistrict(String districtName, int n) {
        List<City> cities = new ArrayList<>();
        String sql = """
                    SELECT city.ID, city.Name AS CityName, country.Name AS CountryName,
                           city.District, city.Population
                    FROM city
                    INNER JOIN country ON city.CountryCode = country.Code
                    WHERE city.District = ?
                    ORDER BY city.Population DESC
                    LIMIT ?
                """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, districtName);
            pstmt.setInt(2, n);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                City city = new City();
                city.setId(rs.getInt("ID"));
                city.setName(rs.getString("CityName"));
                city.setDistrict(rs.getString("District"));
                city.setPopulation(rs.getInt("Population"));
                city.setCountryCode(rs.getString("CountryCode"));
                cities.add(city);
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }

        return cities;
    }

    /**
     * Print the top N most populated cities in a given district.
     * USE CASE 16: Produce a Report on Top N Cities in a District
     */
    public void printTopCitiesByDistrict(String district, int n) {
        if (district == null || district.trim().isEmpty()) {
            System.err.println("Error: District parameter cannot be null or empty.");
            return;
        }

        List<City> cityList = getTopCitiesByDistrict(district, n);

        if (cityList == null || cityList.isEmpty()) {
            System.err.println("Error: No city data found for district: " + district);
        } else {
            System.out.println("Report: Top " + n + " Cities in " + district + " by Population");
            System.out.println("=".repeat(100));
            cityList.forEach(city -> System.out.println(city.toString()));
        }
    }


    /**
     * USE CASE: 17 Produce a Report on All Capital Cities in the World by Population
     *
     * @return A List of City objects containing details of all capital cities ordered by population descending.
     */
    public List<CityPojo> getAllCapitalCitiesByPopulation() {
        List<CityPojo> capitals = new ArrayList<>();
        String sql = """
                    SELECT city.ID, city.Name AS CityName, city.District, country.Name AS CountryName, city.Population
                    FROM city
                    INNER JOIN country ON country.Capital = city.ID
                    ORDER BY city.Population DESC
                """;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                CityPojo city = new CityPojo();
                city.setId(rs.getInt("ID"));
                city.setName(rs.getString("CityName"));
                city.setDistrict(rs.getString("District"));
                city.setCountry(rs.getString("CountryName"));
                city.setPopulation(rs.getInt("Population"));
                capitals.add(city);
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }

        return capitals;
    }


    /**
     * Print all capital cities in the world sorted by population in descending order.
     * USE CASE 17: Produce a Report on All Capital Cities in the World by Population
     */
    public void printAllCapitalCitiesByPopulation() {
        List<CityPojo> cityList = getAllCapitalCitiesByPopulation();

        if (cityList == null || cityList.isEmpty()) {
            System.err.println("Error: No capital city data found.");
        } else {
            System.out.println("Report: All Capital Cities in the World by Population (Largest to Smallest)");
            System.out.println("Total capitals found: " + cityList.size());
            System.out.println("=".repeat(100));
            cityList.forEach(city -> System.out.println(city.toString()));
        }
    }


}