package com.napier.devops.service;

import com.napier.devops.Continent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for continent-related reporting functionality.
 * Handles all continent report use cases.
 */
public class ContinentReportService {

    private final Connection connection;

    public ContinentReportService(Connection connection) {
        this.connection = connection;
    }


    /**
     * Executes the query for Use Case 23 and display the results.
     *
     * @return A list of ContinentPopulation objects containing the relevant data.
     */
    public List<Continent> getContinentPopulationReport() {
        List<Continent> contients = new ArrayList<>();
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
                Continent continent = new Continent();
                continent.setContinent(resultSet.getString("Continent"));
                continent.setCityPopulationPercentage(resultSet.getDouble("CityPopulationPercentage"));
                continent.setCityPopulation(resultSet.getLong("CityPopulation"));
                continent.setTotalPopulation(resultSet.getLong("TotalPopulation"));
                continent.setNonCityPopulation(resultSet.getLong("NonCityPopulation"));
                continent.setNonCityPopulationPercentage(resultSet.getDouble("NonCityPopulationPercentage"));
                contients.add(continent);
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return contients;
    }

    /**
     * Prints the Continent Population Report for Use Case 23.
     */

    public void printContinentPopulationReport() {
        List<Continent> continentList = getContinentPopulationReport();

        if (continentList.isEmpty()) {
            System.out.println("No continent population data found.");
            return;
        }

        System.out.println("===============================================================================================================================================================================================");
        System.out.println("|                                                                 USE CASE: 23 Produce a Population Report for Continents                                                                   |");
        System.out.println("===============================================================================================================================================================================================");
        System.out.printf("| %-15s | %-24s | %-9s | %-24s | %-9s | %-18s |%n",
                "Continent", "Non-City Population", "% Non-City", "City Population", "% City", "Total Population");
        System.out.println("-----------------|--------------------------|-----------|--------------------------|-----------|--------------------|");

        for (Continent continent : continentList) {
            System.out.println(continent.toString());
        }
        System.out.println("===============================================================================================================================================================================================");
    }

}