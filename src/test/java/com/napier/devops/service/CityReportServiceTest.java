package com.napier.devops.service;

import com.napier.devops.City;
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
     * Test getAllCitiesInRegionByPopulationLargestToSmallest with mock data.
     * USE CASE 9 Test
     */
    @Test
    void testGetAllCitiesInRegionByPopulationLargestToSmallest() throws SQLException {
        // Setup mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        // Mock result set to return three cities in Western Europe in descending population order
        when(mockResultSet.next()).thenReturn(true, true, true, false);

        when(mockResultSet.getInt("ID")).thenReturn(3068, 2974, 2515);
        when(mockResultSet.getString("Name")).thenReturn("Berlin", "Paris", "Madrid");
        when(mockResultSet.getString("CountryCode")).thenReturn("DEU", "FRA", "ESP");
        when(mockResultSet.getString("District")).thenReturn("Berlin", "Île-de-France", "Madrid");
        when(mockResultSet.getInt("Population")).thenReturn(3386667, 2125246, 2879052);

        // Call the method
        List<City> cities = cityReportService.getAllCitiesInRegionByPopulationLargestToSmallest("Western Europe");

        // Verify results
        assertNotNull(cities);
        assertEquals(3, cities.size());

        // Verify first city
        City firstCity = cities.get(0);
        assertEquals(3068, firstCity.getId());
        assertEquals("Berlin", firstCity.getName());
        assertEquals("DEU", firstCity.getCountryCode());
        assertEquals("Berlin", firstCity.getDistrict());
        assertEquals(3386667, firstCity.getPopulation());

        // Verify that the prepared statement was called with the correct region
        verify(mockPreparedStatement).setString(1, "Western Europe");
    }

    /**
     * Test getAllCitiesInCountryByPopulationLargestToSmallest with mock data.
     * USE CASE 10 Test
     */
    @Test
    void testGetAllCitiesInCountryByPopulationLargestToSmallest() throws SQLException {
        // Setup mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        // Mock result set to return three cities in USA in descending population order
        when(mockResultSet.next()).thenReturn(true, true, true, false);

        when(mockResultSet.getInt("ID")).thenReturn(3793, 3794, 3795);
        when(mockResultSet.getString("Name")).thenReturn("New York", "Los Angeles", "Chicago");
        when(mockResultSet.getString("CountryCode")).thenReturn("USA", "USA", "USA");
        when(mockResultSet.getString("District")).thenReturn("New York", "California", "Illinois");
        when(mockResultSet.getInt("Population")).thenReturn(8008278, 3694820, 2896016);

        // Call the method
        List<City> cities = cityReportService.getAllCitiesInCountryByPopulationLargestToSmallest("USA");

        // Verify results
        assertNotNull(cities);
        assertEquals(3, cities.size());

        // Verify first city
        City firstCity = cities.get(0);
        assertEquals(3793, firstCity.getId());
        assertEquals("New York", firstCity.getName());
        assertEquals("USA", firstCity.getCountryCode());
        assertEquals("New York", firstCity.getDistrict());
        assertEquals(8008278, firstCity.getPopulation());

        // Verify that the prepared statement was called with the correct country code
        verify(mockPreparedStatement).setString(1, "USA");
    }

    /**
     * Test getAllCitiesInDistrictByPopulationLargestToSmallest with mock data.
     * USE CASE 11 Test
     */
    @Test
    void testGetAllCitiesInDistrictByPopulationLargestToSmallest() throws SQLException {
        // Setup mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        // Mock result set to return two cities in California in descending population order
        when(mockResultSet.next()).thenReturn(true, true, false);

        when(mockResultSet.getInt("ID")).thenReturn(3794, 3796);
        when(mockResultSet.getString("Name")).thenReturn("Los Angeles", "San Diego");
        when(mockResultSet.getString("CountryCode")).thenReturn("USA", "USA");
        when(mockResultSet.getString("District")).thenReturn("California", "California");
        when(mockResultSet.getInt("Population")).thenReturn(3694820, 1223400);

        // Call the method
        List<City> cities = cityReportService.getAllCitiesInDistrictByPopulationLargestToSmallest("California");

        // Verify results
        assertNotNull(cities);
        assertEquals(2, cities.size());

        // Verify first city
        City firstCity = cities.get(0);
        assertEquals(3794, firstCity.getId());
        assertEquals("Los Angeles", firstCity.getName());
        assertEquals("California", firstCity.getDistrict());
        assertEquals(3694820, firstCity.getPopulation());

        // Verify that the prepared statement was called with the correct district
        verify(mockPreparedStatement).setString(1, "California");
    }

    /**
     * Test getTopNCitiesByPopulationLargestToSmallest with mock data.
     * USE CASE 12 Test
     */
    @Test
    void testGetTopNCitiesByPopulationLargestToSmallest() throws SQLException {
        // Setup mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        // Mock result set to return top 3 cities in descending population order
        when(mockResultSet.next()).thenReturn(true, true, true, false);

        when(mockResultSet.getInt("ID")).thenReturn(1024, 2331, 206);
        when(mockResultSet.getString("Name")).thenReturn("Mumbai", "Seoul", "São Paulo");
        when(mockResultSet.getString("CountryCode")).thenReturn("IND", "KOR", "BRA");
        when(mockResultSet.getString("District")).thenReturn("Maharashtra", "Seoul", "São Paulo");
        when(mockResultSet.getInt("Population")).thenReturn(10500000, 9981619, 9968485);

        // Call the method
        List<City> cities = cityReportService.getTopNCitiesByPopulationLargestToSmallest(3);

        // Verify results
        assertNotNull(cities);
        assertEquals(3, cities.size());

        // Verify first city
        City firstCity = cities.get(0);
        assertEquals(1024, firstCity.getId());
        assertEquals("Mumbai", firstCity.getName());
        assertEquals("IND", firstCity.getCountryCode());
        assertEquals(10500000, firstCity.getPopulation());

        // Verify that the prepared statement was called with the correct limit
        verify(mockPreparedStatement).setInt(1, 3);
    }

    /**
     * Test methods with null/empty parameters.
     */
    @Test
    void testMethodsWithInvalidParameters() {
        // Test USE CASE 8 with null continent
        List<City> cities = cityReportService.getAllCitiesInContinentByPopulationLargestToSmallest(null);
        assertNotNull(cities);
        assertTrue(cities.isEmpty());

        // Test USE CASE 9 with empty region
        cities = cityReportService.getAllCitiesInRegionByPopulationLargestToSmallest("");
        assertNotNull(cities);
        assertTrue(cities.isEmpty());

        // Test USE CASE 10 with null country code
        cities = cityReportService.getAllCitiesInCountryByPopulationLargestToSmallest(null);
        assertNotNull(cities);
        assertTrue(cities.isEmpty());

        // Test USE CASE 11 with empty district
        cities = cityReportService.getAllCitiesInDistrictByPopulationLargestToSmallest("");
        assertNotNull(cities);
        assertTrue(cities.isEmpty());

        // Test USE CASE 12 with invalid N
        cities = cityReportService.getTopNCitiesByPopulationLargestToSmallest(0);
        assertNotNull(cities);
        assertTrue(cities.isEmpty());

        cities = cityReportService.getTopNCitiesByPopulationLargestToSmallest(-1);
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    /**
     * Test print methods with null parameters.
     */
    @Test
    void testPrintMethodsWithNullParameters() {
        // These should not throw exceptions and should handle null gracefully
        assertDoesNotThrow(() -> {
            cityReportService.printAllCitiesInContinentByPopulationLargestToSmallest(null);
        });

        assertDoesNotThrow(() -> {
            cityReportService.printAllCitiesInRegionByPopulationLargestToSmallest(null);
        });

        assertDoesNotThrow(() -> {
            cityReportService.printAllCitiesInCountryByPopulationLargestToSmallest(null);
        });

        assertDoesNotThrow(() -> {
            cityReportService.printAllCitiesInDistrictByPopulationLargestToSmallest(null);
        });

        assertDoesNotThrow(() -> {
            cityReportService.printTopNCitiesByPopulationLargestToSmallest(0);
        });
    }

    /**
     * Test methods with SQL exceptions.
     */
    @Test
    void testMethodsWithSQLException() throws SQLException {
        // Setup mock to throw SQLException
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // All methods should return empty lists when there's an exception
        List<City> cities = cityReportService.getAllCitiesByPopulationLargestToSmallest();
        assertNotNull(cities);
        assertTrue(cities.isEmpty());

        cities = cityReportService.getAllCitiesInContinentByPopulationLargestToSmallest("Asia");
        assertNotNull(cities);
        assertTrue(cities.isEmpty());

        cities = cityReportService.getAllCitiesInRegionByPopulationLargestToSmallest("Western Europe");
        assertNotNull(cities);
        assertTrue(cities.isEmpty());

        cities = cityReportService.getAllCitiesInCountryByPopulationLargestToSmallest("USA");
        assertNotNull(cities);
        assertTrue(cities.isEmpty());

        cities = cityReportService.getAllCitiesInDistrictByPopulationLargestToSmallest("California");
        assertNotNull(cities);
        assertTrue(cities.isEmpty());

        cities = cityReportService.getTopNCitiesByPopulationLargestToSmallest(10);
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
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
        when(mockResultSet.getString("CountryCode")).thenReturn("JPN", "CHN");
        when(mockResultSet.getString("Continent")).thenReturn("Asia", "Asia");
        when(mockResultSet.getString("District")).thenReturn("Tokyo", "Shanghai");
        when(mockResultSet.getInt("Population")).thenReturn(37435191, 25582000);

        // Act
        List<City> result = cityReportService.getTopCitiesByContinent("Asia", DEFAULT_N);

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

        List<City> result = cityReportService.getTopCitiesByContinent("Europe", 5);
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

        List<City> result = cityReportService.getTopCitiesByRegion("Western Africa", DEFAULT_N);

        assertEquals(2, result.size());
        assertEquals("Lagos", result.get(0).getName());
        assertEquals("Kano", result.get(1).getName());
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

        List<City> result = cityReportService.getTopCitiesByRegion("Central Europe", 3);

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

        List<City> result = cityReportService.getTopCitiesByRegion("Asia", 5);

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
        when(mockResultSet.getString("CountryCode")).thenReturn("JPN", "UK");
        when(mockResultSet.getString("District")).thenReturn("Tokyo-to", "England");
        when(mockResultSet.getInt("Population")).thenReturn(13960000, 8900000);

        // Act
        List<City> result = cityReportService.getAllCapitalCitiesByPopulation();

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
        List<City> result = cityReportService.getAllCapitalCitiesByPopulation();

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
        List<City> result = cityReportService.getAllCapitalCitiesByPopulation();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}