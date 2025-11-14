package com.napier.devops.service;

import com.napier.devops.Country;
import com.napier.pojo.LanguageReportPojo;
import com.napier.pojo.PopulationReportPojo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

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


    /**
     * USE CASE 28: Produce a Population Report for a Region.
     *
     * @param regionName Name of the region
     * @return A PopulationReportPojo object containing population breakdown, or null if not found.
     */
    public PopulationReportPojo getRegionPopulationReport(String regionName) {
        long totalPopulation = 0;
        long cityPopulation = 0;

        if (regionName == null || regionName.trim().isEmpty()) {
            System.err.println("Error: Region name cannot be null or empty.");
            return null;
        }

        PopulationReportPojo report = new PopulationReportPojo();
        report.setName(regionName);

        // Queries
        String totalQuery = "SELECT SUM(population) AS total_population FROM country WHERE region = ?";
        String cityQuery = """
                SELECT SUM(city.population) AS city_population
                FROM city
                INNER JOIN country ON city.CountryCode = country.Code
                WHERE country.Region = ?
                """;

        try (PreparedStatement stmtTotal = connection.prepareStatement(totalQuery);
             PreparedStatement stmtCity = connection.prepareStatement(cityQuery)) {
            // Total population
            stmtTotal.setString(1, regionName);
            try (ResultSet rsTotal = stmtTotal.executeQuery()) {
                if (rsTotal.next()) {
                    totalPopulation = rsTotal.getLong("total_population");
                }
            }

            // City population
            stmtCity.setString(1, regionName);
            try (ResultSet rsCity = stmtCity.executeQuery()) {
                if (rsCity.next()) {
                    cityPopulation = rsCity.getLong("city_population");
                }
            }

            // Derived values
            long nonCityPopulation = totalPopulation - cityPopulation;
            double cityPercentage = totalPopulation > 0
                    ? ((cityPopulation * 100.0) / totalPopulation)
                    : 0.0;
            double nonCityPercentage = 100.0 - cityPercentage;

            // Fill report
            report.setTotalPopulation(totalPopulation);
            report.setPopulationInCities(cityPopulation);
            report.setPopulationNotInCities(nonCityPopulation);
            report.setPercentageInCities(cityPercentage);
            report.setPercentageNotInCities(nonCityPercentage);

            return report;

        } catch (SQLException e) {
            System.err.println("SQL Error retrieving population report: " + e.getMessage());
            return null;
        }
    }

    /**
     * USE CASE 28: Produce a Population Report for a Region.
     * Prints and returns the population report for a given region.
     *
     * @param defaultRegion Name of the region
     */
    public void printRegionPopulationReport(String defaultRegion) {
        PopulationReportPojo report = getRegionPopulationReport(defaultRegion);

        if (report == null) {
            System.err.println("Error: No population data found for region: " + defaultRegion);
            return;
        }

        NumberFormat nf = NumberFormat.getInstance(Locale.US);

        System.out.println("========================================");
        System.out.println("       REGION POPULATION REPORT         ");
        System.out.println("========================================");
        System.out.println("Region: " + report.getName());
        System.out.println("Total Population: " + nf.format(report.getTotalPopulation()));
        System.out.println("Population in Cities: " + nf.format(report.getPopulationInCities()) +
                " (" + String.format("%.2f", report.getPercentageInCities()) + "%)");
        System.out.println("Population Not in Cities: " + nf.format(report.getPopulationNotInCities()) +
                " (" + String.format("%.2f", report.getPercentageNotInCities()) + "%)");
        System.out.println("========================================");
    }


    /**
     * USE CASE 29: Produce a Population Report for a Country.
     *
     * @param countryName Name of the country
     * @return A PopulationReport object containing population breakdown, or null if not found.
     */
    public PopulationReportPojo getCountryPopulationReport(String countryName) {
        long totalPopulation = 0;
        long cityPopulation = 0;

        if (countryName == null || countryName.trim().isEmpty()) {
            System.err.println("Error: Country name cannot be null or empty.");
            return null;
        }

        PopulationReportPojo report = new PopulationReportPojo();
        report.setName(countryName);

        String totalQuery = "SELECT Population FROM country WHERE Name = ?";
        String cityQuery = """
                SELECT SUM(city.Population) AS city_population
                FROM city
                INNER JOIN country ON city.CountryCode = country.Code
                WHERE country.Name = ?
                """;

        try (PreparedStatement stmtTotal = connection.prepareStatement(totalQuery);
             PreparedStatement stmtCity = connection.prepareStatement(cityQuery)) {
            // Get total population
            stmtTotal.setString(1, countryName);
            try (ResultSet rsTotal = stmtTotal.executeQuery()) {
                if (rsTotal.next()) {
                    totalPopulation = rsTotal.getLong("Population");
                }
            }

            // Get city population
            stmtCity.setString(1, countryName);
            try (ResultSet rsCity = stmtCity.executeQuery()) {
                if (rsCity.next()) {
                    cityPopulation = rsCity.getLong("city_population");
                }
            }

            // Derived calculations
            long nonCityPopulation = totalPopulation - cityPopulation;

            double cityPercentage = totalPopulation > 0
                    ? ((cityPopulation * 100.0) / totalPopulation)
                    : 0.0;

            double nonCityPercentage = 100.0 - cityPercentage;

            // Fill report
            report.setTotalPopulation(totalPopulation);
            report.setPopulationInCities(cityPopulation);
            report.setPopulationNotInCities(nonCityPopulation);
            report.setPercentageInCities(cityPercentage);
            report.setPercentageNotInCities(nonCityPercentage);

            return report;

        } catch (SQLException e) {
            System.err.println("SQL Error retrieving population report: " + e.getMessage());
            return null;
        }
    }


    /**
     * USE CASE 29: Produce a Population Report for a Country.
     * Prints and returns the population report for a given country.
     *
     * @param countryName Name of the country
     */
    public void printCountryPopulationReport(String countryName) {
        PopulationReportPojo report = getCountryPopulationReport(countryName);

        if (report == null) {
            System.err.println("Error: No population data found for country: " + countryName);
            return;
        }

        NumberFormat nf = NumberFormat.getInstance(Locale.US);

        System.out.println("========================================");
        System.out.println("        COUNTRY POPULATION REPORT       ");
        System.out.println("========================================");
        System.out.println("Country: " + report.getName());
        System.out.println("Total Population: " + nf.format(report.getTotalPopulation()));
        System.out.println("Population in Cities: " + nf.format(report.getPopulationInCities()) +
                " (" + String.format("%.2f", report.getPercentageInCities()) + "%)");
        System.out.println("Population Not in Cities: " + nf.format(report.getPopulationNotInCities()) +
                " (" + String.format("%.2f", report.getPercentageNotInCities()) + "%)");
        System.out.println("========================================");
    }


    /**
     * USE CASE 32: Produce a Report on Speakers of Major Languages.
     * Retrieves the number of people who speak Chinese, English, Hindi, Spanish, and Arabic,
     * ordered from greatest to smallest, including the percentage of the world population.
     *
     * @return A list of LanguageReportPojo objects ordered by number of speakers (descending).
     */
    public List<LanguageReportPojo> getMajorLanguageReport() {
        List<LanguageReportPojo> languageReports = new ArrayList<>();

        String totalWorldQuery = "SELECT SUM(Population) AS world_population FROM country";

        String languagesQuery = """
                SELECT Language,
                       SUM(country.Population * countrylanguage.Percentage / 100) AS speakers
                FROM countrylanguage
                INNER JOIN country ON country.Code = countrylanguage.CountryCode
                WHERE Language IN ('Chinese', 'English', 'Hindi', 'Spanish', 'Arabic')
                GROUP BY Language
                ORDER BY speakers DESC
                """;

        try (PreparedStatement stmtWorldPop = connection.prepareStatement(totalWorldQuery);
             PreparedStatement stmtLanguages = connection.prepareStatement(languagesQuery)) {

            long worldPopulation = 0;

            // 1. Get total world population
            try (ResultSet rsWorldPop = stmtWorldPop.executeQuery()) {
                if (rsWorldPop.next()) {
                    worldPopulation = rsWorldPop.getLong("world_population");
                }
            }

            // Validate world population
            if (worldPopulation == 0) {
                System.err.println("Error: Could not determine world population.");
                return languageReports;
            }

            // 2. Get speakers for the five major languages
            try (ResultSet rsLanguages = stmtLanguages.executeQuery()) {
                while (rsLanguages.next()) {
                    String language = rsLanguages.getString("Language");
                    long speakers = rsLanguages.getLong("speakers");

                    double percentage = (speakers > 0 && worldPopulation > 0)
                            ? ((speakers * 100.0) / worldPopulation)
                            : 0.0;

                    LanguageReportPojo report = new LanguageReportPojo();
                    report.setLanguage(language);
                    report.setSpeakers(speakers);
                    report.setWorldPopulation(worldPopulation);
                    report.setPercentageOfWorld(percentage);

                    languageReports.add(report);
                }
            }

        } catch (SQLException e) {
            System.err.println("SQL Error retrieving major language report: " + e.getMessage());
            return Collections.emptyList();
        }

        return languageReports;
    }


    /**
     * USE CASE 32: Produce a Report on Speakers of Major Languages.
     */
    public void printMajorLanguageReport() {
        List<LanguageReportPojo> reports = getMajorLanguageReport();

        if (reports == null || reports.isEmpty()) {
            System.err.println("Error: No language report data found.");
            return;
        }

        NumberFormat nf = NumberFormat.getInstance(Locale.US); // ensures commas

        System.out.println("===============================================================");
        System.out.println("           MAJOR LANGUAGES SPEAKERS REPORT                     ");
        System.out.println("===============================================================");
        System.out.printf("%-15s %-20s %-15s%n", "Language", "Speakers", "% of World Pop");
        System.out.println("---------------------------------------------------------------");

        for (LanguageReportPojo report : reports) {
            String formattedSpeakers = nf.format(report.getSpeakers());
            System.out.printf("%-15s %-20s %-15.2f%n",
                    report.getLanguage(),
                    formattedSpeakers,
                    report.getPercentageOfWorld());
        }

        System.out.println("===============================================================");
    }


}