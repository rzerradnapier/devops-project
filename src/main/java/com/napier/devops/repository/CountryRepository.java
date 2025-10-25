package com.napier.devops.repository;

import com.napier.devops.Country;
import com.napier.devops.database.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for Country data access operations.
 */
public class CountryRepository {
    
    private final DatabaseConnection dbConnection;
    
    public CountryRepository(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }
    
    /**
     * Get a country by its code.
     *
     * @param countryCode The code of the country to retrieve
     * @return A Country object or null if not found
     */
    public Country findByCode(String countryCode) {
        if (dbConnection == null || dbConnection.getConnection() == null) {
            return null;
        }
        
        String sql = "SELECT code, name, continent, region, population, capital FROM country WHERE code = ?";
        
        try (PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, countryCode);
            ResultSet resultSet = pstmt.executeQuery();
            
            if (resultSet.next()) {
                return mapResultSetToCountry(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Get all countries ordered by population descending.
     *
     * @return List of Country objects ordered by population
     */
    public List<Country> findAllOrderByPopulationDesc() {
        List<Country> countries = new ArrayList<>();
        
        if (dbConnection == null || dbConnection.getConnection() == null) {
            return countries;
        }
        
        String sql = "SELECT code, name, continent, region, population, capital FROM country ORDER BY population DESC";
        
        try (PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = pstmt.executeQuery();
            
            while (resultSet.next()) {
                countries.add(mapResultSetToCountry(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        
        return countries;
    }
    
    /**
     * Map ResultSet to Country object.
     *
     * @param resultSet ResultSet from database query
     * @return Country object
     * @throws SQLException if database access error occurs
     */
    private Country mapResultSetToCountry(ResultSet resultSet) throws SQLException {
        Country country = new Country();
        country.setCode(resultSet.getString("code"));
        country.setName(resultSet.getString("name"));
        country.setContinent(resultSet.getString("continent"));
        country.setRegion(resultSet.getString("region"));
        country.setPopulation(resultSet.getInt("population"));
        country.setCapital(resultSet.getInt("capital"));
        return country;
    }
}