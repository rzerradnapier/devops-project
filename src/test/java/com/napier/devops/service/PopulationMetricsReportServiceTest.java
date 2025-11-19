package com.napier.devops.service;

import com.napier.devops.PopulationMetrics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the PopulationMetricsReportService class using mocked database connections.
 */
public class PopulationMetricsReportServiceTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @Mock
    private Statement mockStatement;

    private PopulationMetricsReportService populationMetricsService;
    private PopulationMetricsReportService spyService;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        populationMetricsService = new PopulationMetricsReportService(mockConnection);

        // Setup mock to return empty list

        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(any(String.class))).thenReturn(mockResultSet);

        // Create a spy to test print methods
        spyService = Mockito.spy(populationMetricsService);
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }



    /**
     * Test getContinentPopulationReport with mock data.
     * USE CASE 23 Test
     */
    @Test
    void testGetContinentPopulationReport() throws SQLException {
        // Setup mock ResultSet to return all population metrics (total, city, non-city, and percentages)
        // for two continents.
        when(mockResultSet.next()).thenReturn(true, true, false);

        when(mockResultSet.getString("Continent")).thenReturn("Asia", "Europe");
        when(mockResultSet.getLong("TotalPopulation")).thenReturn(6000000000L, 750000000L);
        when(mockResultSet.getLong("CityPopulation")).thenReturn(3000000000L, 500000000L);
        when(mockResultSet.getLong("NonCityPopulation")).thenReturn(3000000000L, 250000000L);
        when(mockResultSet.getDouble("CityPopulationPercentage")).thenReturn(50.0, 66.7);
        when(mockResultSet.getDouble("NonCityPopulationPercentage")).thenReturn(50.0, 33.3);

        // Call the method
        List<PopulationMetrics> continents = populationMetricsService.getContinentPopulationReport();

        // Verify results
        assertNotNull(continents);
        assertEquals(2, continents.size());

        // Verify first continent
        PopulationMetrics firstContinent = continents.get(0);
        assertEquals("Asia", firstContinent.getNameOfArea());
        assertEquals(6000000000L, firstContinent.getTotalPopulation());
        assertEquals(3000000000L, firstContinent.getNonCityPopulation());
        assertEquals(3000000000L, firstContinent.getCityPopulation());
        assertEquals(50.0, firstContinent.getNonCityPopulationPercentage(), 0.001);
        assertEquals(50.0, firstContinent.getCityPopulationPercentage(), 0.001);
        assertEquals(PopulationMetrics.ReportType.CONTINENT, firstContinent.getReportType());

        // Verify second continent
        PopulationMetrics secondContinent = continents.get(1);
        assertEquals("Europe", secondContinent.getNameOfArea());
        assertEquals(750000000L, secondContinent.getTotalPopulation());
        assertEquals(500000000L, secondContinent.getCityPopulation());
        assertEquals(250000000L, secondContinent.getNonCityPopulation());
        assertEquals(66.7, secondContinent.getCityPopulationPercentage(), 0.001);
        assertEquals(33.3, secondContinent.getNonCityPopulationPercentage(), 0.001);
        assertEquals(PopulationMetrics.ReportType.CONTINENT, secondContinent.getReportType());

        verify(mockStatement).executeQuery(anyString());
    }

    /**
     * Test how the getContinentPopulationReport method handles a database error (SQLException).
     * USE CASE 23 Test
     */
    @Test
    void testGetContinentPopulationReport_SQLException() throws SQLException {
        when(mockStatement.executeQuery(anyString())).thenThrow(new SQLException("Database Error"));

        List<PopulationMetrics> continents = populationMetricsService.getContinentPopulationReport();

        assertNotNull(continents);
        assertTrue(continents.isEmpty());
        assertTrue(outContent.toString().contains("Query failed"));
    }

    /**
     * Test printing of Continent Population Report.
     * USE CASE 23 Test
     */
    @Test
    void testPrintContinentPopulationReport_ValidList() {
        PopulationMetrics populationMetrics = new PopulationMetrics();
        populationMetrics.setNameOfArea("Asia");
        populationMetrics.setTotalPopulation(100L);
        populationMetrics.setReportType(PopulationMetrics.ReportType.CONTINENT);

        // Mock the internal getter call by using Spy
        doReturn(List.of(populationMetrics)).when(spyService).getContinentPopulationReport();

        spyService.printContinentPopulationReport();

        String errorOutput = outContent.toString();
        assertTrue(errorOutput.contains("USE CASE 23"));
        assertTrue(errorOutput.contains("Asia"));
        assertTrue(errorOutput.contains("100"));
    }

    /**
     * Test getRegionPopulationReport with mock data.
     * USE CASE 24 Test
     */
    @Test
    void testGetRegionPopulationReport() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, false);

        when(mockResultSet.getString("Region")).thenReturn("Caribbean");
        when(mockResultSet.getLong("TotalPopulation")).thenReturn(20000000L);
        when(mockResultSet.getLong("CityPopulation")).thenReturn(10000000L);
        when(mockResultSet.getLong("NonCityPopulation")).thenReturn(10000000L);
        when(mockResultSet.getDouble("CityPopulationPercentage")).thenReturn(50.0);
        when(mockResultSet.getDouble("NonCityPopulationPercentage")).thenReturn(50.0);

        List<PopulationMetrics> regions = populationMetricsService.getRegionPopulationReport();
        // Verify results
        assertNotNull(regions);
        assertEquals(1, regions.size());

        PopulationMetrics region = regions.get(0);
        assertEquals("Caribbean", region.getNameOfArea());
        assertEquals(PopulationMetrics.ReportType.REGION, region.getReportType());

        // Verify the numerical data
        assertEquals(20000000L, region.getTotalPopulation());
        assertEquals(10000000L, region.getCityPopulation());
        assertEquals(10000000L, region.getNonCityPopulation());

        // Using delta for double comparison
        assertEquals(50.0, region.getCityPopulationPercentage(), 0.001);
        assertEquals(50.0, region.getNonCityPopulationPercentage(), 0.001);

        // Verify method execution
        verify(mockStatement).executeQuery(anyString());
    }

    /**
     * Test printing of Region Population Report.
     * USE CASE 24 Test
     */
    @Test
    void testPrintRegionPopulationReport_ValidList() {
        PopulationMetrics populationMetrics = new PopulationMetrics();
        populationMetrics.setNameOfArea("Caribbean");
        populationMetrics.setTotalPopulation(700L);
        populationMetrics.setReportType(PopulationMetrics.ReportType.REGION);

        doReturn(List.of(populationMetrics)).when(spyService).getRegionPopulationReport();

        spyService.printRegionPopulationReport();

        String errorOutput = outContent.toString();
        assertTrue(errorOutput.contains("USE CASE: 24"));
        assertTrue(errorOutput.contains("Caribbean"));
    }

    /**
     * Test getCountryPopulationReport with mock data.
     * USE CASE 25 Test
     */
    @Test
    void testGetCountryPopulationReport() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, false);

        when(mockResultSet.getString("CountryName")).thenReturn("Belize");
        when(mockResultSet.getLong("TotalPopulation")).thenReturn(500000L);
        when(mockResultSet.getLong("CityPopulation")).thenReturn(300000L);
        when(mockResultSet.getLong("NonCityPopulation")).thenReturn(200000L);
        when(mockResultSet.getDouble("CityPopulationPercentage")).thenReturn(60.0);
        when(mockResultSet.getDouble("NonCityPopulationPercentage")).thenReturn(40.0);

        List<PopulationMetrics> countries = populationMetricsService.getCountryPopulationReport();

        assertNotNull(countries);
        assertEquals(1, countries.size());

        PopulationMetrics country = countries.get(0);
        assertEquals("Belize", country.getNameOfArea());
        assertEquals(PopulationMetrics.ReportType.COUNTRY, country.getReportType());
        // Verify population counts
        assertEquals(500000L, country.getTotalPopulation());
        assertEquals(300000L, country.getCityPopulation());
        assertEquals(200000L, country.getNonCityPopulation());

        // Verify the calculated percentages
        assertEquals(60.0, country.getCityPopulationPercentage(), 0.001);
        assertEquals(40.0, country.getNonCityPopulationPercentage(), 0.001);

        // Verify execution
        verify(mockStatement).executeQuery(anyString());
    }

    /**
     * Test printing of Country Population Report.
     * USE CASE 25 Test
     */
    @Test
    void testPrintCountryPopulationReport_ValidList() {
        PopulationMetrics populationMetrics = new PopulationMetrics();
        populationMetrics.setNameOfArea("France");
        populationMetrics.setTotalPopulation(80000000L);

        doReturn(List.of(populationMetrics)).when(spyService).getCountryPopulationReport();

        spyService.printCountryPopulationReport();

        String errorOutput = outContent.toString();
        assertTrue(errorOutput.contains("USE CASE 25"));
        assertTrue(errorOutput.contains("France"));
    }

    /**
     * Test getWorldPopulationReport with mock data.
     * USE CASE 26 Test
     */
    @Test
    void testGetWorldPopulationReport() throws SQLException {

        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getLong("WorldPopulation")).thenReturn(8900000000L);

        long pop = populationMetricsService.getWorldPopulationReport();

        assertEquals(8900000000L, pop);
        verify(mockStatement).executeQuery(anyString());
    }

    /**
     * Test printing of World Population Report.
     * USE CASE 26 Test
     */
    @Test
    void testPrintWorldPopulationReport() {
        doReturn(8000000000L).when(spyService).getWorldPopulationReport();

        spyService.printWorldPopulationReport();

        String errorOutput = outContent.toString();
        assertTrue(errorOutput.contains("USE CASE 26"));
        assertTrue(errorOutput.contains("8000000000"));
    }

    /**
     * Test failure to retrieve world population.
     * USE CASE 26 Test
     */
    @Test
    void testPrintWorldPopulationReport_Error() {
        doReturn(-1L).when(spyService).getWorldPopulationReport();

        spyService.printWorldPopulationReport();

        String errorOutput = outContent.toString();
        assertTrue(errorOutput.contains("Error: Could not retrieve world population"));
    }

    /**
     * Test getPopulationContinentReport with PreparedStatement.
     * USE CASE 27 Test
     */
    @Test
    void testGetPopulationContinentReport() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("Name")).thenReturn("Antarctica");
        when(mockResultSet.getLong("TotalPopulation")).thenReturn(1500L);
        when(mockResultSet.getLong("CityPopulation")).thenReturn(0L);
        when(mockResultSet.getLong("NonCityPopulation")).thenReturn(1500L);
        when(mockResultSet.getDouble("CityPercent")).thenReturn(0.0);
        when(mockResultSet.getDouble("NonCityPercent")).thenReturn(100.0);

        PopulationMetrics continent = populationMetricsService.getPopulationContinentReport("Antarctica");

        assertEquals("Antarctica", continent.getNameOfArea());
        assertEquals(1500L, continent.getTotalPopulation());
        assertEquals(0L, continent.getCityPopulation());
        assertEquals(1500L, continent.getNonCityPopulation());
        assertEquals(0.0, continent.getCityPopulationPercentage(), 0.001);
        assertEquals(100.0, continent.getNonCityPopulationPercentage(), 0.001);
        assertNotNull(continent);
        assertEquals(PopulationMetrics.ReportType.CONTINENT, continent.getReportType());

        verify(mockPreparedStatement).setString(1, "Antarctica");
    }

    /**
     * Test getPopulationContinentReport when data is not found.
     * USE CASE 27 Test
     */
    @Test
    void testGetPopulationContinentReport_NotFound() throws SQLException {
        when(mockResultSet.next()).thenReturn(false);

        PopulationMetrics continent = populationMetricsService.getPopulationContinentReport("Atlantis");

        assertNull(continent);
    }

    /**
     * Test printing of Single Continent Population Report.
     * USE CASE 27 Test
     */
    @Test
    void testPrintPopulationContinentReport_Success() {
        PopulationMetrics pop = new PopulationMetrics();
        pop.setNameOfArea("Asia");
        pop.setTotalPopulation(5500L);

        doReturn(pop).when(spyService).getPopulationContinentReport("Asia");

        spyService.printPopulationContinentReport("Asia");

        String errorOutput = outContent.toString();
        assertTrue(errorOutput.contains("USE CASE 27"));
        assertTrue(errorOutput.contains("Asia"));
        assertTrue(errorOutput.contains("5500"));
    }

    /**
     * Test printing of Single Continent Population Report when null.
     * USE CASE 27 Test
     */
    @Test
    void testPrintPopulationContinentReport_Null() {
        doReturn(null).when(spyService).getPopulationContinentReport("Jupiter");

        spyService.printPopulationContinentReport("Jupiter");

        String errorOutput = outContent.toString();
        assertTrue(errorOutput.contains("Error: Could not retrieve population"));
    }
}