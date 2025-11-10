package com.napier.devops.service;

import com.napier.devops.CountryPopulation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for country population reporting functionality.
 */
public class CountryPopulationReportService {

    private final Connection connection;

    public CountryPopulationReportService(Connection connection) {
        this.connection = connection;
    }

    /**
     * Executes the query for Use Case 25.
     *
     * @return A list of CountryPopulation objects containing the relevant data.
     */
    public List<CountryPopulation> getCountryPopulationReport() {
        List<CountryPopulation> countries = new ArrayList<>();
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
                CountryPopulation countryPopulation = new CountryPopulation();
                countryPopulation.setCountryName(resultSet.getString("CountryName"));
                countryPopulation.setCityPopulation(resultSet.getLong("CityPopulation"));
                countryPopulation.setTotalPopulation(resultSet.getLong("TotalPopulation"));
                countryPopulation.setNonCityPopulation(resultSet.getLong("NonCityPopulation"));
                countryPopulation.setCityPercent(resultSet.getDouble("CityPopulationPercentage") + "%");
                countryPopulation.setNonCityPercentage(resultSet.getDouble("NonCityPopulationPercentage") + "%");

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
        List<CountryPopulation> countryPopulationList = getCountryPopulationReport(); // Correct variable name

        if (countryPopulationList.isEmpty()) {
            System.out.println("No country population data found.");
            return;
        }

        System.out.println("=================================================================================================================================================");
        System.out.println("|                                               USE CASE 25: Produce a Population Report for Countries                                            |");
        System.out.println("=================================================================================================================================================");
        System.out.printf("| %-47s | %-18s | %-18s | %-12s | %-18s | %-11s |%n",
                "Country Name", "Total Population", "City Population", "% City", "Non-City Population", "% Non-City");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");

        for (CountryPopulation countryPopulation : countryPopulationList) {
            System.out.printf("| %-47s | %-18s | %-18s | %-12s | %-18s | %-11s |%n",
                    countryPopulation.getCountryName(),
                    countryPopulation.getTotalPopulation(),
                    countryPopulation.getCityPopulation(),
                    countryPopulation.getCityPercent(),
                    countryPopulation.getNonCityPopulation(),
                    countryPopulation.getNonCityPercentage());
        }
        System.out.println("=================================================================================================================================================");
    }
}