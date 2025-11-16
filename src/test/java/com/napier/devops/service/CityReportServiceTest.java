package com.napier.devops.service;

import com.napier.devops.City;
import com.napier.pojo.PopulationReportPojo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.*;
import java.util.List;

import static com.napier.constant.Constant.*;
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
    private CityReportService spyService;

    @Mock
    private Statement mockStatement;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        cityReportService = new CityReportService(mockConnection);

        // Setup mock to return empty list
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(any(String.class))).thenReturn(mockResultSet);

        spyService = Mockito.spy(cityReportService);
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
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
     * Test getAllCitiesByPopulationLargestToSmallest for empty result set.
     */
    @Test
    void testGetAllCitiesByPopulationLargestToSmallest_EmptyResultSet() throws SQLException {
        // Setup mock behavior for empty result set
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(false);

        // Call the method
        List<City> cities = cityReportService.getAllCitiesByPopulationLargestToSmallest();

        // Verify
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    /**
     * Test getAllCitiesByPopulationLargestToSmallest handles SQL exceptions gracefully.
     */
    @Test
    void testGetAllCitiesByPopulationLargestToSmallest_SQLExceptionHandledGracefully() throws SQLException {
        // Setup mock to throw SQL exception
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Call the method
        List<City> cities = cityReportService.getAllCitiesByPopulationLargestToSmallest();

        // Verify
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
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
     * Method under test:
     * {@link CityReportService#printTopCitiesByContinent(String, int)
     */
    @Test
    public void testPrintTopCitiesByContinent_ValidList() {
        City city1 = new City();
        city1.setName("Tokyo");
        city1.setPopulation(37400068);

        City city2 = new City();
        city2.setName("Delhi");
        city2.setPopulation(29399141);

        doReturn(List.of(city1, city2)).when(spyService).getTopCitiesByContinent("Asia", 2);

        spyService.printTopCitiesByContinent("Asia", 2);

        String output = outContent.toString();
        assertTrue(output.contains("Report: Top 2 Cities in Asia by Population"));
        assertTrue(output.contains("=".repeat(100)));
        assertTrue(output.contains("Tokyo"));
        assertTrue(output.contains("Delhi"));
    }

    /**
     * USE CASE: 14 Produce a Report on Top N Cities in a Region
     * Tests that getTopCitiesByRegion returns a list of cities
     * when valid data is available in the ResultSet.
     * <p>
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
     * Tests that getTopCitiesByRegion returns a list of cities
     * when valid data is available in the ResultSet.
     * <p>
     * {@link CityReportService#getTopCitiesByRegion(String, int)
     */
    @Test
    void testGetTopCitiesByRegion_EmptyResult() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        List<City> result = cityReportService.getTopCitiesByRegion("Western Europe", 3);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * USE CASE: 14 Produce a Report on Top N Cities in a Region
     * <p>
     * Tests that getTopCitiesByRegion handles SQL exceptions gracefully
     * and returns an empty list instead of crashing.
     * <p>
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
     * Tests that {@link CityReportService#printTopCitiesByRegion(String, int)}
     */
    @Test
    void testPrintTopCitiesByRegion_ValidList() {
        City city1 = new City();
        city1.setName("Lagos");
        city1.setPopulation(21000000);

        City city2 = new City();
        city2.setName("Kinshasa");
        city2.setPopulation(15000000);

        doReturn(List.of(city1, city2)).when(spyService).getTopCitiesByRegion("Western Africa", 2);

        spyService.printTopCitiesByRegion("Western Africa", 2);

        String output = outContent.toString();
        assertTrue(output.contains("Report: Top 2 Cities in Western Africa by Population"));
        assertTrue(output.contains("=".repeat(100)));
        assertTrue(output.contains("Lagos"));
        assertTrue(output.contains("Kinshasa"));
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
     * USE CASE: 15 Produce a Report on Top N Cities in a Country
     * Tests that {@link CityReportService#getTopCitiesByCountry(String, int)}
     * SQLException after Statement Preparation
     */
    @Test
    void testGetTopCitiesByContinent_SQLExceptionOnExecute() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenThrow(new SQLException("Execute failed"));

        List<City> result = cityReportService.getTopCitiesByContinent("Asia", 10);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Tests that {@link CityReportService#printTopCitiesByCountry(String, int)}
     */
    @Test
    void testPrintTopCitiesByCountry_ValidList() {
        City city1 = new City();
        city1.setName("New York");
        city1.setPopulation(8400000);

        City city2 = new City();
        city2.setName("Los Angeles");
        city2.setPopulation(4000000);

        doReturn(List.of(city1, city2)).when(spyService).getTopCitiesByCountry("USA", 2);

        spyService.printTopCitiesByCountry("USA", 2);

        String output = outContent.toString();
        assertTrue(output.contains("Report: Top 2 Cities in USA by Population"));
        assertTrue(output.contains("=".repeat(100)));
        assertTrue(output.contains("New York"));
        assertTrue(output.contains("Los Angeles"));
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
     * USE CASE: 16 Produce a Report on Top N Cities in a District
     * Tests that {@link CityReportService#getTopCitiesByDistrict(String, int)}
     *
     */
    @Test
    void testGetTopCitiesByDistrict_NullDistrict() {
        List<City> result = cityReportService.getTopCitiesByDistrict(null, 10);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Test when valid data is returned and printed successfully.
     * Method under test:
     * {@link CityReportService#printTopCitiesByDistrict(String, int)
     */
    @Test
    void testPrintTopCitiesByDistrict_ValidList() {
        City city1 = new City();
        city1.setName("San Francisco");
        city1.setPopulation(870000);

        City city2 = new City();
        city2.setName("Los Angeles");
        city2.setPopulation(4000000);

        doReturn(List.of(city1, city2)).when(spyService).getTopCitiesByDistrict("California", 2);

        spyService.printTopCitiesByDistrict("California", 2);

        String output = outContent.toString();
        assertTrue(output.contains("Report: Top 2 Cities in California by Population"));
        assertTrue(output.contains("=".repeat(100)));
        assertTrue(output.contains("San Francisco"));
        assertTrue(output.contains("Los Angeles"));
    }

    /**
     * USE CASE: 17 Produce a Report on All Capital Cities in the World by Population
     * <p>
     * Tests that {@link CityReportService#getAllCapitalCitiesByPopulation()}
     * returns a list of capital cities ordered by population when data exists.
     */
    @Test
    void testGetAllCapitalCitiesByPopulation_ReturnsExpectedCities() throws Exception {

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
     * <p>
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
     * <p>
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

    /**
     * USE CASE: 17 Produce a Report on All Capital Cities in the World by Population
     * <p>
     * Tests that {@link CityReportService#getAllCapitalCitiesByPopulation()}
     * All Capital Cities by Population
     */
    @Test
    void testGetAllCapitalCitiesByPopulation_Success() throws SQLException {
        Statement mockStatement = mock(Statement.class);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("ID")).thenReturn(101);
        when(mockResultSet.getString("CityName")).thenReturn("Tokyo");
        when(mockResultSet.getString("District")).thenReturn("Tokyo Metropolis");
        when(mockResultSet.getString("CountryCode")).thenReturn("JPN");
        when(mockResultSet.getInt("Population")).thenReturn(37400068);

        List<City> result = cityReportService.getAllCapitalCitiesByPopulation();

        assertEquals(1, result.size());
        assertEquals("Tokyo", result.get(0).getName());
        assertEquals("JPN", result.get(0).getCountryCode());
        assertEquals(37400068, result.get(0).getPopulation());
    }


    /**
     * USE CASE: 17 Produce a Report on All Capital Cities in the World by Population
     * <p>
     * Tests that {@link CityReportService#getAllCapitalCitiesByPopulation()}
     * Negative N for Top Cities by Country
     */
    @Test
    void testGetTopCitiesByCountry_InvalidN() {
        List<City> result = cityReportService.getTopCitiesByCountry("France", -3);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * USE CASE: 17 Produce a Report on All Capital Cities in the World by Population
     * <p>
     * Tests that {@link CityReportService#getAllCapitalCitiesByPopulation()}
     */
    @Test
    void testGetAllCapitalCitiesByPopulation_SQLExceptionOnExecute() throws SQLException {
        Statement mockStatement = mock(Statement.class);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenThrow(new SQLException("Query failed"));

        List<City> result = cityReportService.getAllCapitalCitiesByPopulation();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Test when valid data is returned.
     * Method under test:
     * {@link CityReportService#printAllCapitalCitiesByPopulation()
     */
    @Test
    void testPrintAllCapitalCitiesByPopulation_ValidList() {
        City city1 = new City();
        city1.setName("Tokyo");
        city1.setPopulation(37400068);
        city1.setCountryCode("JPN");

        City city2 = new City();
        city2.setName("Delhi");
        city2.setPopulation(28514000);
        city2.setCountryCode("IND");

        doReturn(List.of(city1, city2)).when(spyService).getAllCapitalCitiesByPopulation();

        spyService.printAllCapitalCitiesByPopulation();

        String output = outContent.toString();
        assertTrue(output.contains("Report: All Capital Cities in the World by Population"));
        assertTrue(output.contains("Total capitals found: 2"));
        assertTrue(output.contains("=".repeat(100)));
        assertTrue(output.contains("Tokyo"));
        assertTrue(output.contains("Delhi"));

        verify(spyService, times(1)).getAllCapitalCitiesByPopulation();
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

        // USE CASE 13: Top N Cities in a Continent
        cities = cityReportService.getTopCitiesByContinent(null, 10);
        assertNotNull(cities);
        assertTrue(cities.isEmpty());

        cities = cityReportService.getTopCitiesByContinent("", 10);
        assertNotNull(cities);
        assertTrue(cities.isEmpty());

        // USE CASE 14: Top N Cities in a Region
        cities = cityReportService.getTopCitiesByRegion(null, 10);
        assertNotNull(cities);
        assertTrue(cities.isEmpty());

        cities = cityReportService.getTopCitiesByRegion("", 10);
        assertNotNull(cities);
        assertTrue(cities.isEmpty());

        // USE CASE 15: Top N Cities in a Country
        cities = cityReportService.getTopCitiesByCountry(null, 10);
        assertNotNull(cities);
        assertTrue(cities.isEmpty());

        cities = cityReportService.getTopCitiesByCountry("", 10);
        assertNotNull(cities);
        assertTrue(cities.isEmpty());

        // USE CASE 16: Top N Cities in a District
        cities = cityReportService.getTopCitiesByDistrict(null, 10);
        assertNotNull(cities);
        assertTrue(cities.isEmpty());

        cities = cityReportService.getTopCitiesByDistrict("", 10);
        assertNotNull(cities);
        assertTrue(cities.isEmpty());

        // USE CASE 17: All Capital Cities by Population (no parameters, so test DB state)
        List<City> capitals = cityReportService.getAllCapitalCitiesByPopulation();
        assertNotNull(capitals);
        assertTrue(capitals.isEmpty());
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

        assertDoesNotThrow(() -> cityReportService.printTopCitiesByContinent(null, 0));

        assertDoesNotThrow(() -> cityReportService.printTopCitiesByRegion(null, 0));

        assertDoesNotThrow(() -> cityReportService.getTopCitiesByCountry(null, 0));

        assertDoesNotThrow(() -> cityReportService.printTopCitiesByDistrict(null, 0));

        assertDoesNotThrow(() -> cityReportService.printAllCapitalCitiesByPopulation());

        assertDoesNotThrow(() -> cityReportService.printCityPopulationReport(null));

        assertDoesNotThrow(() -> cityReportService.printDistrictPopulationReport(null));
    }

    /**
     * Test methods with SQL exceptions.
     */
    @Test
    void testMethodsWithSQLException() throws SQLException {
        // Setup mock to throw SQLException
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));
        when(mockConnection.createStatement()).thenThrow(new SQLException("Simulated statement failure"));

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

        // USE CASE 13: Top N Cities in a Continent
        cities = cityReportService.getTopCitiesByContinent("Asia", 10);
        assertNotNull(cities);
        assertTrue(cities.isEmpty());

        // USE CASE 14: Top N Cities in a Region
        cities = cityReportService.getTopCitiesByRegion("Western Europe", 10);
        assertNotNull(cities);
        assertTrue(cities.isEmpty());

        // USE CASE 15: Top N Cities in a Country
        cities = cityReportService.getTopCitiesByCountry("United States", 10);
        assertNotNull(cities);
        assertTrue(cities.isEmpty());

        // USE CASE 16: Top N Cities in a District
        cities = cityReportService.getTopCitiesByDistrict("California", 10);
        assertNotNull(cities);
        assertTrue(cities.isEmpty());

        // USE CASE 17: All Capital Cities by Population
        cities = cityReportService.getAllCapitalCitiesByPopulation();
        assertNotNull(cities);
        assertTrue(cities.isEmpty());

        // USE CASE: Top N Capital Cities by Population
        cities = cityReportService.getTopCapitalCitiesByPopulation(5);
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    /**
     * USE CASE: Top N Capital Cities by Population
     */
    @Test
    void testGetTopCapitalCitiesByPopulation_ReturnsExpectedCities() throws SQLException {
        // Arrange
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("ID")).thenReturn(1, 2);
        when(mockResultSet.getString("CityName")).thenReturn("Tokyo", "London");
        when(mockResultSet.getString("District")).thenReturn("Tokyo-to", "England");
        when(mockResultSet.getString("CountryCode")).thenReturn("JPN", "UK");
        when(mockResultSet.getInt("Population")).thenReturn(37400000, 8900000);

        // Act
        List<City> result = cityReportService.getTopCapitalCitiesByPopulation(2);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Tokyo", result.get(0).getName());
        assertEquals("London", result.get(1).getName());
        verify(mockPreparedStatement).setInt(1, 2);
    }

    @Test
    void testGetTopCapitalCitiesByPopulation_EmptyResultSet() throws SQLException {
        // Arrange
        when(mockResultSet.next()).thenReturn(false);

        // Act
        List<City> result = cityReportService.getTopCapitalCitiesByPopulation(3);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetTopCapitalCitiesByPopulation_InvalidN() {
        // Act
        List<City> result = cityReportService.getTopCapitalCitiesByPopulation(0);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetTopCapitalCitiesByPopulation_SQLExceptionHandledGracefully() throws SQLException {
        // Arrange
        when(mockConnection.prepareStatement(any(String.class))).thenThrow(new SQLException("Database error"));

        // Act
        List<City> result = cityReportService.getTopCapitalCitiesByPopulation(5);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testPrintTopCapitalCitiesByPopulation_ValidList() {
        // Arrange
        City city1 = new City();
        city1.setName("Tokyo");
        city1.setPopulation(37400068);
        city1.setCountryCode("JPN");

        City city2 = new City();
        city2.setName("London");
        city2.setPopulation(8900000);
        city2.setCountryCode("UK");

        doReturn(List.of(city1, city2)).when(spyService).getTopCapitalCitiesByPopulation(2);

        // Act
        spyService.printTopCapitalCitiesByPopulation(2);

        // Assert
        String output = outContent.toString();
        assertTrue(output.contains("Report: Top 2 Capital Cities in the World by Population"));
        assertTrue(output.contains("Tokyo"));
        assertTrue(output.contains("London"));
    }

    @Test
    public void testPrintTopCapitalCitiesByPopulation_EmptyList() {
        // Arrange
        doReturn(List.of()).when(spyService).getTopCapitalCitiesByPopulation(3);

        // Act
        spyService.printTopCapitalCitiesByPopulation(3);

        // Assert
        String output = outContent.toString();
        assertFalse(output.contains("ERROR"));
    }

    @Test
    public void testPrintTopCapitalCitiesByPopulation_InvalidN() {
        // Act
        spyService.printTopCapitalCitiesByPopulation(0);

        // Assert
        String output = outContent.toString();
        assertFalse(output.contains("ERROR"));
    }


    /**
     * Unit test for USE CASE 20: getTopCapitalCitiesByPopulation
     * Tests with single capital city result
     */
    @Test
    void testGetTopCapitalCitiesByPopulation_SingleResult() throws SQLException {
        // Prepare test data
        int n = 1;

        // Mock the PreparedStatement and ResultSet
        PreparedStatement mockPstmt = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPstmt);
        when(mockPstmt.executeQuery()).thenReturn(mockRs);

        // Mock ResultSet behavior - simulate 1 capital city
        when(mockRs.next())
                .thenReturn(true)
                .thenReturn(false);

        // Mock data for capital city
        when(mockRs.getInt("ID")).thenReturn(1);
        when(mockRs.getString("CityName")).thenReturn("Tokyo");
        when(mockRs.getString("District")).thenReturn("Tokyo-to");
        when(mockRs.getString("CountryCode")).thenReturn("JPN");
        when(mockRs.getInt("Population")).thenReturn(37400000);

        // Execute the method
        List<City> result = cityReportService.getTopCapitalCitiesByPopulation(n);

        // Verify the results
        assertNotNull(result, "Result should not be null");
        assertEquals(1, result.size(), "Should return exactly 1 city");

        // Verify city details
        City city = result.get(0);
        assertEquals(1, city.getId(), "City ID should be 1");
        assertEquals("Tokyo", city.getName(), "City name should be Tokyo");
        assertEquals("Tokyo-to", city.getDistrict(), "City district should be Tokyo-to");
        assertEquals("JPN", city.getCountryCode(), "City country code should be JPN");
        assertEquals(37400000, city.getPopulation(), "City population should be 37400000");

        // Verify PreparedStatement parameters
        verify(mockPstmt).setInt(1, n);
        verify(mockPstmt).executeQuery();
    }

    /**
     * Unit test for USE CASE 20: getTopCapitalCitiesByPopulation
     * Tests with large N value
     */
    @Test
    void testGetTopCapitalCitiesByPopulation_LargeN() throws SQLException {
        // Prepare test data
        int n = 1000;

        // Mock the PreparedStatement and ResultSet
        PreparedStatement mockPstmt = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPstmt);
        when(mockPstmt.executeQuery()).thenReturn(mockRs);

        // Mock ResultSet behavior - simulate only 3 capital cities (less than requested)
        when(mockRs.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        when(mockRs.getInt("ID")).thenReturn(1, 2, 3);
        when(mockRs.getString("CityName")).thenReturn("Tokyo", "Delhi", "Beijing");
        when(mockRs.getString("District")).thenReturn("Tokyo-to", "Delhi", "Peking");
        when(mockRs.getString("CountryCode")).thenReturn("JPN", "IND", "CHN");
        when(mockRs.getInt("Population")).thenReturn(37400000, 28514000, 21540000);

        // Execute the method
        List<City> result = cityReportService.getTopCapitalCitiesByPopulation(n);

        // Verify the results - should return only available cities, not N
        assertNotNull(result, "Result should not be null");
        assertEquals(3, result.size(), "Should return exactly 3 cities (available capitals)");

        // Verify PreparedStatement parameters
        verify(mockPstmt).setInt(1, n);
        verify(mockPstmt).executeQuery();
    }

    /**
     * Unit test for USE CASE 20: getTopCapitalCitiesByPopulation
     * Tests with negative N value
     */
    @Test
    void testGetTopCapitalCitiesByPopulation_NegativeN() {
        // Execute with negative n
        List<City> result = cityReportService.getTopCapitalCitiesByPopulation(-10);

        // Verify empty list is returned
        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Result should be empty for negative n");
    }

    /**
     * Unit test for USE CASE 20: getTopCapitalCitiesByPopulation
     * Tests verifying population descending order
     */
    @Test
    void testGetTopCapitalCitiesByPopulation_VerifyPopulationOrder() throws SQLException {
        // Prepare test data
        int n = 5;

        // Mock the PreparedStatement and ResultSet
        PreparedStatement mockPstmt = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPstmt);
        when(mockPstmt.executeQuery()).thenReturn(mockRs);

        // Mock ResultSet with populations in descending order
        when(mockRs.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        when(mockRs.getInt("ID")).thenReturn(1, 2, 3, 4, 5);
        when(mockRs.getString("CityName")).thenReturn("Tokyo", "Delhi", "Beijing", "Moscow", "London");
        when(mockRs.getString("District")).thenReturn("Tokyo-to", "Delhi", "Peking", "Moscow", "England");
        when(mockRs.getString("CountryCode")).thenReturn("JPN", "IND", "CHN", "RUS", "GBR");
        when(mockRs.getInt("Population")).thenReturn(37400000, 28514000, 21540000, 12506000, 8900000);

        // Execute the method
        List<City> result = cityReportService.getTopCapitalCitiesByPopulation(n);

        // Verify results are in descending population order
        assertNotNull(result, "Result should not be null");
        assertEquals(5, result.size(), "Should return exactly 5 cities");

        // Verify population descending order
        for (int i = 0; i < result.size() - 1; i++) {
            assertTrue(result.get(i).getPopulation() >= result.get(i + 1).getPopulation(),
                    "Capital cities should be ordered by population in descending order");
        }

        // Verify first and last cities
        assertEquals("Tokyo", result.get(0).getName(), "First city should be Tokyo");
        assertEquals("London", result.get(4).getName(), "Last city should be London");
    }

    /**
     * Unit test for USE CASE 20: getTopCapitalCitiesByPopulation
     * Tests with SQLException during query execution
     */
    @Test
    void testGetTopCapitalCitiesByPopulation_SQLExceptionOnExecute() throws SQLException {
        // Mock PreparedStatement to throw SQLException on executeQuery
        PreparedStatement mockPstmt = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPstmt);
        when(mockPstmt.executeQuery()).thenThrow(new SQLException("Query execution failed"));

        // Execute the method
        List<City> result = cityReportService.getTopCapitalCitiesByPopulation(5);

        // Verify empty list is returned on exception
        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Result should be empty when SQLException occurs during execution");
    }

    /**
     * Unit test for USE CASE 20: getTopCapitalCitiesByPopulation
     * Tests that all required fields are populated correctly
     */
    @Test
    void testGetTopCapitalCitiesByPopulation_AllFieldsPopulated() throws SQLException {
        // Prepare test data
        int n = 2;

        // Mock the PreparedStatement and ResultSet
        PreparedStatement mockPstmt = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPstmt);
        when(mockPstmt.executeQuery()).thenReturn(mockRs);

        // Mock ResultSet behavior
        when(mockRs.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        when(mockRs.getInt("ID")).thenReturn(100, 200);
        when(mockRs.getString("CityName")).thenReturn("Paris", "Rome");
        when(mockRs.getString("District")).thenReturn("Île-de-France", "Lazio");
        when(mockRs.getString("CountryCode")).thenReturn("FRA", "ITA");
        when(mockRs.getInt("Population")).thenReturn(2138551, 2643581);

        // Execute the method
        List<City> result = cityReportService.getTopCapitalCitiesByPopulation(n);

        // Verify all fields are populated for each city
        assertNotNull(result, "Result should not be null");
        assertEquals(2, result.size(), "Should return exactly 2 cities");

        for (City city : result) {
            assertNotNull(city.getId(), "City ID should not be null");
            assertNotNull(city.getName(), "City name should not be null");
            assertNotNull(city.getDistrict(), "City district should not be null");
            assertNotNull(city.getCountryCode(), "City country code should not be null");
            assertTrue(city.getPopulation() > 0, "City population should be greater than 0");
        }
    }

    /**
     * Unit test for USE CASE 20: getTopCapitalCitiesByPopulation
     * Tests with maximum integer value for N
     */
    @Test
    void testGetTopCapitalCitiesByPopulation_MaxIntValue() throws SQLException {
        // Prepare test data
        int n = Integer.MAX_VALUE;

        // Mock the PreparedStatement and ResultSet
        PreparedStatement mockPstmt = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPstmt);
        when(mockPstmt.executeQuery()).thenReturn(mockRs);

        // Mock ResultSet to return one capital
        when(mockRs.next()).thenReturn(true, false);
        when(mockRs.getInt("ID")).thenReturn(1);
        when(mockRs.getString("CityName")).thenReturn("Tokyo");
        when(mockRs.getString("District")).thenReturn("Tokyo-to");
        when(mockRs.getString("CountryCode")).thenReturn("JPN");
        when(mockRs.getInt("Population")).thenReturn(37400000);

        // Execute the method - should not throw exception
        List<City> result = cityReportService.getTopCapitalCitiesByPopulation(n);

        // Verify result
        assertNotNull(result, "Result should not be null");
        assertEquals(1, result.size(), "Should return 1 city");

        // Verify PreparedStatement was called with MAX_VALUE
        verify(mockPstmt).setInt(1, Integer.MAX_VALUE);
    }


    /**
     * Unit test for USE CASE 20: printTopCapitalCitiesByPopulation
     * Tests printing with null list returned from getTopCapitalCitiesByPopulation
     */
    @Test
    void testPrintTopCapitalCitiesByPopulation_NullList() {
        // Arrange
        doReturn(null).when(spyService).getTopCapitalCitiesByPopulation(5);

        // Act
        spyService.printTopCapitalCitiesByPopulation(5);

        // Assert
        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("Error: No capital city data found."));

        verify(spyService, times(1)).getTopCapitalCitiesByPopulation(5);
    }

    /**
     * Unit test for USE CASE 20: printTopCapitalCitiesByPopulation
     * Tests printing with empty list
     */
    @Test
    void testPrintTopCapitalCitiesByPopulation_EmptyListDetailed() {
        // Arrange
        doReturn(List.of()).when(spyService).getTopCapitalCitiesByPopulation(10);

        // Act
        spyService.printTopCapitalCitiesByPopulation(10);

        // Assert
        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("Error: No capital city data found."));

        verify(spyService, times(1)).getTopCapitalCitiesByPopulation(10);
    }

    /**
     * Unit test for USE CASE 20: printTopCapitalCitiesByPopulation
     * Tests error handling with zero N parameter
     */
    @Test
    void testPrintTopCapitalCitiesByPopulation_ZeroN() {
        // Act
        cityReportService.printTopCapitalCitiesByPopulation(0);

        // Assert
        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("Error: N parameter must be greater than 0."));
    }

    /**
     * Unit test for USE CASE 20: printTopCapitalCitiesByPopulation
     * Tests error handling with negative N parameter
     */
    @Test
    void testPrintTopCapitalCitiesByPopulation_NegativeN() {
        // Act
        cityReportService.printTopCapitalCitiesByPopulation(-10);

        // Assert
        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("Error: N parameter must be greater than 0."));
    }

    /**
     * Unit test for USE CASE 20: printTopCapitalCitiesByPopulation
     * Tests printing with single capital city
     */
    @Test
    void testPrintTopCapitalCitiesByPopulation_SingleCity() {
        // Arrange
        City city = new City();
        city.setName("Tokyo");
        city.setPopulation(37400068);
        city.setCountryCode("JPN");
        city.setDistrict("Tokyo-to");

        doReturn(List.of(city)).when(spyService).getTopCapitalCitiesByPopulation(1);

        // Act
        spyService.printTopCapitalCitiesByPopulation(1);

        // Assert
        String output = outContent.toString();
        assertTrue(output.contains("Report: Top 1 Capital Cities in the World by Population"));
        assertTrue(output.contains("Total capitals found: 1"));
        assertTrue(output.contains("Tokyo"));

        verify(spyService, times(1)).getTopCapitalCitiesByPopulation(1);
    }

    /**
     * Unit test for USE CASE 20: printTopCapitalCitiesByPopulation
     * Tests printing with multiple capital cities and verifies all are printed
     */
    @Test
    void testPrintTopCapitalCitiesByPopulation_MultipleCities() {
        // Arrange
        City city1 = new City();
        city1.setName("Tokyo");
        city1.setPopulation(37400068);
        city1.setCountryCode("JPN");

        City city2 = new City();
        city2.setName("Delhi");
        city2.setPopulation(28514000);
        city2.setCountryCode("IND");

        City city3 = new City();
        city3.setName("Beijing");
        city3.setPopulation(21540000);
        city3.setCountryCode("CHN");

        doReturn(List.of(city1, city2, city3)).when(spyService).getTopCapitalCitiesByPopulation(3);

        // Act
        spyService.printTopCapitalCitiesByPopulation(3);

        // Assert
        String output = outContent.toString();
        assertTrue(output.contains("Report: Top 3 Capital Cities in the World by Population"));
        assertTrue(output.contains("Total capitals found: 3"));
        assertTrue(output.contains("=".repeat(100)));
        assertTrue(output.contains("Tokyo"));
        assertTrue(output.contains("Delhi"));
        assertTrue(output.contains("Beijing"));

        verify(spyService, times(1)).getTopCapitalCitiesByPopulation(3);
    }

    /**
     * Unit test for USE CASE 20: printTopCapitalCitiesByPopulation
     * Tests that method does not throw exception
     */
    @Test
    void testPrintTopCapitalCitiesByPopulation_DoesNotThrowException() {
        // Arrange
        doReturn(List.of()).when(spyService).getTopCapitalCitiesByPopulation(5);

        // Act & Assert
        assertDoesNotThrow(() -> spyService.printTopCapitalCitiesByPopulation(5));
    }

    /**
     * Unit test for USE CASE 20: printTopCapitalCitiesByPopulation
     * Tests with large N value
     */
    @Test
    void testPrintTopCapitalCitiesByPopulation_LargeN() {
        // Arrange
        City city1 = new City();
        city1.setName("Tokyo");
        city1.setPopulation(37400068);

        City city2 = new City();
        city2.setName("Delhi");
        city2.setPopulation(28514000);

        doReturn(List.of(city1, city2)).when(spyService).getTopCapitalCitiesByPopulation(1000);

        // Act
        spyService.printTopCapitalCitiesByPopulation(1000);

        // Assert
        String output = outContent.toString();
        assertTrue(output.contains("Report: Top 1000 Capital Cities in the World by Population"));
        assertTrue(output.contains("Total capitals found: 2"));
        assertTrue(output.contains("Tokyo"));
        assertTrue(output.contains("Delhi"));
    }

    /**
     * Unit test for USE CASE 20: printTopCapitalCitiesByPopulation
     * Tests correct format of report output
     */
    @Test
    void testPrintTopCapitalCitiesByPopulation_ReportFormat() {
        // Arrange
        City city = new City();
        city.setName("London");
        city.setPopulation(8900000);
        city.setCountryCode("GBR");

        doReturn(List.of(city)).when(spyService).getTopCapitalCitiesByPopulation(5);

        // Act
        spyService.printTopCapitalCitiesByPopulation(5);

        // Assert
        String output = outContent.toString();
        assertTrue(output.contains("Report: Top 5 Capital Cities in the World by Population"));
        assertTrue(output.contains("Total capitals found: 1"));
        assertTrue(output.contains("=".repeat(100)));
        assertTrue(output.contains("London"));

        // Verify method was called exactly once
        verify(spyService, times(1)).getTopCapitalCitiesByPopulation(5);
    }

    /**
     * Unit test for USE CASE 20: printTopCapitalCitiesByPopulation
     * Tests that validation is performed before calling getTopCapitalCitiesByPopulation
     */
    @Test
    void testPrintTopCapitalCitiesByPopulation_ValidationBeforeQuery() {
        // Act - with invalid N
        cityReportService.printTopCapitalCitiesByPopulation(-1);

        // Assert - error message printed
        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("Error: N parameter must be greater than 0."));

        // Verify the underlying method was never called because validation failed early
        verify(spyService, never()).getTopCapitalCitiesByPopulation(-1);
    }

    /**
     * Unit test for USE CASE 20: printTopCapitalCitiesByPopulation
     * Tests with cities containing special characters
     */
    @Test
    void testPrintTopCapitalCitiesByPopulation_SpecialCharacters() {
        // Arrange
        City city1 = new City();
        city1.setName("São Paulo");
        city1.setPopulation(12000000);
        city1.setCountryCode("BRA");

        City city2 = new City();
        city2.setName("México");
        city2.setPopulation(9000000);
        city2.setCountryCode("MEX");

        doReturn(List.of(city1, city2)).when(spyService).getTopCapitalCitiesByPopulation(2);

        // Act
        spyService.printTopCapitalCitiesByPopulation(2);

        // Assert
        String output = outContent.toString();
        assertTrue(output.contains("São Paulo"));
        assertTrue(output.contains("México"));
    }

    /**
     * Unit test for USE CASE 20: printTopCapitalCitiesByPopulation
     * Tests output contains separator line
     */
    @Test
    void testPrintTopCapitalCitiesByPopulation_ContainsSeparator() {
        // Arrange
        City city = new City();
        city.setName("Paris");
        city.setPopulation(2138551);
        city.setCountryCode("FRA");

        doReturn(List.of(city)).when(spyService).getTopCapitalCitiesByPopulation(1);

        // Act
        spyService.printTopCapitalCitiesByPopulation(1);

        // Assert
        String output = outContent.toString();
        assertTrue(output.contains("=".repeat(100)), "Output should contain separator line");
    }

    /**
     * Unit test for USE CASE 20: printTopCapitalCitiesByPopulation
     * Tests that all cities in the list are printed
     */
    @Test
    void testPrintTopCapitalCitiesByPopulation_AllCitiesPrinted() {
        // Arrange
        City city1 = new City();
        city1.setName("City1");
        city1.setPopulation(10000000);

        City city2 = new City();
        city2.setName("City2");
        city2.setPopulation(9000000);

        City city3 = new City();
        city3.setName("City3");
        city3.setPopulation(8000000);

        City city4 = new City();
        city4.setName("City4");
        city4.setPopulation(7000000);

        City city5 = new City();
        city5.setName("City5");
        city5.setPopulation(6000000);

        doReturn(List.of(city1, city2, city3, city4, city5)).when(spyService).getTopCapitalCitiesByPopulation(5);

        // Act
        spyService.printTopCapitalCitiesByPopulation(5);

        // Assert
        String output = outContent.toString();
        assertTrue(output.contains("City1"));
        assertTrue(output.contains("City2"));
        assertTrue(output.contains("City3"));
        assertTrue(output.contains("City4"));
        assertTrue(output.contains("City5"));
        assertTrue(output.contains("Total capitals found: 5"));
    }


    
    /**
     * Unit test for USE CASE 21: getTopCapitalCitiesByContinent
     * Tests the method that retrieves top N capital cities in a continent by population
     */
    @Test
    void testGetTopCapitalCitiesByContinent() throws SQLException {
        // Prepare test data
        String continent = "Europe";
        int n = 5;

        // Mock the PreparedStatement and ResultSet
        PreparedStatement mockPstmt = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPstmt);
        when(mockPstmt.executeQuery()).thenReturn(mockRs);

        // Mock ResultSet behavior - simulate 5 capital cities
        when(mockRs.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        // Mock data for capital cities
        when(mockRs.getInt("ID"))
                .thenReturn(1)
                .thenReturn(2)
                .thenReturn(3)
                .thenReturn(4)
                .thenReturn(5);

        when(mockRs.getString("CityName"))
                .thenReturn("Moscow")
                .thenReturn("London")
                .thenReturn("Berlin")
                .thenReturn("Madrid")
                .thenReturn("Rome");

        when(mockRs.getString("District"))
                .thenReturn("Moscow")
                .thenReturn("England")
                .thenReturn("Berliini")
                .thenReturn("Madrid")
                .thenReturn("Lazio");

        when(mockRs.getString("CountryCode"))
                .thenReturn("RUS")
                .thenReturn("GBR")
                .thenReturn("DEU")
                .thenReturn("ESP")
                .thenReturn("ITA");

        when(mockRs.getInt("Population"))
                .thenReturn(8389200)
                .thenReturn(7285000)
                .thenReturn(3386667)
                .thenReturn(2879052)
                .thenReturn(2643581);

        // Execute the method
        List<City> result = cityReportService.getTopCapitalCitiesByContinent(continent, n);

        // Verify the results
        assertNotNull(result, "Result should not be null");
        assertEquals(5, result.size(), "Should return exactly 5 cities");

        // Verify first city details
        City firstCity = result.get(0);
        assertEquals(1, firstCity.getId(), "First city ID should be 1");
        assertEquals("Moscow", firstCity.getName(), "First city name should be Moscow");
        assertEquals("Moscow", firstCity.getDistrict(), "First city district should be Moscow");
        assertEquals("RUS", firstCity.getCountryCode(), "First city country code should be RUS");
        assertEquals(8389200, firstCity.getPopulation(), "First city population should be 8389200");

        // Verify PreparedStatement parameters were set correctly
        verify(mockPstmt).setString(1, continent);
        verify(mockPstmt).setInt(2, n);
        verify(mockPstmt).executeQuery();
    }

    /**
     * Unit test for USE CASE 21: getTopCapitalCitiesByContinent with null continent
     */
    @Test
    void testGetTopCapitalCitiesByContinent_NullContinent() {
        // Execute with null continent
        List<City> result = cityReportService.getTopCapitalCitiesByContinent(null, 5);

        // Verify empty list is returned
        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Result should be empty for null continent");
    }

    /**
     * Unit test for USE CASE 21: getTopCapitalCitiesByContinent with empty continent
     */
    @Test
    void testGetTopCapitalCitiesByContinent_EmptyContinent() {
        // Execute with empty continent
        List<City> result = cityReportService.getTopCapitalCitiesByContinent("", 5);

        // Verify empty list is returned
        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Result should be empty for empty continent");
    }

    /**
     * Unit test for USE CASE 21: getTopCapitalCitiesByContinent with zero N
     */
    @Test
    void testGetTopCapitalCitiesByContinent_ZeroN() {
        // Execute with n = 0
        List<City> result = cityReportService.getTopCapitalCitiesByContinent("Europe", 0);

        // Verify empty list is returned
        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Result should be empty for n = 0");
    }

    /**
     * Unit test for USE CASE 21: getTopCapitalCitiesByContinent with negative N
     */
    @Test
    void testGetTopCapitalCitiesByContinent_NegativeN() {
        // Execute with negative n
        List<City> result = cityReportService.getTopCapitalCitiesByContinent("Europe", -5);

        // Verify empty list is returned
        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Result should be empty for negative n");
    }

    /**
     * Unit test for USE CASE 21: getTopCapitalCitiesByContinent with SQLException
     */
    @Test
    void testGetTopCapitalCitiesByContinent_SQLException() throws SQLException {
        // Mock PreparedStatement to throw SQLException
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Execute the method
        List<City> result = cityReportService.getTopCapitalCitiesByContinent("Europe", 5);

        // Verify empty list is returned on exception
        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Result should be empty when SQLException occurs");
    }

    /**
     * Unit test for USE CASE 21: getTopCapitalCitiesByContinent with no results
     */
    @Test
    void testGetTopCapitalCitiesByContinent_NoResults() throws SQLException {
        // Mock the PreparedStatement and ResultSet
        PreparedStatement mockPstmt = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPstmt);
        when(mockPstmt.executeQuery()).thenReturn(mockRs);

        // Mock ResultSet to return no rows
        when(mockRs.next()).thenReturn(false);

        // Execute the method
        List<City> result = cityReportService.getTopCapitalCitiesByContinent("InvalidContinent", 5);

        // Verify empty list is returned
        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Result should be empty when no data found");
    }
    
    /**
     * Unit test for USE CASE 21: printTopCapitalCitiesByContinent
     * Tests successful printing with valid data
     */
    @Test
    void testPrintTopCapitalCitiesByContinent_ValidList() {
        // Arrange
        City city1 = new City();
        city1.setName("Moscow");
        city1.setPopulation(8389200);
        city1.setCountryCode("RUS");
        city1.setDistrict("Moscow");

        City city2 = new City();
        city2.setName("London");
        city2.setPopulation(7285000);
        city2.setCountryCode("GBR");
        city2.setDistrict("England");

        City city3 = new City();
        city3.setName("Berlin");
        city3.setPopulation(3386667);
        city3.setCountryCode("DEU");
        city3.setDistrict("Berliini");

        doReturn(List.of(city1, city2, city3)).when(spyService).getTopCapitalCitiesByContinent("Europe", 3);

        // Act
        spyService.printTopCapitalCitiesByContinent("Europe", 3);

        // Assert
        String output = outContent.toString();
        assertTrue(output.contains("Report: Top 3 Capital Cities in Europe by Population"));
        assertTrue(output.contains("Total capitals found: 3"));
        assertTrue(output.contains("=".repeat(100)));
        assertTrue(output.contains("Moscow"));
        assertTrue(output.contains("London"));
        assertTrue(output.contains("Berlin"));

        verify(spyService, times(1)).getTopCapitalCitiesByContinent("Europe", 3);
    }

    /**
     * Unit test for USE CASE 21: printTopCapitalCitiesByContinent
     * Tests printing with empty list
     */
    @Test
    void testPrintTopCapitalCitiesByContinent_EmptyList() {
        // Arrange
        doReturn(List.of()).when(spyService).getTopCapitalCitiesByContinent("Antarctica", 5);

        // Act
        spyService.printTopCapitalCitiesByContinent("Antarctica", 5);

        // Assert
        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("Error: No capital city data found for continent: Antarctica"));

        verify(spyService, times(1)).getTopCapitalCitiesByContinent("Antarctica", 5);
    }

    /**
     * Unit test for USE CASE 21: printTopCapitalCitiesByContinent
     * Tests printing with null list
     */
    @Test
    void testPrintTopCapitalCitiesByContinent_NullList() {
        // Arrange
        doReturn(null).when(spyService).getTopCapitalCitiesByContinent("InvalidContinent", 5);

        // Act
        spyService.printTopCapitalCitiesByContinent("InvalidContinent", 5);

        // Assert
        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("Error: No capital city data found for continent: InvalidContinent"));

        verify(spyService, times(1)).getTopCapitalCitiesByContinent("InvalidContinent", 5);
    }

    /**
     * Unit test for USE CASE 21: printTopCapitalCitiesByContinent
     * Tests error handling with null continent parameter
     */
    @Test
    void testPrintTopCapitalCitiesByContinent_NullContinent() {
        // Act
        cityReportService.printTopCapitalCitiesByContinent(null, 5);

        // Assert
        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("Error: Continent parameter cannot be null or empty."));
    }

    /**
     * Unit test for USE CASE 21: printTopCapitalCitiesByContinent
     * Tests error handling with empty continent parameter
     */
    @Test
    void testPrintTopCapitalCitiesByContinent_EmptyContinent() {
        // Act
        cityReportService.printTopCapitalCitiesByContinent("", 5);

        // Assert
        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("Error: Continent parameter cannot be null or empty."));
    }

    /**
     * Unit test for USE CASE 21: printTopCapitalCitiesByContinent
     * Tests error handling with whitespace-only continent parameter
     */
    @Test
    void testPrintTopCapitalCitiesByContinent_WhitespaceContinent() {
        // Act
        cityReportService.printTopCapitalCitiesByContinent("   ", 5);

        // Assert
        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("Error: Continent parameter cannot be null or empty."));
    }

    /**
     * Unit test for USE CASE 21: printTopCapitalCitiesByContinent
     * Tests error handling with zero N parameter
     */
    @Test
    void testPrintTopCapitalCitiesByContinent_ZeroN() {
        // Act
        cityReportService.printTopCapitalCitiesByContinent("Europe", 0);

        // Assert
        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("Error: N parameter must be greater than 0."));
    }

    /**
     * Unit test for USE CASE 21: printTopCapitalCitiesByContinent
     * Tests error handling with negative N parameter
     */
    @Test
    void testPrintTopCapitalCitiesByContinent_NegativeN() {
        // Act
        cityReportService.printTopCapitalCitiesByContinent("Europe", -5);

        // Assert
        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("Error: N parameter must be greater than 0."));
    }

    /**
     * Unit test for USE CASE 21: printTopCapitalCitiesByContinent
     * Tests printing with single capital city
     */
    @Test
    void testPrintTopCapitalCitiesByContinent_SingleCity() {
        // Arrange
        City city = new City();
        city.setName("Paris");
        city.setPopulation(2138551);
        city.setCountryCode("FRA");
        city.setDistrict("Île-de-France");

        doReturn(List.of(city)).when(spyService).getTopCapitalCitiesByContinent("Europe", 1);

        // Act
        spyService.printTopCapitalCitiesByContinent("Europe", 1);

        // Assert
        String output = outContent.toString();
        assertTrue(output.contains("Report: Top 1 Capital Cities in Europe by Population"));
        assertTrue(output.contains("Total capitals found: 1"));
        assertTrue(output.contains("Paris"));

        verify(spyService, times(1)).getTopCapitalCitiesByContinent("Europe", 1);
    }

    /**
     * Unit test for USE CASE 21: printTopCapitalCitiesByContinent
     * Tests that method does not throw exception
     */
    @Test
    void testPrintTopCapitalCitiesByContinent_DoesNotThrowException() {
        // Arrange
        doReturn(List.of()).when(spyService).getTopCapitalCitiesByContinent("Asia", 10);

        // Act & Assert
        assertDoesNotThrow(() -> spyService.printTopCapitalCitiesByContinent("Asia", 10));
    }

    /**
     * Unit test for USE CASE 21: printTopCapitalCitiesByContinent
     * Tests printing with various continent names
     */
    @Test
    void testPrintTopCapitalCitiesByContinent_DifferentContinents() {
        // Arrange
        City city1 = new City();
        city1.setName("Tokyo");
        city1.setPopulation(13960000);
        
        City city2 = new City();
        city2.setName("Seoul");
        city2.setPopulation(9981619);

        doReturn(List.of(city1, city2)).when(spyService).getTopCapitalCitiesByContinent("Asia", 2);

        // Act
        spyService.printTopCapitalCitiesByContinent("Asia", 2);

        // Assert
        String output = outContent.toString();
        assertTrue(output.contains("Report: Top 2 Capital Cities in Asia by Population"));
        assertTrue(output.contains("Tokyo"));
        assertTrue(output.contains("Seoul"));
    }
    
    /**
     * Unit test for USE CASE 22: getTopCapitalCitiesByRegion
     * Tests the method that retrieves top N capital cities in a region by population
     */
    @Test
    void testGetTopCapitalCitiesByRegion() throws SQLException {
        // Prepare test data
        String region = "Western Europe";
        int n = 5;

        // Mock the PreparedStatement and ResultSet
        PreparedStatement mockPstmt = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPstmt);
        when(mockPstmt.executeQuery()).thenReturn(mockRs);

        // Mock ResultSet behavior - simulate 5 capital cities
        when(mockRs.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        // Mock data for capital cities
        when(mockRs.getInt("ID"))
                .thenReturn(1)
                .thenReturn(2)
                .thenReturn(3)
                .thenReturn(4)
                .thenReturn(5);

        when(mockRs.getString("CityName"))
                .thenReturn("Berlin")
                .thenReturn("Madrid")
                .thenReturn("Rome")
                .thenReturn("Paris")
                .thenReturn("Amsterdam");

        when(mockRs.getString("District"))
                .thenReturn("Berliini")
                .thenReturn("Madrid")
                .thenReturn("Lazio")
                .thenReturn("Île-de-France")
                .thenReturn("Noord-Holland");

        when(mockRs.getString("CountryCode"))
                .thenReturn("DEU")
                .thenReturn("ESP")
                .thenReturn("ITA")
                .thenReturn("FRA")
                .thenReturn("NLD");

        when(mockRs.getInt("Population"))
                .thenReturn(3386667)
                .thenReturn(2879052)
                .thenReturn(2643581)
                .thenReturn(2138551)
                .thenReturn(731200);

        // Execute the method
        List<City> result = cityReportService.getTopCapitalCitiesByRegion(region, n);

        // Verify the results
        assertNotNull(result, "Result should not be null");
        assertEquals(5, result.size(), "Should return exactly 5 cities");

        // Verify first city details
        City firstCity = result.get(0);
        assertEquals(1, firstCity.getId(), "First city ID should be 1");
        assertEquals("Berlin", firstCity.getName(), "First city name should be Berlin");
        assertEquals("Berliini", firstCity.getDistrict(), "First city district should be Berliini");
        assertEquals("DEU", firstCity.getCountryCode(), "First city country code should be DEU");
        assertEquals(3386667, firstCity.getPopulation(), "First city population should be 3386667");

        // Verify PreparedStatement parameters were set correctly
        verify(mockPstmt).setString(1, region);
        verify(mockPstmt).setInt(2, n);
        verify(mockPstmt).executeQuery();
    }

    /**
     * Unit test for USE CASE 22: getTopCapitalCitiesByRegion with null region
     */
    @Test
    void testGetTopCapitalCitiesByRegion_NullRegion() {
        // Execute with null region
        List<City> result = cityReportService.getTopCapitalCitiesByRegion(null, 5);

        // Verify empty list is returned
        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Result should be empty for null region");
    }

    /**
     * Unit test for USE CASE 22: getTopCapitalCitiesByRegion with empty region
     */
    @Test
    void testGetTopCapitalCitiesByRegion_EmptyRegion() {
        // Execute with empty region
        List<City> result = cityReportService.getTopCapitalCitiesByRegion("", 5);

        // Verify empty list is returned
        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Result should be empty for empty region");
    }

    /**
     * Unit test for USE CASE 22: getTopCapitalCitiesByRegion with whitespace region
     */
    @Test
    void testGetTopCapitalCitiesByRegion_WhitespaceRegion() {
        // Execute with whitespace-only region
        List<City> result = cityReportService.getTopCapitalCitiesByRegion("   ", 5);

        // Verify empty list is returned
        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Result should be empty for whitespace region");
    }

    /**
     * Unit test for USE CASE 22: getTopCapitalCitiesByRegion with zero N
     */
    @Test
    void testGetTopCapitalCitiesByRegion_ZeroN() {
        // Execute with n = 0
        List<City> result = cityReportService.getTopCapitalCitiesByRegion("Western Europe", 0);

        // Verify empty list is returned
        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Result should be empty for n = 0");
    }

    /**
     * Unit test for USE CASE 22: getTopCapitalCitiesByRegion with negative N
     */
    @Test
    void testGetTopCapitalCitiesByRegion_NegativeN() {
        // Execute with negative n
        List<City> result = cityReportService.getTopCapitalCitiesByRegion("Western Europe", -5);

        // Verify empty list is returned
        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Result should be empty for negative n");
    }

    /**
     * Unit test for USE CASE 22: getTopCapitalCitiesByRegion with SQLException
     */
    @Test
    void testGetTopCapitalCitiesByRegion_SQLException() throws SQLException {
        // Mock PreparedStatement to throw SQLException
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Execute the method
        List<City> result = cityReportService.getTopCapitalCitiesByRegion("Western Europe", 5);

        // Verify empty list is returned on exception
        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Result should be empty when SQLException occurs");
    }

    /**
     * Unit test for USE CASE 22: getTopCapitalCitiesByRegion with no results
     */
    @Test
    void testGetTopCapitalCitiesByRegion_NoResults() throws SQLException {
        // Mock the PreparedStatement and ResultSet
        PreparedStatement mockPstmt = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPstmt);
        when(mockPstmt.executeQuery()).thenReturn(mockRs);

        // Mock ResultSet to return no rows
        when(mockRs.next()).thenReturn(false);

        // Execute the method
        List<City> result = cityReportService.getTopCapitalCitiesByRegion("InvalidRegion", 5);

        // Verify empty list is returned
        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Result should be empty when no data found");
    }

    /**
     * Unit test for USE CASE 22: getTopCapitalCitiesByRegion with single result
     */
    @Test
    void testGetTopCapitalCitiesByRegion_SingleResult() throws SQLException {
        // Prepare test data
        String region = "Caribbean";
        int n = 1;

        // Mock the PreparedStatement and ResultSet
        PreparedStatement mockPstmt = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPstmt);
        when(mockPstmt.executeQuery()).thenReturn(mockRs);

        // Mock ResultSet behavior - simulate 1 capital city
        when(mockRs.next())
                .thenReturn(true)
                .thenReturn(false);

        // Mock data for capital city
        when(mockRs.getInt("ID")).thenReturn(100);
        when(mockRs.getString("CityName")).thenReturn("Havana");
        when(mockRs.getString("District")).thenReturn("La Habana");
        when(mockRs.getString("CountryCode")).thenReturn("CUB");
        when(mockRs.getInt("Population")).thenReturn(2201610);

        // Execute the method
        List<City> result = cityReportService.getTopCapitalCitiesByRegion(region, n);

        // Verify the results
        assertNotNull(result, "Result should not be null");
        assertEquals(1, result.size(), "Should return exactly 1 city");

        // Verify city details
        City city = result.get(0);
        assertEquals(100, city.getId(), "City ID should be 100");
        assertEquals("Havana", city.getName(), "City name should be Havana");
        assertEquals("La Habana", city.getDistrict(), "City district should be La Habana");
        assertEquals("CUB", city.getCountryCode(), "City country code should be CUB");
        assertEquals(2201610, city.getPopulation(), "City population should be 2201610");

        // Verify PreparedStatement parameters
        verify(mockPstmt).setString(1, region);
        verify(mockPstmt).setInt(2, n);
        verify(mockPstmt).executeQuery();
    }

    /**
     * Unit test for USE CASE 22: getTopCapitalCitiesByRegion with large N value
     */
    @Test
    void testGetTopCapitalCitiesByRegion_LargeN() throws SQLException {
        // Prepare test data
        String region = "Southern Europe";
        int n = 100;

        // Mock the PreparedStatement and ResultSet
        PreparedStatement mockPstmt = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPstmt);
        when(mockPstmt.executeQuery()).thenReturn(mockRs);

        // Mock ResultSet behavior - simulate 2 capital cities (less than requested)
        when(mockRs.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        when(mockRs.getInt("ID")).thenReturn(1, 2);
        when(mockRs.getString("CityName")).thenReturn("Rome", "Athens");
        when(mockRs.getString("District")).thenReturn("Lazio", "Attika");
        when(mockRs.getString("CountryCode")).thenReturn("ITA", "GRC");
        when(mockRs.getInt("Population")).thenReturn(2643581, 772072);

        // Execute the method
        List<City> result = cityReportService.getTopCapitalCitiesByRegion(region, n);

        // Verify the results - should return only available cities, not N
        assertNotNull(result, "Result should not be null");
        assertEquals(2, result.size(), "Should return exactly 2 cities (available in region)");

        // Verify PreparedStatement parameters
        verify(mockPstmt).setString(1, region);
        verify(mockPstmt).setInt(2, n);
        verify(mockPstmt).executeQuery();
    }

    /**
     * Unit test for USE CASE 22: getTopCapitalCitiesByRegion verifying population order
     */
    @Test
    void testGetTopCapitalCitiesByRegion_VerifyPopulationOrder() throws SQLException {
        // Prepare test data
        String region = "Eastern Europe";
        int n = 3;

        // Mock the PreparedStatement and ResultSet
        PreparedStatement mockPstmt = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPstmt);
        when(mockPstmt.executeQuery()).thenReturn(mockRs);

        // Mock ResultSet with populations in descending order
        when(mockRs.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        when(mockRs.getInt("ID")).thenReturn(1, 2, 3);
        when(mockRs.getString("CityName")).thenReturn("Moscow", "Kiev", "Minsk");
        when(mockRs.getString("District")).thenReturn("Moscow", "Kyiv", "Minsk");
        when(mockRs.getString("CountryCode")).thenReturn("RUS", "UKR", "BLR");
        when(mockRs.getInt("Population")).thenReturn(8389200, 2797553, 1674000);

        // Execute the method
        List<City> result = cityReportService.getTopCapitalCitiesByRegion(region, n);

        // Verify results are in descending population order
        assertNotNull(result, "Result should not be null");
        assertEquals(3, result.size(), "Should return exactly 3 cities");

        // Verify population descending order
        for (int i = 0; i < result.size() - 1; i++) {
            assertTrue(result.get(i).getPopulation() >= result.get(i + 1).getPopulation(),
                    "Cities should be ordered by population in descending order");
        }
    }

    /**
     * Unit test for USE CASE 22: printTopCapitalCitiesByRegion
     * Tests successful printing with valid data
     */
    @Test
    void testPrintTopCapitalCitiesByRegion_ValidList() {
        // Arrange
        City city1 = new City();
        city1.setName("Berlin");
        city1.setPopulation(3386667);
        city1.setCountryCode("DEU");
        city1.setDistrict("Berliini");

        City city2 = new City();
        city2.setName("Madrid");
        city2.setPopulation(2879052);
        city2.setCountryCode("ESP");
        city2.setDistrict("Madrid");

        City city3 = new City();
        city3.setName("Rome");
        city3.setPopulation(2643581);
        city3.setCountryCode("ITA");
        city3.setDistrict("Lazio");

        doReturn(List.of(city1, city2, city3)).when(spyService).getTopCapitalCitiesByRegion("Western Europe", 3);

        // Act
        spyService.printTopCapitalCitiesByRegion("Western Europe", 3);

        // Assert
        String output = outContent.toString();
        assertTrue(output.contains("Report: Top 3 Capital Cities in Western Europe by Population"));
        assertTrue(output.contains("Total capitals found: 3"));
        assertTrue(output.contains("=".repeat(100)));
        assertTrue(output.contains("Berlin"));
        assertTrue(output.contains("Madrid"));
        assertTrue(output.contains("Rome"));

        verify(spyService, times(1)).getTopCapitalCitiesByRegion("Western Europe", 3);
    }

    /**
     * Unit test for USE CASE 22: printTopCapitalCitiesByRegion
     * Tests printing with empty list
     */
    @Test
    void testPrintTopCapitalCitiesByRegion_EmptyList() {
        // Arrange
        doReturn(List.of()).when(spyService).getTopCapitalCitiesByRegion("Polynesia", 5);

        // Act
        spyService.printTopCapitalCitiesByRegion("Polynesia", 5);

        // Assert
        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("Error: No capital city data found for region: Polynesia"));

        verify(spyService, times(1)).getTopCapitalCitiesByRegion("Polynesia", 5);
    }

    /**
     * Unit test for USE CASE 22: printTopCapitalCitiesByRegion
     * Tests printing with null list
     */
    @Test
    void testPrintTopCapitalCitiesByRegion_NullList() {
        // Arrange
        doReturn(null).when(spyService).getTopCapitalCitiesByRegion("InvalidRegion", 5);

        // Act
        spyService.printTopCapitalCitiesByRegion("InvalidRegion", 5);

        // Assert
        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("Error: No capital city data found for region: InvalidRegion"));

        verify(spyService, times(1)).getTopCapitalCitiesByRegion("InvalidRegion", 5);
    }

    /**
     * Unit test for USE CASE 22: printTopCapitalCitiesByRegion
     * Tests error handling with null region parameter
     */
    @Test
    void testPrintTopCapitalCitiesByRegion_NullRegion() {
        // Act
        cityReportService.printTopCapitalCitiesByRegion(null, 5);

        // Assert
        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("Error: Region parameter cannot be null or empty."));
    }

    /**
     * Unit test for USE CASE 22: printTopCapitalCitiesByRegion
     * Tests error handling with empty region parameter
     */
    @Test
    void testPrintTopCapitalCitiesByRegion_EmptyRegion() {
        // Act
        cityReportService.printTopCapitalCitiesByRegion("", 5);

        // Assert
        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("Error: Region parameter cannot be null or empty."));
    }

    /**
     * Unit test for USE CASE 22: printTopCapitalCitiesByRegion
     * Tests error handling with whitespace-only region parameter
     */
    @Test
    void testPrintTopCapitalCitiesByRegion_WhitespaceRegion() {
        // Act
        cityReportService.printTopCapitalCitiesByRegion("   ", 5);

        // Assert
        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("Error: Region parameter cannot be null or empty."));
    }

    /**
     * Unit test for USE CASE 22: printTopCapitalCitiesByRegion
     * Tests error handling with zero N parameter
     */
    @Test
    void testPrintTopCapitalCitiesByRegion_ZeroN() {
        // Act
        cityReportService.printTopCapitalCitiesByRegion("Western Europe", 0);

        // Assert
        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("Error: N parameter must be greater than 0."));
    }

    /**
     * Unit test for USE CASE 22: printTopCapitalCitiesByRegion
     * Tests error handling with negative N parameter
     */
    @Test
    void testPrintTopCapitalCitiesByRegion_NegativeN() {
        // Act
        cityReportService.printTopCapitalCitiesByRegion("Western Europe", -5);

        // Assert
        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("Error: N parameter must be greater than 0."));
    }

    /**
     * Unit test for USE CASE 22: printTopCapitalCitiesByRegion
     * Tests printing with single capital city
     */
    @Test
    void testPrintTopCapitalCitiesByRegion_SingleCity() {
        // Arrange
        City city = new City();
        city.setName("Havana");
        city.setPopulation(2201610);
        city.setCountryCode("CUB");
        city.setDistrict("La Habana");

        doReturn(List.of(city)).when(spyService).getTopCapitalCitiesByRegion("Caribbean", 1);

        // Act
        spyService.printTopCapitalCitiesByRegion("Caribbean", 1);

        // Assert
        String output = outContent.toString();
        assertTrue(output.contains("Report: Top 1 Capital Cities in Caribbean by Population"));
        assertTrue(output.contains("Total capitals found: 1"));
        assertTrue(output.contains("Havana"));

        verify(spyService, times(1)).getTopCapitalCitiesByRegion("Caribbean", 1);
    }

    /**
     * Unit test for USE CASE 22: printTopCapitalCitiesByRegion
     * Tests that method does not throw exception
     */
    @Test
    void testPrintTopCapitalCitiesByRegion_DoesNotThrowException() {
        // Arrange
        doReturn(List.of()).when(spyService).getTopCapitalCitiesByRegion("Eastern Asia", 10);

        // Act & Assert
        assertDoesNotThrow(() -> spyService.printTopCapitalCitiesByRegion("Eastern Asia", 10));
    }

    /**
     * Unit test for USE CASE 22: printTopCapitalCitiesByRegion
     * Tests printing with various region names
     */
    @Test
    void testPrintTopCapitalCitiesByRegion_DifferentRegions() {
        // Arrange
        City city1 = new City();
        city1.setName("Tokyo");
        city1.setPopulation(13960000);
        city1.setCountryCode("JPN");
        
        City city2 = new City();
        city2.setName("Seoul");
        city2.setPopulation(9981619);
        city2.setCountryCode("KOR");

        doReturn(List.of(city1, city2)).when(spyService).getTopCapitalCitiesByRegion("Eastern Asia", 2);

        // Act
        spyService.printTopCapitalCitiesByRegion("Eastern Asia", 2);

        // Assert
        String output = outContent.toString();
        assertTrue(output.contains("Report: Top 2 Capital Cities in Eastern Asia by Population"));
        assertTrue(output.contains("Tokyo"));
        assertTrue(output.contains("Seoul"));
    }

    /**
     * Unit test for USE CASE 22: printTopCapitalCitiesByRegion
     * Tests printing with large N value
     */
    @Test
    void testPrintTopCapitalCitiesByRegion_LargeN() {
        // Arrange
        City city1 = new City();
        city1.setName("Cairo");
        city1.setPopulation(6789479);

        City city2 = new City();
        city2.setName("Nairobi");
        city2.setPopulation(2750547);

        doReturn(List.of(city1, city2)).when(spyService).getTopCapitalCitiesByRegion("Eastern Africa", 100);

        // Act
        spyService.printTopCapitalCitiesByRegion("Eastern Africa", 100);

        // Assert
        String output = outContent.toString();
        assertTrue(output.contains("Report: Top 100 Capital Cities in Eastern Africa by Population"));
        assertTrue(output.contains("Total capitals found: 2"));
        assertTrue(output.contains("Cairo"));
        assertTrue(output.contains("Nairobi"));
    }

    /**
     * Unit test for USE CASE 22: printTopCapitalCitiesByRegion
     * Tests printing with region containing special characters
     */
    @Test
    void testPrintTopCapitalCitiesByRegion_SpecialCharactersInRegion() {
        // Arrange
        City city = new City();
        city.setName("São Paulo");
        city.setPopulation(10021295);
        city.setCountryCode("BRA");

        doReturn(List.of(city)).when(spyService).getTopCapitalCitiesByRegion("South America", 1);

        // Act
        spyService.printTopCapitalCitiesByRegion("South America", 1);

        // Assert
        String output = outContent.toString();
        assertTrue(output.contains("Report: Top 1 Capital Cities in South America by Population"));
        assertTrue(output.contains("São Paulo"));
    }

    /**
     * Unit test for USE CASE 22: printTopCapitalCitiesByRegion
     * Tests validation is performed before calling getTopCapitalCitiesByRegion
     */
    @Test
    void testPrintTopCapitalCitiesByRegion_ValidationBeforeQuery() {
        // Act - with null region
        cityReportService.printTopCapitalCitiesByRegion(null, 5);

        // Assert - error message printed, but getTopCapitalCitiesByRegion not called
        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("Error: Region parameter cannot be null or empty."));

        // Verify the spy was never called because validation failed early
        verify(spyService, never()).getTopCapitalCitiesByRegion(null, 5);
    }

    /**
     * Unit test for USE CASE 22: printTopCapitalCitiesByRegion
     * Tests with both parameters invalid
     */
    @Test
    void testPrintTopCapitalCitiesByRegion_BothParametersInvalid() {
        // Act
        cityReportService.printTopCapitalCitiesByRegion("", -1);

        // Assert - should catch region error first
        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("Error: Region parameter cannot be null or empty."));
    }


    /**
     * USE CASE 30 - Retrieve the Population of a District.
     * Tests valid district input returning correct report.
     * Tests that {@link CityReportService#getDistrictPopulationReport(String)}
     */
    @Test
    void testValidDistrictPopulationReport() throws Exception {
        // Mocks for queries
        PreparedStatement totalStmt = mock(PreparedStatement.class);
        PreparedStatement cityStmt = mock(PreparedStatement.class);
        ResultSet totalRS = mock(ResultSet.class);
        ResultSet cityRS = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString()))
                .thenReturn(totalStmt)
                .thenReturn(cityStmt);
        when(totalStmt.executeQuery()).thenReturn(totalRS);
        when(cityStmt.executeQuery()).thenReturn(cityRS);

        // Mock data returned from DB
        when(totalRS.next()).thenReturn(true);
        when(totalRS.getLong("population")).thenReturn(500000L);
        when(cityRS.next()).thenReturn(true);
        when(cityRS.getLong("city_population")).thenReturn(400000L);

        // Execute
        PopulationReportPojo report = cityReportService.getDistrictPopulationReport(DEFAULT_DISTRICT);

        // Assertions
        assertEquals(DEFAULT_DISTRICT, report.getName());
        assertEquals(500000L, report.getTotalPopulation());
        assertEquals(400000L, report.getPopulationInCities());
        assertEquals(100000L, report.getPopulationNotInCities());
        assertEquals(80.0, report.getPercentageInCities(), 0.01);
        assertEquals(20.0, report.getPercentageNotInCities(), 0.01);
    }

    /**
     * USE CASE 30 - Retrieve the Population of a District.
     * Tests invalid district input returning null OR empty report.
     * Tests that {@link CityReportService#getDistrictPopulationReport(String)}
     */
    @Test
    void testDistrictPopulationReportWithInvalidName() {
        assertNull(cityReportService.getDistrictPopulationReport(null));
        assertNull(cityReportService.getDistrictPopulationReport(""));
    }


    /**
     * USE CASE 31 - Retrieve the Population of a City.
     * Tests valid city input returning correct report.
     * Tests that {@link CityReportService#getCityPopulationReport(String)}
     */
    @Test
    void testValidCityPopulationReport() throws Exception {
        PreparedStatement totalStmt = mock(PreparedStatement.class);
        PreparedStatement cityStmt = mock(PreparedStatement.class);
        ResultSet totalRS = mock(ResultSet.class);
        ResultSet cityRS = mock(ResultSet.class);

        // Exact string for total population query
        when(mockConnection.prepareStatement(eq("SELECT Population FROM city WHERE Name = ?")))
                .thenReturn(totalStmt);
        when(totalStmt.executeQuery()).thenReturn(totalRS);

        // City population query (use contains to avoid whitespace issues)
        when(mockConnection.prepareStatement(contains("AS city_population")))
                .thenReturn(cityStmt);
        when(cityStmt.executeQuery()).thenReturn(cityRS);

        when(totalRS.next()).thenReturn(true);
        when(totalRS.getLong("Population")).thenReturn(500000L);

        when(cityRS.next()).thenReturn(true);
        when(cityRS.getLong("city_population")).thenReturn(500000L);

        PopulationReportPojo result = cityReportService.getCityPopulationReport("Lagos");

        assertNotNull(result);
        assertEquals("Lagos", result.getName());
        assertEquals(500000L, result.getTotalPopulation());
        assertEquals(500000L, result.getPopulationInCities());
        assertEquals(0L, result.getPopulationNotInCities());
        assertEquals(100.0, result.getPercentageInCities(), 0.001);
        assertEquals(0.0, result.getPercentageNotInCities(), 0.001);

        verify(totalStmt).setString(1, "Lagos");
        verify(cityStmt).setString(1, "Lagos");
    }

    /**
     * USE CASE 31 - Retrieve the Population of a City.
     * Tests invalid city input returning null OR empty report.
     * Tests that {@link CityReportService#getCityPopulationReport(String)}
     */
    @Test
    void testInvalidCityNameReturnsNull() throws Exception {

        PopulationReportPojo result = cityReportService.getCityPopulationReport(" ");
        PopulationReportPojo nullResult = cityReportService.getCityPopulationReport(null);
        assertNull(result);
        assertNull(nullResult);
    }

    /**
     * USE CASE 31 - Retrieve the Population of a City.
     * Tests SQL exception being handled gracefully.
     * Tests that {@link CityReportService#getCityPopulationReport(String)}
     */
    @Test
    void testSQLExceptionHandledGracefully() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("DB Error"));

        PopulationReportPojo result = cityReportService.getCityPopulationReport(DEFAULT_CITY_NAME);
        assertNull(result);
    }

    /**
     * USE CASE 30 - printDistrictPopulationReport
     * When a valid report is returned, it should print a nicely formatted district report
     * and return the same report instance.
     */
    @Test
    void testPrintDistrictPopulationReport_ValidReport() {
        PopulationReportPojo report = new PopulationReportPojo();
        report.setName("California");
        report.setTotalPopulation(500_000L);
        report.setPopulationInCities(300_000L);
        report.setPopulationNotInCities(200_000L);
        report.setPercentageInCities(60.0);
        report.setPercentageNotInCities(40.0);

        doReturn(report).when(spyService).getDistrictPopulationReport("California");

        PopulationReportPojo returned = spyService.printDistrictPopulationReport("California");

        assertSame(report, returned, "printDistrictPopulationReport should return the same report instance");

        String output = outContent.toString();
        assertTrue(output.contains("DISTRICT POPULATION REPORT"));
        assertTrue(output.contains("District: California"));
        assertTrue(output.contains("Total Population: 500,000"));
        assertTrue(output.contains("Population in Cities: 300,000 (60.00%)"));
        assertTrue(output.contains("Population Not in Cities: 200,000 (40.00%)"));
    }

    /**
     * USE CASE 30 - printDistrictPopulationReport
     * When underlying getDistrictPopulationReport returns null,
     * it should print an error and return null.
     */
    @Test
    void testPrintDistrictPopulationReport_NoData() {
        doReturn(null).when(spyService).getDistrictPopulationReport("UnknownDistrict");

        PopulationReportPojo returned = spyService.printDistrictPopulationReport("UnknownDistrict");

        assertNull(returned, "Should return null when no report is available");
        String error = errContent.toString();
        assertTrue(error.contains("Error: No population data found for district: UnknownDistrict"));
    }

    /**
     * USE CASE 31 - printCityPopulationReport
     * When a valid report is returned, it should print a nicely formatted city report
     * and return the same report instance.
     */
    @Test
    void testPrintCityPopulationReport_ValidReport() {
        PopulationReportPojo report = new PopulationReportPojo();
        report.setName("Lagos");
        report.setTotalPopulation(8_000_000L);
        report.setPopulationInCities(8_000_000L);
        report.setPopulationNotInCities(0L);
        report.setPercentageInCities(100.0);
        report.setPercentageNotInCities(0.0);

        doReturn(report).when(spyService).getCityPopulationReport("Lagos");

        PopulationReportPojo returned = spyService.printCityPopulationReport("Lagos");

        assertSame(report, returned, "printCityPopulationReport should return the same report instance");

        String output = outContent.toString();
        assertTrue(output.contains("CITY POPULATION REPORT"));
        assertTrue(output.contains("City: Lagos"));
        assertTrue(output.contains("Total Population: 8,000,000"));
        assertTrue(output.contains("Population in City: 8,000,000 (100.00%)"));
        assertTrue(output.contains("Population Not in City: 0 (0.00%)"));
    }

    /**
     * USE CASE 31 - printCityPopulationReport
     * When underlying getCityPopulationReport returns null,
     * it should print an error and return null.
     */
    @Test
    void testPrintCityPopulationReport_NoData() {
        doReturn(null).when(spyService).getCityPopulationReport("UnknownCity");

        PopulationReportPojo returned = spyService.printCityPopulationReport("UnknownCity");

        assertNull(returned, "Should return null when no city report is available");
        String error = errContent.toString();
        assertTrue(error.contains("Error: No population data found for city: UnknownCity"));
    }

    /**
     * USE CASE 18 - getAllCapitalCitiesInContinentByPopulation
     * Test retrieving all capital cities in a continent ordered by population.
     */
    @Test
    void testGetAllCapitalCitiesInContinentByPopulation() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("ID")).thenReturn(1, 2);
        when(mockResultSet.getString("CityName")).thenReturn("Tokyo", "Seoul");
        when(mockResultSet.getString("CountryCode")).thenReturn("JPN", "KOR");
        when(mockResultSet.getString("District")).thenReturn("Tokyo", "Seoul");
        when(mockResultSet.getInt("Population")).thenReturn(7980000, 9981619);

        List<City> capitals = cityReportService.getAllCapitalCitiesInContinentByPopulation("Asia");

        assertNotNull(capitals);
        assertEquals(2, capitals.size());
        assertEquals("Tokyo", capitals.get(0).getName());
        assertEquals("Seoul", capitals.get(1).getName());
        verify(mockPreparedStatement).setString(1, "Asia");
    }

    /**
     * USE CASE 18 - getAllCapitalCitiesInContinentByPopulation
     * Test with null continent parameter.
     */
    @Test
    void testGetAllCapitalCitiesInContinentByPopulation_NullContinent() {
        List<City> capitals = cityReportService.getAllCapitalCitiesInContinentByPopulation(null);

        assertNotNull(capitals);
        assertTrue(capitals.isEmpty());
        String error = errContent.toString();
        assertTrue(error.contains("Error: Continent parameter cannot be null or empty"));
    }

    /**
     * USE CASE 18 - printAllCapitalCitiesInContinentByPopulation
     * Test printing capital cities in a continent.
     */
    @Test
    void testPrintAllCapitalCitiesInContinentByPopulation() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("ID")).thenReturn(1);
        when(mockResultSet.getString("CityName")).thenReturn("Tokyo");
        when(mockResultSet.getString("CountryCode")).thenReturn("JPN");
        when(mockResultSet.getString("District")).thenReturn("Tokyo");
        when(mockResultSet.getInt("Population")).thenReturn(7980000);

        cityReportService.printAllCapitalCitiesInContinentByPopulation("Asia");

        String output = outContent.toString();
        assertTrue(output.contains("Report: All Capital Cities in Asia by Population"));
        assertTrue(output.contains("Total capitals found: 1"));
    }

    /**
     * USE CASE 19 - getAllCapitalCitiesInRegionByPopulation
     * Test retrieving all capital cities in a region ordered by population.
     */
    @Test
    void testGetAllCapitalCitiesInRegionByPopulation() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("ID")).thenReturn(1, 2);
        when(mockResultSet.getString("CityName")).thenReturn("London", "Paris");
        when(mockResultSet.getString("CountryCode")).thenReturn("GBR", "FRA");
        when(mockResultSet.getString("District")).thenReturn("England", "Île-de-France");
        when(mockResultSet.getInt("Population")).thenReturn(7285000, 2125246);

        List<City> capitals = cityReportService.getAllCapitalCitiesInRegionByPopulation("Western Europe");

        assertNotNull(capitals);
        assertEquals(2, capitals.size());
        assertEquals("London", capitals.get(0).getName());
        assertEquals("Paris", capitals.get(1).getName());
        verify(mockPreparedStatement).setString(1, "Western Europe");
    }

    /**
     * USE CASE 19 - getAllCapitalCitiesInRegionByPopulation
     * Test with empty region parameter.
     */
    @Test
    void testGetAllCapitalCitiesInRegionByPopulation_EmptyRegion() {
        List<City> capitals = cityReportService.getAllCapitalCitiesInRegionByPopulation("");

        assertNotNull(capitals);
        assertTrue(capitals.isEmpty());
        String error = errContent.toString();
        assertTrue(error.contains("Error: Region parameter cannot be null or empty"));
    }

    /**
     * USE CASE 19 - printAllCapitalCitiesInRegionByPopulation
     * Test printing capital cities in a region.
     */
    @Test
    void testPrintAllCapitalCitiesInRegionByPopulation() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("ID")).thenReturn(1);
        when(mockResultSet.getString("CityName")).thenReturn("London");
        when(mockResultSet.getString("CountryCode")).thenReturn("GBR");
        when(mockResultSet.getString("District")).thenReturn("England");
        when(mockResultSet.getInt("Population")).thenReturn(7285000);

        cityReportService.printAllCapitalCitiesInRegionByPopulation("Western Europe");

        String output = outContent.toString();
        assertTrue(output.contains("Report: All Capital Cities in Western Europe by Population"));
        assertTrue(output.contains("Total capitals found: 1"));
    }

}