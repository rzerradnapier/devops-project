package com.napier.devops.service;

import com.napier.devops.Region;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for region-related reporting functionality.
 * Deals with all region report use cases.
 */
public class RegionReportService {

    private final Connection connection;

    public RegionReportService(Connection connection) {
        this.connection = connection;
    }

    /**
     * Executes the query for Use Case 24 and displays the results.
     *
     * @return A list of Region objects.
     */
    public List<Region> getRegionPopulationReport() {
        List<Region> report = new ArrayList<>();


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
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Region r = new Region();
                // Using the setter methods from Region class
                r.setNameofRegion(rs.getString("Region"));
                r.settotalPopulation(rs.getLong("TotalPopulation"));
                r.setcityPopulation(rs.getLong("CityPopulation"));
                r.setnonCityPopulation(rs.getLong("NonCityPopulation"));
                r.setcityPopulationPercentage(rs.getFloat("CityPopulationPercentage"));
                r.setnonCityPopulationPercentage(rs.getFloat("NonCityPopulationPercentage"));
                report.add(r);
            }
        } catch (SQLException e) {
            System.err.println("Error running region population report query: " + e.getMessage());
        }
        return report;
    }

    /**
     * Prints the population report for all regions.
     */
    public void printRegionPopulationReport() {
        List<Region> regionList = getRegionPopulationReport(); // Call the data getter

        if (regionList.isEmpty()) {
            System.out.println("No region population data found.");
            return;
        }

        System.out.println("==========================================================================================================");
        System.out.println("|                                        USE CASE: 24 Produce a Population Report for Regions                                     |");
        System.out.println("==========================================================================================================");



        System.out.printf("| %-15s | %-18s | %-22s | %-22s |%n", "Region", "Total Population", "City Population", "Non-City Population");
        System.out.println("|-----------------|--------------------|------------------------|------------------------|");

        for (Region rp : regionList) {
            System.out.println(rp.toString());
        }
        System.out.println("==========================================================================================================");
    }
}