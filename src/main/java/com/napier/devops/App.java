package com.napier.devops;

import com.napier.devops.service.CityReportService;
import com.napier.devops.service.CountryReportService;

import java.sql.*;
import java.util.List;

import static com.napier.constant.Constant.*;

/**
 * Main application class to connect to the database and retrieve country details.
 */
public class App {

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;
    
    /**
     * Service for city-related reports.
     */
    private CityReportService cityReportService;
    
    /**
     * Service for country-related reports.
     */
    private CountryReportService countryReportService;

    /**
     * sets the con object of the app, this is useful for mock testing
     *
     * @param con Connection con
     */
    public void setCon(Connection con) {
        this.con = con;
        // Initialize services when connection is set
        this.cityReportService = new CityReportService(con);
        this.countryReportService = new CountryReportService(con);
    }

    /**
     * gets the con object of the app, this is useful for mock testing
     *
     * @return Connection con
     */
    public Connection getCon() {
        return this.con;
    }
    
    /**
     * Gets the city report service.
     *
     * @return CityReportService instance
     */
    public CityReportService getCityReportService() {
        return this.cityReportService;
    }
    
    /**
     * Gets the country report service.
     *
     * @return CountryReportService instance
     */
    public CountryReportService getCountryReportService() {
        return this.countryReportService;
    }


