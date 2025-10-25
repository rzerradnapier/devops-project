package com.napier.devops.repository;

import com.napier.devops.City;
import com.napier.devops.database.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CityRepository class.
 */
public class CityRepositoryTest {

    @Mock
    private DatabaseConnection databaseConnection;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private CityRepository cityRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cityRepository = new CityRepository(databaseConnection);
    }

    @Test
    void testConstructorWithValidConnection() {
        DatabaseConnection dbConn = mock(DatabaseConnection.class);
        CityRepository repository = new CityRepository(dbConn);
        assertNotNull(repository);
    }

    @Test
    void testConstructorWithNullConnection() {
        CityRepository repository = new CityRepository(null);
        assertNotNull(repository);
    }

    @Test
    void testFindAllOrderByPopulationDescSuccess() throws SQLException {
        // Arrange
        when(databaseConnection.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false);
        
        // Mock result set data
        when(resultSet.getInt("ID")).thenReturn(1, 2);
        when(resultSet.getString("Name")).thenReturn("Tokyo", "Delhi");
        when(resultSet.getString("CountryCode")).thenReturn("JPN", "IND");
        when(resultSet.getString("District")).thenReturn("Tokyo-to", "Delhi");
        when(resultSet.getInt("Population")).thenReturn(37400068, 28514000);

        // Act
        List<City> result = cityRepository.findAllOrderByPopulationDesc();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Tokyo", result.get(0).getName());
        assertEquals("JPN", result.get(0).getCountryCode());
        assertEquals(37400068, result.get(0).getPopulation());
        assertEquals("Delhi", result.get(1).getName());
        assertEquals("IND", result.get(1).getCountryCode());
        assertEquals(28514000, result.get(1).getPopulation());
        verify(preparedStatement).executeQuery();
    }

    @Test
    void testFindAllOrderByPopulationDescEmpty() throws SQLException {
        // Arrange
        when(databaseConnection.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        // Act
        List<City> result = cityRepository.findAllOrderByPopulationDesc();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(preparedStatement).executeQuery();
    }

    @Test
    void testFindAllOrderByPopulationDescWithNullConnection() {
        // Arrange
        CityRepository repository = new CityRepository(null);

        // Act
        List<City> result = repository.findAllOrderByPopulationDesc();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAllOrderByPopulationDescWithNullDatabaseConnection() {
        // Arrange
        when(databaseConnection.getConnection()).thenReturn(null);

        // Act
        List<City> result = cityRepository.findAllOrderByPopulationDesc();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAllOrderByPopulationDescWithSQLException() throws SQLException {
        // Arrange
        when(databaseConnection.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Act
        List<City> result = cityRepository.findAllOrderByPopulationDesc();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}