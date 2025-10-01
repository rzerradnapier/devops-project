package com.napier.devops;

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
     * sets the con object of the app, this is useful for mock testing
     *
     * @param con Connection con
     */
    public void setCon(Connection con) {
        this.con = con;
    }

    /**
     * gets the con object of the app, this is useful for mock testing
     *
     * @return Connection con
     */
    public Connection getCon() {
        return this.con;
    }


    public static void main(String[] args) {
        // Create new Application
        App appIns = new App();

        // Connect to database
        appIns.connect();

        // Get Countries
        Country sampleCountryDetails = appIns.getCountryByCode(DEFAULT_COUNTRY_CODE);
        // Display results
        System.out.println(sampleCountryDetails != null ? sampleCountryDetails.toString() : "No country details found");
    }


    /**
     * Connecting to the MySQL world database.
     */
    public void connect() {
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
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "ei:UA@_oSnDZ");
                System.out.println("Successfully Connected");
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
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
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


}