    public static void main(String[] args) {
        // Create new Application
        App appIns = new App();

        // Connect to database
        if(args.length < 1){
            appIns.connect("localhost:3306", 30000);
        }else{
            appIns.connect(args[0], Integer.parseInt(args[1]));
        }

        System.out.println("\n=== USE CASE 1:list of all countries sorted by population largest to smallest ===");
        // Get list of all countries sorted by population largest to smallest
        appIns.countryReportService.printAllCountriesByPopulationLargestToSmallest();

        System.out.println("\n=== USE CASE 2:Produce a Report all countries in a continent organised by largest to smallest population Continent : Africa ===");
        // Get list of all countries in a continent sorted by population largest to smallest
        appIns.countryReportService.printAllCountriesByPopulationInAContinentLargestToSmallest(DEFAULT_CONTINENT);

        System.out.println("\n=== USE CASE 3: Countries in a Region by Population (South America) ===");
        // Get list of all countries in a region sorted by population largest to smallest
        appIns.countryReportService.printAllCountriesByPopulationInARegionLargestToSmallest(DEFAULT_REGION);

        System.out.println("\n=== USE CASE 4: produce a report of the top N most populated countries in the world where N is provided so that I can identify the largest countries globally n : 10 ===");
        // Get list of the top N most populated countries in the world where N is provided so that I can identify the largest countries globally
        appIns.countryReportService.printTopNCountriesByPopulation(DEFAULT_N);

        System.out.println("\n=== USE CASE 5: Produce a Report on Top N Countries in a Continent, Continent : Africa and N : 10 ===");
        // Get list of the Top N Countries in a Continent
        appIns.countryReportService.printTopNCountriesInContinentByPopulation(DEFAULT_CONTINENT,DEFAULT_N);

        System.out.println("\n=== USE CASE 6: Produce a Report on Top N Countries in a Region, Region : South America and N : 10 ===");
        // Get list of the Top N Countries in a region
        appIns.countryReportService.printTopNCountriesInRegionByPopulation(DEFAULT_REGION,DEFAULT_N);

        System.out.println("\n=== USE CASE 7: All Cities in the World by Population ===");
        // Get list of all cities sorted by population largest to smallest
        appIns.cityReportService.printAllCitiesByPopulationLargestToSmallest();

        System.out.println("\n=== USE CASE 8: Cities in a Continent by Population (Asia) ===");
        // Get list of all cities in Asia sorted by population largest to smallest
        appIns.cityReportService.printAllCitiesInContinentByPopulationLargestToSmallest("Asia");

        System.out.println("\n=== USE CASE 9: Cities in a Region by Population (Western Europe) ===");
        // Get list of all cities in Western Europe sorted by population largest to smallest
        appIns.cityReportService.printAllCitiesInRegionByPopulationLargestToSmallest("Western Europe");

        System.out.println("\n=== USE CASE 10: Cities in a Country by Population (USA) ===");
        // Get list of all cities in USA sorted by population largest to smallest
        appIns.cityReportService.printAllCitiesInCountryByPopulationLargestToSmallest("USA");

        System.out.println("\n=== USE CASE 11: Cities in a District by Population (California) ===");
        // Get list of all cities in California sorted by population largest to smallest
        appIns.cityReportService.printAllCitiesInDistrictByPopulationLargestToSmallest("California");

        System.out.println("\n=== USE CASE 12: Top N Cities in the World by Population (N=10) ===");
        // Get list of top 10 cities in the world sorted by population largest to smallest
        appIns.cityReportService.printTopNCitiesByPopulationLargestToSmallest(DEFAULT_N);
        // Get list of all cities in Asia sorted by population largest to smallest
        appIns.cityReportService.printAllCitiesInContinentByPopulationLargestToSmallest("Asia");

        System.out.println("\n=== USE CASE 13: Produce a Report on Top N Cities in a Continent ===");
        appIns.cityReportService.printTopCitiesByContinent(DEFAULT_CONTINENT, DEFAULT_N);

        System.out.println("\n=== USE CASE 14: Produce a Report on Top N Cities in a Region ===");
        appIns.cityReportService.printTopCitiesByRegion(DEFAULT_REGION, DEFAULT_N);

        System.out.println("\n=== USE CASE 15: Produce a Report on Top N Cities in a Country ===");
        appIns.cityReportService.printTopCitiesByCountry(DEFAULT_COUNTRY_NAME, DEFAULT_N);

        System.out.println("\n=== USE CASE 16: Produce a Report on Top N Cities in a District ===");
        appIns.cityReportService.printTopCitiesByDistrict(DEFAULT_DISTRICT, DEFAULT_N);

        System.out.println("\n=== USE CASE 17: Produce a Report on All Capital Cities in the World by Population ===");
        appIns.cityReportService.printAllCapitalCitiesByPopulation();

        System.out.println("\n=== USE CASE 20: produce a report of the top N most populated capital cities in the world where N is provided so that I can identify the most populated capitals globally ===");
        appIns.cityReportService.printTopCapitalCitiesByPopulation(DEFAULT_N);

        System.out.println("\n=== USE CASE 21: produce a report of the top N most populated capital cities in a continent where N is provided so that I can identify the largest capitals within a continent. ===");
        appIns.cityReportService.printTopCapitalCitiesByContinent(DEFAULT_CONTINENT, DEFAULT_N);

        System.out.println("\n=== USE CASE 22: produce a report of the top N most populated capital cities in a region where N is provided ===");
        appIns.cityReportService.printTopCapitalCitiesByRegion(DEFAULT_REGION, DEFAULT_N);

        System.out.println("\n=== USE CASE 28: Retrieve the Population of a Region ===");
        appIns.countryReportService.printRegionPopulationReport(DEFAULT_REGION);

        System.out.println("\n=== USE CASE 29: Produce a Population Report for a Country. ===");
        appIns.countryReportService.printCountryPopulationReport(DEFAULT_COUNTRY_NAME);

        System.out.println("\n=== USE CASE 30: Retrieve the Population of a District. ===");
        appIns.cityReportService.printDistrictPopulationReport(DEFAULT_DISTRICT);

        System.out.println("\n=== USE CASE 31: Produce a Population Report for a City. ===");
        appIns.cityReportService.printCityPopulationReport(DEFAULT_CITY_NAME);

        System.out.println("\n=== USE CASE 32: Produce a Report on Speakers of Major Languages. ===");
        appIns.countryReportService.printMajorLanguageReport();

        System.out.println("Get country by code USA");
        // Get country by code USA
        Country sampleCountryDetails = appIns.countryReportService.getCountryByCode(DEFAULT_COUNTRY_CODE);
        // Display results
        System.out.println(sampleCountryDetails != null ? sampleCountryDetails.toString() : "No country details found");
    }


    /**
     * Connecting to the MySQL world database.
     */
    public void connect(String location, int delay) {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver " + e.getMessage());
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database....");
            try {
                // Wait a bit for db to start
                Thread.sleep(delay);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + location
                                + "/world?allowPublicKeyRetrieval=true&useSSL=false",
                        "root", "ei:UA@_oSnDZ");
                System.out.println("Successfully Connected");
                // Initialize services after successful connection
                this.cityReportService = new CityReportService(con);
                this.countryReportService = new CountryReportService(con);
                break;
            } catch (SQLException sql) {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(sql.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                // Close connection
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }



}
