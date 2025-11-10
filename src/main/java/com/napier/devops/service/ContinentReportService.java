package com.napier.devops.service;

import com.napier.devops.Continent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for contient-related reporting functionality.
 * Handles all contient report use cases.
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
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Continent cp = new Continent();
                cp.setContinent(rs.getString("Continent"));
                cp.setTotalPopulation(rs.getLong("TotalPopulation"));
                cp.setCityPopulation(rs.getLong("CityPopulation"));
                cp.setNonCityPopulation(rs.getLong("NonCityPopulation"));
                cp.setCityPopulationPercentage(rs.getDouble("CityPopulationPercentage"));
                cp.setNonCityPopulationPercentage(rs.getDouble("NonCityPopulationPercentage"));
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