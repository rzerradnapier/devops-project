package com.napier.devops.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection manager for MySQL world database.
 */
public class DatabaseConnection {
    
    private Connection connection = null;
    
    /**
     * Connect to the MySQL world database.
     *
     * @param location Database location (host:port)
     * @param delay    Delay in milliseconds before connection attempt
     * @return true if connection successful, false otherwise
     */
    public boolean connect(String location, int delay) {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver " + e.getMessage());
            return false;
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database....");
            try {
                // Wait a bit for db to start
                Thread.sleep(delay);
                // Connect to database
                connection = DriverManager.getConnection("jdbc:mysql://" + location
                                + "/world?allowPublicKeyRetrieval=true&useSSL=false",
                        "root", "ei:UA@_oSnDZ");
                System.out.println("Successfully Connected");
                return true;
            } catch (SQLException sql) {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(sql.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
        return false;
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    /**
     * Get the current database connection.
     *
     * @return Connection object or null if not connected
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Set the database connection (useful for testing).
     *
     * @param connection Connection object
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Check if database is connected.
     *
     * @return true if connected, false otherwise
     */
    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}