package com.napier.devops;

import com.napier.devops.config.ApplicationFactory;
import com.napier.devops.database.DatabaseConnection;
import com.napier.devops.ui.MenuUI;

/**
 * Main application class - Entry point for the World Database Reporting System.
 * This class follows the Dependency Injection pattern and sets up the application layers.
 */
public class App {

    private DatabaseConnection dbConnection;
    private MenuUI menuUI;

    public static void main(String[] args) {
        App app = new App();
        app.run(args);
    }

    /**
     * Main application runner that initializes all components and starts the application.
     *
     * @param args Command line arguments [host:port, delay]
     */
    public void run(String[] args) {
        // Initialize database connection
        initializeDatabase(args);
        
        // Initialize application layers
        initializeApplicationLayers();
        
        // Start the application
        startApplication();
        
        // Cleanup resources
        cleanup();
    }

    /**
     * Initialize database connection with command line arguments.
     *
     * @param args Command line arguments
     */
    private void initializeDatabase(String[] args) {
        dbConnection = new DatabaseConnection();
        
        String location = "localhost:3307";
        int delay = 30000;
        
        if (args.length >= 1) {
            location = args[0];
        }
        if (args.length >= 2) {
            delay = Integer.parseInt(args[1]);
        }
        
        boolean connected = dbConnection.connect(location, delay);
        if (!connected) {
            System.err.println("Failed to connect to database. Exiting application.");
            System.exit(-1);
        }
    }

    /**
     * Initialize all application layers using dependency injection.
     */
    private void initializeApplicationLayers() {
        ApplicationFactory factory = new ApplicationFactory(dbConnection);
        menuUI = factory.createApplication();
    }

    /**
     * Start the main application menu.
     */
    private void startApplication() {
        menuUI.displayMenu();
    }

    /**
     * Cleanup application resources.
     */
    private void cleanup() {
        if (menuUI != null) {
            menuUI.close();
        }
        if (dbConnection != null) {
            dbConnection.disconnect();
        }
    }

    // Legacy methods for backward compatibility and testing
    
    /**
     * Get database connection (for testing purposes).
     *
     * @return DatabaseConnection instance
     */
    public DatabaseConnection getDbConnection() {
        return dbConnection;
    }

    /**
     * Set database connection (for testing purposes).
     *
     * @param dbConnection DatabaseConnection instance
     */
    public void setDbConnection(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }
}
