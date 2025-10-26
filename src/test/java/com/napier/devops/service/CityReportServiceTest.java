package com.napier.devops.service;

import com.napier.devops.City;
import com.napier.devops.pojo.CityPojo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.List;

import static com.napier.constant.Constant.DEFAULT_N;
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

    @Mock
    private Statement mockStatement;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        cityReportService = new CityReportService(mockConnection);

        // Setup mock to return empty list
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
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



    /**
     * USE CASE: 13 Produce a Report on Top N Cities in a Continent
     * Method under test:
     * {@link CityReportService#getTopCitiesByContinent(String, int)
     */
    @Test
    void testGetTopCitiesByContinent_ReturnsExpectedCities() throws Exception {

        // Arrange
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("ID")).thenReturn(1, 2);
        when(mockResultSet.getString("CityName")).thenReturn("Tokyo", "Shanghai");
        when(mockResultSet.getString("CountryName")).thenReturn("Japan", "China");
        when(mockResultSet.getString("Continent")).thenReturn("Asia", "Asia");
        when(mockResultSet.getString("District")).thenReturn("Tokyo", "Shanghai");
        when(mockResultSet.getInt("Population")).thenReturn(37435191, 25582000);

        // Act
        List<CityPojo> result = cityReportService.getTopCitiesByContinent("Asia", DEFAULT_N);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Tokyo", result.get(0).getName());
        assertEquals("Shanghai", result.get(1).getName());

        verify(mockPreparedStatement, times(1)).setString(1, "Asia");
        verify(mockPreparedStatement, times(1)).setInt(2, DEFAULT_N);
        verify(mockPreparedStatement, times(1)).executeQuery();
    }

    /**
     * USE CASE: 13 Produce a Report on Top N Cities in a Continent
     * Method under test:
     * {@link CityReportService#getTopCitiesByContinent(String, int)
     */
    @Test
    void testGetTopCitiesByContinent_EmptyResultSet() throws Exception {

        when(mockResultSet.next()).thenReturn(false);

        List<CityPojo> result = cityReportService.getTopCitiesByContinent("Europe", 5);
        assertTrue(result.isEmpty());
    }


    /**
     * USE CASE: 14 Produce a Report on Top N Cities in a Region
     * Tests that getTopCitiesByRegion returns a list of cities
     * when valid data is available in the ResultSet.

     * {@link CityReportService#getTopCitiesByRegion(String, int)
     */
    @Test
    void testGetTopCitiesByRegion_ReturnsExpectedCities() throws Exception {
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("ID")).thenReturn(1, 2);
        when(mockResultSet.getString("CityName")).thenReturn("Lagos", "Kano");
        when(mockResultSet.getString("CountryName")).thenReturn("Nigeria", "Nigeria");
        when(mockResultSet.getString("Region")).thenReturn("Western Africa", "Western Africa");
        when(mockResultSet.getInt("Population")).thenReturn(15000000, 4000000);

        List<CityPojo> result = cityReportService.getTopCitiesByRegion("Western Africa", DEFAULT_N);

        assertEquals(2, result.size());
        assertEquals("Lagos", result.get(0).getName());
        assertEquals("Kano", result.get(1).getName());
        assertEquals("Western Africa", result.get(0).getRegion());
        assertTrue(result.get(0).getPopulation() > result.get(1).getPopulation());

        verify(mockPreparedStatement).setString(1, "Western Africa");
        verify(mockPreparedStatement).setInt(2, DEFAULT_N);
        verify(mockPreparedStatement).executeQuery();
    }

    /**
     * USE CASE: 14 Produce a Report on Top N Cities in a Region

     * Tests that getTopCitiesByRegion returns an empty list
     * when no cities are found for the given region.
     * {@link CityReportService#getTopCitiesByRegion(String, int)
     */
    @Test
    void testGetTopCitiesByRegion_EmptyResultSet() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        List<CityPojo> result = cityReportService.getTopCitiesByRegion("Central Europe", 3);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * USE CASE: 14 Produce a Report on Top N Cities in a Region

     * Tests that getTopCitiesByRegion handles SQL exceptions gracefully
     * and returns an empty list instead of crashing.

     * {@link CityReportService#getTopCitiesByRegion(String, int)
     */
    @Test
    void testGetTopCitiesByRegion_SQLExceptionHandledGracefully() throws Exception {
        when(mockConnection.prepareStatement(any(String.class))).thenThrow(new SQLException("DB failure"));

        List<CityPojo> result = cityReportService.getTopCitiesByRegion("Asia", 5);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * USE CASE: 15 Produce a Report on Top N Cities in a Country
     * Tests that {@link CityReportService#getTopCitiesByCountry(String, int)}
     * returns a list of cities when valid results exist in the ResultSet.
     */
    @Test
    void testGetTopCitiesByCountry_ReturnsExpectedCities() throws Exception {
        // Arrange
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("ID")).thenReturn(1, 2);
        when(mockResultSet.getString("CityName")).thenReturn("Lagos", "Abuja");
        when(mockResultSet.getString("CountryCode")).thenReturn("NG", "NG");
        when(mockResultSet.getString("District")).thenReturn("Lagos", "Abuja");
        when(mockResultSet.getInt("Population")).thenReturn(15000000, 3500000);

        // Act
        List<City> result = cityReportService.getTopCitiesByCountry("Nigeria", 2);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Lagos", result.get(0).getName());
        assertEquals("Abuja", result.get(1).getName());
        assertEquals("NG", result.get(0).getCountryCode());
        assertTrue(result.get(0).getPopulation() > result.get(1).getPopulation());

        verify(mockPreparedStatement).setString(1, "Nigeria");
        verify(mockPreparedStatement).setInt(2, 2);
        verify(mockPreparedStatement).executeQuery();
    }

    /**
     * USE CASE: 15 Produce a Report on Top N Cities in a Country
     * Tests that {@link CityReportService#getTopCitiesByCountry(String, int)}
     * returns an empty list when no cities are found for the given country.
     */
    @Test
    void testGetTopCitiesByCountry_EmptyResultSet() throws Exception {
        // Arrange
        when(mockResultSet.next()).thenReturn(false);

        // Act
        List<City> result = cityReportService.getTopCitiesByCountry("Atlantis", 5);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * USE CASE: 15 Produce a Report on Top N Cities in a Country
     * Tests that {@link CityReportService#getTopCitiesByCountry(String, int)}
     * handles SQL exceptions gracefully by printing the error
     * and returning an empty list.
     */
    @Test
    void testGetTopCitiesByCountry_SQLExceptionHandledGracefully() throws Exception {
        // Arrange
        when(mockConnection.prepareStatement(any(String.class))).thenThrow(new SQLException("Database error"));

        // Act
        List<City> result = cityReportService.getTopCitiesByCountry("Nigeria", 3);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }


    /**
     * USE CASE: 16 Produce a Report on Top N Cities in a District
     * Tests that {@link CityReportService#getTopCitiesByDistrict(String, int)}
     * returns a list of cities when valid results exist in the ResultSet.
     */
    @Test
    void testGetTopCitiesByDistrict_ReturnsExpectedCities() throws Exception {
        // Arrange
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("ID")).thenReturn(1, 2);
        when(mockResultSet.getString("CityName")).thenReturn("Ikeja", "Epe");
        when(mockResultSet.getString("CountryCode")).thenReturn("NG", "NG");
        when(mockResultSet.getString("District")).thenReturn("Lagos", "Lagos");
        when(mockResultSet.getInt("Population")).thenReturn(500000, 150000);

        // Act
        List<City> result = cityReportService.getTopCitiesByDistrict("Lagos", DEFAULT_N);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Ikeja", result.get(0).getName());
        assertEquals("Epe", result.get(1).getName());
        assertEquals("NG", result.get(0).getCountryCode());
        assertEquals("Lagos", result.get(0).getDistrict());
        assertTrue(result.get(0).getPopulation() > result.get(1).getPopulation());

        verify(mockPreparedStatement).setString(1, "Lagos");
        verify(mockPreparedStatement).setInt(2, DEFAULT_N);
        verify(mockPreparedStatement).executeQuery();
    }

    /**
     * USE CASE: 16 Produce a Report on Top N Cities in a District
     * Tests that {@link CityReportService#getTopCitiesByDistrict(String, int)}
     * returns an empty list when no cities are found for the given district.
     */
    @Test
    void testGetTopCitiesByDistrict_EmptyResultSet() throws Exception {
        // Arrange
        when(mockResultSet.next()).thenReturn(false);

        // Act
        List<City> result = cityReportService.getTopCitiesByDistrict("UnknownDistrict", 5);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * USE CASE: 16 Produce a Report on Top N Cities in a District
     * Tests that {@link CityReportService#getTopCitiesByDistrict(String, int)}
     * handles SQL exceptions gracefully and returns an empty list.
     */
    @Test
    void testGetTopCitiesByDistrict_SQLExceptionHandledGracefully() throws Exception {
        // Arrange
        when(mockConnection.prepareStatement(any(String.class))).thenThrow(new SQLException("DB Error"));

        // Act
        List<City> result = cityReportService.getTopCitiesByDistrict("Lagos", 3);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * USE CASE: 17 Produce a Report on All Capital Cities in the World by Population

     * Tests that {@link CityReportService#getAllCapitalCitiesByPopulation()}
     * returns a list of capital cities ordered by population when data exists.
     */
    @Test
    void testGetAllCapitalCitiesByPopulation_ReturnsExpectedCities() throws Exception {

        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(any(String.class))).thenReturn(mockResultSet);


        // Arrange
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("ID")).thenReturn(1, 2);
        when(mockResultSet.getString("CityName")).thenReturn("Tokyo", "London");
        when(mockResultSet.getString("CountryName")).thenReturn("Japan", "United Kingdom");
        when(mockResultSet.getString("District")).thenReturn("Tokyo-to", "England");
        when(mockResultSet.getInt("Population")).thenReturn(13960000, 8900000);

        // Act
        List<CityPojo> result = cityReportService.getAllCapitalCitiesByPopulation();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Tokyo", result.get(0).getName());
        assertEquals("London", result.get(1).getName());
        assertTrue(result.get(0).getPopulation() > result.get(1).getPopulation());
        verify(mockStatement).executeQuery(any(String.class));
    }

    /**
     * USE CASE: 17 Produce a Report on All Capital Cities in the World by Population

     * Tests that {@link CityReportService#getAllCapitalCitiesByPopulation()}
     * returns an empty list when no capital cities are found.
     */
    @Test
    void testGetAllCapitalCitiesByPopulation_EmptyResultSet() throws Exception {
        // Arrange
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(any(String.class))).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        // Act
        List<CityPojo> result = cityReportService.getAllCapitalCitiesByPopulation();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * USE CASE: 17 Produce a Report on All Capital Cities in the World by Population

     * Tests that {@link CityReportService#getAllCapitalCitiesByPopulation()}
     * handles SQL exceptions gracefully and returns an empty list.
     */
    @Test
    void testGetAllCapitalCitiesByPopulation_SQLExceptionHandledGracefully() throws Exception {
        // Arrange
        when(mockConnection.createStatement()).thenThrow(new SQLException("Database failure"));

        // Act
        List<CityPojo> result = cityReportService.getAllCapitalCitiesByPopulation();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}