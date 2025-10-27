package com.napier.devops.service;

import com.napier.devops.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
    }


}