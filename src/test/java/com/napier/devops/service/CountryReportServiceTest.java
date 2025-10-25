package com.napier.devops.service;

import com.napier.devops.Country;
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
 * Unit tests for the CountryReportService class using mocked database connections.
 */
public class CountryReportServiceTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    private CountryReportService countryReportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        countryReportService = new CountryReportService(mockConnection);
    }

    /**
     * Test getCountryByCode with mock data.
     */
    @Test
    void testGetCountryByCode() throws SQLException {
        // Setup mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("code")).thenReturn("USA");
        when(mockResultSet.getString("name")).thenReturn("United States");
        when(mockResultSet.getString("continent")).thenReturn("North America");
        when(mockResultSet.getString("region")).thenReturn("North America");
        when(mockResultSet.getInt("population")).thenReturn(278357000);
        when(mockResultSet.getInt("capital")).thenReturn(3813);

        // Call the method
        Country country = countryReportService.getCountryByCode("USA");

        // Verify results
        assertNotNull(country);
        assertEquals("USA", country.getCode());
        assertEquals("United States", country.getName());
        assertEquals("North America", country.getContinent());
        assertEquals("North America", country.getRegion());
        assertEquals(278357000, country.getPopulation());
        assertEquals(3813, country.getCapital());

        // Verify that the prepared statement was called with the correct country code
        verify(mockPreparedStatement).setString(1, "USA");
    }

    /**
     * Test getCountryByCode with no results.
     */
    @Test
    void testGetCountryByCodeWithNoResults() throws SQLException {
        // Setup mock behavior for no results
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        // Call the method
        Country country = countryReportService.getCountryByCode("XXX");

        // Verify that null is returned
        assertNull(country);
    }

    /**
     * Test getCountryByCode with SQL exception.
     */
    @Test
    void testGetCountryByCodeWithSQLException() throws SQLException {
        // Setup mock to throw SQLException
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Call the method
        Country country = countryReportService.getCountryByCode("USA");

        // Verify that null is returned when there's an exception
        assertNull(country);
    }

    /**
     * Test getAllCountriesByPopulationLargestToSmallest with mock data.
     */
    @Test
    void testGetAllCountriesByPopulationLargestToSmallest() throws SQLException {
        // Setup mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, true, false);
        
        when(mockResultSet.getString("code")).thenReturn("CHN", "IND", "USA");
        when(mockResultSet.getString("name")).thenReturn("China", "India", "United States");
        when(mockResultSet.getString("continent")).thenReturn("Asia", "Asia", "North America");
        when(mockResultSet.getString("region")).thenReturn("Eastern Asia", "Southern and Central Asia", "North America");
        when(mockResultSet.getInt("population")).thenReturn(1277558000, 1013662000, 278357000);
        when(mockResultSet.getInt("capital")).thenReturn(1891, 1109, 3813);

        // Call the method
        List<Country> countries = countryReportService.getAllCountriesByPopulationLargestToSmallest();

        // Verify results
        assertNotNull(countries);
        assertEquals(3, countries.size());

        // Verify first country
        Country firstCountry = countries.get(0);
        assertEquals("CHN", firstCountry.getCode());
        assertEquals("China", firstCountry.getName());
        assertEquals("Asia", firstCountry.getContinent());
        assertEquals("Eastern Asia", firstCountry.getRegion());
        assertEquals(1277558000, firstCountry.getPopulation());
        assertEquals(1891, firstCountry.getCapital());
        
        // Verify descending order
        assertTrue(countries.get(0).getPopulation() >= countries.get(1).getPopulation());
        assertTrue(countries.get(1).getPopulation() >= countries.get(2).getPopulation());
    }

    /**
     * Test getAllCountriesByPopulationLargestToSmallest with SQL exception.
     */
    @Test
    void testGetAllCountriesByPopulationLargestToSmallestWithSQLException() throws SQLException {
        // Setup mock to throw SQLException
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Call the method
        List<Country> countries = countryReportService.getAllCountriesByPopulationLargestToSmallest();

        // Verify that an empty list is returned when there's an exception
        assertNotNull(countries);
        assertTrue(countries.isEmpty());
    }

    /**
     * Test getAllCountriesByPopulationLargestToSmallest with empty result set.
     */
    @Test
    void testGetAllCountriesByPopulationLargestToSmallestWithEmptyResult() throws SQLException {
        // Setup mock behavior for empty result
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // No results

        // Call the method
        List<Country> countries = countryReportService.getAllCountriesByPopulationLargestToSmallest();

        // Verify that an empty list is returned
        assertNotNull(countries);
        assertTrue(countries.isEmpty());
    }

    /**
     * Test printAllCountriesByPopulationLargestToSmallest with null list.
     */
    @Test
    void testPrintAllCountriesByPopulationLargestToSmallestWithNullList() throws SQLException {
        // Setup mock to return null (simulate database connection failure)
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Connection failed"));

        // This should not throw an exception and should handle null gracefully
        assertDoesNotThrow(() -> {
            countryReportService.printAllCountriesByPopulationLargestToSmallest();
        });
    }
}