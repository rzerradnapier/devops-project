package com.napier.devops.service;


import com.napier.devops.PopulationMetrics;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Service class for continent-related reporting functionality.
 * Handles all continent report use cases.
 */
public class PopulationMetricsReportService {

    private final Connection connection;

    public PopulationMetricsReportService(Connection connection) {
        this.connection = connection;
    }


    /**
     * Executes the query for Use Case 23 and displays the results.
     *
     * @return A list of ContinentPopulation objects containing the relevant data.
     */
    public List<PopulationMetrics> getContinentPopulationReport() {
        List<PopulationMetrics> continents = new ArrayList<>();
        String sql = "SELECT A.Continent, SUM(A.Population) AS TotalPopulation, " +
                "COALESCE(SUM(B.CityPopulation), 0) AS CityPopulation, " +
                "(SUM(A.Population) - COALESCE(SUM(B.CityPopulation), 0)) AS NonCityPopulation, " +
                "ROUND((COALESCE(SUM(B.CityPopulation), 0) / SUM(A.Population)) * 100, 2) AS CityPopulationPercentage, " +
                "ROUND(((SUM(A.Population) - COALESCE(SUM(B.CityPopulation), 0)) / SUM(A.Population)) * 100, 2) AS NonCityPopulationPercentage " +
                "FROM country AS A LEFT JOIN (SELECT CountryCode, SUM(Population) AS CityPopulation FROM city GROUP BY CountryCode) AS B " +
                "ON A.Code = B.CountryCode GROUP BY A.Continent ORDER BY TotalPopulation DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {

            while (resultSet.next()) {
                PopulationMetrics continent = new PopulationMetrics();

                // Set the data fields
                continent.setNameOfArea(resultSet.getString("Continent"));
                continent.setCityPopulationPercentage(resultSet.getDouble("CityPopulationPercentage"));
                continent.setCityPopulation(resultSet.getLong("CityPopulation"));
                continent.setTotalPopulation(resultSet.getLong("TotalPopulation"));
                continent.setNonCityPopulation(resultSet.getLong("NonCityPopulation"));
                continent.setNonCityPopulationPercentage(resultSet.getDouble("NonCityPopulationPercentage"));


                continent.setReportType(PopulationMetrics.ReportType.CONTINENT);

                continents.add(continent);
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return continents;
    }

    /**
     * Prints the population report for all continents (Use Case 23).
     */
    public void printContinentPopulationReport() {
        // Get the data from the method
        List<PopulationMetrics> continentList = getContinentPopulationReport();

        // Check if any data was returned
        if (continentList == null || continentList.isEmpty()) {
            System.out.println("No continent population data found.");
            return;
        }

        // Print a clear header
        String separator = "===================================================================================================================================================";
        System.out.println(separator);
        System.out.println("|                                               USE CASE 23: Continent Population Report                                                      |");
        System.out.println(separator);

        // Loop through the list and print each item
        for (PopulationMetrics continent : continentList) {
            System.out.println(continent);
        }

        // Print a footer
        System.out.println(separator);
    }

    /**
     * Executes the query for Use Case 24 and displays the results.
     *
     * @return A list of PopulationMetrics objects containing the relevant data.
     */
    public List<PopulationMetrics> getRegionPopulationReport() {

        List<PopulationMetrics> regions = new ArrayList<>();

        String sql = "SELECT A.Region, SUM(A.Population) AS TotalPopulation, " +
                "COALESCE(SUM(B.CityPopulation), 0) AS CityPopulation, " +
                "(SUM(A.Population) - COALESCE(SUM(B.CityPopulation), 0)) AS NonCityPopulation, " +
                "ROUND((COALESCE(SUM(B.CityPopulation), 0) / SUM(A.Population)) * 100, 2) AS CityPopulationPercentage, " +
                "ROUND(((SUM(A.Population) - COALESCE(SUM(B.CityPopulation), 0)) / SUM(A.Population)) * 100, 2) AS NonCityPopulationPercentage " +
                "FROM country AS A LEFT JOIN (SELECT CountryCode, SUM(Population) AS CityPopulation FROM city GROUP BY CountryCode) AS B " +
                "ON A.Code = B.CountryCode " +
                "GROUP BY A.Region " +
                "ORDER BY TotalPopulation DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {

            while (resultSet.next()) {
                // Create a PopulationMetrics object
                PopulationMetrics region = new PopulationMetrics();

                // Use the PopulationMetrics setters
                region.setNameOfArea(resultSet.getString("Region"));
                region.setTotalPopulation(resultSet.getLong("TotalPopulation"));
                region.setCityPopulation(resultSet.getLong("CityPopulation"));
                region.setNonCityPopulation(resultSet.getLong("NonCityPopulation"));
                region.setCityPopulationPercentage(resultSet.getDouble("CityPopulationPercentage"));
                region.setNonCityPopulationPercentage(resultSet.getDouble("NonCityPopulationPercentage"));

                // Set the ReportType to trigger the correct toString() format
                region.setReportType(PopulationMetrics.ReportType.REGION);

                regions.add(region);
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return regions;
    }

    /**
     * Prints the population report for all regions (Use Case 24).
     */
    public void printRegionPopulationReport() {
        List<PopulationMetrics> regionList = getRegionPopulationReport();

        if (regionList == null || regionList.isEmpty()) {
            System.out.println("No region population data found.");
            return;
        }


        System.out.println("=================================================================================================================================================");
        System.out.println("|                                  USE CASE: 24 Produce a Population Report for Regions                                                       |");
        System.out.println("=================================================================================================================================================");
        System.out.printf("| %-25s | %16s | %18s | %18s | %18s | %18s |%n",
                "Region", "Total Population", "City Population", "% City", "Non-City Population", "% Non-City");
        System.out.println("|---------------------------|------------------|--------------------|--------------------|--------------------|--------------------|");


        for (PopulationMetrics rp : regionList) {

            System.out.println(rp.toString());
        }

        System.out.println("=================================================================================================================================================");
    }
    /**
     * Executes the query for Use Case 25.
     *
     * @return A list of PopulationMetrics objects containing the relevant data.
     */
    public List<PopulationMetrics> getCountryPopulationReport() {
        List<PopulationMetrics> countries = new ArrayList<>();
        // SQL query
        String sql = "SELECT A.Name AS CountryName, SUM(A.Population) AS TotalPopulation, " +
                "COALESCE(SUM(B.CityPopulation), 0) AS CityPopulation, " +
                "(SUM(A.Population) - COALESCE(SUM(B.CityPopulation), 0)) AS NonCityPopulation, " +
                "ROUND((COALESCE(SUM(B.CityPopulation), 0) / SUM(A.Population)) * 100, 2) AS CityPopulationPercentage, " +
                "ROUND(((SUM(A.Population) - COALESCE(SUM(B.CityPopulation), 0)) / SUM(A.Population)) * 100, 2) AS NonCityPopulationPercentage " +
                "FROM country AS A " +
                "LEFT JOIN (SELECT CountryCode, SUM(Population) AS CityPopulation FROM city GROUP BY CountryCode) AS B " +
                "ON A.Code = B.CountryCode " +
                "GROUP BY A.Code, A.Name, A.Population " +
                "ORDER BY A.Name";

        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {

            while (resultSet.next()) {
                PopulationMetrics countryPopulation = new PopulationMetrics();


                countryPopulation.setNameOfArea(resultSet.getString("CountryName"));
                countryPopulation.setCityPopulation(resultSet.getLong("CityPopulation"));
                countryPopulation.setTotalPopulation(resultSet.getLong("TotalPopulation"));
                countryPopulation.setNonCityPopulation(resultSet.getLong("NonCityPopulation"));
                countryPopulation.setCityPopulationPercentage(resultSet.getDouble("CityPopulationPercentage"));
                countryPopulation.setNonCityPopulationPercentage(resultSet.getDouble("NonCityPopulationPercentage"));

                // Set the ReportType so toString() knows which format to use.
                countryPopulation.setReportType(PopulationMetrics.ReportType.COUNTRY);

                countries.add(countryPopulation);
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return countries;
    }



    /**
     * Prints the Country Population Report for Use Case 25.
     */
    public void printCountryPopulationReport() {
        List<PopulationMetrics> countryPopulationList = getCountryPopulationReport();

        if (countryPopulationList == null || countryPopulationList.isEmpty()) {
            System.out.println("No country population data found.");
            return;
        }

        System.out.println("=================================================================================================================================================");
        System.out.println("|                                               USE CASE 25: Produce a Population Report for Countries                                            |");
        System.out.println("=================================================================================================================================================");
        // Headers for the COUNTRY format
        System.out.printf("| %-47s | %-18s | %-18s | %-12s | %-18s | %-11s |%n",
                "Country Name", "Total Population", "City Population", "% City", "Non-City Population", "% Non-City");

        System.out.println("|-------------------------------------------------|--------------------|--------------------|--------------|--------------------|-------------|");


        for (PopulationMetrics populationMetrics : countryPopulationList) {

            // Simply call toString().

            System.out.println(populationMetrics.toString());
        }
        System.out.println("=================================================================================================================================================");
    }

    /**
     * Executes the query for Use Case 26.
     *
     * @return The total world population.
     */
    public long getWorldPopulationReport() {

        // SQL query
        String sql = "SELECT SUM(Population) AS WorldPopulation FROM country";

        long worldPopulation = -1; // Variable that holds the single result

        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {


            while (resultSet.next()) {


                // This will get the value using the alias WorldPopulation
                worldPopulation = resultSet.getLong("WorldPopulation");

            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());

        }


        return worldPopulation;
    }

    /**
     * Prints the total population of the world.
     * Use Case 26.
     */
    public void printWorldPopulationReport() {
        long population = getWorldPopulationReport(); // Calls the method

        // Define the separator line
        String separator = "=================================================================================================================================================";

        if (population == -1) {
            System.out.println("Error: Could not retrieve world population.");
            return;
        }

        // Header
        System.out.println(separator);

        System.out.println("|                                                         USE CASE 26: World Population Report                                                          |");
        System.out.println(separator);

        // Data Line
        System.out.printf("| Total World Population: %-123s |\n", population);

        // Footer
        System.out.println(separator);
    }


    /**
     * Executes a query to get the full population metrics for a specific continent USE CASE 27.
     */
    public PopulationMetrics getPopulationContinentReport(String continentName) {

        // SQL query
        String sql = "SELECT A.Continent AS Name, A.TotalPopulation AS TotalPopulation, " +
                "IFNULL(B.CityPopulation, 0) AS CityPopulation, " +
                "(A.TotalPopulation - IFNULL(B.CityPopulation, 0)) AS NonCityPopulation, " +
                "(IFNULL(B.CityPopulation, 0) * 100.0 / A.TotalPopulation) AS CityPercent, " +
                "((A.TotalPopulation - IFNULL(B.CityPopulation, 0)) * 100.0 / A.TotalPopulation) AS NonCityPercent " +
                "FROM (SELECT Continent, SUM(Population) AS TotalPopulation FROM country GROUP BY Continent) AS A " +
                "LEFT JOIN (SELECT co.Continent, SUM(ci.Population) AS CityPopulation FROM city ci JOIN country co ON ci.CountryCode = co.Code GROUP BY co.Continent) AS B " +
                "ON A.Continent = B.Continent " +
                "WHERE A.Continent = ?";

        PopulationMetrics continent = null;


        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, continentName);

            try (ResultSet resultSet = pstmt.executeQuery()) {

                if (resultSet.next()) {
                    continent = new PopulationMetrics();

                    // Populate the object
                    continent.setAll(
                            resultSet.getString("Name"),
                            PopulationMetrics.ReportType.CONTINENT,
                            resultSet.getLong("TotalPopulation"),
                            resultSet.getLong("CityPopulation"),
                            resultSet.getLong("NonCityPopulation"),
                            resultSet.getDouble("CityPercent"),
                            resultSet.getDouble("NonCityPercent")
                    );
                }
            }
        } catch (SQLException e) {
            // print error message
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
        return continent; // return statement at the end of the method
    }

    /**
     * Prints the Population of a Continent.
     * Use Case 27 (Total Population Only).
     *
     * @param continentName The name of the continent to display.
     */
    public void printPopulationContinentReport(String continentName) {

        PopulationMetrics continent = getPopulationContinentReport(continentName);

        // Check if the object is null
        if (continent == null) {
            System.out.println("Error: Could not retrieve population for continent: " + continentName);
            return;
        }

        // Header
        System.out.println("\n--- USE CASE 27: Continent Total Population Report (" + continentName + ") ---");

        // Display ONLY the total population
        System.out.println("Continent {\t" +
                "  name='" + continent.getNameOfArea() + "',\t" +
                "  totalPopulation=" + continent.getTotalPopulation() + "\t" +
                '}');

        System.out.println("----------------------------------------------------------------------\n");
    }
}