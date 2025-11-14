package com.napier.devops.service;

import com.napier.devops.City;
import com.napier.pojo.PopulationReportPojo;

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

    /**
     * USE CASE: 13 Produce a Report on Top N Cities in a Continent
     *
     * @param continent The continent to filter cities by.
     * @param limit     The maximum number of cities to return.
     * @return A List of City objects containing details of the top N populated cities in the specified continent.
     */
    public List<City> getTopCitiesByContinent(String continent, int limit) {
        List<City> cities = new ArrayList<>();
        String sql = """
                    SELECT city.ID, city.Name AS CityName, city.District, city.Population, city.CountryCode
                    FROM city
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
     * Print the top N most populated cities in a given continent.
     * USE CASE 13: Produce a Report on Top N Cities in a Continent
     */
    public void printTopCitiesByContinent(String continent, int n) {
        if (continent == null || continent.trim().isEmpty()) {
            System.err.println("Error: Continent parameter cannot be null or empty.");
            return;
        }

        List<City> cityList = getTopCitiesByContinent(continent, n);

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
     * @return A List of City objects containing details of the top N populated cities in the specified region.
     */
    public List<City> getTopCitiesByRegion(String region, int n) {
        List<City> cities = new ArrayList<>();
        String sql = """
                    SELECT city.ID, city.Name AS CityName,  city.District, city.Population, city.CountryCode
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
     * Print the top N most populated cities in a given region.
     * USE CASE 14: Produce a Report on Top N Cities in a Region
     */
    public void printTopCitiesByRegion(String region, int n) {
        if (region == null || region.trim().isEmpty()) {
            System.err.println("Error: Region parameter cannot be null or empty.");
            return;
        }

        List<City> cityList = getTopCitiesByRegion(region, n);

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
     * @return A List of City objects containing details of the top N populated cities in the specified country.
     */
    public List<City> getTopCitiesByCountry(String countryName, int n) {
        List<City> cities = new ArrayList<>();
        String sql = """
                    SELECT city.ID, city.Name AS CityName, city.District, city.Population, city.CountryCode
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
     * @return A List of City objects containing details of the top N populated cities in the specified district.
     */
    public List<City> getTopCitiesByDistrict(String districtName, int n) {
        List<City> cities = new ArrayList<>();
        String sql = """
                    SELECT city.ID, city.Name AS CityName,
                           city.District, city.Population, city.CountryCode
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
    public List<City> getAllCapitalCitiesByPopulation() {
        List<City> capitals = new ArrayList<>();
        String sql = """
                    SELECT city.ID, city.Name AS CityName, city.District, city.CountryCode, city.Population
                    FROM city
                    INNER JOIN country ON country.Capital = city.ID
                    ORDER BY city.Population DESC
                """;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                City city = new City();
                city.setId(rs.getInt("ID"));
                city.setName(rs.getString("CityName"));
                city.setDistrict(rs.getString("District"));
                city.setCountryCode(rs.getString("CountryCode"));
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
        List<City> cityList = getAllCapitalCitiesByPopulation();

        if (cityList == null || cityList.isEmpty()) {
            System.err.println("Error: No capital city data found.");
        } else {
            System.out.println("Report: All Capital Cities in the World by Population (Largest to Smallest)");
            System.out.println("Total capitals found: " + cityList.size());
            System.out.println("=".repeat(100));
            cityList.forEach(city -> System.out.println(city.toString()));
        }
    }

    /**
     * USE CASE 20: Get top N capital cities in the world organized by population descending.
     * Produce a Report on Top N Capital Cities in the World
     *
     * @param n The number of top capital cities to retrieve
     * @return A List of City objects containing details of the top N capital cities, ordered by population descending.
     */
    public List<City> getTopCapitalCitiesByPopulation(int n) {
        List<City> capitals = new ArrayList<>();

        if (n <= 0) {
            System.err.println("Error: N parameter must be greater than 0.");
            return capitals;
        }

        String sql = """
                SELECT city.ID, city.Name AS CityName, city.District, city.CountryCode, city.Population
                FROM city
                INNER JOIN country ON country.Capital = city.ID
                ORDER BY city.Population DESC
                LIMIT ?
                """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, n);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                City city = new City();
                city.setId(rs.getInt("ID"));
                city.setName(rs.getString("CityName"));
                city.setDistrict(rs.getString("District"));
                city.setCountryCode(rs.getString("CountryCode"));
                city.setPopulation(rs.getInt("Population"));
                capitals.add(city);
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }

        return capitals;
    }

    /**
     * Print top N capital cities in the world sorted by population in descending order.
     * USE CASE 20: Produce a Report on Top N Capital Cities in the World
     *
     * @param n The number of top capital cities to print
     */
    public void printTopCapitalCitiesByPopulation(int n) {
        if (n <= 0) {
            System.err.println("Error: N parameter must be greater than 0.");
            return;
        }

        List<City> cityList = getTopCapitalCitiesByPopulation(n);

        if (cityList == null || cityList.isEmpty()) {
            System.err.println("Error: No capital city data found.");
        } else {
            System.out.println("Report: Top " + n + " Capital Cities in the World by Population");
            System.out.println("Total capitals found: " + cityList.size());
            System.out.println("=".repeat(100));
            cityList.forEach(city -> System.out.println(city.toString()));

        }
    }

    /**
     * USE CASE 21: Get top N capital cities in a continent organized by population descending.
     * Produce a Report on Top N Capital Cities in a Continent
     *
     * @param continent The continent to filter capital cities by
     * @param n         The number of top capital cities to retrieve
     * @return A List of City objects containing details of the top N capital cities in the continent, ordered by population descending.
     */
    public List<City> getTopCapitalCitiesByContinent(String continent, int n) {
        List<City> capitals = new ArrayList<>();

        if (continent == null || continent.trim().isEmpty() || n <= 0) {
            System.err.println("Error: Invalid parameters provided.");
            return capitals;
        }

        String sql = """
                SELECT city.ID, city.Name AS CityName, city.District, city.CountryCode, city.Population
                FROM city
                INNER JOIN country ON country.Capital = city.ID
                WHERE country.Continent = ?
                ORDER BY city.Population DESC
                LIMIT ?
                """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, continent);
            pstmt.setInt(2, n);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                City city = new City();
                city.setId(rs.getInt("ID"));
                city.setName(rs.getString("CityName"));
                city.setDistrict(rs.getString("District"));
                city.setCountryCode(rs.getString("CountryCode"));
                city.setPopulation(rs.getInt("Population"));
                capitals.add(city);
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }

        return capitals;
    }

    /**
     * Print top N capital cities in a continent sorted by population in descending order.
     * USE CASE 21: Produce a Report on Top N Capital Cities in a Continent
     *
     * @param continent The continent to filter capital cities by
     * @param n         The number of top capital cities to print
     */
    public void printTopCapitalCitiesByContinent(String continent, int n) {
        if (continent == null || continent.trim().isEmpty()) {
            System.err.println("Error: Continent parameter cannot be null or empty.");
            return;
        }

        if (n <= 0) {
            System.err.println("Error: N parameter must be greater than 0.");
            return;
        }

        List<City> cityList = getTopCapitalCitiesByContinent(continent, n);

        if (cityList == null || cityList.isEmpty()) {
            System.err.println("Error: No capital city data found for continent: " + continent);
        } else {
            System.out.println("Report: Top " + n + " Capital Cities in " + continent + " by Population");
            System.out.println("Total capitals found: " + cityList.size());
            System.out.println("=".repeat(100));
            cityList.forEach(city -> System.out.println(city.toString()));
        }
    }

    /**
     * USE CASE 22: Get top N capital cities in a region organized by population descending.
     * Produce a Report on Top N Capital Cities in a Region
     *
     * @param region The region to filter capital cities by
     * @param n      The number of top capital cities to retrieve
     * @return A List of City objects containing details of the top N capital cities in the region, ordered by population descending.
     */
    public List<City> getTopCapitalCitiesByRegion(String region, int n) {
        List<City> capitals = new ArrayList<>();

        if (region == null || region.trim().isEmpty() || n <= 0) {
            System.err.println("Error: Invalid parameters provided.");
            return capitals;
        }

        String sql = """
                SELECT city.ID, city.Name AS CityName, city.District, city.CountryCode, city.Population
                FROM city
                INNER JOIN country ON country.Capital = city.ID
                WHERE country.Region = ?
                ORDER BY city.Population DESC
                LIMIT ?
                """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, region);
            pstmt.setInt(2, n);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                City city = new City();
                city.setId(rs.getInt("ID"));
                city.setName(rs.getString("CityName"));
                city.setDistrict(rs.getString("District"));
                city.setCountryCode(rs.getString("CountryCode"));
                city.setPopulation(rs.getInt("Population"));
                capitals.add(city);
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }

        return capitals;
    }

    /**
     * Print top N capital cities in a region sorted by population in descending order.
     * USE CASE 22: Produce a Report on Top N Capital Cities in a Region
     *
     * @param region The region to filter capital cities by
     * @param n      The number of top capital cities to print
     */
    public void printTopCapitalCitiesByRegion(String region, int n) {
        if (region == null || region.trim().isEmpty()) {
            System.err.println("Error: Region parameter cannot be null or empty.");
            return;
        }

        if (n <= 0) {
            System.err.println("Error: N parameter must be greater than 0.");
            return;
        }

        List<City> cityList = getTopCapitalCitiesByRegion(region, n);

        if (cityList == null || cityList.isEmpty()) {
            System.err.println("Error: No capital city data found for region: " + region);
        } else {
            System.out.println("Report: Top " + n + " Capital Cities in " + region + " by Population");
            System.out.println("Total capitals found: " + cityList.size());
            System.out.println("=".repeat(100));
            cityList.forEach(city -> System.out.println(city.toString()));
        }
    }


    /**
     * USE CASE 30: Retrieve the Population of a District.
     *
     * @param districtName Name of the district
     * @return A PopulationReportPojo object containing population breakdown, or null if not found.
     */
    public PopulationReportPojo getDistrictPopulationReport(String districtName) {
        long totalPopulation = 0;
        long cityPopulation = 0;

        if (districtName == null || districtName.trim().isEmpty()) {
            System.err.println("Error: District name cannot be null or empty.");
            return null;
        }

        PopulationReportPojo report = new PopulationReportPojo();
        report.setName(districtName);

        String totalQuery = "SELECT SUM(Population) AS population FROM city WHERE district = ?";
        String cityQuery = """
                SELECT SUM(Population) AS city_population
                FROM city
                WHERE district = ?
                """;

        try (
                PreparedStatement stmtTotal = connection.prepareStatement(totalQuery);
                PreparedStatement stmtCity = connection.prepareStatement(cityQuery)
        ) {
            // Total population in district
            stmtTotal.setString(1, districtName);
            try (ResultSet rsTotal = stmtTotal.executeQuery()) {
                if (rsTotal.next()) {
                    totalPopulation = rsTotal.getLong("population");
                }
            }

            // City population in district (same as total)
            stmtCity.setString(1, districtName);
            try (ResultSet rsCity = stmtCity.executeQuery()) {
                if (rsCity.next()) {
                    cityPopulation = rsCity.getLong("city_population");
                }
            }

            long nonCityPopulation = totalPopulation - cityPopulation;

            double cityPercentage = totalPopulation > 0
                    ? ((cityPopulation * 100.0) / totalPopulation)
                    : 0.0;

            double nonCityPercentage = 100.0 - cityPercentage;

            report.setTotalPopulation(totalPopulation);
            report.setPopulationInCities(cityPopulation);
            report.setPopulationNotInCities(nonCityPopulation);
            report.setPercentageInCities(cityPercentage);
            report.setPercentageNotInCities(nonCityPercentage);

            return report;

        } catch (SQLException e) {
            System.err.println("SQL Error retrieving population report for district: " + e.getMessage());
            return null;
        }
    }

    /**
     * USE CASE 30: Retrieve the Population of a District.
     * Prints and returns the population report for a given district.
     *
     * @param districtName Name of the district
     * @return The PopulationReportPojo containing the population details for the district, or null if not found.
     */
    public PopulationReportPojo printDistrictPopulationReport(String districtName) {
        PopulationReportPojo report = getDistrictPopulationReport(districtName);

        if (report == null) {
            System.err.println("Error: No population data found for district: " + districtName);
            return null;
        }

        System.out.println("========================================");
        System.out.println("        DISTRICT POPULATION REPORT      ");
        System.out.println("========================================");
        System.out.println("District: " + report.getName());
        System.out.println("Total Population: " + report.getTotalPopulation());
        System.out.println("Population in Cities: " + report.getPopulationInCities() +
                " (" + String.format("%.2f", report.getPercentageInCities()) + "%)");
        System.out.println("Population Not in Cities: " + report.getPopulationNotInCities() +
                " (" + String.format("%.2f", report.getPercentageNotInCities()) + "%)");
        System.out.println("========================================");

        return report;
    }

    /**
     * USE CASE 31: Produce a Population Report for a City.
     *
     * @param cityName Name of the city
     * @return A PopulationReportPojo containing population breakdown, or null if not found.
     */
    public PopulationReportPojo getCityPopulationReport(String cityName) {
        long totalPopulation = 0;
        long cityPopulation = 0;

        if (cityName == null || cityName.trim().isEmpty()) {
            System.err.println("Error: City name cannot be null or empty.");
            return null;
        }

        PopulationReportPojo report = new PopulationReportPojo();
        report.setName(cityName);

        String totalQuery = "SELECT Population FROM city WHERE Name = ?";
        String cityQuery = """
                SELECT Population AS city_population
                FROM city
                WHERE Name = ?
                """;

        try (
                PreparedStatement stmtTotal = connection.prepareStatement(totalQuery);
                PreparedStatement stmtCity = connection.prepareStatement(cityQuery)
        ) {
            // Total population (same as city population for a single city)
            stmtTotal.setString(1, cityName);
            try (ResultSet rsTotal = stmtTotal.executeQuery()) {
                if (rsTotal.next()) {
                    totalPopulation = rsTotal.getLong("Population");
                }
            }

            // City population (same as total)
            stmtCity.setString(1, cityName);
            try (ResultSet rsCity = stmtCity.executeQuery()) {
                if (rsCity.next()) {
                    cityPopulation = rsCity.getLong("city_population");
                }
            }

            long nonCityPopulation = totalPopulation - cityPopulation;

            double cityPercentage = totalPopulation > 0
                    ? ((cityPopulation * 100.0) / totalPopulation)
                    : 0.0;

            double nonCityPercentage = 100.0 - cityPercentage;

            report.setTotalPopulation(totalPopulation);
            report.setPopulationInCities(cityPopulation);
            report.setPopulationNotInCities(nonCityPopulation);
            report.setPercentageInCities(cityPercentage);
            report.setPercentageNotInCities(nonCityPercentage);

            return report;

        } catch (SQLException e) {
            System.err.println("SQL Error retrieving population report for city: " + e.getMessage());
            return null;
        }
    }


    /**
     * USE CASE 31: Produce a Population Report for a City.
     * Prints and returns the population report for a given city.
     *
     * @param cityName Name of the city
     * @return The PopulationReportPojo containing population details for the city, or null if not found.
     */
    public PopulationReportPojo printCityPopulationReport(String cityName) {
        PopulationReportPojo report = getCityPopulationReport(cityName);

        if (report == null) {
            System.err.println("Error: No population data found for city: " + cityName);
            return null;
        }

        System.out.println("=========================================");
        System.out.println("            CITY POPULATION REPORT       ");
        System.out.println("=========================================");
        System.out.println("City: " + report.getName());
        System.out.println("Total Population: " + report.getTotalPopulation());
        System.out.println("Population in City: " + report.getPopulationInCities() +
                " (" + String.format("%.2f", report.getPercentageInCities()) + "%)");
        System.out.println("Population Not in City: " + report.getPopulationNotInCities() +
                " (" + String.format("%.2f", report.getPercentageNotInCities()) + "%)");
        System.out.println("========================================");

        return report;
    }


}