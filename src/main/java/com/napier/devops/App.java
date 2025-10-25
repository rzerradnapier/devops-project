package com.napier.devops;

import com.napier.devops.service.CityReportService;
import com.napier.devops.service.CountryReportService;

import java.sql.*;

import static com.napier.constant.Constant.DEFAULT_COUNTRY_CODE;

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

        System.out.println("list of all countries sorted by population largest to smallest");
        // Get list of all countries sorted by population largest to smallest
        appIns.countryReportService.printAllCountriesByPopulationLargestToSmallest();

        System.out.println("Get country by code USA");
        // Get country by code USA
        Country sampleCountryDetails = appIns.countryReportService.getCountryByCode(DEFAULT_COUNTRY_CODE);
        // Display results
        System.out.println(sampleCountryDetails != null ? sampleCountryDetails.toString() : "No country details found");

        System.out.println("\n=== USE CASE 7: All Cities in the World by Population ===");
        // Get list of all cities sorted by population largest to smallest
        appIns.cityReportService.printAllCitiesByPopulationLargestToSmallest();

        System.out.println("\n=== USE CASE 8: Cities in a Continent by Population ===");
        // Get list of all cities in Asia sorted by population largest to smallest
        appIns.cityReportService.printAllCitiesInContinentByPopulationLargestToSmallest("Asia");
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
