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
     * Executes the query for Use Case 23 and maps the results.
     *
     * @return A list of ContinentPopulation objects.
     */
    public List<Continent> getContinentPopulationReport() {
        List<Continent> report = new ArrayList<>();
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
                Continent cp = new Continent();
                cp.setContinent(resultSet.getString("Continent"));
                cp.setTotalPopulation(resultSet.getLong("TotalPopulation"));
                cp.setCityPopulation(resultSet.getLong("CityPopulation"));
                cp.setNonCityPopulation(resultSet.getLong("NonCityPopulation"));
                cp.setCityPopulationPercentage(resultSet.getDouble("CityPopulationPercentage"));
                cp.setNonCityPopulationPercentage(resultSet.getDouble("NonCityPopulationPercentage"));
                report.add(cp);
            }
        } catch (SQLException e) {
            System.err.println("Error running continent population report query: " + e.getMessage());
        }
        return report;
    }

    /**
     * Prints the Continent Population Report (Use Case 23).
     */

    public void printContinentPopulationReport() {
        List<Continent> continentList = getContinentPopulationReport();

        if (continentList.isEmpty()) {
            System.out.println("No continent population data found.");
            return;
        }

        System.out.println("==========================================================================================================================");
        System.out.println("|                                     USE CASE: 23 Produce a Population Report for Continents                                   |");
        System.out.println("==========================================================================================================================");
        System.out.printf("| %-15s | %-22s | %-22s | %-18s |%n", "Continent", "Non-City Population", "City Population", "Total Population");
        System.out.println("-----------------|------------------------|------------------------|--------------------|");

        for (Continent cp : continentList) {
            System.out.println(cp.toString());
        }
        System.out.println("==========================================================================================================================");
    }

}