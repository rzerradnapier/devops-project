package com.napier.devops.repository;

import com.napier.devops.Country;
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
 * Unit tests for CountryRepository class.
 */
public class CountryRepositoryTest {

    @Mock
    private DatabaseConnection databaseConnection;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private CountryRepository countryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        countryRepository = new CountryRepository(databaseConnection);
    }

    @Test
    void testConstructorWithValidConnection() {
        DatabaseConnection dbConn = mock(DatabaseConnection.class);
        CountryRepository repository = new CountryRepository(dbConn);
        assertNotNull(repository);
    }

    @Test
    void testConstructorWithNullConnection() {
        CountryRepository repository = new CountryRepository(null);
        assertNotNull(repository);
    }

    @Test
    void testFindByCodeSuccess() throws SQLException {
        // Arrange
        String countryCode = "USA";
        when(databaseConnection.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("code")).thenReturn("USA");
        when(resultSet.getString("name")).thenReturn("United States");
        when(resultSet.getString("continent")).thenReturn("North America");
        when(resultSet.getString("region")).thenReturn("North America");
        when(resultSet.getInt("population")).thenReturn(331000000);
        when(resultSet.getInt("capital")).thenReturn(3813);

        // Act
        Country result = countryRepository.findByCode(countryCode);

        // Assert
        assertNotNull(result);
        assertEquals("USA", result.getCode());
        assertEquals("United States", result.getName());
        assertEquals("North America", result.getContinent());
        assertEquals(331000000, result.getPopulation());
        verify(preparedStatement).setString(1, countryCode);
        verify(preparedStatement).executeQuery();
    }

    @Test
    void testFindByCodeNotFound() throws SQLException {
        // Arrange
        String countryCode = "XXX";
        when(databaseConnection.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        // Act
        Country result = countryRepository.findByCode(countryCode);

        // Assert
        assertNull(result);
        verify(preparedStatement).setString(1, countryCode);
        verify(preparedStatement).executeQuery();
    }

    @Test
    void testFindByCodeWithNullConnection() {
        // Arrange
        CountryRepository repository = new CountryRepository(null);

        // Act
        Country result = repository.findByCode("USA");

        // Assert
        assertNull(result);
    }

    @Test
    void testFindByCodeWithNullDatabaseConnection() {
        // Arrange
        when(databaseConnection.getConnection()).thenReturn(null);

        // Act
        Country result = countryRepository.findByCode("USA");

        // Assert
        assertNull(result);
    }

    @Test
    void testFindByCodeWithSQLException() throws SQLException {
        // Arrange
        String countryCode = "USA";
        when(databaseConnection.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Act
        Country result = countryRepository.findByCode(countryCode);

        // Assert
        assertNull(result);
    }

    @Test
    void testFindAllOrderByPopulationDescSuccess() throws SQLException {
        // Arrange
        when(databaseConnection.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false);
        
        // Mock ResultSet data for both countries
        when(resultSet.getString("code")).thenReturn("CHN", "USA");
        when(resultSet.getString("name")).thenReturn("China", "United States");
        when(resultSet.getString("continent")).thenReturn("Asia", "North America");
        when(resultSet.getString("region")).thenReturn("Eastern Asia", "North America");
        when(resultSet.getInt("population")).thenReturn(1439323776, 331000000);
        when(resultSet.getInt("capital")).thenReturn(1891, 3813);

        // Act
        List<Country> result = countryRepository.findAllOrderByPopulationDesc();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("CHN", result.get(0).getCode());
        assertEquals("USA", result.get(1).getCode());
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
        List<Country> result = countryRepository.findAllOrderByPopulationDesc();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(preparedStatement).executeQuery();
    }

    @Test
    void testFindAllOrderByPopulationDescWithNullConnection() {
        // Arrange
        CountryRepository repository = new CountryRepository(null);

        // Act
        List<Country> result = repository.findAllOrderByPopulationDesc();

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
        List<Country> result = countryRepository.findAllOrderByPopulationDesc();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}