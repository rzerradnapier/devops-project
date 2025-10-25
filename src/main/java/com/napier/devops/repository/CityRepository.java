package com.napier.devops.repository;

import com.napier.devops.City;
import com.napier.devops.database.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for City data access operations.
 */
public class CityRepository {
    
    private final DatabaseConnection dbConnection;
    
    public CityRepository(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }
    
    /**
     * Get all cities ordered by population descending.
     *
     * @return List of City objects ordered by population
     */
    public List<City> findAllOrderByPopulationDesc() {
        List<City> cities = new ArrayList<>();
        
        if (dbConnection == null || dbConnection.getConnection() == null) {
            return cities;
        }
        
        String sql = "SELECT ID, Name, CountryCode, District, Population FROM city ORDER BY Population DESC";
        
        try (PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(sql);
             ResultSet resultSet = pstmt.executeQuery()) {
            
            while (resultSet.next()) {
                cities.add(mapResultSetToCity(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        
        return cities;
    }
    
    /**
     * Map ResultSet to City object.
     *
     * @param resultSet ResultSet from database query
     * @return City object
     * @throws SQLException if database access error occurs
     */
    private City mapResultSetToCity(ResultSet resultSet) throws SQLException {
        City city = new City();
        city.setId(resultSet.getInt("ID"));
        city.setName(resultSet.getString("Name"));
        city.setCountryCode(resultSet.getString("CountryCode"));
        city.setDistrict(resultSet.getString("District"));
        city.setPopulation(resultSet.getInt("Population"));
        return city;
    }
}