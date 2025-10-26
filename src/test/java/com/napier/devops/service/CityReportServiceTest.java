package com.napier.devops.service;

import com.napier.devops.City;
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
 * Unit tests for the CityReportService class using mocked database connections.
 */
public class CityReportServiceTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    private CityReportService cityReportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cityReportService = new CityReportService(mockConnection);
    }

    /**
     * Test getAllCitiesByPopulationLargestToSmallest with mock data.
     * USE CASE 7 Test
     */
    @Test
    void testGetAllCitiesByPopulationLargestToSmallest() throws SQLException {
        // Setup mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        // Mock result set to return three cities in descending population order
        when(mockResultSet.next()).thenReturn(true, true, true, false);
        
        when(mockResultSet.getInt("ID")).thenReturn(1, 2, 3);
        when(mockResultSet.getString("Name")).thenReturn("Mumbai", "Seoul", "São Paulo");
        when(mockResultSet.getString("CountryCode")).thenReturn("IND", "KOR", "BRA");
        when(mockResultSet.getString("District")).thenReturn("Maharashtra", "Seoul", "São Paulo");
        when(mockResultSet.getInt("Population")).thenReturn(10500000, 9981619, 9968485);
        when(mockResultSet.getString("Continent")).thenReturn("Asia", "Asia", "South America");




        // Call the method
        List<City> cities = cityReportService.getAllCitiesByPopulationLargestToSmallest();

        // Verify results
        assertNotNull(cities);
        assertEquals(3, cities.size());

        // Verify first city
        City firstCity = cities.get(0);
        assertEquals(1, firstCity.getId());
        assertEquals("Mumbai", firstCity.getName());
        assertEquals("IND", firstCity.getCountryCode());
        assertEquals("Maharashtra", firstCity.getDistrict());
        assertEquals(10500000, firstCity.getPopulation());

        // Verify cities are in descending order
        assertTrue(cities.get(0).getPopulation() >= cities.get(1).getPopulation());
        assertTrue(cities.get(1).getPopulation() >= cities.get(2).getPopulation());
    }

    /**
     * Test getAllCitiesByPopulationLargestToSmallest with SQL exception.
     */
    @Test
    void testGetAllCitiesByPopulationLargestToSmallestWithSQLException() throws SQLException {
        // Setup mock to throw SQLException
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Call the method
        List<City> cities = cityReportService.getAllCitiesByPopulationLargestToSmallest();

        // Verify that an empty list is returned when there's an exception
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    /**
     * Test getAllCitiesByPopulationLargestToSmallest with empty result set.
     */
    @Test
    void testGetAllCitiesByPopulationLargestToSmallestWithEmptyResult() throws SQLException {
        // Setup mock behavior for empty result
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // No results

        // Call the method
        List<City> cities = cityReportService.getAllCitiesByPopulationLargestToSmallest();

        // Verify that an empty list is returned
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    /**
     * Test printAllCitiesByPopulationLargestToSmallest with null list.
     */
    @Test
    void testPrintAllCitiesByPopulationLargestToSmallestWithNullList() throws SQLException {
        // Setup mock to return null (simulate database connection failure)
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Connection failed"));

        // This should not throw an exception and should handle null gracefully
        assertDoesNotThrow(() -> {
            cityReportService.printAllCitiesByPopulationLargestToSmallest();
        });
    }

    /**
     * Test getAllCitiesInContinentByPopulationLargestToSmallest with mock data.
     * USE CASE 8 Test
     */
    @Test
    void testGetAllCitiesInContinentByPopulationLargestToSmallest() throws SQLException {
        // Setup mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        // Mock result set to return three cities in Asia in descending population order
        when(mockResultSet.next()).thenReturn(true, true, true, false);
        
        when(mockResultSet.getInt("ID")).thenReturn(1024, 2331, 1890);
        when(mockResultSet.getString("Name")).thenReturn("Mumbai", "Seoul", "Shanghai");
        when(mockResultSet.getString("CountryCode")).thenReturn("IND", "KOR", "CHN");
        when(mockResultSet.getString("District")).thenReturn("Maharashtra", "Seoul", "Shanghai");
        when(mockResultSet.getInt("Population")).thenReturn(10500000, 9981619, 9696300);
        when(mockResultSet.getString("Continent")).thenReturn("Asia", "Asia", "South America");

        // Call the method
        List<City> cities = cityReportService.getAllCitiesInContinentByPopulationLargestToSmallest("Asia");

        // Verify results
        assertNotNull(cities);
        assertEquals(3, cities.size());

        // Verify first city
        City firstCity = cities.get(0);
        assertEquals(1024, firstCity.getId());
        assertEquals("Mumbai", firstCity.getName());
        assertEquals("IND", firstCity.getCountryCode());
        assertEquals("Maharashtra", firstCity.getDistrict());
        assertEquals(10500000, firstCity.getPopulation());

        // Verify cities are in descending order
        assertTrue(cities.get(0).getPopulation() >= cities.get(1).getPopulation());
        assertTrue(cities.get(1).getPopulation() >= cities.get(2).getPopulation());

        // Verify that the prepared statement was called with the correct continent
        verify(mockPreparedStatement).setString(1, "Asia");
    }

    /**
     * Test getAllCitiesInContinentByPopulationLargestToSmallest with null continent.
     */
    @Test
    void testGetAllCitiesInContinentByPopulationLargestToSmallestWithNullContinent() {
        // Call the method with null continent
        List<City> cities = cityReportService.getAllCitiesInContinentByPopulationLargestToSmallest(null);

        // Verify that an empty list is returned
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    /**
     * Test getAllCitiesInContinentByPopulationLargestToSmallest with empty continent.
     */
    @Test
    void testGetAllCitiesInContinentByPopulationLargestToSmallestWithEmptyContinent() {
        // Call the method with empty continent
        List<City> cities = cityReportService.getAllCitiesInContinentByPopulationLargestToSmallest("");

        // Verify that an empty list is returned
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    /**
     * Test getAllCitiesInContinentByPopulationLargestToSmallest with SQL exception.
     */
    @Test
    void testGetAllCitiesInContinentByPopulationLargestToSmallestWithSQLException() throws SQLException {
        // Setup mock to throw SQLException
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Call the method
        List<City> cities = cityReportService.getAllCitiesInContinentByPopulationLargestToSmallest("Asia");

        // Verify that an empty list is returned when there's an exception
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    /**
     * Test getAllCitiesInContinentByPopulationLargestToSmallest with empty result set.
     */
    @Test
    void testGetAllCitiesInContinentByPopulationLargestToSmallestWithEmptyResult() throws SQLException {
        // Setup mock behavior for empty result
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // No results

        // Call the method
        List<City> cities = cityReportService.getAllCitiesInContinentByPopulationLargestToSmallest("Antarctica");

        // Verify that an empty list is returned
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    /**
     * Test printAllCitiesInContinentByPopulationLargestToSmallest with null continent.
     */
    @Test
    void testPrintAllCitiesInContinentByPopulationLargestToSmallestWithNullContinent() {
        // This should not throw an exception and should handle null gracefully
        assertDoesNotThrow(() -> {
            cityReportService.printAllCitiesInContinentByPopulationLargestToSmallest(null);
        });
    }

    /**
     * Test printAllCitiesInContinentByPopulationLargestToSmallest with empty continent.
     */
    @Test
    void testPrintAllCitiesInContinentByPopulationLargestToSmallestWithEmptyContinent() {
        // This should not throw an exception and should handle empty string gracefully
        assertDoesNotThrow(() -> {
            cityReportService.printAllCitiesInContinentByPopulationLargestToSmallest("");
        });
    }

    /**
     * Test printAllCitiesInContinentByPopulationLargestToSmallest with valid continent but no data.
     */
    @Test
    void testPrintAllCitiesInContinentByPopulationLargestToSmallestWithNoData() throws SQLException {
        // Setup mock to return empty list
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        // This should not throw an exception and should handle empty data gracefully
        assertDoesNotThrow(() -> {
            cityReportService.printAllCitiesInContinentByPopulationLargestToSmallest("Antarctica");
        });
    }
}